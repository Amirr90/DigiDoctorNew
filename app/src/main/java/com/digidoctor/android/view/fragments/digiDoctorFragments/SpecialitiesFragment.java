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

import com.digidoctor.android.adapters.SpecialityAdapter;
import com.digidoctor.android.databinding.FragmentSpecialitiesBinding;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class SpecialitiesFragment extends Fragment {

    FragmentSpecialitiesBinding specialitiesBinding;
    SpecialityAdapter specialityAdapter;
    PatientViewModel viewModel;

    String specialityName;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        specialitiesBinding = FragmentSpecialitiesBinding.inflate(inflater, container, false);
        return specialitiesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        specialityAdapter = new SpecialityAdapter(requireActivity());
        specialitiesBinding.specRec.setAdapter(specialityAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        getSpecialityData(specialityName);


        specialitiesBinding.editTextTextSearchSpeciality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence != null && charSequence.length() > 1) {
                    specialitiesBinding.progressBar3.setVisibility(View.VISIBLE);
                    getSpecialityData(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                specialitiesBinding.progressBar3.setVisibility(View.GONE);
            } else PatientDashboard.getInstance().onSupportNavigateUp();

        });
    }


}