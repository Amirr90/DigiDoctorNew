package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.FragmentSearchBlutoothDeviceBinding;

import org.jetbrains.annotations.NotNull;


public class SearchBluetoothDeviceFragment extends Fragment {
    FragmentSearchBlutoothDeviceBinding bluetoothDeviceBinding;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bluetoothDeviceBinding = FragmentSearchBlutoothDeviceBinding.inflate(getLayoutInflater());
        return bluetoothDeviceBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        searchBLEDevice();
    }

    private void searchBLEDevice() {
    }
}