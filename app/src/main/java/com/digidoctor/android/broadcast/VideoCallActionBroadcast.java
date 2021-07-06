package com.digidoctor.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.digidoctor.android.utility.NotificationVideoCall;
import com.digidoctor.android.videoCall.VideoCallActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.digidoctor.android.utility.NotificationVideoCall.cancelShowMissedCallNotification;
import static com.digidoctor.android.utility.NotificationVideoCall.hideNotification;

public class VideoCallActionBroadcast extends BroadcastReceiver {
    private static final String TAG = "VideoCallActionBroadcas";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getStringExtra("com.jc_code_ACTION"));
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(NotificationVideoCall.ACCEPT)) {

            NotificationVideoCall.updateVideoCalNotification("Call is on progress", context);
            Intent fullScreenIntent = new Intent(context, VideoCallActivity.class);
            fullScreenIntent.putExtra("roomName", intent.getStringExtra("roomName"));
            fullScreenIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(fullScreenIntent);
            Toast.makeText(context, "Call Accepted !!", Toast.LENGTH_SHORT).show();
        }
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(NotificationVideoCall.REJECT)) {
            hideNotification(context);
            cancelShowMissedCallNotification();
            Toast.makeText(context, "Call Rejected !!", Toast.LENGTH_SHORT).show();
        }
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(NotificationVideoCall.DISMISS)) {
            NotificationVideoCall.hideCallOnProgressNotification(context);
            Toast.makeText(context, "Call Disconnected !!", Toast.LENGTH_SHORT).show();
        }

    }
}
