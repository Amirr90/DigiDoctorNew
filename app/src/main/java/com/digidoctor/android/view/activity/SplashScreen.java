package com.digidoctor.android.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ActivityMainBinding;
import com.google.firebase.iid.FirebaseInstanceId;

import static com.digidoctor.android.utility.utils.IS_LOGIN;
import static com.digidoctor.android.utility.utils.fcmToken;
import static com.digidoctor.android.utility.utils.getLoginStatus;
import static com.digidoctor.android.utility.utils.setString;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    ActivityMainBinding activityMainBinding;
    String doctorName;
    String twillioAccessToken, roomName, msg, title, profilePhotoPath, type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, activityMainBinding.ivLogo, getString(R.string.logo_transition));

        // create();
        generateFcmToken(SplashScreen.this);


        getData();

        new Handler().postDelayed(() -> {
            boolean loggedIn = getLoginStatus(IS_LOGIN, SplashScreen.this);
            Intent intent;
            if (loggedIn) {
                intent = new Intent(SplashScreen.this, PatientDashboard.class)
                        .putExtra("twillioAccessToken", twillioAccessToken)
                        .putExtra("roomName", roomName)
                        .putExtra("msg", msg)
                        .putExtra("title", title)
                        .putExtra("doctorName", doctorName)
                        .putExtra("profilePhotoPath", profilePhotoPath)
                        .putExtra("type", type);
            } else {
                intent = new Intent(SplashScreen.this, SignUpJourneyActivity.class);
            }

            startActivity(intent);
            SplashScreen.this.finish();
        }, 1000);
    }


    public static void generateFcmToken(final Activity activity) {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            setString(fcmToken, newToken, activity);
            Log.e("newToken2", newToken);
        });

    }


    private void getData() {
        if (null != getIntent().getExtras()) {
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("twillioAccessToken")) {
                    Log.d(TAG, "getData: twillioAccessToken " + getIntent().getExtras().getString(key));
                    twillioAccessToken = getIntent().getExtras().getString(key);
                }
                if (key.equals("doctorName")) {
                    Log.d(TAG, "getData: doctorName " + getIntent().getExtras().getString(key));
                    doctorName = getIntent().getExtras().getString(key);
                }
                if (key.equals("message")) {
                    Log.d(TAG, "getData: message " + getIntent().getExtras().getString(key));
                    msg = getIntent().getExtras().getString(key);
                }
                if (key.equals("type")) {
                    Log.d(TAG, "getData: type " + getIntent().getExtras().getString(key));
                    type = getIntent().getExtras().getString(key);
                }
                if (key.equals("profilePhotoPath")) {
                    Log.d(TAG, "getData: profilePhotoPath " + getIntent().getExtras().getString(key));
                    profilePhotoPath = getIntent().getExtras().getString(key);
                }
                if (key.equals("title")) {
                    Log.d(TAG, "getData: title " + getIntent().getExtras().getString(key));
                    title = getIntent().getExtras().getString(key);
                }
                if (key.equals("roomName")) {
                    Log.d(TAG, "getData: roomName " + getIntent().getExtras().getString(key));
                    roomName = getIntent().getExtras().getString(key);
                }
            }
        } else {
            Log.d(TAG, "getData: null");
        }
    }

}