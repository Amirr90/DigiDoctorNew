package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.adapters.MedicineAdapter;
import com.digidoctor.android.adapters.SelectedImageAdapter;
import com.digidoctor.android.databinding.FragmentAddPrescriptionManuallyBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ImageClickListener;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.MedicineModel;
import com.digidoctor.android.model.PrescriptionDtTableModel;
import com.digidoctor.android.model.PrescriptionModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.getDate;
import static com.digidoctor.android.utility.utils.getDateInDMYFormatFromTimestampInDayMonthFormat;
import static com.digidoctor.android.utility.utils.getDtTableData;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;
import static com.digidoctor.android.utility.utils.parseDateToYMDToMMDD;


public class AddPrescriptionManuallyFragment extends Fragment implements OnClickListener, ImageClickListener {
    private static final String TAG = "AddPrescriptionManually";
    public static final int REQ_CAPTURE_FROM_CAMERA = 10;

    FragmentAddPrescriptionManuallyBinding addPrescriptionManuallyBinding;
    PrescriptionModel prescriptionModel;

    MedicineAdapter adapter;

    List<MedicineModel.MedicineDetailModel> medicineDetailModels;
    List<PrescriptionDtTableModel> tableModelsList;

    public static AddPrescriptionManuallyFragment instance;

    public static AddPrescriptionManuallyFragment getInstance() {
        return instance;
    }

    PrescriptionDtTableModel dtTableModel = new PrescriptionDtTableModel();

    List<PrescriptionDtTableModel> prescriptionDtTableModels = new ArrayList<>();


    String Prescription_Url = null;


    List<MedicineModel.MedicineFrequencyModel> frequencyList;

    ArrayList<String> imagePicked;


    SelectedImageAdapter imageAdapter;


    Boolean isUploadPrescription = false;


    PatientViewModel viewModel;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addPrescriptionManuallyBinding = FragmentAddPrescriptionManuallyBinding.inflate(getLayoutInflater());
        instance = this;
        return addPrescriptionManuallyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        prescriptionModel = new PrescriptionModel();
        tableModelsList = new ArrayList<>();
        prescriptionModel.setStartDate(getDateInDMYFormatFromTimestampInDayMonthFormat(System.currentTimeMillis()));

        addPrescriptionManuallyBinding.setPrescriptionModel(prescriptionModel);


        addPrescriptionManuallyBinding.tvDateTime.setOnClickListener(view1 -> showDatePickerDialog());

        //setting Selected ImageRec
        imagePicked = new ArrayList<>();
        imageAdapter = new SelectedImageAdapter(imagePicked, this);
        addPrescriptionManuallyBinding.recSelectedImage.setAdapter(imageAdapter);
        updateSelectedImageRecVisibility();


        //Setting AutoComplete Text View
        setMedicineDataToAutoCompleteTv();


        //Adding Medicine details
        addPrescriptionManuallyBinding.btnAddMedicine.setOnClickListener(view12 -> {
            if (isAllAddMedicineFieldsFilled()) {
                addMedicineData();
            }
        });


        //selection Image for Prescription
        addPrescriptionManuallyBinding.btnBrowseImage.setOnClickListener(view13 -> {
            if (imageAdapter.getItemCount() == 0) {
                selectImage();
            } else {
                Toast.makeText(requireActivity(), "Image Already selected", Toast.LENGTH_SHORT).show();
            }

        });
        addPrescriptionManuallyBinding.cvSelectImage.setOnClickListener(view14 -> {
            if (imageAdapter.getItemCount() == 0) {
                selectImage();
            } else {
                Toast.makeText(requireActivity(), "Image Already selected", Toast.LENGTH_SHORT).show();
            }
        });


