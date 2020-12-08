package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAddVitalaBinding;

import org.jetbrains.annotations.NotNull;


public class AddVitalsFragment extends Fragment {

    FragmentAddVitalaBinding addVitalsBinding;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addVitalsBinding = FragmentAddVitalaBinding.inflate(getLayoutInflater());
        return addVitalsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }
}