package com.digidoctor.android.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ActivityMainBinding;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Locale;

import timber.log.Timber;

import static com.digidoctor.android.utility.utils.IS_LOGIN;
import static com.digidoctor.android.utility.utils.fcmToken;
import static com.digidoctor.android.utility.utils.getLoginStatus;
import static com.digidoctor.android.utility.utils.setString;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private static final int OVERLAY_REQUEST_CODE = 1122;
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


        getNotificationData();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);

        new Handler().postDelayed(this::checkPermission, 1000);


    }

    private void initUserJourney() {
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
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                if ("xiaomi".equals(Build.MANUFACTURER.toLowerCase(Locale.ROOT))) {
                    final Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter",
                            "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", getPackageName());
                    new AlertDialog.Builder(this)
                            .setTitle("Please Enable the additional permissions")
                            .setMessage("You will not receive notifications while the app is in background if you disable these permissions")
                            .setPositiveButton("Go to Settings", (dialog, which) -> startActivity(intent))
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setCancelable(false)
                            .show();
                } else {
                    Intent overlaySettings = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(overlaySettings, OVERLAY_REQUEST_CODE);
                }
            } else initUserJourney();

        } else initUserJourney();

    }

    private void updateUI(FirebaseUser currentUser) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (currentUser == null)
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String token = utils.getString(fcmToken, SplashScreen.this);
                            AppUtils.setUserToFirebaseDatabase(user, token);
                        } else {
                            Timber.d(task.getException(), "signInAnonymously:failure");
                            Toast.makeText(SplashScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        else {
            Timber.d("updateUI: %s", mAuth.getCurrentUser().getUid());
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
                String newToken = instanceIdResult.getToken();
                AppUtils.updateTokenToDatabase(newToken);
            });
        }
    }


    public static void generateFcmToken(final Activity activity) {

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            setString(fcmToken, newToken, activity);
            Timber.e(newToken);
        });

    }


    private void getNotificationData() {
        if (null != getIntent().getExtras()) {
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("twillioAccessToken")) {
                    Timber.d("getData: twillioAccessToken %s", getIntent().getExtras().getString(key));
                    twillioAccessToken = getIntent().getExtras().getString(key);
                }
                if (key.equals("doctorName")) {
                    Timber.d("getData: doctorName %s", getIntent().getExtras().getString(key));
                    doctorName = getIntent().getExtras().getString(key);
                }
                if (key.equals("message")) {
                    Timber.d("getData: message %s", getIntent().getExtras().getString(key));
                    msg = getIntent().getExtras().getString(key);
                }
                if (key.equals("type")) {
                    Timber.d("getData: type %s", getIntent().getExtras().getString(key));
                    type = getIntent().getExtras().getString(key);
                }
                if (key.equals("profilePhotoPath")) {
                    Timber.d("getData: profilePhotoPath %s", getIntent().getExtras().getString(key));
                    profilePhotoPath = getIntent().getExtras().getString(key);
                }
                if (key.equals("title")) {
                    Timber.d("getData: title %s", getIntent().getExtras().getString(key));
                    title = getIntent().getExtras().getString(key);
                }
                if (key.equals("roomName")) {
                    Timber.d("getData: roomName %s", getIntent().getExtras().getString(key));
                    roomName = getIntent().getExtras().getString(key);
                }
            }
        } else {
            Timber.d("getData: null");
        }
    }

}