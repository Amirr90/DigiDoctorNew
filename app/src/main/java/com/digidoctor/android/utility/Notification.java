package com.digidoctor.android.utility;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.digidoctor.android.R;

public class Notification {

    public static void createLocalNotification(String title, String description) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(App.context)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(title)
                        .setContentText(description);

 /*       Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);*/

        // Add as notification
        NotificationManager manager = (NotificationManager) App.context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
