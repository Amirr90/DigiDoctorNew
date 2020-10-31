package com.digidoctor.android.view.activity;

import android.os.Bundle;

import com.digidoctor.android.R;
import com.google.android.material.tabs.TabLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.digidoctor.android.view.activity.ui.main.SectionsPagerAdapter;
import com.mazenrashed.dotsindicator.DotsIndicator;

import static com.digidoctor.android.utility.utils.fadeIn;

public class SignUpJourneyActivity extends AppCompatActivity {

    NavController navController;

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



}