package com.digidoctor.android.view.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.utility.URLUtils;
import com.digidoctor.android.utility.utils;
import com.getmedcheck.lib.MedCheck;
import com.getmedcheck.lib.MedCheckActivity;
import com.getmedcheck.lib.constant.Constants;
import com.getmedcheck.lib.events.EventClearCommand;
import com.getmedcheck.lib.events.EventReadingProgress;
import com.getmedcheck.lib.model.BleDevice;
import com.getmedcheck.lib.model.BloodGlucoseData;
import com.getmedcheck.lib.model.BloodPressureData;
import com.getmedcheck.lib.model.IDeviceData;
import com.getmedcheck.lib.utils.StringUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.AppUtils.showToastSort;
import static com.digidoctor.android.utility.utils.getDateInDMYFormatFromTimestamp;
import static com.digidoctor.android.utility.utils.isNetworkConnected;

public class DeviceConnectionActivity extends MedCheckActivity implements View.OnClickListener {
    private Date today = new Date();
    private BleDevice mBleDevice;
    private TextView mTvDeviceName, txtPid, txtSys, txtDias, txtPulse, btnSaveData, btnScan;
    private TextView mTvConnectionState;
    private Button mBtnConnect, mBtnReadData, mBtnClearData, mBtnTimeSync, mBtnDisconnect;
    private LinearLayout mLlCoreOperations, mLlBLEDeviceOperation;
    private TextView mTvResult;
    private boolean mAllPermissionsReady;
    private ProgressDialog dialog;


    int hour = 0, minutes = 0;
    Calendar c;
    private String time;

    public static void start(Context context, BleDevice bleDevice) {
        Intent starter = new Intent(context, DeviceConnectionActivity.class);
        starter.putExtra("DATA", bleDevice);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_connection);
        Toolbar toolbar = findViewById(R.id.toolbar2);
 /*       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Data");*/


        dialog = new ProgressDialog(DeviceConnectionActivity.this);


        dialog = new ProgressDialog(this);
        if (getIntent() != null && getIntent().hasExtra("DATA")) {
            mBleDevice = getIntent().getParcelableExtra("DATA");
            connectDevice();
        }
        initView();
        requestLocationPermission();
        if (mAllPermissionsReady) {
            checkDeviceOnline();
        } else {
            Toast.makeText(this, "Some of the Permissions are missing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            if (mBleDevice != null && !TextUtils.isEmpty(mBleDevice.getDeviceName())) {
                getSupportActionBar().setTitle(mBleDevice.getDeviceName());
            } else {
                getSupportActionBar().setTitle("Device Connection");
            }
        }

