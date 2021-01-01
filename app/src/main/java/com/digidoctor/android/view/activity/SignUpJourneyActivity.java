package com.digidoctor.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.google.android.gms.auth.api.credentials.Credential;

import static com.digidoctor.android.view.fragments.InputOtpFragment.RESOLVE_HINT;

public class SignUpJourneyActivity extends AppCompatActivity {

    NavController navController;
    private static final String TAG = "SignUpJourneyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_journey);

        navController = Navigation.findNavController(this, R.id.signUp_nav_fragment);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // credential.getId();  <-- will need to process phone number string
                Log.d(TAG, "onActivityResult: " + credential.getId());
            }
        }
    }
}