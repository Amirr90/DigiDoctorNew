package com.digidoctor.android.newVideoCall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ActivityIncomingCallBinding;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.digidoctor.android.newVideoCall.LocalNotification.cancelCallRingingNotification;
import static com.digidoctor.android.utility.NotificationVideoCall.playNotificationSound;
import static com.digidoctor.android.utility.NotificationVideoCall.player;
import static com.digidoctor.android.utility.NotificationVideoCall.stopSound;


public class IncomingCallActivity extends AppCompatActivity {

    private static final String TAG = "IncomingCallActivity";

    ActivityIncomingCallBinding binding;
    String callId, icon, doctorName;
    FirebaseFirestore firebaseFirestore;
    String callStatus = AppUtils.CALL_RINGING;
    LocalNotification localNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_incoming_call);


        callId = getIntent().getStringExtra(AppUtils.CALL_ID);
        icon = getIntent().getStringExtra(AppUtils.ICON);
        doctorName = getIntent().getStringExtra(AppUtils.DOCTOR_NAME);


        localNotification = new LocalNotification(this, callId, doctorName, icon);

        firebaseFirestore = FirebaseFirestore.getInstance();
        binding.ivAcceptCall.setOnClickListener(v -> {
            callStatus = AppUtils.CALL_ACCEPT;
            acceptIncomingCall();
        });
        binding.ivDeclineCall.setOnClickListener(v -> rejectIncomingCall());

        binding.setIcon(icon);
        binding.setDrName(doctorName);


        listenForReaTimeCallStatus();
    }

    private void listenForReaTimeCallStatus() {
        firebaseFirestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(callId)
                .addSnapshotListener( (value, error) -> {
                    if (null == error && null != value) {
                        String callStatus = value.getString(AppUtils.CALL_STATUS);
                        this.callStatus = callStatus;
                        if (null != callStatus && !TextUtils.isEmpty(callStatus)) {
                            if (callStatus.equalsIgnoreCase(AppUtils.CALL_DISCONNECTED) || callStatus.equalsIgnoreCase(AppUtils.CALL_MISSED)) {
                                Toasty.error(App.context, "Call  " + callStatus + " !!", Toast.LENGTH_SHORT).show();
                                stopSound();
                                finish();
                            }
                        }
                    }
                });
    }

    private void rejectIncomingCall() {
        cancelCallRingingNotification(this);
        binding.getRoot().setAnimation(AppUtils.slideDown(this));
        Toasty.error(App.context, "Call Rejected !!", Toast.LENGTH_SHORT).show();
        updateCallStatus(AppUtils.CALL_REJECT);
        stopSound();
        finish();
    }

    private void updateCallStatus(String status) {
        Map<String, Object> map = new HashMap<>();
        map.put(AppUtils.CALL_STATUS, status);
        if (status.equalsIgnoreCase(AppUtils.CALL_REJECT))
            map.put(AppUtils.CALL_REJECTED_BY, "patient");
        firebaseFirestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(callId)
                .update(map)
                .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getLocalizedMessage()));

    }


    private void acceptIncomingCall() {
        stopSound();
        Toasty.success(App.context, "Call Accepted !!", Toast.LENGTH_SHORT).show();
        updateCallStatus(AppUtils.CALL_ACCEPT);
        startActivity(new Intent(this, JitsiVideoCallActivity.class)
                .putExtra(AppUtils.ROOM_CODE, callId));
        finish();

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (null == player)
            playNotificationSound();
        else if (player.isPlaying()) {
            player.stop();
            playNotificationSound();
        }
        updateCallStatus(AppUtils.CALL_RINGING);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopSound();
        if (callStatus.equalsIgnoreCase(AppUtils.CALL_RINGING)) {
            LocalNotification.showCallRingingNotification("Incoming Video Call", doctorName, this);
        } else if (callStatus.equalsIgnoreCase(AppUtils.CALL_MISSED))
            LocalNotification.showMissedCallNotification(this);
        finish();
    }


}