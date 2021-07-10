package com.digidoctor.android.utility;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.digidoctor.android.R;
import com.digidoctor.android.broadcast.VideoCallActionBroadcast;
import com.digidoctor.android.jitsiVideoCall.VideoCallActivity;
import com.digidoctor.android.view.activity.PatientDashboard;

public class NotificationVideoCall {
    private static final String TAG = "NotificationVideoCall";
    private static final String CHANNEL_ID = "001100";
    public static final String ACCEPT = "Accept";
    public static final String REJECT = "Reject";
    private static final int NOTIFICATION_MESSED_CALL = 102;
    private static final int NOTIFICATION_CALL_ON_PROGRESS = 103;
    public static final String DISMISS = "Dismiss";
    private static NotificationManager mManager;
    private static NotificationCompat.Builder builder;
    private static NotificationCompat.Builder callOnProgressBuilder;
    public static int SECOND = 1000;
    static Ringtone r;
    static Thread thread;

    static String msg;
    String title;
    static String doctorName;
    String profilePhotoPath;
    static String roomName;

    static MediaPlayer player;


    public NotificationVideoCall(String msg, String title, String doctorName, String profilePhotoPath, String roomName) {
        this.msg = msg;
        this.title = title;
        this.doctorName = doctorName;
        this.profilePhotoPath = profilePhotoPath;
        this.roomName = roomName;
    }


    public static void updateCallOnProgressNotification(String textTimer, Activity activity) {
        if (null != callOnProgressBuilder) {
            callOnProgressBuilder.setContentText(textTimer);
            getManager(activity).notify(NOTIFICATION_CALL_ON_PROGRESS, callOnProgressBuilder.build());
        }
    }

    public void startVideo() {
        showFullScreenNotification(PatientDashboard.getInstance());
    }


    public void showFullScreenNotification(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    "title", NotificationManager.IMPORTANCE_HIGH);
            androidChannel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PUBLIC);
            getManager(activity).createNotificationChannel(androidChannel);
        }


        builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(msg)
                .setContentText(doctorName + " is calling .....")
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(callAction(NotificationVideoCall.ACCEPT, 1, activity))
                .addAction(callAction(NotificationVideoCall.REJECT, 2, activity))
                .setAutoCancel(true)
                .setOngoing(true)
        //.setContentIntent(getVideoCallScreenIntent(activity))
        ;
        getManager(activity).notify(101, builder.build());
        playNotificationSound();
        updateMissedCallAlert(activity);


    }

    private static PendingIntent getVideoCallScreenIntent(Context activity) {
        Intent fullScreenIntent = new Intent(activity, VideoCallActivity.class);
        fullScreenIntent.putExtra("roomName", roomName);
        return PendingIntent.getActivity(activity, 0,
                fullScreenIntent, 0);
    }

    public static void showCallOnProgressNotification(String text, Context context) {
        hideNotification(context);
        if (null != builder) {
            callOnProgressBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setOnlyAlertOnce(true)
                    .setContentIntent(getVideoCallScreenIntent(context))
                    .setContentTitle(text)
                    .setContentText("with " + doctorName)
                    .setOngoing(true)
                    .addAction(callAction(NotificationVideoCall.DISMISS, 1, context));
            getManager(context).notify(NOTIFICATION_CALL_ON_PROGRESS, callOnProgressBuilder.build());
        }
        stopSound();
        cancelShowMissedCallNotification();
        /*   startForNotification(context);*/
    }

    public static void hideNotification(Context context) {
        getManager(context).cancel(null, 101);
        stopSound();
    }

    public static void showMissedCallNotification(Activity activity) {
        playAlertSound();
        Intent fullScreenIntent = new Intent(activity, PatientDashboard.class);
        fullScreenIntent.putExtra("action", "callHistory");
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(activity, 0,
                fullScreenIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    "title", NotificationManager.IMPORTANCE_HIGH);
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager(activity).createNotificationChannel(androidChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Missed Video Call")
                .setContentText(doctorName)
                .setContentIntent(fullScreenPendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        getManager(activity).notify(NotificationVideoCall.NOTIFICATION_MESSED_CALL, builder.build());
        AppUtils.updateTodatabase(AppUtils.CALL_MISSED, roomName);

    }

    public static NotificationManager getManager(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public static NotificationCompat.Action callAction(String action, int i, Context activity) {


        Intent snoozeIntent = new Intent(App.context, VideoCallActionBroadcast.class);
        snoozeIntent.putExtra("com.jc_code_ACTION", action);
        snoozeIntent.putExtra("roomName", roomName);
        PendingIntent pendingintent = PendingIntent.getBroadcast(App.context, i, snoozeIntent, i == 2 ? PendingIntent.FLAG_CANCEL_CURRENT : PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_background,
                    HtmlCompat.fromHtml("<font color=\"" + ContextCompat.getColor(activity, i == 1 ? R.color.green : R.color.red) + "\">" + action + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                    pendingintent)
                    .build();
        } else return null;

    }

    public static void playNotificationSound() {
        try {
           /* Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            r = RingtoneManager.getRingtone(App.context, notificationSoundUri);
            r.play();*/


            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            player = MediaPlayer.create(App.context, notification);
            player.setLooping(true);
            player.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopSound() {
        if (null != player) {
            player.stop();
        }
    }

    public static void updateMissedCallAlert(Activity activity) {
        SECOND = 1000;
        thread = new Thread(() -> {
            SystemClock.sleep(45 * SECOND);
            if (Thread.currentThread().isInterrupted())
                return;
            hideNotification(activity);
            showMissedCallNotification(activity);
        });
        thread.start();
    }

    public static void cancelShowMissedCallNotification() {
        thread.interrupt();
    }

    public static void playAlertSound() {
        try {
            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(App.context, notificationSoundUri);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideCallOnProgressNotification(Context context) {
        getManager(context).cancel(null, NOTIFICATION_CALL_ON_PROGRESS);
    }
}
