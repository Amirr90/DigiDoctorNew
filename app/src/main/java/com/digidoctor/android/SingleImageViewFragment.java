package com.digidoctor.android;

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

import com.digidoctor.android.databinding.FragmentSingleImageViewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class SingleImageViewFragment extends Fragment {


    FragmentSingleImageViewBinding fragmentSingleImageViewBinding;
    NavController navController;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSingleImageViewBinding = FragmentSingleImageViewBinding.inflate(getLayoutInflater());
        return fragmentSingleImageViewBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if (getArguments() == null) {
            return;
        }

        fragmentSingleImageViewBinding.setImage(getArguments().getString("image"));


    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}