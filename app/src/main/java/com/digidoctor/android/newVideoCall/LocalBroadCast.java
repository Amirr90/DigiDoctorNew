package com.digidoctor.android.newVideoCall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import timber.log.Timber;

import static com.digidoctor.android.newVideoCall.LocalNotification.cancelCallRingingNotification;

public class LocalBroadCast extends BroadcastReceiver {
    private static final String TAG = "LocalBroadCast";

    @Override
    public void onReceive(Context context, Intent intent) {
        cancelCallRingingNotification(context);
        if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(AppUtils.CALL_RINGING)) {
            Intent incomingCallIntent = new Intent(context, IncomingCallActivity.class);
            incomingCallIntent.putExtra("com.jc_code_ACTION", AppUtils.CALL_RINGING);
            incomingCallIntent.putExtra(AppUtils.CALL_ID, intent.getStringExtra(AppUtils.CALL_ID));
            incomingCallIntent.putExtra(AppUtils.ICON, intent.getStringExtra(AppUtils.ICON));
            incomingCallIntent.putExtra(AppUtils.DOCTOR_NAME, intent.getStringExtra(AppUtils.DOCTOR_NAME));
            incomingCallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(incomingCallIntent);
        } else if (intent.getStringExtra("com.jc_code_ACTION").equalsIgnoreCase(AppUtils.CALL_REJECT)) {
            Toasty.error(App.context, "Call Rejected !!", Toast.LENGTH_SHORT).show();
            updateCallStatus(intent.getStringExtra(AppUtils.CALL_ID));
        }
    }

    private void updateCallStatus(String callId) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> map = new HashMap<>();
        map.put(AppUtils.CALL_STATUS, AppUtils.CALL_REJECT);
        map.put(AppUtils.CALL_REJECTED_BY, "patient");
        firebaseFirestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(callId)
                .update(map)
                .addOnFailureListener(e -> Timber.d("onFailure: %s", e.getLocalizedMessage()));

    }
}
