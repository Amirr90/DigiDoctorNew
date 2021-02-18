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

        new Handler().postDelayed(() -> {

            boolean loggedIn = getLoginStatus(IS_LOGIN, SplashScreen.this);
            Intent intent;
            if (loggedIn) {
                intent = new Intent(SplashScreen.this, PatientDashboard.class);
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

}