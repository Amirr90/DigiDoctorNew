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
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentProfileBinding;
import com.digidoctor.android.databinding.GenderViewBinding;
import com.digidoctor.android.interfaces.MyDialogInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.patientRegistration;
import static com.digidoctor.android.utility.utils.BOOKING_USER;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.USER;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;
import static com.digidoctor.android.utility.utils.logout;


public class ProfileFragment extends Fragment implements MyDialogInterface {


    FragmentProfileBinding profileBinding;
    String name, email, dob, address, gender, mobile;

    User user;
    String GENDER;
    AlertDialog optionDialog;

    private static final String TAG = "ProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        profileBinding.setDialogInterface(this);
        return profileBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = getPrimaryUser(requireActivity());

        updateVisibility(user);
        Log.d(TAG, "onViewCreated: isExist" + user.getIsExists() + "\n" + user.toString());

        if (user.getIsExists() == 1) {
            profileBinding.setUser(user);
            Log.d(TAG, "onViewCreated: Profile" + user.toString());
        }

        profileBinding.setMobile(utils.getString(utils.MOBILE_NUMBER, requireActivity()));

        Log.d(TAG, "onViewCreated: Mobile " + utils.getString(utils.MOBILE_NUMBER, requireActivity()));

        profileBinding.btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getIsExists() == 0) {
                    if (isAllFieldFilled()) {
                        registerUser();
                    } else
                        Toast.makeText(requireActivity(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
                } else updateProfile();
            }
        });


    }

    private void updateVisibility(User user) {
        profileBinding.ivDobDialog.setVisibility(user.getIsExists() == 0 ? View.VISIBLE : View.GONE);
        profileBinding.ivGenderDialog.setVisibility(user.getIsExists() == 0 ? View.VISIBLE : View.GONE);
    }

    private void updateProfile() {

        Toast.makeText(requireContext(), "Updating Profile", Toast.LENGTH_SHORT).show();
    }

    private void registerUser() {
        AppUtils.showRequestDialog(requireActivity());
        patientRegistration(mobile, name, email, dob, GENDER, address, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {
                hideSoftKeyboard(requireActivity());
                Toast.makeText(requireActivity(), R.string.profile_saved, Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
                try {
                    List<User> users = (List<User>) obj;

                    user = users.get(0);

                    profileBinding.setUser(user);

                    Log.d(TAG, "onSuccess: " + users.get(0).toString());

                    PatientDashboard.getInstance().setUser(user);

                    //setting primary user info
                    utils.savePrimaryUserData(USER, requireActivity(), user);

                    //setting user info for Booking Appointment
                    utils.setUserForBooking(BOOKING_USER, requireActivity(), user);

                    utils.setString(MOBILE_NUMBER, mobile, requireActivity());

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
        dob = profileBinding.editTextTextPersonDob.getText().toString().trim();
        gender = profileBinding.editTextTextPersonGender.getText().toString().trim();
        address = profileBinding.editTextTextPersonAddress.getText().toString().trim();
        mobile = profileBinding.editTextTextPersonNumber.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireActivity(), R.string.name_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireActivity(), R.string.email_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            Toast.makeText(requireActivity(), R.string.date_of_birth_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(gender)) {
            Toast.makeText(requireActivity(), R.string.select_gender, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(requireActivity(), R.string.address_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(requireActivity(), R.string.mobilel_required, Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }

    @Override
    public void onGenderItemClick() {
        showSelectGenderDialog();

    }

    private void showSelectGenderDialog() {


        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.gender_view, null, false);

        GenderViewBinding genderViewBinding = GenderViewBinding.bind(formElementsView);


        genderViewBinding.llMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GENDER = "1";
                profileBinding.editTextTextPersonGender.setText(R.string.male);
                optionDialog.dismiss();
            }
        });

        genderViewBinding.llFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GENDER = "2";
                profileBinding.editTextTextPersonGender.setText(R.string.female);
                optionDialog.dismiss();

            }
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

        // the time picker on the alert dialog, this is how to get the value
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);

        // so that the calendar view won't appear
        myDatePicker.setCalendarViewShown(false);

        // the alert dialog
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(R.string.set_date)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();

                        profileBinding.editTextTextPersonDob.setText(year + "-" + month + "-" + day);

                        dialog.cancel();

                    }

                }).show();
    }
}