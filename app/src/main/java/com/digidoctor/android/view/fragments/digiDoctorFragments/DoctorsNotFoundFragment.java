package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digidoctor.android.databinding.FragmentDoctorsNotFoundBinding;

import org.jetbrains.annotations.NotNull;


public class DoctorsNotFoundFragment extends Fragment {


    FragmentDoctorsNotFoundBinding doctorsNotFoundBinding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        doctorsNotFoundBinding = FragmentDoctorsNotFoundBinding.inflate(inflater, container, false);
        return doctorsNotFoundBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}