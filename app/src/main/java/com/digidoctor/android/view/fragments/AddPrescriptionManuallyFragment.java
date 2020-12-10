package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digidoctor.android.databinding.FragmentAddPrescriptionManuallyBinding;

import org.jetbrains.annotations.NotNull;


public class AddPrescriptionManuallyFragment extends Fragment {

    FragmentAddPrescriptionManuallyBinding addPrescriptionManuallyBinding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addPrescriptionManuallyBinding = FragmentAddPrescriptionManuallyBinding.inflate(getLayoutInflater());
        return addPrescriptionManuallyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}