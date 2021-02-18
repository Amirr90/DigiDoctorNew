package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentGetStartedBinding;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.SignUpJourneyActivity;
import com.digidoctor.android.view.activity.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import static com.digidoctor.android.utility.utils.IS_FIRST_TIME;
import static com.digidoctor.android.utility.utils.fadeIn;


public class GetStartedFragment extends Fragment {


    FragmentGetStartedBinding getStartedBinding;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getStartedBinding = FragmentGetStartedBinding.inflate(inflater, container, false);
        return getStartedBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        utils.setBoolean(IS_FIRST_TIME, false, requireActivity());

        navController = Navigation.findNavController(view);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(requireContext(), ((SignUpJourneyActivity) requireContext()).getSupportFragmentManager());
        getStartedBinding.viewPager.setAdapter(sectionsPagerAdapter);
        getStartedBinding.tabs.setupWithViewPager(getStartedBinding.viewPager);

        getStartedBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                getStartedBinding.dotsIndicator.setDotSelection(position);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getStartedBinding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getStartedBinding.btnGetStarted.setVisibility(tab.getPosition() == 2 ? View.VISIBLE : View.GONE);
                if (tab.getPosition() == 2)
                    getStartedBinding.btnGetStarted.setAnimation(fadeIn(requireActivity()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        getStartedBinding.btnGetStarted.setOnClickListener(view1 -> navController.navigate(R.id.action_getStartedFragment_to_inputMobileNumberFragment));
    }


}