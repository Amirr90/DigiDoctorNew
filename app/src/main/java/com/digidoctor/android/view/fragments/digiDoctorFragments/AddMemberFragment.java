package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAddMemberBinding;
import com.digidoctor.android.databinding.GenderViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.MyDialogInterface;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.FileUtil;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.fadeIn;
import static com.digidoctor.android.utility.utils.logout;


public class AddMemberFragment extends Fragment implements MyDialogInterface {
    private static final String TAG = "AddMemberFragment";

    FragmentAddMemberBinding addMemberBinding;

    String from = null;

    NavController navController;

    String name, mobile, dob, address, Gender;

    String GENDER;

    AlertDialog optionDialog;

    String imagePath = null;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addMemberBinding = FragmentAddMemberBinding.inflate(getLayoutInflater());
        addMemberBinding.setDialogInterface(this);
        return addMemberBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        addMemberBinding.getRoot().setAnimation(fadeIn(requireActivity()));

        if (null != getArguments())
            from = getArguments().getString("from");


        addMemberBinding.btnSavePatient.setOnClickListener(view1 -> {
            if (isAllFieldsFilled()) {
                addMember();
            }
        });


        addMemberBinding.textView5.setOnClickListener(view1 -> {
            ImagePicker.Companion.with(this)
                    .crop(4f, 4f)                    //Crop image(Optional), Check Customization for more option
                    .compress(512)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (null != data) {
                try {
                    Uri uri = data.getData();
                    addMemberBinding.profileImage.setImageURI(uri);
                    File file = FileUtil.from(requireActivity(), uri);
                    Log.d(TAG, "onActivityResult: " + file);
                    uploadImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private void uploadImage(File imageFile) throws IOException {
        ApiUtils.uploadProfileImage(imageFile, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<String> Path = (List<String>) o;
                Log.d(TAG, "ImagePath: " + Path.get(0));

                imagePath = Path.get(0);
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

    private void addMember() {

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("dob", dob);
        map.put("address", address);
        map.put("email", address);
        map.put("profilePath", imagePath);
        map.put("gender", Gender.equalsIgnoreCase(getString(R.string.male)) ? "1" : "2");

        ApiUtils.addMember(requireActivity(), map, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                Toast.makeText(requireActivity(), R.string.member_added_successful, Toast.LENGTH_SHORT).show();
                PatientDashboard.getInstance().onSupportNavigateUp();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equalsIgnoreCase(getString(R.string.failed_to_authenticate_token))) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isAllFieldsFilled() {
        name = addMemberBinding.etName.getText().toString().trim();
        mobile = addMemberBinding.etNumber.getText().toString().trim();
        dob = addMemberBinding.etDob.getText().toString().trim();
        address = addMemberBinding.etAddress.getText().toString().trim();
        Gender = addMemberBinding.etGender.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireActivity(), R.string.name_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(requireActivity(), R.string.mobile_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (mobile.length() < 10) {
            Toast.makeText(requireActivity(), R.string.enter_valid_mobile_number, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(requireActivity(), R.string.address_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(Gender)) {
            Toast.makeText(requireActivity(), R.string.select_gender, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            Toast.makeText(requireActivity(), R.string.select_age, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onAgeItemClick() {
        showSelectAgeDialog();
    }

    @Override
    public void onGenderItemClick() {
        showSelectGenderDialog();

    }

    private void showSelectGenderDialog() {


        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.gender_view, null, false);

        GenderViewBinding genderViewBinding = GenderViewBinding.bind(formElementsView);


        genderViewBinding.llMale.setOnClickListener(view -> {
            GENDER = "1";
            addMemberBinding.etGender.setText(R.string.male);
            optionDialog.dismiss();
        });

        genderViewBinding.llFemale.setOnClickListener(view -> {
            GENDER = "2";
            addMemberBinding.etGender.setText(R.string.female);
            optionDialog.dismiss();

        });

        // the alert dialog
        optionDialog = new AlertDialog.Builder(requireActivity()).create();
        optionDialog.setView(formElementsView);
        optionDialog.show();


    }

    private void showSelectAgeDialog() {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);

        // the time picker on the alert dialog, this is how to get the value
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);

        // so that the calendar view won't appear
        myDatePicker.setCalendarViewShown(false);
        myDatePicker.setMaxDate(System.currentTimeMillis());

        // the alert dialog
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(R.string.set_date)
                .setPositiveButton(R.string.ok, (dialog, id) -> {

                    int month = myDatePicker.getMonth() + 1;
                    int day = myDatePicker.getDayOfMonth();
                    int year = myDatePicker.getYear();
                    addMemberBinding.etDob.setText(year + "-" + month + "-" + day);


                    dialog.cancel();

                }).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}