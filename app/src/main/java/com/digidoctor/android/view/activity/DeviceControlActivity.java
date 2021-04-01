package com.digidoctor.android.view.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.digidoctor.android.R;
import com.digidoctor.android.blueTooth.BluetoothLeService1;
import com.digidoctor.android.blueTooth.SampleGattAttributes;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.utility.URLUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.AppUtils.showToastSort;
import static com.digidoctor.android.utility.utils.getDateInDMYFormatFromTimestamp;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.isNetworkConnected;

public class DeviceControlActivity extends AppCompatActivity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    static String sys, dias, pulse;
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private TextView mConnectionState;
    private TextView mDataField1, mDataField2, mDataField3, btnGetData, btnScan;
    EditText txtPid;
    private Button btnSaveData;
    private String mDeviceName;
    private Date today = new Date();
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService1 mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private ProgressDialog dialog;


    int hour = 0, minutes = 0;
    Calendar c;
    private String time;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService1.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService1.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService1.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService1.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService1.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService1.EXTRA_DATA), intent.getStringExtra(BluetoothLeService1.EXTRA_DATA1), intent.getStringExtra(BluetoothLeService1.EXTRA_DATA2));
                sys = intent.getStringExtra(BluetoothLeService1.EXTRA_DATA2);
                dias = intent.getStringExtra(BluetoothLeService1.EXTRA_DATA);
                pulse = intent.getStringExtra(BluetoothLeService1.EXTRA_DATA1);
            }
        }
    };

    private final ExpandableListView.OnChildClickListener servicesListClickListner = new ExpandableListView.OnChildClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                    int childPosition, long id) {
            if (mGattCharacteristics != null) {
                final BluetoothGattCharacteristic characteristic =
                        mGattCharacteristics.get(groupPosition).get(childPosition);
                final int charaProp = characteristic.getProperties();
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                    if (mNotifyCharacteristic != null) {
                        mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                        mNotifyCharacteristic = null;
                    }
                    mBluetoothLeService.readCharacteristic(characteristic);
                }
                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mNotifyCharacteristic = characteristic;
                    mBluetoothLeService.setCharacteristicNotification(
                            characteristic, true);
                }
                return true;
            }
            return false;
        }
    };

    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField1.setText(R.string.no_data);
        mDataField2.setText(R.string.no_data);
        mDataField3.setText(R.string.no_data);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Data");
       /* setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Data");*/

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
        mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
        mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField1 = (TextView) findViewById(R.id.txtSpo2);
        mDataField2 = (TextView) findViewById(R.id.txtPulse);
        mDataField3 = (TextView) findViewById(R.id.txtSys);
        btnGetData = (TextView) findViewById(R.id.btnGetData);
        btnSaveData = (Button) findViewById(R.id.btnSaveData);
        txtPid = findViewById(R.id.txtPid);
        btnScan = (TextView) findViewById(R.id.btnScan);
        mGattServicesList.setVisibility(View.GONE);

        dialog = new ProgressDialog(this);

        if (getIntent().getStringExtra("status1") != null) {
            btnScan.setVisibility(View.GONE);
            btnSaveData.setVisibility(View.VISIBLE);
            btnSaveData.setText("OK");
            txtPid.setVisibility(View.GONE);
//            txtPid.setText(String.valueOf(SharedPrefManager.getInstance(DeviceControlActivity.this).getPid()));
        } else {
            btnScan.setVisibility(View.VISIBLE);
            btnSaveData.setVisibility(View.VISIBLE);
            txtPid.setVisibility(View.VISIBLE);
        }
        if (getIntent().getStringExtra("status") != null) {
            txtPid.setText(getIntent().getStringExtra("id"));
//            txtPid.setText(String.valueOf(SharedPrefManager.getInstance(DeviceControlActivity.this).getPid()));
            mDataField1.setText(dias);
            mDataField2.setText(pulse);
            mDataField3.setText(sys);
        }
        /*Objects.requireNonNull(getActionBar()).setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);*/
        Intent gattServiceIntent = new Intent(this, BluetoothLeService1.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeviceControlActivity.this.displayData(dias, pulse, sys);
            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /* Intent intent1 = new Intent(DeviceControlActivity.this, ScannerActivity.class);
            intent1.putExtra("redi", "5");
            startActivity(intent1);*/
            }
        });
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DeviceControlActivity.this.getIntent().getStringExtra("status1") != null) {
                    DeviceControlActivity.this.onBackPressed();
                } else {
                    try {
                        if ((!pulse.equalsIgnoreCase("No data")) && (!dias.equalsIgnoreCase("No data")) && (!sys.equalsIgnoreCase("No data"))) {
                            JSONArray dtTableArray = new JSONArray();
                            try {
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("vitalId", 206);
                                jsonObject1.put("vitalValue", pulse);
                                dtTableArray.put(jsonObject1);
                                JSONObject jsonObject2 = new JSONObject();
                                jsonObject2.put("vitalId", 6);
                                jsonObject2.put("vitalValue", dias);
                                dtTableArray.put(jsonObject2);
                                JSONObject jsonObject3 = new JSONObject();
                                jsonObject3.put("vitalId", 4);
                                jsonObject3.put("vitalValue", sys);
                                dtTableArray.put(jsonObject3);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (dtTableArray.length() != 0) {
                                if (isNetworkConnected(DeviceControlActivity.this.getApplicationContext())) {


                                    String memberId = String.valueOf(getPrimaryUser(DeviceControlActivity.this).getMemberId());
                                    if (memberId.equals("")) {
                                        DeviceControlActivity.this.saveBluetoothVital(dtTableArray.toString(), memberId);
                                    } else {
                                        showToastSort(DeviceControlActivity.this.getApplicationContext(), "Please scan or enter PID");
                                    }
                                } else {
                                    Toast.makeText(DeviceControlActivity.this, "Network connection not found!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else
                            Toast.makeText(DeviceControlActivity.this, "Error in data.\nPlease retest", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private void initTime() {
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        time = hour + ":" + minutes;
    }

    public void saveBluetoothVital(String dt, String memberId) {

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();

        VitalModel model = new VitalModel();
        model.setDate(getDateInDMYFormatFromTimestamp(System.currentTimeMillis()));
        model.setTime(time);
        model.setMemberId(memberId);
        model.setDtDataTable(dt);

        Call<ResponseModel> call = iRestInterfaces.addVitals(model);
        call.enqueue(new Callback<ResponseModel>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        showToastSort(DeviceControlActivity.this, getString(R.string.vital_added_successfully));
                        //startActivity(new Intent(DeviceControlActivity.this, Dashboard.class));
                        finish();
                    } else
                        showToastSort(DeviceControlActivity.this, response.body().getResponseMessage());
                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideDialog();
                showToastSort(DeviceControlActivity.this, t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void getData() {
        if (mGattCharacteristics != null) {
            final BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(5).get(0);
            final int charaProp = characteristic.getProperties();
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                if (mNotifyCharacteristic != null) {
                    mBluetoothLeService.setCharacteristicNotification(mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                mBluetoothLeService.readCharacteristic(characteristic);
            }
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                mNotifyCharacteristic = characteristic;
                mBluetoothLeService.setCharacteristicNotification(
                        characteristic, true);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data, String data1, String data2) {
        if (data != null) {
            mDataField1.setText(data);
            mDataField2.setText(data1);
            mDataField3.setText(data2);
        }
    }

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        String LIST_NAME = "NAME";
        String LIST_UUID = "UUID";
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);
            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();

            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        SimpleExpandableListAdapter gattServiceAdapter = new SimpleExpandableListAdapter(
                this,
                gattServiceData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{LIST_NAME, LIST_UUID},
                new int[]{android.R.id.text1, android.R.id.text2},
                gattCharacteristicData,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{LIST_NAME, LIST_UUID},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        mGattServicesList.setAdapter(gattServiceAdapter);
        getData();
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService1.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService1.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService1.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService1.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
