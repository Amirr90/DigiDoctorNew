package com.digidoctor.android.view.fragments.Patient;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAddMemberBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.digidoctor.android.utility.utils.logout;


public class AddMemberFragment extends Fragment {

    FragmentAddMemberBinding addMemberBinding;

    String from = null;














    NavController navController;


    String name, mobile, dob, address, Gender;
    int gender;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        addMemberBinding = FragmentAddMemberBinding.inflate(getLayoutInflater());
        return addMemberBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        from = getArguments().getString("from");


        addMemberBinding.btnSavePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAllFieldsFilled()) {
                    addMember();
                }
            }
        });

    }

    private void addMember() {

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("dob", dob);
        map.put("address", address);
        map.put("gender", Gender == "male" ? "1" : "2");

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
        } else if (TextUtils.isEmpty(Gender)) {
            Toast.makeText(requireActivity(), R.string.select_gender, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(requireActivity(), R.string.mobile_required, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            Toast.makeText(requireActivity(), R.string.select_age, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(address)) {
            Toast.makeText(requireActivity(), R.string.address_required, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}