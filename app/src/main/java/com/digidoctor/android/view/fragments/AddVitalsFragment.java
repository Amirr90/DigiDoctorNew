package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAddVitalaBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.view.activity.PatientDashboard;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.getDateInDMYFormatFromTimestamp;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;
import static com.digidoctor.android.utility.utils.isNetworkConnected;


public class AddVitalsFragment extends Fragment {
    private static final String TAG = "AddVitalsFragment";

    FragmentAddVitalaBinding addVitalsBinding;
    NavController navController;
    VitalModel vitalModel;

    private int mDay = 0, mMonth = 0, mYear = 0, hour = 0, minutes = 0;
    private Calendar c;
    private String date, time;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addVitalsBinding = FragmentAddVitalaBinding.inflate(getLayoutInflater());
        return addVitalsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        vitalModel = new VitalModel();

        addVitalsBinding.setVital(vitalModel);


        initTime();
        addVitalsBinding.btnAddVital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitalModel = addVitalsBinding.getVital();
                Log.d(TAG, "onClickVitalModel: " + vitalModel.toString());

                try {
                    if (checkFields(vitalModel)) {
                        if (isNetworkConnected(requireContext()))
                            addVitals(vitalModel);
                        else
                            Toast.makeText(requireActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onClick: " + e.getLocalizedMessage());
                    Toast.makeText(requireActivity(), "Enter some vital value before adding !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initTime() {
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minutes = c.get(Calendar.MINUTE);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        time = hour + ":" + minutes;
    }

    private boolean checkFields(VitalModel vitalModel) {
        if (null == vitalModel)
            return false;
        else if (vitalModel.getSys().isEmpty()
                && vitalModel.getDia().isEmpty()
                && vitalModel.getPulseRate().isEmpty()
                && vitalModel.getTemperature().isEmpty()
                && vitalModel.getRandomBloodSugar().isEmpty()
                && vitalModel.getSpo2().isEmpty()
                && vitalModel.getRespiratoryRate().isEmpty()
        )

            return false;

        else if (!vitalModel.getSys().isEmpty() && vitalModel.getDia().isEmpty()) {
            Toast.makeText(requireActivity(), getString(R.string.please_fill_diastolic), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!vitalModel.getDia().isEmpty() && vitalModel.getSys().isEmpty()) {
            Toast.makeText(requireActivity(), getString(R.string.please_fill_syslolic), Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private void addVitals(VitalModel vitalModel) {


        hideSoftKeyboard(requireActivity());

        try {
            VitalModel vitalModel1 = new VitalModel();
            User user = getPrimaryUser(requireActivity());

            vitalModel1.setMemberId(String.valueOf(user.getId()));

            vitalModel1.setDtDataTable(getDtTableValue(vitalModel));

            vitalModel1.setDate(getDateInDMYFormatFromTimestamp(System.currentTimeMillis()));
            vitalModel1.setTime(time);


            Log.d(TAG, "addVitals: " + vitalModel1.toString());


            showRequestDialog(requireActivity());

            ApiUtils.addVitals(vitalModel1, new ApiCallbackInterface() {
                @Override
                public void onSuccess(List<?> o) {
                    hideDialog();
                    Toast.makeText(requireActivity(), getString(R.string.vital_added_successfully), Toast.LENGTH_SHORT).show();
                    PatientDashboard.getInstance().onSupportNavigateUp();
                }

                @Override
                public void onError(String s) {
                    hideDialog();
                    Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    hideDialog();
                    Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            hideDialog();
            e.printStackTrace();
            Log.d(TAG, "addVitalsError : " + e.getLocalizedMessage());
        }


    }


    private String getDtTableValue(VitalModel vitalModel) throws JSONException {

        JSONArray dtTableArray = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("vitalId", "4");
        jsonObject1.put("vitalValue", vitalModel.getSys());
        dtTableArray.put(jsonObject1);


        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("vitalId", "6");
        jsonObject2.put("vitalValue", vitalModel.getDia());
        dtTableArray.put(jsonObject2);


        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("vitalId", "3");
        jsonObject3.put("vitalValue", vitalModel.getPulseRate());
        dtTableArray.put(jsonObject3);


        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("vitalId", "56");
        jsonObject4.put("vitalValue", vitalModel.getSpo2());
        dtTableArray.put(jsonObject4);


        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("vitalId", "5");
        jsonObject5.put("vitalValue", vitalModel.getTemperature());
        dtTableArray.put(jsonObject5);


        JSONObject jsonObject6 = new JSONObject();
        jsonObject6.put("vitalId", "10");
        jsonObject6.put("vitalValue", vitalModel.getRandomBloodSugar());
        dtTableArray.put(jsonObject6);


        JSONObject jsonObject7 = new JSONObject();
        jsonObject7.put("vitalId", "7");
        jsonObject7.put("vitalValue", vitalModel.getRespiratoryRate());
        dtTableArray.put(jsonObject7);


        return dtTableArray.toString();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}