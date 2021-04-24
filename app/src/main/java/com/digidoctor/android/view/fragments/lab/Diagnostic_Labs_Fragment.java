package com.digidoctor.android.view.fragments.lab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.DiagnosticLabsFragmentBinding;

public class Diagnostic_Labs_Fragment extends Fragment {
    NavController navController;
    DiagnosticLabsFragmentBinding diagnosticLabsFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        diagnosticLabsFragmentBinding = DiagnosticLabsFragmentBinding.inflate(getLayoutInflater());
        return diagnosticLabsFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

    }
}
