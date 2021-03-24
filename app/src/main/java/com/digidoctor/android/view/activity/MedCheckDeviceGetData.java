package com.digidoctor.android.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.ScanResultAdapter;
import com.digidoctor.android.interfaces.OnItemClickListener;
import com.getmedcheck.lib.MedCheck;
import com.getmedcheck.lib.MedCheckActivity;
import com.getmedcheck.lib.model.BleDevice;

import java.util.ArrayList;
import java.util.HashMap;

public class MedCheckDeviceGetData extends MedCheckActivity implements OnItemClickListener<BleDevice>, View.OnClickListener {


    private static final String TAG = MedCheckDeviceGetData.class.getSimpleName();
    private HashMap<String, BleDevice> mDeviceHashMap = new HashMap<>();
    private RecyclerView mRvScanResult;
    private Button mBtnStartScan, mBtnStopScan;
    private LinearLayout mLlProgressLayout;
    private ScanResultAdapter mScanResultAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_check_device_get_data);
        Toolbar toolbar = findViewById(R.id.toolbar3);
     /*   setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_device));*/

        initViews();
        requestLocationPermission();
        checkAllConditions();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initViews() {

        mLlProgressLayout = findViewById(R.id.llProgressLayout);
        mBtnStopScan = findViewById(R.id.btnStopScan);
        mBtnStopScan.setOnClickListener(this);
        mBtnStartScan = findViewById(R.id.btnStartScan);
        mBtnStartScan.setOnClickListener(this);

        mScanResultAdapter = new ScanResultAdapter(this);
        mScanResultAdapter.setOnItemClickListener(this);
        mRvScanResult = findViewById(R.id.rvScanResult);
        mRvScanResult.setLayoutManager(new LinearLayoutManager(this));
        mRvScanResult.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRvScanResult.setAdapter(mScanResultAdapter);
        // FloatingActionButton fabBarcode = findViewById(R.id.fabBarcode);
        // fabBarcode.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DeviceConnectScanActivity.class).putExtra("type", "BP")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerCallback();
    }

    @Override
    protected void onDeviceScanResult(no.nordicsemi.android.support.v18.scanner.ScanResult scanResult) {
        super.onDeviceScanResult(scanResult);
        BleDevice bleDevice = new BleDevice(scanResult.getDevice());
        mDeviceHashMap.put(bleDevice.getDevice().getAddress(), bleDevice);

        if (mScanResultAdapter != null) {
            mScanResultAdapter.setItems(new ArrayList<>(mDeviceHashMap.values()));
        }
    }


    @Override
    public void onItemClick(View view, BleDevice object, int position) {
        if (mBtnStopScan.isClickable()) {
            mBtnStopScan.performClick();
        }

        DeviceConnectionActivity.start(this, object);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStopScan:
                MedCheck.getInstance().stopScan(this);
                mBtnStopScan.setClickable(false);
                mBtnStartScan.setClickable(true);
                mLlProgressLayout.setVisibility(View.GONE);
                break;
            case R.id.btnStartScan:
                checkAllConditions();
                break;
            default:
                break;
        }
    }

    @Override
    protected void startScan() {
        super.startScan();
        mDeviceHashMap.clear();
        if (mScanResultAdapter != null) {
            mScanResultAdapter.clear();
        }
        MedCheck.getInstance().startScan(this);
        mBtnStopScan.setClickable(true);
        mBtnStartScan.setClickable(false);
        mLlProgressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPermissionGrantedAndBluetoothOn() {
        super.onPermissionGrantedAndBluetoothOn();
    }

}