package com.digidoctor.android.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ActivityMainBinding;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.view.activity.SignUpJourneyActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static com.digidoctor.android.utility.utils.IS_FIRST_TIME;
import static com.digidoctor.android.utility.utils.IS_LOGIN;
import static com.digidoctor.android.utility.utils.fadeOut;
import static com.digidoctor.android.utility.utils.fcmToken;
import static com.digidoctor.android.utility.utils.getBoolean;
import static com.digidoctor.android.utility.utils.getLoginStatus;
import static com.digidoctor.android.utility.utils.setString;

public class SplashScreen extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    //Hello Updated 11:51 Am

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, activityMainBinding.ivLogo, getString(R.string.logo_transition));

        generateFcmToken(SplashScreen.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean loggedIn = getLoginStatus(IS_LOGIN, SplashScreen.this);
                Intent intent;
                if (loggedIn) {

                    intent = new Intent(SplashScreen.this, PatientDashboard.class);
                } else {

                    intent = new Intent(SplashScreen.this, SignUpJourneyActivity.class);
                }

                startActivity(intent);
                SplashScreen.this.finish();
            }
        }, 1000);
    }

    public static void generateFcmToken(final Activity activity) {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                setString(fcmToken, newToken, activity);
                Log.e("newToken2", newToken);
            }
        });

    }

}