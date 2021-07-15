package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.ShimmerAdapter;
import com.digidoctor.android.adapters.SpecialityAdapter;
import com.digidoctor.android.databinding.FragmentSpecialitiesBinding;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SpecialitiesFragment extends Fragment {


    FragmentSpecialitiesBinding specialitiesBinding;
    SpecialityAdapter specialityAdapter;
    PatientViewModel viewModel;
    String specialityName;
    List<SpecialityModel> allSpecialityData;
    NavController navController;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        specialitiesBinding = FragmentSpecialitiesBinding.inflate(inflater, container, false);
        return specialitiesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        if (!utils.isNetworkConnected(requireActivity()))
            navController.navigateUp();


        specialityAdapter = new SpecialityAdapter(requireActivity());
        specialitiesBinding.specRec.setAdapter(specialityAdapter);
        //set ShimmerAdapter
        specialitiesBinding.recShimmerSpeciality.setAdapter(new ShimmerAdapter(R.layout.speciality_shimmer_view));

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);




        getSpecialityData(specialityName);
        PatientDashboardFragmentDirections.ActionPatientDashboardFragmentToSubSpecialistFragment action = PatientDashboardFragmentDirections.actionPatientDashboardFragmentToSubSpecialistFragment();
        specialitiesBinding.shimmerHOmeScreen.setVisibility(PatientDashboard.getInstance() != null ? View.VISIBLE : View.GONE);

        specialitiesBinding.editTextTextSearchSpeciality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence != null) {
                    if (charSequence.toString().isEmpty()) {
                        specialitiesBinding.progressBar3.setVisibility(View.GONE);
                        utils.hideSoftKeyboard(requireActivity());
                        specialityAdapter.submitList(allSpecialityData);
                    } else filterSpeciality(charSequence.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void filterSpeciality(CharSequence charSequence) {
        List<SpecialityModel> temp = new ArrayList();
        for (SpecialityModel d : allSpecialityData) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getSpecialityName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                temp.add(d);
            }
        }
        //update recyclerview
        specialityAdapter.submitList(temp);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    private void getSpecialityData(String specialityName) {
        viewModel.getSpecialityData(specialityName).observe(getViewLifecycleOwner(), specialityModels -> {
            if (null != specialityModels) {
                specialityAdapter.submitList(specialityModels);
                allSpecialityData = specialityModels;
                specialitiesBinding.recShimmerSpeciality.setVisibility(specialityModels.isEmpty() ? View.VISIBLE : View.GONE);
                specialitiesBinding.constraintLayout18.setVisibility(specialityModels.isEmpty() ? View.GONE : View.VISIBLE);
                specialitiesBinding.specRec.setVisibility(specialityModels.isEmpty() ? View.GONE : View.VISIBLE);
            } else PatientDashboard.getInstance().onSupportNavigateUp();

        });


        viewModel.getSpecialityText().observe(getViewLifecycleOwner(), s -> specialitiesBinding.textView225.setText(s));
    }


}