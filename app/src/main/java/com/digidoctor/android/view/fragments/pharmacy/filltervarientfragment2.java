package com.digidoctor.android.view.fragments.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.digidoctor.android.databinding.Filtterfragment2Binding;

public class filltervarientfragment2 extends Fragment {

    NavController navController;

    Filtterfragment2Binding filtterfragment2Binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        filtterfragment2Binding = Filtterfragment2Binding.inflate(getLayoutInflater());
        return filtterfragment2Binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        navController = Navigation.findNavController(view);
    }


}
