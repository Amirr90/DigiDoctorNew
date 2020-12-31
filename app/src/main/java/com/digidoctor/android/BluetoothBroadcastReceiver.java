package com.digidoctor.android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BluetoothBroadcastRecei";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


        if (BluetoothDevice.ACTION_FOUND.equals(action)) {

            Log.d(TAG, "DeviceFound: ");

        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

            Log.d(TAG, "Device is now connected");

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            Log.d(TAG, "Done searching");

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {

            Log.d(TAG, "Device is about to disconnect");

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

            Log.d(TAG, "Device has disconnected: ");

        }

    }
}

