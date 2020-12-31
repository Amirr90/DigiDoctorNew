package com.digidoctor.android.adapters;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.BtPairedDevicesViewBinding;
import com.digidoctor.android.interfaces.OnClickListener;

import java.util.ArrayList;
import java.util.List;

public class BluetoothDevicesAdapter extends RecyclerView.Adapter<BluetoothDevicesAdapter.BluetoothVH> {
    List<BluetoothDevice> devices;
    OnClickListener onClickListener;

    public BluetoothDevicesAdapter(List<BluetoothDevice> devices, OnClickListener onClickListener) {
        this.devices = devices;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BluetoothVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BtPairedDevicesViewBinding devicesViewBinding = BtPairedDevicesViewBinding.inflate(inflater, parent, false);
        devicesViewBinding.setClickListener(onClickListener);
        return new BluetoothVH(devicesViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothVH holder, int position) {
        try {
            BluetoothDevice bluetoothDevice = devices.get(position);
            holder.devicesViewBinding.setBluetooth(bluetoothDevice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (null == devices)
            devices = new ArrayList<>();
        return devices.size();
    }

    public class BluetoothVH extends RecyclerView.ViewHolder {
        BtPairedDevicesViewBinding devicesViewBinding;

        public BluetoothVH(@NonNull BtPairedDevicesViewBinding devicesViewBinding) {
            super(devicesViewBinding.getRoot());
            this.devicesViewBinding = devicesViewBinding;
        }
    }
}
