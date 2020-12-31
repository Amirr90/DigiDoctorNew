package com.digidoctor.android.view.fragments;

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

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentProfileBinding;
import com.digidoctor.android.databinding.GenderViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.MyDialogInterface;
import com.digidoctor.android.model.Registration;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.FileUtil;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.ApiUtils.patientRegistration;
import static com.digidoctor.android.utility.AppUtils.isEmailValid;
import static com.digidoctor.android.utility.utils.BOOKING_USER;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.USER;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;
import static com.digidoctor.android.utility.utils.logout;


public class ProfileFragment extends Fragment implements MyDialogInterface {


    FragmentProfileBinding profileBinding;
    String name, email, dob, address, gender, mobile;

    User user;
    String GENDER = null;
    AlertDialog optionDialog;


    Registration registration = new Registration();
    private static final String TAG = "ProfileFragment";

    String memberId = null;

    boolean isPicChange = false;

    String imagePath = null;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        profileBinding.setDialogInterface(this);
        return profileBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        user = getPrimaryUser(requireActivity());

        memberId = String.valueOf(user.getMemberId());

        Log.d(TAG, "memberIdReceived: " + memberId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = getPrimaryUser(requireActivity());
        Log.d(TAG, "onViewCreatedUser: " + user.getPrimaryStatus());

        updateVisibility(user);

        if (user.getIsExists() == 1) {

            profileBinding.setUser(user);
            if (null != user && null != user.getPrimaryStatus())
                profileBinding.editTextTextPersonNumber.setEnabled(user.getPrimaryStatus() == 1 ? false : true);

        }

        profileBinding.setMobile(utils.getString(utils.MOBILE_NUMBER, requireActivity()));

        profileBinding.btnUpdateProfile.setOnClickListener(view12 -> {
            if (user.getIsExists() == 0) {
                if (isAllFieldFilled()) {
                    registerUser();
                }

            } else {
                if (checkFieldsForUpdateProfile()) {
                    updateProfile();
                }
            }
        });


        profileBinding.textView5.setOnClickListener(view1 -> {
            ImagePicker.Companion.with(this)
                    .crop(4f, 4f)                    //Crop image(Optional), Check Customization for more option
                    .compress(512)            //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });


    }

    private boolean checkFieldsForUpdateProfile() {

        name = profileBinding.editTextTextPersonName.getText().toString().trim();
        email = profileBinding.editTextTextPersonEmail.getText().toString().trim();
        dob = AppUtils.parseUserDate(profileBinding.editTextTextPersonDob.getText().toString().trim());
        gender = profileBinding.editTextTextPersonGender.getText().toString().trim();
        address = profileBinding.editTextTextPersonAddress.getText().toString().trim();
        mobile = profileBinding.editTextTextPersonNumber.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireActivity(), R.string.name_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireActivity(), R.string.email_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isEmailValid(email.trim())) {
            Toast.makeText(requireActivity(), R.string.enter_valid_email_id, Toast.LENGTH_SHORT).show();
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
        } else return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (null != data) {
                try {
                    Uri uri = data.getData();
                    profileBinding.profileImage.setImageURI(uri);
                    isPicChange = true;
                    File file = FileUtil.from(requireActivity(), uri);
                    uploadImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    private void uploadImage(File imagFile) throws IOException {
        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.uploadProfileImage(imagFile, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<String> Path = (List<String>) o;
                Log.d(TAG, "ImagePath: " + Path.get(0));
                imagePath = Path.get(0);
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

    private void updateVisibility(User user) {
        profileBinding.ivDobDialog.setVisibility(user.getIsExists() == 0 ? View.VISIBLE : View.GONE);
        profileBinding.ivGenderDialog.setVisibility(user.getIsExists() == 0 ? View.VISIBLE : View.GONE);
    }

    private void updateProfile() {

        User updatedUser = new User();
        updatedUser.setMemberId(user.getId());
        updatedUser.setName(profileBinding.editTextTextPersonName.getText().toString());
        updatedUser.setMobileNo(profileBinding.editTextTextPersonNumber.getText().toString());
        updatedUser.setEmailId(profileBinding.editTextTextPersonEmail.getText().toString());
        updatedUser.setGender(profileBinding.editTextTextPersonGender.getText().toString().equalsIgnoreCase("male") ? 1 : 2);
        updatedUser.setDob(AppUtils.parseUserDate(profileBinding.editTextTextPersonDob.getText().toString()));
        updatedUser.setAddress(profileBinding.editTextTextPersonAddress.getText().toString());

        updatedUser.setProfilePhotoPath(imagePath);


        AppUtils.showRequestDialog(requireActivity());
        ApiUtils.updateMember(updatedUser, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {

                List<User> updatedUsers = (List<User>) o;
                for (User user : updatedUsers) {

                    Log.d(TAG, "memberIdReceived: " + memberId);
                    if (user.getMemberId() == Integer.parseInt(memberId)) {
                        utils.savePrimaryUserData(USER, requireActivity(), user);
                        utils.setString(MOBILE_NUMBER, user.getMobileNo(), requireActivity());
                        PatientDashboard.getInstance().updateUser();
                    }
                }
                Toast.makeText(requireActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
                PatientDashboard.getInstance().updateUser();
                PatientDashboard.getInstance().onSupportNavigateUp();



            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), getString(R.string.retry), Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), getString(R.string.retry), Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
            }
        });
    }

    private void registerUser() {

        AppUtils.showRequestDialog(requireActivity());
        registration.setProfilePhotoPath(imagePath);
        patientRegistration(registration, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {
                hideSoftKeyboard(requireActivity());
                Toast.makeText(requireActivity(), R.string.profile_saved, Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
                try {
                    List<User> users = (List<User>) obj;

                    user = users.get(0);

                    profileBinding.setUser(user);


                    PatientDashboard.getInstance().setUser(user);

                    //setting primary user info
                    utils.savePrimaryUserData(USER, requireActivity(), user);

                    //setting user info for Booking Appointment
                    utils.setUserForBooking(BOOKING_USER, requireActivity(), user);

                    utils.setString(MOBILE_NUMBER, mobile, requireActivity());

                    PatientDashboard.getInstance().updateUser();
                    PatientDashboard.getInstance().onSupportNavigateUp();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());

            }
        });
    }

    private boolean isAllFieldFilled() {

        name = profileBinding.editTextTextPersonName.getText().toString().trim();
        email = profileBinding.editTextTextPersonEmail.getText().toString().trim();
        dob = AppUtils.parseUserDate(profileBinding.editTextTextPersonDob.getText().toString().trim());
        gender = profileBinding.editTextTextPersonGender.getText().toString().trim();
        address = profileBinding.editTextTextPersonAddress.getText().toString().trim();
        mobile = profileBinding.editTextTextPersonNumber.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireActivity(), R.string.name_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireActivity(), R.string.email_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isEmailValid(email.trim())) {
            Toast.makeText(requireActivity(), R.string.enter_valid_email_id, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            Toast.makeText(requireActivity(), R.string.date_of_birth_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (null == GENDER) {
            Toast.makeText(requireActivity(), R.string.select_gender, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(requireActivity(), R.string.address_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(requireActivity(), R.string.mobilel_required, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            registration.setName(name);
            registration.setEmailId(email);
            registration.setDob(dob);
            registration.setGender(Long.parseLong(GENDER));
            registration.setAddress(address);
            registration.setMobileNo(mobile);
            registration.setDeviceToken(utils.getString(TOKEN, requireActivity()));
            return true;
        }
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
            profileBinding.editTextTextPersonGender.setText(R.string.male);
            optionDialog.dismiss();
        });

        genderViewBinding.llFemale.setOnClickListener(view -> {
            GENDER = "2";
            profileBinding.editTextTextPersonGender.setText(R.string.female);
            optionDialog.dismiss();

        });

        // the alert dialog
        optionDialog = new AlertDialog.Builder(requireActivity()).create();
        optionDialog.setView(formElementsView);
        optionDialog.show();


    }

    @Override
    public void onAgeItemClick() {
        showSelectAgeDialog();
    }

    private void showSelectAgeDialog() {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);

        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);

        myDatePicker.setCalendarViewShown(false);
        myDatePicker.setMaxDate(System.currentTimeMillis());
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(R.string.set_date)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    int month = myDatePicker.getMonth() + 1;
                    int day = myDatePicker.getDayOfMonth();
                    int year = myDatePicker.getYear();
                    profileBinding.editTextTextPersonDob.setText(day + "/" + month + "/" + year);

                    dialog.cancel();

                }).show();
    }
}