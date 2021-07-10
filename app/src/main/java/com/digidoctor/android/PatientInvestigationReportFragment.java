package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.digidoctor.android.adapters.ViewPagerAdapter;
import com.digidoctor.android.databinding.FragmentPatientInvestigationReportBinding;
import com.digidoctor.android.model.EraInvestigationData;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PatientInvestigationReportFragment extends Fragment {

    FragmentPatientInvestigationReportBinding binding;
    PatientViewModel viewModel;

    private static final String TAG = "PatientInvestigationRep";


    @Override

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPatientInvestigationReportBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(false);

        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        loadInvestigationData();


    }


    private void loadInvestigationData() {
        String PID = String.valueOf(utils.getUserForBooking(requireActivity()).getPid());
        viewModel.eraInvestigationData(PID, requireActivity()).observe(requireActivity(), eraInvestigationData -> {
            if (!eraInvestigationData.getInvestigationResult().isEmpty()) {
                initTabbed(eraInvestigationData.getInvestigationResult());
                binding.noInvestigationLay.setVisibility(View.GONE);
                binding.tabLayout.setVisibility(View.VISIBLE);
                binding.viewPager.setVisibility(View.VISIBLE);
                binding.cardView.setVisibility(View.VISIBLE);
            } else {
                binding.noInvestigationLay.setVisibility(View.VISIBLE);
                binding.tabLayout.setVisibility(View.GONE);
                binding.viewPager.setVisibility(View.GONE);
                binding.cardView.setVisibility(View.GONE);
            }
            binding.progressBar7.setVisibility(View.GONE);
        });

    }

    private void initTabbed(List<EraInvestigationData.PatientTestResult> patientTestDate) {
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
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_START);
        setupViewPager(binding.viewPager, patientTestDate);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void setupViewPager(ViewPager viewPager, List<EraInvestigationData.PatientTestResult> patientTestDate) {

        Log.d(TAG, "setupViewPager: " + patientTestDate.toString());
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity().getSupportFragmentManager());
        for (int i = 0; i < patientTestDate.size(); i++) {
            OneFragment fView = new OneFragment();
            adapter.addFrag(fView, AppUtils.parseDate(patientTestDate.get(i).getBillDate(), "d MMM yyyy", "dd-MM-yyyy"));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }


    @Override
    public void onResume() {
        super.onResume();
        AppUtils.showToolbar(requireActivity());
    }
}