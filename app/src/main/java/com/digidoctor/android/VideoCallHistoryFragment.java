package com.digidoctor.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.digidoctor.android.databinding.FragmentVideoCallHistoryBinding;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.fragments.digiDoctorFragments.CallFragment;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.digidoctor.android.utility.utils.fadeIn;

public class VideoCallHistoryFragment extends Fragment {


    FragmentVideoCallHistoryBinding binding;
    private static final String TAG = "VideoCallHistoryFragmen";

    PatientViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVideoCallHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.getRoot().setAnimation(fadeIn(requireActivity()));

        viewModel=new ViewModelProvider(requireActivity()).get(PatientViewModel.class);
        List<String> strings = new ArrayList<>();
        strings.add("Received");
        strings.add("Missed");
        initTabbed(strings);


        viewModel.getCallLogs();


    }

    private void initTabbed(List<String> patientTestDate) {
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        setupViewPager(binding.viewPager, patientTestDate);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void setupViewPager(ViewPager viewPager, List<String> patientTestDate) {

        Timber.d("setupViewPager: %s", patientTestDate.toString());
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity().getSupportFragmentManager());
        for (int i = 0; i < patientTestDate.size(); i++) {
            CallFragment fView = new CallFragment();
            adapter.addFrag(fView, patientTestDate.get(i));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            Fragment frag = CallFragment.newInstance();
            return frag;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.showToolbar(requireActivity());
    }
}