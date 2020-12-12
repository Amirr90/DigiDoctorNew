package com.digidoctor.android.view.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.MedicineAdapter;
import com.digidoctor.android.databinding.FragmentAddPrescriptionManuallyBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.MedicineModel;
import com.digidoctor.android.model.PrescriptionModel;
import com.digidoctor.android.repositories.PatientRepo;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.getDateInDMYFormatFromTimestampInDayMonthFormat;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;
import static com.digidoctor.android.utility.utils.parseDateToYMDToMMDD;


public class AddPrescriptionManuallyFragment extends Fragment {
    private static final String TAG = "AddPrescriptionManually";

    FragmentAddPrescriptionManuallyBinding addPrescriptionManuallyBinding;
    PrescriptionModel prescriptionModel;
    PatientViewModel viewModel;
    PatientRepo patientRepo = new PatientRepo();


    MedicineAdapter adapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addPrescriptionManuallyBinding = FragmentAddPrescriptionManuallyBinding.inflate(getLayoutInflater());
        return addPrescriptionManuallyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        prescriptionModel = new PrescriptionModel();
        prescriptionModel.setStartDate(getDateInDMYFormatFromTimestampInDayMonthFormat(System.currentTimeMillis()));

        addPrescriptionManuallyBinding.setPrescriptionModel(prescriptionModel);


        addPrescriptionManuallyBinding.tvDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        setMedicineDataToAutoCompleteTv();


        addPrescriptionManuallyBinding.btnAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllAddMedicineFieldsFilled()) {
                    addMedicineData();
                }
            }
        });


        adapter = new MedicineAdapter();
        addPrescriptionManuallyBinding.recInputMedicine.setAdapter(adapter);
        viewModel.getInputMedicine().observe(getViewLifecycleOwner(), new Observer<List<MedicineModel.MedicineDetailModel>>() {
            @Override
            public void onChanged(List<MedicineModel.MedicineDetailModel> medicineDetailModels) {

                addPrescriptionManuallyBinding.recInputMedicine.setVisibility(medicineDetailModels.isEmpty() ? View.GONE : View.VISIBLE);

                adapter.submitList(medicineDetailModels);

                Log.d(TAG, "onChanged: " + medicineDetailModels.toString());

            }
        });

    }

    private void addMedicineData() {

        MedicineModel.MedicineDetailModel medicineDetailModel = new MedicineModel.MedicineDetailModel();

        medicineDetailModel.setMedicineName(addPrescriptionManuallyBinding.tcAutoMedicine.getText().toString());
        medicineDetailModel.setFrequencyName(addPrescriptionManuallyBinding.etFrequency.getText().toString().trim());
        medicineDetailModel.setDays(addPrescriptionManuallyBinding.etDays.getText().toString().trim());

        patientRepo.loadMedicineData(medicineDetailModel);

        Log.d(TAG, "addMedicineDataClicked: " + medicineDetailModel.toString());


    }

    private boolean isAllAddMedicineFieldsFilled() {
        String medicineName = addPrescriptionManuallyBinding.tcAutoMedicine.getText().toString();
        String medicineFrequency = addPrescriptionManuallyBinding.etFrequency.getText().toString().trim();
        String medicineDays = addPrescriptionManuallyBinding.etDays.getText().toString().trim();

        if (TextUtils.isEmpty(medicineName)) {
            Toast.makeText(requireActivity(), "please add medicine name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(medicineFrequency)) {
            Toast.makeText(requireActivity(), "please add medicine frequency", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(medicineDays)) {
            Toast.makeText(requireActivity(), "please add medicine day(s)", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private void setMedicineDataToAutoCompleteTv() {
        showRequestDialog(requireActivity());
        ApiUtils.getMedicineData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<MedicineModel> responseValue = (List<MedicineModel>) o;
                hideDialog();

                List<MedicineModel.MedicineDetailModel> medicineList = responseValue.get(0).getMedicineList();
                bindMedicineData(medicineList);


                List<MedicineModel.MedicineFrequencyModel> frequencyList = responseValue.get(0).getFrequencyList();
                bindFrequencyData(frequencyList);


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

    }

    private void bindFrequencyData(final List<MedicineModel.MedicineFrequencyModel> frequencyList) {
        Log.d(TAG, "bindFrequencyData: " + frequencyList.toString());
        final List<String> medicineData = new ArrayList<>();
        for (MedicineModel.MedicineFrequencyModel medicineDetailModel : frequencyList)
            medicineData.add(medicineDetailModel.getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (requireActivity(), R.layout.inflate_auto_complete_text, medicineData);
        addPrescriptionManuallyBinding.etFrequency.setThreshold(1);
        addPrescriptionManuallyBinding.etFrequency.setAdapter(adapter);
        addPrescriptionManuallyBinding.etFrequency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                /*for (MedicineModel.MedicineFrequencyModel medicineDetailModel : frequencyList) {
                    if (medicineDetailModel.getName().equalsIgnoreCase(item.toString())) {
                        addPrescriptionManuallyBinding.etFrequency.setText(medicineDetailModel.getName());
                        AppUtils.hideDialog();
                    }
                }*/
            }
        });

    }

    private void bindMedicineData(final List<MedicineModel.MedicineDetailModel> medicineList) {


        final List<String> medicineData = new ArrayList<>();
        for (MedicineModel.MedicineDetailModel medicineDetailModel : medicineList)
            medicineData.add(medicineDetailModel.getMedicineName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (requireActivity(), R.layout.inflate_auto_complete_text, medicineData);
        addPrescriptionManuallyBinding.tcAutoMedicine.setThreshold(1);
        addPrescriptionManuallyBinding.tcAutoMedicine.setAdapter(adapter);
        addPrescriptionManuallyBinding.tcAutoMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AppUtils.showRequestDialog(requireActivity());
                Object item = adapterView.getItemAtPosition(position);

                hideSoftKeyboard(requireActivity());
                for (MedicineModel.MedicineDetailModel medicineDetailModel : medicineList) {
                    if (medicineDetailModel.getMedicineName().equalsIgnoreCase(item.toString())) {
                        addPrescriptionManuallyBinding.etFrequency.setText(medicineDetailModel.getFrequencyName());
                        Toast.makeText(requireActivity(), "FreName: " + medicineDetailModel.getFrequencyName(), Toast.LENGTH_SHORT).show();
                        AppUtils.hideDialog();
                    }
                }
            }
        });


       /* ArrayAdapter arrayAdapter = new ArrayAdapter<>(requireActivity(), R.layout.inflate_auto_complete_text, medicineList);
        arrayAdapter.setDropDownViewResource(R.layout.inflate_auto_complete_text);
        addPrescriptionManuallyBinding.tcAutoMedicine.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        addPrescriptionManuallyBinding.tcAutoMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
*/
    }

    private void showDatePickerDialog() {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        myDatePicker.setCalendarViewShown(false);
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(getString(R.string.select_date))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String date = (year + "/" + month + "/" + day);
                        addPrescriptionManuallyBinding.tvDateTime.setText(parseDateToYMDToMMDD(date));
                    }

                }).show();
    }


}