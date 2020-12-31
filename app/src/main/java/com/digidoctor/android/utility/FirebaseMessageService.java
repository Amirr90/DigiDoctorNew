package com.digidoctor.android.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.digidoctor.android.R;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

import static com.digidoctor.android.utility.utils.TOKEN;

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    String CHANNEL_ID = "com.digidoctor.android";

    private NotificationManager mManager;

    SharedPreferences prefs;

    Bitmap bitmap;

    Context context;

    @Override
    public void onNewToken(String token) {
        Log.v(TAG, "Refreshed token: " + token);

        //utils.setString(AppSettings.fcmToken, token);
        //sendRegistrationToServer(token);
       // utils.setString(TOKEN, token, activity);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " + remoteMessage.getData() + "...." + remoteMessage.getFrom());

        context = this;

        if (remoteMessage.getData() != null) {

            Map<String, String> params = remoteMessage.getData();
            JSONObject json = new JSONObject(params);

            try {
                String message;
                int typeMain;
                String twillioAccessToken = "";
                String roomName = "";
                String profilePhotoPath = "";
                String doctorName = "";
                String titleToShow = "";

                message = json.getString("message");
                typeMain = json.getInt("type");
                profilePhotoPath = json.getString("profilePhotoPath");
                doctorName = json.getString("doctorName");
                titleToShow = json.getString("title");

                prefs = getSharedPreferences(utils.PREFS_MAIN_FILE, Context.MODE_PRIVATE);

                if (typeMain == 1) {

                    roomName = json.getString("roomName");
//                    if (AppSettings.getString(AppSettings.serviceProviderType).equals("6")) {
                 /*   if (prefs.getString(AppSettings.serviceProviderType, "").equals("6")) {
                        twillioAccessToken = json.getString("twillioAccessToken");
                    } else {
                        twillioAccessToken = json.getString("doctorTwilioAccessToken");
                    }*/

                    twillioAccessToken = json.getString("twillioAccessToken");
                }

                createNotification(titleToShow, message, roomName, typeMain, twillioAccessToken, profilePhotoPath, doctorName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createNotification(String title, String msg, String roomName, int type, String twillioAccessToken, String profilePhotoPath, String doctorName) {

        if (type == 1) {

            Intent myIntent = new Intent(this, WakefulBroadcasterReceiver.class);
            myIntent.setAction("myReceiver");
            myIntent.putExtra("roomName", roomName);
            myIntent.putExtra("accessToken", twillioAccessToken);
            myIntent.putExtra("message", msg);
            myIntent.putExtra("title", title);
            myIntent.putExtra("profilePhotoPath", profilePhotoPath);
            myIntent.putExtra("doctorName", doctorName);

            sendBroadcast(myIntent);

        } else {
            final Intent intent;

            intent = new Intent(this, PatientDashboard.class).putExtra("type", String.valueOf(type));

           /* if (prefs.getString(AppSettings.serviceProviderType, "").equals("6")) {
                intent = new Intent(this, PatientDashboard.class).putExtra("type", String.valueOf(type));
            } else {
                intent = new Intent(this, DoctorsDashboardActivity.class).putExtra("type", String.valueOf(type));
            }*/

            PendingIntent contentIntent = PendingIntent.getActivity(this, 99, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Glide.with(this)
                    .asBitmap()
                    .load(profilePhotoPath.trim())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            //Save the bitmap to your global bitmap
                            bitmap = resource;

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                                        title, NotificationManager.IMPORTANCE_HIGH);
                                androidChannel.enableLights(true);
                                androidChannel.enableVibration(true);
                                androidChannel.setLightColor(Color.GREEN);

                                androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                                getManager().createNotificationChannel(androidChannel);

                                NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                        //   .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setContentTitle(title)
                                        .setContentText(msg)
                                        //  .setTicker(title)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                                        .setAutoCancel(true) //remove notification after click
                                        .setContentIntent(contentIntent);

                                if (bitmap != null) {
                                    notification.setLargeIcon(bitmap);
                                }

                                //int timestamp = 1000;
                                int timestamp = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                                getManager().notify(timestamp, notification.build());

                                playNotificationSound();

                            } else {
                                try {

                                    playNotificationSound();

                                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
//                                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                                            //.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle(title)
                                            // .setTicker(title)
                                            .setContentText(msg)
                                            .setAutoCancel(true)
                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                                            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                                            .setContentIntent(contentIntent)
                                            .setDefaults(Notification.DEFAULT_ALL)
                                            .setLights(0xFF760193, 300, 1000)
                                            .setVibrate(new long[]{200, 400});

                                    if (bitmap != null) {
                                        notificationBuilder.setLargeIcon(bitmap);
                                    }

                                    int timestamp = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.notify(timestamp/* ID of notification */, notificationBuilder.build());

                                } catch (SecurityException se) {
                                    se.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }

    }


    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    // Playing notification sound
    public void playNotificationSound() {

        try {
            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(this, notificationSoundUri);



            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