        //Adding Prescription
        addPrescriptionManuallyBinding.btnSubmitPrescription.setOnClickListener(view15 -> {
            prescriptionModel = addPrescriptionManuallyBinding.getPrescriptionModel();
            Log.d(TAG, "ImageUrl: " + Prescription_Url);

            if (isUploadPrescription) {
                if (checkFieldsFroUploadPrescription()) {
                    try {
                        uploadPrescription();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "uploadPresError: " + e.getLocalizedMessage());
                    }
                }
            } else {
                if (checkAllField(prescriptionModel)) {
                    submitPrescription(prescriptionModel);
                }
            }

        });


        //setting Added medicine Data To RecView
        medicineDetailModels = new ArrayList<>();
        adapter = new MedicineAdapter(prescriptionDtTableModels, this);
        addPrescriptionManuallyBinding.recInputMedicine.setAdapter(adapter);

    }

    private boolean checkFieldsFroUploadPrescription() {
        if (null == prescriptionModel.drName || prescriptionModel.drName.isEmpty()) {
            Toast.makeText(requireActivity(), "please enter Doctor's name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == prescriptionModel.diagnosis || prescriptionModel.diagnosis.isEmpty()) {
            Toast.makeText(requireActivity(), "please enter Doctor's diagnosis", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private void uploadPrescription() throws IOException {
        ApiUtils.uploadMultipleFile(imagePicked, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<String> filePaths = (List<String>) o;
                prescriptionModel.setDtFileDataTable(filePaths.get(0));
                adapter.deleteAllItems();
                submitPrescription(prescriptionModel);

            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitPrescription(PrescriptionModel prescriptionModel) {

        prescriptionModel.setDtDataTable(getDtTableData(adapter.getMedicineData()));
        prescriptionModel.setServiceProviderName(prescriptionModel.getDrName());

        Log.d(TAG, "submitPrescriptionModel: " + prescriptionModel.toString());


        User user = getPrimaryUser(requireActivity());


        prescriptionModel.setMemberId(String.valueOf(user.getId()));
        prescriptionModel.setProblemName(prescriptionModel.getDiagnosis());
        prescriptionModel.setStartDate(getDate(addPrescriptionManuallyBinding.tvDateTime.getText().toString()));


        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.addPrescription(prescriptionModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Prescription Added !!", Toast.LENGTH_SHORT).show();
                PatientDashboard.getInstance().onSupportNavigateUp();
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private boolean checkAllField(PrescriptionModel prescriptionModel) {

        if (Prescription_Url == null) {
            if (null == prescriptionModel)
                return false;
            if (null == prescriptionModel.drName || prescriptionModel.drName.isEmpty()) {
                Toast.makeText(requireActivity(), "please enter Doctor's name", Toast.LENGTH_SHORT).show();
                return false;
            } else if (null == prescriptionModel.diagnosis || prescriptionModel.diagnosis.isEmpty()) {
                Toast.makeText(requireActivity(), "please enter Doctor's diagnosis", Toast.LENGTH_SHORT).show();
                return false;
            } else if (null == prescriptionModel.startDate || prescriptionModel.startDate.isEmpty()) {
                Toast.makeText(requireActivity(), "please select prescription date", Toast.LENGTH_SHORT).show();
                return false;
            } else if (null == prescriptionDtTableModels || prescriptionDtTableModels.isEmpty() || null == adapter.getMedicineData() || adapter.getMedicineData().isEmpty()) {
                Toast.makeText(requireActivity(), "fill medicine details", Toast.LENGTH_SHORT).show();
                return false;
            } else
                return true;
        } else {
            if (null == prescriptionModel)
                return false;
            if (null == prescriptionModel.drName || prescriptionModel.drName.isEmpty()) {
                Toast.makeText(requireActivity(), "please enter Doctor's name", Toast.LENGTH_SHORT).show();
                return false;
            } else if (null == prescriptionModel.diagnosis || prescriptionModel.diagnosis.isEmpty()) {
                Toast.makeText(requireActivity(), "please enter Doctor's diagnosis", Toast.LENGTH_SHORT).show();
                return false;
            } else return true;
        }
    }

    private void selectImage() {
        ImagePicker.Companion.with(this)
                .crop(8f, 8f)                    //Crop image(Optional), Check Customization for more option
                .compress(512)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (null != data) {
                try {
                    Uri uri = data.getData();
                    imagePicked.add(uri.toString());
                    updateImageRecyclerView(imagePicked);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private void updateImageRecyclerView(ArrayList<String> imagePicked) {

        if (!imageAdapter.addMoreImage(imagePicked)) {
            Toast.makeText(requireActivity(), "Image Already Selected", Toast.LENGTH_SHORT).show();
            imageAdapter.notifyDataSetChanged();
        }
        updateSelectedImageRecVisibility();
    }

    public void updateSelectedImageRecVisibility() {
        addPrescriptionManuallyBinding.textView47.setText(imageAdapter.getItemCount() == 0 ? getString(R.string.select_image) : getString(R.string.selected_images));
        addPrescriptionManuallyBinding.recSelectedImage.setVisibility(imageAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
        addPrescriptionManuallyBinding.cvSelectImage.setVisibility(imageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        addPrescriptionManuallyBinding.clMedication.setVisibility(imageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        addPrescriptionManuallyBinding.recInputMedicine.setVisibility(imageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        isUploadPrescription = imageAdapter.getItemCount() != 0;

    }


    private void addMedicineData() {
        dtTableModel.setMedicineName(addPrescriptionManuallyBinding.tcAutoMedicine.getText().toString());
        dtTableModel.setFrequencyName(addPrescriptionManuallyBinding.etFrequency.getText().toString().trim());
        dtTableModel.setDays(addPrescriptionManuallyBinding.etDays.getText().toString().trim());


        if (!adapter.addItems(dtTableModel))
            Toast.makeText(requireActivity(), "Medicine already added", Toast.LENGTH_SHORT).show();
        else {
            dtTableModel = new PrescriptionDtTableModel();
            setMedicineFieldsEmpty();
        }


    }

    private void setMedicineFieldsEmpty() {
        addPrescriptionManuallyBinding.tcAutoMedicine.setText("");
        addPrescriptionManuallyBinding.etFrequency.setText("");
        addPrescriptionManuallyBinding.etDays.setText("");
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
        } else if (!isValidFrequencySelected(medicineFrequency)) {
            Toast.makeText(requireActivity(), "please select valid frequency", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(medicineDays)) {
            Toast.makeText(requireActivity(), "please add medicine day(s)", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private boolean isValidFrequencySelected(String medicineFrequency) {
        if (null == frequencyList || frequencyList.isEmpty())
            return false;

        for (MedicineModel.MedicineFrequencyModel frequencyModel : frequencyList) {
            if (frequencyModel.getName().equals(medicineFrequency)) {
                return true;
            }

        }
        return false;
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


                if (!responseValue.get(0).getFrequencyList().isEmpty()) {
                    frequencyList = responseValue.get(0).getFrequencyList();
                    bindFrequencyData(frequencyList);
                }


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

       /* viewModel.getMedicineData().observe(getViewLifecycleOwner(), new Observer<MedicineModel>() {
            @Override
            public void onChanged(MedicineModel medicineModel) {
                if (null != medicineModel)
                    Log.d(TAG, "onChanged: " + medicineModel.getMedicineList());
                else Log.d(TAG, "onChanged: null");
            }
        });*/

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
        addPrescriptionManuallyBinding.etFrequency.setOnItemClickListener((adapterView, view, position, l) -> {

            String item = (String) adapterView.getItemAtPosition(position);
            for (MedicineModel.MedicineFrequencyModel frequencyModel : frequencyList)
                if (item.equalsIgnoreCase(frequencyModel.getName()))
                    dtTableModel.setFrequencyId(frequencyModel.getId());
            Log.d(TAG, "bindMedicineData: " + dtTableModel.toString());
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

        addPrescriptionManuallyBinding.tcAutoMedicine.setOnItemClickListener((adapterView, view, position, l) -> {
            AppUtils.showRequestDialog(requireActivity());
            Object item = adapterView.getItemAtPosition(position);

            hideSoftKeyboard(requireActivity());
            for (MedicineModel.MedicineDetailModel medicineDetailModel : medicineList) {
                if (medicineDetailModel.getMedicineName().equalsIgnoreCase(item.toString())) {
                    addPrescriptionManuallyBinding.etFrequency.setText(medicineDetailModel.getFrequencyName());

                    //Adding Medicine Details
                    dtTableModel.setMedicineName(medicineDetailModel.getMedicineName());
                    dtTableModel.setMedicineId(medicineDetailModel.getId());
                    dtTableModel.setFrequencyId(medicineDetailModel.getFrequencyId());
                    dtTableModel.setDosageFormId(medicineDetailModel.getDosageId());
                    dtTableModel.setDoseUnitId(medicineDetailModel.getDoseUnitId());
                    dtTableModel.setStrength(medicineDetailModel.getStrength());


                    AppUtils.hideDialog();
                }
            }
        });

    }

    private void showDatePickerDialog() {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        myDatePicker.setMaxDate(System.currentTimeMillis());
        myDatePicker.setCalendarViewShown(false);
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(getString(R.string.select_date))
                .setPositiveButton(getString(R.string.ok), (dialog, id) -> {
                    dialog.cancel();
                    int month = myDatePicker.getMonth() + 1;
                    int day = myDatePicker.getDayOfMonth();
                    int year = myDatePicker.getYear();
                    String date = (year + "/" + month + "/" + day);
                    addPrescriptionManuallyBinding.tvDateTime.setText(parseDateToYMDToMMDD(date));

                }).show();
    }


    @Override
    public void onItemClick(Object object) {
        int position = (Integer) object;
        Log.d(TAG, "MedicineDeleted: " + adapter.deleteItems(position).size());
    }


    public void setImage(ArrayList<String> uri) {
        Prescription_Url = uri.get(0);
        Bitmap bm = BitmapFactory.decodeFile(Prescription_Url);
        addPrescriptionManuallyBinding.imageView17.setImageBitmap(bm);
        addPrescriptionManuallyBinding.clMedication.setVisibility(Prescription_Url.isEmpty() ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onDeleteButtonClick(int position) {
        if (imageAdapter.removeImage(position))
            Toast.makeText(requireActivity(), "Image removed", Toast.LENGTH_SHORT).show();

        updateSelectedImageRecVisibility();

    }

    @Override
    public void onViewButtonClick(Object obj) {
        Uri imageUri = (Uri) obj;
        showImageDialog(imageUri.toString());
    }

    private void showImageDialog(String filePath) {
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.image_view,
                null, false);
        ImageView imageView = formElementsView.findViewById(R.id.imageView46);
        loadImage(imageView, filePath);
        new androidx.appcompat.app.AlertDialog.Builder(requireActivity())
                .setView(formElementsView)
                .setPositiveButton(getString(R.string.close), (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }

    private void loadImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.diagnosis_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadPrescriptionImage: " + e.getLocalizedMessage());
            }
        }


    }
}
