package com.digidoctor.android;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.FragmentHomeIsolationBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.patientModel.HomeIsolationReqModel;
import com.digidoctor.android.model.patientModel.HospitalAndPackageResponse;
import com.digidoctor.android.model.patientModel.HospitalModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.isNetworkConnected;


public class HomeIsolationFragment extends Fragment {
    private static final String TAG = "HomeIsolationFragment";


    FragmentHomeIsolationBinding binding;
    NavController navController;
    VitalModel vitalModel;
    HomeIsolationReqModel homeIsolationReqModel;
    User user;

    PatientViewModel viewModel;


    AlertDialog.Builder hospitalBuilder, packageBuilder;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeIsolationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        setModalDataData();


        binding.btnRequestIsolation.setOnClickListener(v -> {
            if (isValidate()) {
                if (isNetworkConnected(requireActivity())) {
                    try {
                        sendHomeIsolationRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(requireActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            } else Log.d(TAG, "setModalDataData: false");
        });

        //listener for select hospital
        binding.tvHospital.setOnClickListener(v -> {
            if (null != hospitalBuilder)
                hospitalBuilder.show();
        });

        //listener for select Package hospital
        binding.tvSelectPackages.setOnClickListener(v -> {
            if (null != packageBuilder)
                packageBuilder.show();
        });

        //listener for select Corona Test Date
        binding.tvCoronaTestDate.setOnClickListener(v -> {
            showDatePickerDialog(binding.tvCoronaTestDate);
        });

        //listener for select Corona Test Date
        binding.tvDateOnSetSymptom.setOnClickListener(v -> {
            showDatePickerDialog(binding.tvDateOnSetSymptom);
        });

        //listener for select LifeSupport
        binding.tvLifeSupport.setOnClickListener(v -> {
            showLifeSupportDialog(binding.tvLifeSupport);
        });


    }

    private void sendHomeIsolationRequest() throws JSONException {
        AppUtils.showRequestDialog(requireActivity());
        HomeIsolationReqModel homeIsolationReqModel = binding.getIsolationModel();
        homeIsolationReqModel.setMemberId(user.getMemberId());
        if (null != homeIsolationReqModel.getTestdate())
            homeIsolationReqModel.setTestdate(AppUtils.parseDate(homeIsolationReqModel.getTestdate(), "yyyy-MM-dd", "EEE, MMM d, ''yyyy"));
        homeIsolationReqModel.setOnSetDate(AppUtils.parseDate(homeIsolationReqModel.getOnSetDate(), "yyyy-MM-dd", "EEE, MMM d, ''yyyy"));
        homeIsolationReqModel.setDtDataTable(getDtTableValue(binding.getVital()));
        ApiUtils.sendHomeIsolationRequest(homeIsolationReqModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                Toasty.success(App.context, "request submitted successfully !!", Toast.LENGTH_SHORT, true).show();
                navController.navigate(R.id.action_homeIsolationFragment_to_homeIsolationRequestListFragment);

            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toasty.error(App.context, s, Toast.LENGTH_SHORT, true).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toasty.error(App.context, throwable.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });
    }


    private void setHospitalList(List<HospitalModel> hospitalModels) {
        final CharSequence[] items = new CharSequence[hospitalModels.size()];
        for (int a = 0; a < hospitalModels.size(); a++) {
            items[a] = hospitalModels.get(a).getName();
        }
        hospitalBuilder = new AlertDialog.Builder(requireActivity());
        hospitalBuilder.setTitle(getString(R.string.select_hospital));
        hospitalBuilder.setItems(items, (dialog, item) -> {
            showToast(items[item].toString(), binding.tvHospital);
            homeIsolationReqModel.setHospitalId(hospitalModels.get(item).getId());
            dialog.dismiss();

        });
    }

    private void showToast(String s, TextView textView) {
        Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
        textView.setText(s);
    }

    private void setModalDataData() {
        user = getUserForBooking(requireActivity());
        vitalModel = new VitalModel();
        homeIsolationReqModel = new HomeIsolationReqModel();

        binding.setUser(user);
        binding.setIsolationModel(homeIsolationReqModel);
        binding.setVital(vitalModel);


        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        viewModel.getHospitalAndPackageList().observe(getViewLifecycleOwner(), hospitalAndPackageResponses -> {

            if (null != hospitalAndPackageResponses || !hospitalAndPackageResponses.isEmpty())
                setHospitalList(hospitalAndPackageResponses.get(0).getHospitalList());
            if (null != hospitalAndPackageResponses || !hospitalAndPackageResponses.isEmpty())
                setPackageList(hospitalAndPackageResponses.get(0).getPackageList());
        });

    }

    private void setPackageList(List<HospitalAndPackageResponse.PackageModel> hospitalList) {
        final CharSequence[] items = new CharSequence[hospitalList.size()];
        for (int a = 0; a < hospitalList.size(); a++) {
            items[a] = hospitalList.get(a).getPackageName();
        }
        packageBuilder = new AlertDialog.Builder(requireActivity());
        packageBuilder.setTitle(getString(R.string.select_hospital));
        packageBuilder.setItems(items, (dialog, item) -> {
            showToast(items[item].toString(), binding.tvSelectPackages);
            homeIsolationReqModel.setPackageId(hospitalList.get(item).getId());
            binding.tvPackagePrice.setText(AppUtils.getCurrencyFormat(hospitalList.get(item).getPackagePrice()));
            dialog.dismiss();

        });
    }

    private boolean isValidate() {

        if (null == binding.getIsolationModel().getHospitalId() || binding.tvHospital.getText().toString().isEmpty()) {
            Toasty.warning(requireActivity(), "Select Hospital !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (null == binding.getIsolationModel().getComorbidities() || binding.getIsolationModel().getComorbidities().isEmpty()) {
            Toasty.warning(requireActivity(), "Enter Comorbid !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (null == binding.getIsolationModel().getCurrentProblem() || binding.getIsolationModel().getCurrentProblem().isEmpty()) {
            Toasty.warning(requireActivity(), "Enter Symptoms !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (null == binding.getIsolationModel().getPackageId() || binding.tvSelectPackages.getText().toString().isEmpty()) {
            Toasty.warning(requireActivity(), "Select Package !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (binding.covidSwitchBtn.isChecked() && null == binding.getIsolationModel().getTestdate() && binding.tvCoronaTestDate.getText().toString().isEmpty()) {
            Toasty.warning(requireActivity(), "Select Corona Test Date !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } /*else if (null == binding.getIsolationModel().getAllergires() || binding.getIsolationModel().getAllergires().isEmpty()) {
            Toasty.warning(requireActivity(), "Enter Allergy !!", Toast.LENGTH_SHORT, true).show();
            return false;
        }*/ else if (null == binding.getIsolationModel().getLifeSupport() || binding.tvLifeSupport.getText().toString().isEmpty()) {
            Toasty.warning(requireActivity(), "Select Life support from list !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } else if (null == binding.getIsolationModel().getOnSetDate() || binding.tvDateOnSetSymptom.getText().toString().isEmpty()) {
            Toasty.warning(requireActivity(), "Select onSet Date of Symptoms !!", Toast.LENGTH_SHORT, true).show();
            return false;
        } /*else if (null == binding.getVital())
            return false;*/
       /* else if (binding.getVital().getSys().isEmpty()
                && binding.getVital().getDia().isEmpty()
                && binding.getVital().getPulseRate().isEmpty()
                && binding.getVital().getTemperature().isEmpty()
                && binding.getVital().getRandomBloodSugar().isEmpty()
                && binding.getVital().getSpo2().isEmpty()
                && binding.getVital().getRespiratoryRate().isEmpty())
            return false;*/

        else if (!vitalModel.getSys().isEmpty() && vitalModel.getDia().isEmpty()) {
            Toast.makeText(requireActivity(), getString(R.string.please_fill_diastolic), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!vitalModel.getDia().isEmpty() && vitalModel.getSys().isEmpty()) {
            Toast.makeText(requireActivity(), getString(R.string.please_fill_syslolic), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Log.d(TAG, "isValidate: " + binding.getIsolationModel().toString());


            return true;
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

    public void showLifeSupportDialog(TextView textView) {
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.life_support_element,
                null, false);

        // You have to list down your form elements
        final RadioGroup lifeSupportRadioGroup = (RadioGroup) formElementsView
                .findViewById(R.id.genderRadioGroup);

        final EditText etO2 = (EditText) formElementsView
                .findViewById(R.id.etO2);

        etO2.setVisibility(View.GONE);

        lifeSupportRadioGroup.setOnCheckedChangeListener((group, checkedId) -> etO2.setVisibility(checkedId == R.id.otRadioButton ? View.VISIBLE : View.GONE));
        // the alert dialog
        new AlertDialog.Builder(requireActivity()).setView(formElementsView)
                .setTitle("Select Life Support Option")
                .setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, id) -> {
                    String o2Value = "";
                    String lifeSupport = "";
                    // get selected radio button from radioGroup
                    int selectedId = lifeSupportRadioGroup.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton selectedRadioButton = (RadioButton) formElementsView.findViewById(selectedId);

                    lifeSupport += selectedRadioButton.getText();
                    o2Value = etO2.getText().toString();
                    if (selectedId == R.id.otRadioButton && o2Value.isEmpty()) {
                        Toast.makeText(requireActivity(), "Enter O2 value", Toast.LENGTH_SHORT).show();
                    }

                    lifeSupport += o2Value.isEmpty() ? "" : " : " + o2Value;

                    binding.getIsolationModel().setLifeSupport(lifeSupport);
                    binding.getIsolationModel().setO2(o2Value);
                    showToast(lifeSupport, textView);

                    Log.d(TAG, "showLifeSupportDialog: lifeSupport " + lifeSupport);
                    Log.d(TAG, "showLifeSupportDialog: O2 " + lifeSupport);
                    dialog.cancel();
                }).setCancelable(false).show();
    }

    private void showDatePickerDialog(TextView textView) {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        myDatePicker.setMaxDate(System.currentTimeMillis());
        myDatePicker.setCalendarViewShown(false);
        new android.app.AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(getString(R.string.select_date))
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    dialog.cancel();
                    int month = myDatePicker.getMonth() + 1;
                    int day = myDatePicker.getDayOfMonth();
                    int year = myDatePicker.getYear();
                    String date = (year + "/" + month + "/" + day);
                    textView.setText(AppUtils.parseDate(date, "EEE, MMM d, ''yyyy", "yyyy/MM/dd"));

                }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}