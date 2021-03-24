package com.digidoctor.android.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.GattServicesCharacteristicsBinding;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.URLUtils;
import com.digidoctor.android.utility.utils;
import com.inuker.bluetooth.library.Code;
import com.inuker.bluetooth.library.Constants;
import com.vphealthy.oximetersdk.OxiOprateManager;
import com.vphealthy.oximetersdk.listener.base.IABleConnectStatusListener;
import com.vphealthy.oximetersdk.listener.base.IBleWriteResponse;
import com.vphealthy.oximetersdk.listener.data.OnACKDataListener;
import com.vphealthy.oximetersdk.model.data.AckData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.utils.getDateInDMYFormatFromTimestamp;

public class DataActivity extends AppCompatActivity {

    private final static String TAG = DataActivity.class.getSimpleName();
    static String spo2, pulse, pi, hrv;
    GattServicesCharacteristicsBinding binding;
    String mac;

    int hour = 0, minutes = 0;
    Calendar c;
    private String time;

    private IBleWriteResponse getBleWriteResponse() {
        return new IBleWriteResponse() {
            @Override
            public void onResponse(int state) {
                if (state == Code.REQUEST_SUCCESS) {
                    Log.i(TAG, "write success");
                } else {
                    Log.i(TAG, "write fail");
                }
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GattServicesCharacteristicsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        init();
    }

    private void init() {
        mac = getIntent().getStringExtra("mac");
        listenState(mac);

        binding.deviceAddress.setText(mac);

        binding.btnScan.setOnClickListener(view -> {
        });

        if (getIntent().getStringExtra("id") != null) {
            binding.txtId.setText(getIntent().getStringExtra("id"));
        }

        binding.btnGetData.setOnClickListener(view -> {
            start_recevice_data();
        });

        binding.btnSaveData.setOnClickListener(view -> {
//            displayData(spo2, pulse);
            JSONArray dtTableArray = new JSONArray();
            try {
                if (pulse != null && !pulse.equals("0")) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("vitalId", "3");
                    jsonObject1.put("vitalValue", pulse);
                    dtTableArray.put(jsonObject1);
                }

                if (spo2 != null && !spo2.equals("0")) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("vitalId", "56");
                    jsonObject2.put("vitalValue", spo2);
                    dtTableArray.put(jsonObject2);
                }
                if (hrv != null && !spo2.equals("0")) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("vitalId", "204");
                    jsonObject2.put("vitalValue", hrv);
                    dtTableArray.put(jsonObject2);
                }
                if (pi != null && !spo2.equals("0")) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("vitalId", "203");
                    jsonObject2.put("vitalValue", pi);
                    dtTableArray.put(jsonObject2);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dtTableArray.length() != 0) {
                if (utils.isNetworkConnected(this)) {
                    saveBluetoothVital(dtTableArray.toString());
                } else {
                    AppUtils.showToastSort(this, getString(R.string.noInternetConnection));
                }
            }
        });

        if (getIntent().getStringExtra("show") != null) {
            if (getIntent().getStringExtra("show").equalsIgnoreCase("0")) {
                binding.ll.setVisibility(View.GONE);
            } else binding.ll.setVisibility(View.VISIBLE);
        }

        initTime();
    }

    private void listenState(String mac) {


        OxiOprateManager.getMangerInstance(getApplicationContext()).registerConnectStatusListener(mac, new IABleConnectStatusListener() {
            @Override
            public void onConnectStatusChanged(String mac, int code) {
                if (code == Constants.STATUS_CONNECTED) {
                    String connectStr = getString(R.string.connected);
                    Log.i(TAG, connectStr);
                    binding.connectionState.setText(connectStr);
                } else {
                    String unConnectStr = getString(R.string.disconnected);
                    Log.i(TAG, unConnectStr);
                    binding.connectionState.setText(unConnectStr);
                    binding.txtPulse.setText(getString(R.string.no_data));
                    binding.txtSpo2.setText(getString(R.string.no_data));
                }
            }
        });
    }


    public void start_recevice_data() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).startListenTestData(getBleWriteResponse(), new OnACKDataListener() {
            @Override
            public void onDataChange(AckData ackData) {
                listenDetectResult();
            }
        });
    }

    public void listenDetectResult() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).setOnDetectDataListener(detectData -> {
            binding.txtSpo2.setText(String.valueOf(detectData.getSpo2h()));
            binding.txtPulse.setText(String.valueOf(detectData.getHeart()));
            binding.tvHrv.setText(String.valueOf(detectData.getHrv()));
            binding.tvPi.setText(String.valueOf(detectData.getPerfusionIndex()));

            binding.textView46.setVisibility(View.VISIBLE);
            binding.textView45.setVisibility(View.VISIBLE);
            binding.tvHrv.setVisibility(View.VISIBLE);
            binding.tvPi.setVisibility(View.VISIBLE);

            pulse = String.valueOf(detectData.getHeart());
            spo2 = String.valueOf(detectData.getSpo2h());
            hrv = String.valueOf(detectData.getHeart());
            pi = String.valueOf(detectData.getPerfusionIndex());

            Log.d(TAG, "DataDetected: " + detectData);
        });
    }

    public void disconnectDevice() {
        OxiOprateManager.getMangerInstance(getApplicationContext()).disconnectWatch(mac);
        finish();
    }


    private void initTime() {
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        time = hour + ":" + minutes;
    }


    @Override
    public void onBackPressed() {
        disconnectDevice();
    }

    public void saveBluetoothVital(String dt) {
        String memberId = String.valueOf(utils.getPrimaryUser(this).getMemberId());

        AppUtils.showRequestDialog(this);
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
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getResponseCode() == 1)
                        AppUtils.showToastSort(DataActivity.this, getString(R.string.vital_added_successfully));
                    else
                        AppUtils.showToastSort(DataActivity.this, response.body().getResponseMessage());
                }
                AppUtils.hideDialog();
            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                AppUtils.showToastSort(DataActivity.this, t.getMessage());
            }
        });
    }

}