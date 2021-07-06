package com.digidoctor.android.videoCall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.digidoctor.android.broadcast.VideoCallActionBroadcast;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.NotificationVideoCall;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.util.Map;

public class VideoCallActivity extends JitsiMeetActivity implements JitsiMeetActivityInterface, JitsiMeetViewListener {

    VideoCallActionBroadcast videoCallActionBroadcast;

    private static final String TAG = "VideoCallActivity";
    String roomCode;

    private JitsiMeetView view;
    public static final String SERVER_URL = "https://theorganicdelight.com/";
    public static final String JIT_SI_SERVER_URL = "https://meet.jit.si/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_video_call);

        roomCode = getIntent().getStringExtra("roomName");
        joinMeeting(roomCode);

    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationVideoCall.stopSound();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(AppUtils.VIDEO_CALLS).document(roomCode).addSnapshotListener((value, error) -> {
            if (error == null && value.getData() != null) {
                String callStatus = value.getString(AppUtils.CALL_STATUS);
                if (AppUtils.CALL_DISCONNECTED.equalsIgnoreCase(callStatus)) {
                    view.dispose();
                    finish();
                }
            }

        });
    }

    private void joinMeeting(String roomCode) {

        view = new JitsiMeetView(this);

        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(JIT_SI_SERVER_URL + roomCode)
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
    public void onDestroy() {
        super.onDestroy();
        view.dispose();
        view = null;
        JitsiMeetActivityDelegate.onHostDestroy(this);
    }

    @Override
    public void onConferenceJoined(Map<String, Object> data) {
        super.onConferenceJoined(data);
        AppUtils.updateJoinedDatabase(AppUtils.CALL_CONNECTED, roomCode);
    }

    @Override
    public void onConferenceTerminated(Map<String, Object> data) {
        super.onConferenceTerminated(data);
        JitsiMeetActivityDelegate.onHostDestroy(this);
        NotificationVideoCall.hideCallOnProgressNotification(App.context);
        Toast.makeText(App.context, "Call Disconnected ", Toast.LENGTH_SHORT).show();
        AppUtils.updateTodatabase(AppUtils.CALL_DISCONNECTED, roomCode);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void join(JitsiMeetConferenceOptions options) {
        super.join(options);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        JitsiMeetActivityDelegate.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: " + intent.getData().toString());
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JitsiMeetActivityDelegate.onHostResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JitsiMeetActivityDelegate.onHostPause(this);
    }
}
