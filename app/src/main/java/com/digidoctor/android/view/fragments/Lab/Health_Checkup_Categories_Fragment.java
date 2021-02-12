package com.digidoctor.android.view.fragments.Lab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.HealthCheckupCategoriesFragmentBinding;

public class Health_Checkup_Categories_Fragment extends Fragment {
    NavController navController;
    HealthCheckupCategoriesFragmentBinding healthCheckupCategoriesFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        healthCheckupCategoriesFragmentBinding = HealthCheckupCategoriesFragmentBinding.inflate(getLayoutInflater());
        return healthCheckupCategoriesFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }
}
