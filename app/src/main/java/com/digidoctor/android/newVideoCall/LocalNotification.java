package com.digidoctor.android.newVideoCall;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;

import com.digidoctor.android.R;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.NotificationVideoCall;
import com.digidoctor.android.view.activity.PatientDashboard;

import static com.digidoctor.android.utility.NotificationVideoCall.stopSound;

public class LocalNotification {
    private static final String TAG = "LocalNotification";
    private static final String CHANNEL_ID = "localCallNotification";
    private static final int NOTIFICATION_CALL_RINGING = 223322;
    private static final int NOTIFICATION_CALL_ON_PROGRESS = 332211;
    public static final int NOTIFICATION_MESSED_CALL = 3652;
    private static NotificationCompat.Builder callOnProgressBuilder;
    private static NotificationManager mManager;

    Activity activity;
    static String roomName, doctorName, icon;

    public LocalNotification(Activity activity, String roomName, String doctorName, String icon) {
        this.activity = activity;
        this.roomName = roomName;
        this.doctorName = doctorName;
        this.icon = icon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID,
                    "title", NotificationManager.IMPORTANCE_HIGH);
            androidChannel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PUBLIC);
            getManager(App.context).createNotificationChannel(androidChannel);
        }


    }

    public static void showCallRingingNotification(String text, String doctorName, Activity activity) {

        NotificationCompat.Builder callOnProgressBuilder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setOnlyAlertOnce(true)
                // .setContentIntent(getVideoCallScreenIntent(activity))
                .setContentTitle(text)
                .setContentText(doctorName)
                .addAction(callAction(NotificationVideoCall.ACCEPT, 1, activity))
                .addAction(callAction(NotificationVideoCall.REJECT, 2, activity))
                //.setOngoing(true)
                ;
        getManager(activity).notify(NOTIFICATION_CALL_RINGING, callOnProgressBuilder.build());


    }

    public static NotificationManager getManager(Context context) {
        if (mManager == null) {
            mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public static void updateCallOnProgressNotification(String textTimer, Activity activity) {
        if (null != callOnProgressBuilder) {
            callOnProgressBuilder.setContentText(textTimer);
            getManager(activity).notify(NOTIFICATION_CALL_ON_PROGRESS, callOnProgressBuilder.build());
        }
    }

    public static void showCallOnProgressNotification(String text, String doctorName, Activity context) {
        callOnProgressBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setOnlyAlertOnce(true)
                .setContentIntent(getVideoCallScreenIntent(context))
                .setContentTitle(text)
                .setContentText(doctorName)
                .setOngoing(true)
        ;

        getManager(context).notify(NOTIFICATION_CALL_ON_PROGRESS, callOnProgressBuilder.build());
    }


    private static PendingIntent getVideoCallScreenIntent(Context activity) {
        Intent intent = new Intent(App.context, IncomingCallActivity.class);
        intent.putExtra(AppUtils.CALL_ID, roomName);
        intent.putExtra(AppUtils.ICON, intent);
        intent.putExtra(AppUtils.DOCTOR_NAME, doctorName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(activity, 0,
                intent, 0);
    }


    public static NotificationCompat.Action callAction(String action, int i, Activity activity) {
        Intent snoozeIntent = new Intent(activity, LocalBroadCast.class);
        snoozeIntent.putExtra("com.jc_code_ACTION", i == 1 ? AppUtils.CALL_RINGING : AppUtils.CALL_REJECT);
        snoozeIntent.putExtra(AppUtils.CALL_ID, roomName);
        snoozeIntent.putExtra(AppUtils.ICON, icon);
        snoozeIntent.putExtra(AppUtils.DOCTOR_NAME, doctorName);
        PendingIntent pendingintent = PendingIntent.getBroadcast(activity, i, snoozeIntent, i == 2 ? PendingIntent.FLAG_CANCEL_CURRENT : PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return new NotificationCompat.Action.Builder(
                    R.drawable.ic_launcher_background,
                    HtmlCompat.fromHtml("<font color=\"" + ContextCompat.getColor(activity, i == 1 ? R.color.green : R.color.red) + "\">" + action + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY),
                    pendingintent)
                    .build();
        } else return null;
    }

    public static void cancelCallRingingNotification(Activity activity) {
        getManager(activity).cancel(null, NOTIFICATION_CALL_RINGING);
    }

    public static void cancelCallRingingNotification(Context activity) {
        stopSound();
        getManager(activity).cancel(null, NOTIFICATION_CALL_RINGING);
    }

    public static void hideCallOnProgressNotification(Context context) {
        getManager(context).cancel(null, NOTIFICATION_CALL_ON_PROGRESS);
    }

    public static void showMissedCallNotification(Activity activity) {
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
        getManager(activity).notify(NOTIFICATION_MESSED_CALL, builder.build());
    }

    public static void hideMissedCallNotification(Activity activity) {
        getManager(activity).cancel(null, NOTIFICATION_MESSED_CALL);
    }
}
