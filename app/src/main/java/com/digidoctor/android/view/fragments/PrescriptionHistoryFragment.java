package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.PrescriptionAdapter;
import com.digidoctor.android.databinding.FragmentPrescriptionHistoryBinding;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.List;


public class PrescriptionHistoryFragment extends Fragment {


    FragmentPrescriptionHistoryBinding prescriptionHistoryBinding;
    NavController navController;
    PrescriptionAdapter prescriptionAdapter;

    PatientViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prescriptionHistoryBinding = FragmentPrescriptionHistoryBinding.inflate(getLayoutInflater());
        return prescriptionHistoryBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        prescriptionAdapter=new PrescriptionAdapter();


        prescriptionHistoryBinding.prescriptionRec.setAdapter(prescriptionAdapter);


        viewModel.getPrescriptionData(requireActivity()).observe(getViewLifecycleOwner(), new Observer<List<GetPatientMedicationMainModel>>() {
            @Override
            public void onChanged(List<GetPatientMedicationMainModel> getPatientMedicationMainModels) {
                if (getPatientMedicationMainModels != null) {
                    prescriptionAdapter.submitList(getPatientMedicationMainModels);
                }
            }
        });

    }
}