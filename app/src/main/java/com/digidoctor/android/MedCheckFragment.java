package com.digidoctor.android;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.digidoctor.android.databinding.FragmentMedCheckBinding;
import com.digidoctor.android.utility.utils;
import com.getmedcheck.lib.MedCheck;
import com.getmedcheck.lib.model.BleDevice;
import com.getmedcheck.lib.model.IDeviceData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MedCheckFragment extends com.getmedcheck.lib.MedCheckFragment {
    private static final String TAG = "MedCheckFragment";

    FragmentMedCheckBinding binding;
    String macAddress;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedCheckBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        macAddress = getArguments().getString(utils.MAC_ADDRESS);
        binding.button.setText("Hello !!");

        connectDevice();
    }

    private void connectDevice() {
        MedCheck.getInstance().connect(requireActivity(), macAddress);
        readData();
    }

    private void readData() {

        MedCheck.getInstance().writeCommand(requireActivity(), macAddress);
    }

    protected void onPermissionGrantedAndBluetoothOn() {
    }

    protected void startScan() {
    }

    protected void onDeviceClearCommand(int state) {
    }

    private void onDeviceTimeSyncCommand(int state) {
    }

    protected void onDeviceScanResult(ScanResult scanResult) {
    }

    protected void onDeviceDataReadingStateChange(int state, String message) {
    }

    protected void onDeviceDataReceive(BluetoothDevice device, ArrayList<IDeviceData> deviceData, String jsonString, String deviceType) {
        Log.e(TAG, "onDeviceDataReceive: " + jsonString);
    }

    protected void onDeviceConnectionStateChange(BleDevice bleDevice, int status) {
    }
}