        mTvDeviceName = findViewById(R.id.tvDeviceName);
        mTvConnectionState = findViewById(R.id.tvStatus);
        mLlCoreOperations = findViewById(R.id.llCoreOperations);
        mLlBLEDeviceOperation = findViewById(R.id.llBLEDeviceOperation);
        txtPid = findViewById(R.id.txtPid);

        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);
        mBtnConnect = findViewById(R.id.btnConnect);
        mBtnConnect.setOnClickListener(this);
        mBtnReadData = findViewById(R.id.btnReadData);
        mBtnReadData.setOnClickListener(this);
        mBtnClearData = findViewById(R.id.btnClearData);
        mBtnClearData.setOnClickListener(this);
        mBtnTimeSync = findViewById(R.id.btnTimeSync);
        mBtnTimeSync.setOnClickListener(this);
        mBtnDisconnect = findViewById(R.id.btnDisconnect);
        mBtnDisconnect.setOnClickListener(this);
        btnSaveData = findViewById(R.id.btnSaveData);
        btnSaveData.setOnClickListener(this);

        mTvResult = findViewById(R.id.tvResult);
        txtSys = findViewById(R.id.txtSys);
        txtDias = findViewById(R.id.txtDias);
        txtPulse = findViewById(R.id.txtPulse);

        if (mBleDevice != null) {
            mTvDeviceName.setText(mBleDevice.getDeviceName());
        }

        registerCallback();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void initTime() {
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        time = hour + ":" + minutes;
    }

    @Override
    protected void onPermissionGrantedAndBluetoothOn() {
        mAllPermissionsReady = true;
        mLlCoreOperations.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDeviceClearCommand(int state) {
        super.onDeviceClearCommand(state);
        switch (state) {
            case EventClearCommand.CLEAR_START:
                mTvResult.setText("Clear Start");
                break;
            case EventClearCommand.CLEAR_COMPLETE:
                mTvResult.setText("Clear Successfully Completed");
                break;
            case EventClearCommand.CLEAR_FAIL:
                mTvResult.setText("Clear Fail");
                break;
        }
    }

    @Override
    protected void onDeviceConnectionStateChange(BleDevice bleDevice, int status) {
        super.onDeviceConnectionStateChange(bleDevice, status);
        if (bleDevice.getMacAddress().equals(mBleDevice.getMacAddress()) && status == 1) {
        }
    }

    @Override
    protected void onDeviceDataReadingStateChange(int state, String message) {
        super.onDeviceDataReadingStateChange(state, message);
        if (message.equalsIgnoreCase("Completed"))
            mTvConnectionState.setTextColor(getResources().getColor(R.color.green));
        else if (message.equalsIgnoreCase("Disconnected"))
            mTvConnectionState.setTextColor(getResources().getColor(R.color.red));
        else mTvConnectionState.setTextColor(getResources().getColor(R.color.greyLight));

        mTvConnectionState.setText(message);
        mBtnConnect.setEnabled(!(state == EventReadingProgress.COMPLETED));
    }

    @Override
    protected void onDeviceDataReceive(BluetoothDevice device, ArrayList<IDeviceData> deviceData, String json, String deviceType) {
        super.onDeviceDataReceive(device, deviceData, json, deviceType);
        if (deviceData == null) {
            return;
        }

        Log.e("MedcheckJson", "onDeviceDataReceive: " + json);

        if (deviceData.size() == 0) {
            mTvResult.setText("No Data Found!");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Type: ").append(deviceType).append("\n\n");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);

        for (IDeviceData deviceDatum : deviceData) {

            if (deviceDatum.getType().equals(Constants.TYPE_BPM)) {
                BloodPressureData bloodPressureData = (BloodPressureData) deviceDatum;

                stringBuilder.append("SYS: ").append(bloodPressureData.getSystolic()).append(" mmHg, ");
                stringBuilder.append("DIA: ").append(bloodPressureData.getDiastolic()).append(" mmHg, ");
                stringBuilder.append("PUL: ").append(bloodPressureData.getHeartRate()).append(" min\n");
                stringBuilder.append("IHB: ").append(bloodPressureData.getIHB()).append(", ");
                stringBuilder.append("DATE: ").append(sdf.format(bloodPressureData.getDateTime()));
                stringBuilder.append("\n------------------------\n");

            } else if (deviceDatum.getType().equals(Constants.TYPE_BGM)) {
                BloodGlucoseData bloodGlucoseData = (BloodGlucoseData) deviceDatum;

                DecimalFormat df = new DecimalFormat("0.0");
                float val = 0;
                if (StringUtils.isNumber(bloodGlucoseData.getHigh())) {
                    val = Float.parseFloat(bloodGlucoseData.getHigh()) / 18f;
                }

                stringBuilder.append(df.format(val)).append(" mmol/L (").append(bloodGlucoseData.getHigh()).append(" mg/dL)\n");
                stringBuilder.append(bloodGlucoseData.getAcPcStringValue()).append("\n");
                stringBuilder.append("DATE: ").append(sdf.format(bloodGlucoseData.getDateTime()));
                stringBuilder.append("\n------------------------\n");
            }
        }

        mTvResult.setText(stringBuilder.toString());
        BloodPressureData bloodPressureData = (BloodPressureData) deviceData.get(deviceData.size() - 1);
        txtSys.setText(bloodPressureData.getSystolic());
        txtDias.setText(bloodPressureData.getDiastolic());
        txtPulse.setText(bloodPressureData.getHeartRate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:
                if (mAllPermissionsReady) {
                    checkDeviceOnline();
                } else {
                    Toast.makeText(this, "Some of the Permissions are missing", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnReadData:
                readData();
                break;
            case R.id.btnClearData:
                clearData();
                break;
            case R.id.btnTimeSync:
                timeSync();
                break;
            case R.id.btnDisconnect:
                disconnectDevice();
                break;
            case R.id.btnSaveData:
                //saveBluetoothVital();
                updateBluetoothVitals();
                break;
            case R.id.btnScan:
                break;
        }
    }

    private void updateBluetoothVitals() {

        if (!txtPulse.getText().toString().equalsIgnoreCase(getString(R.string.no_data))
                && !txtSys.getText().toString().equalsIgnoreCase(getString(R.string.no_data))
                && !txtDias.getText().toString().equalsIgnoreCase(getString(R.string.no_data))) {
            JSONArray dtTableArray = new JSONArray();
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("vitalId", 206);
                jsonObject1.put("vitalValue", txtPulse.getText().toString());
                dtTableArray.put(jsonObject1);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("vitalId", 6);
                jsonObject2.put("vitalValue", txtDias.getText().toString());
                dtTableArray.put(jsonObject2);
                JSONObject jsonObject3 = new JSONObject();
                jsonObject3.put("vitalId", 4);
                jsonObject3.put("vitalValue", txtSys.getText().toString());
                dtTableArray.put(jsonObject3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dtTableArray.length() != 0) {
                if (isNetworkConnected(getApplicationContext())) {

                    String memberId = String.valueOf(utils.getPrimaryUser(this).getMemberId());

                    if (!memberId.equals("")) {
                        saveBluetoothVital(dtTableArray.toString(), memberId);
                    } else {
                        showToastSort(getApplicationContext(), "Please scan or enter PID");
                    }
                } else {
                    Toast.makeText(DeviceConnectionActivity.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "get data before upload", Toast.LENGTH_SHORT).show();
        }
    }


    public void saveBluetoothVital(String dt, String memberId) {
        showDialog("please wait...");
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();

        VitalModel model = new VitalModel();
        model.setDate(getDateInDMYFormatFromTimestamp(System.currentTimeMillis()));
        model.setTime(time);
        model.setDtDataTable(dt);
        model.setMemberId(memberId);

        Call<ResponseModel> call = iRestInterfaces.addVitals(model);
        call.enqueue(new Callback<ResponseModel>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)


            @Override
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        showToastSort(DeviceConnectionActivity.this, getString(R.string.vital_added_successfully));
                        // startActivity(new Intent(DeviceConnectionActivity.this, Dashboard.class));
                        finish();
                    } else
                        showToastSort(DeviceConnectionActivity.this, response.body().getResponseMessage());
                }
                hideDialog();
            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                hideDialog();
                showToastSort(DeviceConnectionActivity.this, t.getMessage());
            }
        });
    }


    @Override
    protected void onDeviceScanResult(no.nordicsemi.android.support.v18.scanner.ScanResult scanResult) {
        super.onDeviceScanResult(scanResult);
    }


    private void connectDevice() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        } else {
            MedCheck.getInstance().connect(this, mBleDevice.getMacAddress());
        }
    }

    private void checkDeviceOnline() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        } else {
            connectDevice();
        }
    }

    private void readData() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().writeCommand(this, mBleDevice.getMacAddress());
    }

    private void clearData() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().clearDevice(this, mBleDevice.getMacAddress());
    }

    private void timeSync() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().timeSyncDevice(this, mBleDevice.getMacAddress());
    }

    private void disconnectDevice() {
        if (mBleDevice == null || !mAllPermissionsReady || TextUtils.isEmpty(mBleDevice.getMacAddress())) {
            return;
        }
        MedCheck.getInstance().disconnectDevice(this);
    }


    public void showDialog(String msg) {
        dialog.setMessage(msg);
        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.dismiss();
    }
}
