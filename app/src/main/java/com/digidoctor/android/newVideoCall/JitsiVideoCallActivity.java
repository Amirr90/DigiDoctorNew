package com.digidoctor.android.newVideoCall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.digidoctor.android.utility.AppUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;


public class JitsiVideoCallActivity extends JitsiMeetActivity implements JitsiMeetActivityInterface, JitsiMeetViewListener {

    private static final String TAG = "JitsiVideoCallActivity";

    String roomCode;

    private JitsiMeetView view;
    public static final String SERVER_URL = "https://theorganicdelight.com/";
    public static final String JIT_SI_SERVER_URL = "https://meet.jit.si/";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_video_call_jitsi);


        roomCode = getIntent().getStringExtra(AppUtils.ROOM_CODE);

        joinMeeting(roomCode);
        listenForReaTimeCallStatus();

    }


    private void listenForReaTimeCallStatus() {

        firebaseFirestore.collection(AppUtils.VIDEO_CALLS_DEMO)
                .document(roomCode)
                .addSnapshotListener(this, (value, error) -> {
                    if (null == error && null != value) {
                        String callStatus = value.getString(AppUtils.CALL_STATUS);
                        if (null != callStatus && !TextUtils.isEmpty(callStatus)) {
                            if (callStatus.equalsIgnoreCase(AppUtils.CALL_DISCONNECTED)) {
                                closeJitsiVideoCall();
                            }
                        }
                    }
                });
    }

    private void closeJitsiVideoCall() {
        view.dispose();
        finish();
    }


    private void joinMeeting(String roomCode) {
        Log.d(TAG, "joinMeeting: ");
        view = new JitsiMeetView(this);
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(SERVER_URL + roomCode)
                .setFeatureFlag("toolbox.enabled", false)
                .setFeatureFlag("raise-hand.enabled", false)
                .setFeatureFlag("recording.enabled", false)
                .setFeatureFlag("pip.enabled", true)
                .setFeatureFlag("notifications.enabled", true)
                .setFeatureFlag("call-integration.enabled", true)
                .setFeatureFlag("toolbox.alwaysVisible", false)
                .setFeatureFlag("video-share.enabled", false)
                .setWelcomePageEnabled(false)
                .build();

        view.setListener(this);
        view.join(options);
        setContentView(view);
    }


    @Override
    public void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JitsiMeetActivityDelegate.onActivityResult(
                this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed();

    }

    @Override
    public void onConferenceTerminated(Map<String, Object> data) {
        super.onConferenceTerminated(data);
        closeMeetingToDatabase(roomCode);
    }

    private void closeMeetingToDatabase(String roomCode) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(AppUtils.VIDEO_CALLS_DEMO).document(roomCode).update(AppUtils.CALL_STATUS, AppUtils.CALL_DISCONNECTED);
    }

}
