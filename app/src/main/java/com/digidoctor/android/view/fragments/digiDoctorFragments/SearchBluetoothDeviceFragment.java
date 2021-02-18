package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.BluetoothBroadcastReceiver;
import com.digidoctor.android.ConnectThread;
import com.digidoctor.android.adapters.BluetoothDevicesAdapter;
import com.digidoctor.android.adapters.BluetoothScanDevicesAdapter;
import com.digidoctor.android.databinding.FragmentSearchBlutoothDeviceBinding;
import com.digidoctor.android.interfaces.OnClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.digidoctor.android.utility.utils.fadeIn;


public class SearchBluetoothDeviceFragment extends Fragment implements OnClickListener {
    private static final String TAG = "SearchBluetoothDeviceFr";
    FragmentSearchBlutoothDeviceBinding bluetoothDeviceBinding;
    NavController navController;
    BluetoothDevicesAdapter adapter;
    BluetoothScanDevicesAdapter scanDevicesAdapter;


    public static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter btAdapter;
    List<BluetoothDevice> devicesList;
    List<BluetoothDevice> scanDevicesList;

    ConnectThread connectThread;


    public static SearchBluetoothDeviceFragment instance;


    public static SearchBluetoothDeviceFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bluetoothDeviceBinding = FragmentSearchBlutoothDeviceBinding.inflate(getLayoutInflater());
        instance = this;
        return bluetoothDeviceBinding.getRoot();
    }

    private void showLoadingView() {
        new Handler().postDelayed(() -> {
            searchBLEDevice();
        }, 1000);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //paired Devices Adapter
        devicesList = new ArrayList<>();
        adapter = new BluetoothDevicesAdapter(devicesList, this);
        bluetoothDeviceBinding.recyclerView2.setAdapter(adapter);


        //Scan Devices Adapter
        scanDevicesList = new ArrayList<>();
        scanDevicesAdapter = new BluetoothScanDevicesAdapter(scanDevicesList, this);
        bluetoothDeviceBinding.recyclerView4.setAdapter(scanDevicesAdapter);


        bluetoothDeviceBinding.btnScan.setOnClickListener(view1 -> {
            scanDevicesList.clear();
            bluetoothDeviceBinding.dataView.setVisibility(View.GONE);
            bluetoothDeviceBinding.loadingView.setVisibility(View.VISIBLE);
            showLoadingView();
        });

    }

    private void searchBLEDevice() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        CheckBluetoothState();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBluetoothState();
        }
    }

    public void CheckBluetoothState() {
        bluetoothDeviceBinding.dataView.setVisibility(View.VISIBLE);
        bluetoothDeviceBinding.loadingView.setVisibility(View.GONE);
        if (null == devicesList)
            devicesList = new ArrayList<>();
        if (btAdapter == null) {
            Log.d(TAG, "CheckBluetoothState: Bluetooth NOT supported. Aborting.");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "Bluetooth is enabled...");
                Log.d(TAG, "Paired Devices are:");
                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                for (BluetoothDevice device : devices) {
                    // if (device.getType() == DEVICE_TYPE_LE)
                    if (!devicesList.contains(device))
                        devicesList.add(device);
                }
                adapter.notifyDataSetChanged();
                updateVisibility();
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    private void updateVisibility() {
        bluetoothDeviceBinding.loadingView.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        bluetoothDeviceBinding.loadingView.setAnimation(fadeIn(requireActivity()));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(Object object) {
        BluetoothDevice bluetoothDevice = (BluetoothDevice) object;
        Log.d(TAG, "onItemClick: " + bluetoothDevice.getName());

        //Connecting A Device
      /*  connectThread = new ConnectThread();
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        Method getUuidsMethod = null;
        try {
            getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        ParcelUuid[] uuids = new ParcelUuid[0];
        try {
            uuids = (ParcelUuid[]) getUuidsMethod.invoke(adapter, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        for (ParcelUuid uuid : uuids) {
            Log.d(TAG, "UUID: " + uuid.getUuid().toString());
            if (connectThread.connect(bluetoothDevice, uuid.getUuid())) {
                Log.d(TAG, "Connected : ");
            }

        }*/


    }


    public void ScanBLEDevices() {
        if (null == btAdapter)
            btAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(bReciever, filter);

        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }
        btAdapter.startDiscovery();

    }

    private final BroadcastReceiver bReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (null == scanDevicesList)
                scanDevicesList = new ArrayList<>();

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                Log.d(TAG, "onDeviceFound: " + device.getName() + " Type: " + device.getType());

                if (null != device.getName())
                    if (!scanDevicesList.contains(device))
                        scanDevicesList.add(device);
                scanDevicesAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onResume() {

        super.onResume();

        showLoadingView();

        updateVisibility();

        getConnecctedDevices();

        ScanBLEDevices();


    }

    private void getConnecctedDevices() {
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);

        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);

        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);

        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        requireActivity().registerReceiver(bluetoothBroadcastReceiver, filter);
    }
}