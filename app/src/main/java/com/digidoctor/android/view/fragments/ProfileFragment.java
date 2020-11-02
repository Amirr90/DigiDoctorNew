package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentProfileBinding;
import com.digidoctor.android.utility.ApiCallbackInterface;

import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.patientRegistration;


public class ProfileFragment extends Fragment {


    FragmentProfileBinding profileBinding;
    String name, email, password, dob, address, re_password, gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        return profileBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        profileBinding.btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllFieldFilled()) {
                    if (password.equalsIgnoreCase(re_password)) {
                        updateProfile();
                    } else
                        Toast.makeText(requireActivity(), R.string.password_dont_match, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(requireActivity(), R.string.please_fill_all_fields, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        patientRegistration(name, email, dob, gender, address, password, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                Toast.makeText(requireActivity(), R.string.profile_saved, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isAllFieldFilled() {

        name = profileBinding.editTextTextPersonName.getText().toString().trim();
        email = profileBinding.editTextTextPersonEmail.getText().toString().trim();
        dob = profileBinding.editTextTextPersonDob.getText().toString().trim();
        gender = profileBinding.editTextTextPersonGender.getText().toString().trim();
        address = profileBinding.editTextTextPersonAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)
                && TextUtils.isEmpty(email)
                && TextUtils.isEmpty(dob)
                && TextUtils.isEmpty(address)
                && TextUtils.isEmpty(gender))
            return false;
        else return true;
    }
}