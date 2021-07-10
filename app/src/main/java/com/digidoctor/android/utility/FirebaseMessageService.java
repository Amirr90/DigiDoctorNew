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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.digidoctor.android.R;
import com.digidoctor.android.newVideoCall.IncomingCallActivity;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import timber.log.Timber;

public class FirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessageService";

    String CHANNEL_ID = "com.digidoctor.android";

    private NotificationManager mManager;

    SharedPreferences prefs;

    Bitmap bitmap;

    Context context;

    int directionId;

    @Override
    public void onNewToken(@NotNull String token) {
        Timber.tag(TAG).v("Refreshed token: %s", token);
        AppUtils.updateTokenToDatabase(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /*Timber.d("FCM Message Id: %s", remoteMessage.getMessageId());
        Timber.d("FCM Notification Message: " + remoteMessage.getData() + "...." + remoteMessage.getFrom());
*/
        context = this;

        Timber.d("onMessageReceived: %s", remoteMessage.getData());
        try {
            String callId = remoteMessage.getData().get("roomName");
            String image = remoteMessage.getData().get("profilePhotoPath");
            String doctorName = remoteMessage.getData().get("doctorName");
            setupCallNotification(callId, image, doctorName);
        } catch (Exception e) {
            e.printStackTrace();
            Timber.d("onMessageReceived Error: %s", e.getLocalizedMessage());
        }


    /*    if (remoteMessage.getData() != null) {
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
                String appointmentId = "";
                String deepLink = "";


                message = json.getString("message");
                typeMain = json.getInt("type");
                profilePhotoPath = json.getString("profilePhotoPath");
                doctorName = json.getString("doctorName");
                titleToShow = json.getString("title");


                prefs = getSharedPreferences(utils.PREFS_MAIN_FILE, Context.MODE_PRIVATE);

                if (typeMain == 1) {
                    roomName = json.getString("roomName");
                    twillioAccessToken = json.getString("twillioAccessToken");

                    Log.d(TAG, "onMessageReceived: roomName : => " + roomName);
                    Log.d(TAG, "onMessageReceived: twillioAccessToken => " + twillioAccessToken);

                }

                if (typeMain == 2) {
                    if (remoteMessage.getNotification() != null)
                        appointmentId = remoteMessage.getNotification().getClickAction();
                    else
                        appointmentId = json.getString("appointmentId");

                    deepLink = json.getString("deepLink");
                }

                createNotification(titleToShow, message, roomName, typeMain, twillioAccessToken, profilePhotoPath, doctorName, appointmentId, deepLink);


            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onMessageReceived: " + e.getLocalizedMessage());
            }
        }
*/


    }

    private void setupCallNotification(String callId, String image, String doctorName) {
        Intent intent = new Intent(this, IncomingCallActivity.class);
        intent.putExtra(AppUtils.CALL_ID, callId);
        intent.putExtra(AppUtils.ICON, image);
        intent.putExtra(AppUtils.DOCTOR_NAME, doctorName);
        /*myIntent.setAction("myReceiver");
        myIntent.putExtra("roomName", roomName);
        myIntent.putExtra("accessToken", twillioAccessToken);
        myIntent.putExtra("message", msg);
        myIntent.putExtra("title", title);
        myIntent.putExtra("profilePhotoPath", profilePhotoPath);
        myIntent.putExtra("doctorName", doctorName);*/

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void createNotification(String title, String msg, String roomName, int type, String twillioAccessToken, String profilePhotoPath, String doctorName, String appointmentId, String deepLink) {

        if (type == 1) {

            Intent myIntent = new Intent(this, WakefulBroadcasterReceiver.class);
            myIntent.setAction("myReceiver");
            myIntent.putExtra("roomName", roomName);
            myIntent.putExtra("accessToken", twillioAccessToken);
            myIntent.putExtra("message", msg);
            myIntent.putExtra("title", title);
            myIntent.putExtra("profilePhotoPath", profilePhotoPath);
            myIntent.putExtra("doctorName", doctorName);

            // sendBroadcast(myIntent);

            NotificationVideoCall videoCall = new NotificationVideoCall(msg, title, doctorName, profilePhotoPath, roomName);
            videoCall.startVideo();


        } else {
            Log.d(TAG, "createNotification: in Type ");

            Bundle bundle = new Bundle();
            if (type == 4) {
                directionId = R.id.homeIsolationRequestDetailFragment;
                bundle.putString("id", appointmentId);
            } else {
                directionId = R.id.appointmentDetailFragment;
                bundle.putString("appointmentId", appointmentId);
            }
            PendingIntent pendingIntent = new NavDeepLinkBuilder(PatientDashboard.getInstance())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(directionId)
                    .setArguments(bundle)
                    .createPendingIntent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d(TAG, "createNotification: ");
                NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                        title, NotificationManager.IMPORTANCE_HIGH);
                androidChannel.enableLights(true);
                androidChannel.enableVibration(true);
                androidChannel.setLightColor(Color.GREEN);

                androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                getManager().createNotificationChannel(androidChannel);

                NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                        .setAutoCancel(true) //remove notification after click
                        .setContentIntent(pendingIntent);

                if (bitmap != null) {
                    notification.setLargeIcon(bitmap);
                }

                //int timestamp = 1000;
                int timestamp = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

                getManager().notify(timestamp, notification.build());

                playNotificationSound();

            } else {
                Log.d(TAG, "createNotification: else");
                try {

                    playNotificationSound();
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(msg)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                            .setContentIntent(pendingIntent)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setLights(0xFF760193, 300, 1000)
                            .setVibrate(new long[]{200, 400});

                    if (bitmap != null) {
                        notificationBuilder.setLargeIcon(bitmap);
                    }
                    int timestamp = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(timestamp, notificationBuilder.build());
                } catch (SecurityException se) {
                    se.printStackTrace();
                    Log.d(TAG, "createNotification: " + se.getLocalizedMessage());
                }
            }


        }

    }


    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

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
