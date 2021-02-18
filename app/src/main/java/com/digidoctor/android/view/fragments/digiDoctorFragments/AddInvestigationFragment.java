package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.adapters.AddInvestigationTestInformationAdapter;
import com.digidoctor.android.adapters.SelectedImageAdapter;
import com.digidoctor.android.databinding.FragmentAddInvestigationBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ButtonListener;
import com.digidoctor.android.interfaces.ImageClickListener;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.AddInvestigationModel;
import com.digidoctor.android.model.InvestigationDataRes;
import com.digidoctor.android.model.TestInformationModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddInvestigationFragment extends Fragment implements ButtonListener, OnClickListener, ImageClickListener {
    private static final String TAG = "AddInvestigationFragmen";

    FragmentAddInvestigationBinding addInvestigationBinding;
    NavController navController;
    AddInvestigationModel addInvestigationModel;
    String date = null;


    AddInvestigationTestInformationAdapter adapter;
    List<TestInformationModel> testInformationModelList;


    ArrayList<String> imagePicked;


    SelectedImageAdapter imageAdapter;


    Boolean isUploadPrescription = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addInvestigationBinding = FragmentAddInvestigationBinding.inflate(getLayoutInflater());
        return addInvestigationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        //setting Test Information Adapter
        testInformationModelList = new ArrayList<>();
        adapter = new AddInvestigationTestInformationAdapter(testInformationModelList, this);
        addInvestigationBinding.recInputMedicine.setAdapter(adapter);

        //setting Selected ImageRec
        imagePicked = new ArrayList<>();
        imageAdapter = new SelectedImageAdapter(imagePicked, this);
        addInvestigationBinding.recyclerView3.setAdapter(imageAdapter);
        updateSelectedImageRecVisibility();


        //Adding Model
        addInvestigationModel = new AddInvestigationModel();
        addInvestigationBinding.setAddInvestigation(addInvestigationModel);

        addInvestigationBinding.setButtonListener(this);


        getDataToBind();

        //Adding test Data to Recycler View
        addInvestigationBinding.btnAddTestInformation.setOnClickListener(view1 -> {
            if (checkFieldForAddTestInformation()) {
                TestInformationModel testInformationModel = getTestInformationModel(addInvestigationBinding.getAddInvestigation());
                if (!adapter.addItem(testInformationModel))
                    Toast.makeText(requireActivity(), "Test Already Added", Toast.LENGTH_SHORT).show();
                else {
                    setMedicineFieldsEmpty();
                }
            }
        });

        addInvestigationBinding.cvSelectImage.setOnClickListener(view12 -> selectImage());
        addInvestigationBinding.btnBrowseImage.setOnClickListener(view13 -> selectImage());

    }

    public void updateSelectedImageRecVisibility() {
        addInvestigationBinding.textView47.setText(imageAdapter.getItemCount() == 0 ? getString(R.string.select_image) : getString(R.string.selected_images));
        addInvestigationBinding.recyclerView3.setVisibility(imageAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
        addInvestigationBinding.cvSelectImage.setVisibility(imageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        addInvestigationBinding.clMedication.setVisibility(imageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        addInvestigationBinding.recInputMedicine.setVisibility(imageAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        isUploadPrescription = imageAdapter.getItemCount() != 0;

    }

    private void selectImage() {
        if (imageAdapter.getItemCount() <= 3)
            ImagePicker.Companion.with(this)
                    .crop(8f, 8f)                    //Crop image(Optional), Check Customization for more option
                    .compress(512)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        else
            Toast.makeText(requireActivity(), "can not add more than 4 images", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(requireActivity(), "You can not select more than 4 images", Toast.LENGTH_SHORT).show();
        }
        updateSelectedImageRecVisibility();
    }

    private void setMedicineFieldsEmpty() {
        addInvestigationBinding.tcAutoMedicine.setText("");
        addInvestigationBinding.etFrequency.setText("");
        addInvestigationBinding.etUnit.setText("");
        addInvestigationBinding.editText4.setText("0");
    }

    private TestInformationModel getTestInformationModel(AddInvestigationModel addInvestigation) {
        TestInformationModel testInformationModel = new TestInformationModel();
        testInformationModel.setTestName(addInvestigation.getTestName());
        testInformationModel.setUnit(addInvestigation.getUnit());
        testInformationModel.setValue(addInvestigation.getValue());
        testInformationModel.setRange(addInvestigation.getRange());


        testInformationModel.setSubTestId(addInvestigation.getSubTestId());
        testInformationModel.setTestValue(addInvestigation.getTestValue());
        testInformationModel.setTestId(addInvestigation.getTestId());
        testInformationModel.setSubTestRangeId(addInvestigation.getSubTestRangeId());
        testInformationModel.setUnitId(addInvestigation.getUnitId());
        testInformationModel.setTestRemark(addInvestigation.getTestRemark());

        Log.d(TAG, "getTestInformationModel: " + testInformationModel.toString());
        return testInformationModel;
    }

    private boolean checkFieldForAddTestInformation() {
        if (null == addInvestigationModel) return false;
        else if (null == addInvestigationModel.testName || addInvestigationModel.testName.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter test name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.value || addInvestigationModel.value.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter test's value", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.unit || addInvestigationModel.unit.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter test's unit", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.range || addInvestigationModel.range.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter test's range", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;

    }

    private void getDataToBind() {
        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.investigationData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<InvestigationDataRes.InvestigationDataModel> dataModels = (List<InvestigationDataRes.InvestigationDataModel>) o;

                if (null == dataModels || dataModels.isEmpty())
                    return;
                List<InvestigationDataRes.TestNameModel> testList = dataModels.get(0).getTestList();
                List<InvestigationDataRes.UnitNameModel> unitList = dataModels.get(0).getUnitList();

                if (null != testList || !testList.isEmpty())
                    bindTestNameDataToAutoCompleteTv(testList);

                if (null != unitList || !unitList.isEmpty())
                    bindUnitDataToAutoCompleteTv(unitList);
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
            }
        });
    }

    private void bindUnitDataToAutoCompleteTv(List<InvestigationDataRes.UnitNameModel> unitList) {
        List<String> nameList = new ArrayList<>();
        for (InvestigationDataRes.UnitNameModel nameModel : unitList)
            nameList.add(nameModel.getName());

        //Adding Unit data To AutoCompleteView
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (requireActivity(), R.layout.inflate_auto_complete_text, nameList);
        addInvestigationBinding.etUnit.setThreshold(1);
        addInvestigationBinding.etUnit.setAdapter(adapter);
        addInvestigationBinding.etUnit.setOnItemClickListener((adapterView, view, position, l) -> {
            String item = (String) adapterView.getItemAtPosition(position);
            for (InvestigationDataRes.UnitNameModel unitModel : unitList) {
                if (unitModel.getName().equalsIgnoreCase(item)) {
                    addInvestigationModel.setUnitId(String.valueOf(unitModel.getId()));
                }
            }
        });

    }

    private void bindTestNameDataToAutoCompleteTv(List<InvestigationDataRes.TestNameModel> testList) {
        List<String> nameList = new ArrayList<>();
        for (InvestigationDataRes.TestNameModel nameModel : testList)
            nameList.add(nameModel.getName());

        //Adding test name data To AutoCompleteView
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (requireActivity(), R.layout.inflate_auto_complete_text, nameList);
        addInvestigationBinding.tcAutoMedicine.setThreshold(1);
        addInvestigationBinding.tcAutoMedicine.setAdapter(adapter);
        addInvestigationBinding.tcAutoMedicine.setOnItemClickListener((adapterView, view, position, l) -> {
            String item = (String) adapterView.getItemAtPosition(position);
            for (InvestigationDataRes.TestNameModel nameModel : testList) {
                if (nameModel.getName().equalsIgnoreCase(item)) {
                    addInvestigationModel.setSubTestId(String.valueOf(nameModel.getId()));
                    addInvestigationModel.setTestId("0");
                }
            }
        });

    }

    @Override
    public void OnButtonClick(Object obj) {
        String text = (String) obj;
        if (text.equalsIgnoreCase("btnSubmitInvestigation")) {
            addInvestigationModel = addInvestigationBinding.getAddInvestigation();
            if (isUploadPrescription) {
                if (checkFieldsFroUploadInvestigation()) {
                    try {
                        uploadInvestigation();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (checkAllFieldsToSubmitInvestigation(addInvestigationModel)) {
                    submitInvestigation(addInvestigationModel);
                }
            }
        } else if (text.equalsIgnoreCase("date")) {
            showDatePickerDialog();
        }
    }

    private void uploadInvestigation() throws IOException {
        ApiUtils.uploadMultipleFile(imagePicked, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<String> filePaths = (List<String>) o;
                addInvestigationModel.setDtFileDataTable(filePaths.get(0));
                adapter.deleteAllItems();
                submitInvestigation(addInvestigationModel);

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

    private boolean checkFieldsFroUploadInvestigation() {
        if (null == addInvestigationModel) return false;
        else if (null == addInvestigationModel.pathologyName || addInvestigationModel.pathologyName.isEmpty()) {
            Toast.makeText(requireActivity(), R.string.hospital_name, Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.receiptNo || addInvestigationModel.receiptNo.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter receipt number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.testDate || addInvestigationModel.testDate.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter test Date", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    private String getDtDataTable(List<TestInformationModel> testInformationData) {
        JSONArray jsonArray = new JSONArray();
        for (TestInformationModel model : testInformationData) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("testId", model.getTestId());
                jsonObject.put("subTestId", model.getSubTestId());
                jsonObject.put("testValue", model.getValue());
                jsonObject.put("subTestRangeId", model.getRange());
                jsonObject.put("unitId", model.getUnitId());
                jsonObject.put("testRemark", model.getTestRemark());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return jsonArray.toString();
    }

    private void submitInvestigation(AddInvestigationModel addInvestigationModel) {
        addInvestigationModel.setDtDataTable(getDtDataTable(adapter.getTestInformationData()));
        addInvestigationModel.setTestDate(date);
        addInvestigationModel.setInvestigationCategoryId("0");
        addInvestigationModel.setInvestigationTypeId("0");
        addInvestigationModel.setMemberId(String.valueOf(utils.getPrimaryUser(requireActivity()).getId()));
        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.submitInvestigation(addInvestigationModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), R.string.prescription_added_successfully, Toast.LENGTH_SHORT).show();
                PatientDashboard.getInstance().onSupportNavigateUp();
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: " + s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "something went wrong, try again !!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());
            }
        });

    }

    private boolean checkAllFieldsToSubmitInvestigation(AddInvestigationModel addInvestigationModel) {
        if (null == addInvestigationModel) return false;
        else if (null == addInvestigationModel.pathologyName || addInvestigationModel.pathologyName.isEmpty()) {
            Toast.makeText(requireActivity(), R.string.hospital_name, Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.receiptNo || addInvestigationModel.receiptNo.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter receipt number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == addInvestigationModel.testDate || addInvestigationModel.testDate.isEmpty()) {
            Toast.makeText(requireActivity(), "Enter test Date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (adapter.testInformationCount() <= 0) {
            Toast.makeText(requireActivity(), "Add Test(s) information data", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
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
                    date = (year + "-" + month + "-" + day);
                    addInvestigationBinding.tvDateTime.setText(AppUtils.parseDate((day + "/" + month + "/" + year), "dd MMMM yyyy"));

                }).show();
    }

    @Override
    public void onItemClick(Object object) {
        int position = (Integer) object;
        adapter.deleteItems(position);
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