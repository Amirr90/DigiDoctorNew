package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.digidoctor.android.databinding.FragmentAddressFragmentBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class AddressFragment extends Fragment {
    NavController navController;
    FragmentAddressFragmentBinding fragmentAddressFragmentBinding;
    String from = null;
    String Full_name, House, Mobile, City, ZipCode, area, State, locality, isDefault, setIsSundayOpen, setIsSaturdayOpen, setAddressType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAddressFragmentBinding = FragmentAddressFragmentBinding.inflate(getLayoutInflater());
        return fragmentAddressFragmentBinding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        navController = Navigation.findNavController(view);
        AppUtils.hideDialog();


        fragmentAddressFragmentBinding.btnUpdateProfile.setOnClickListener(view1 -> {

            if (!isAllFieldsFilled()) {
                address();
                Toast.makeText(requireActivity(), "Address Added Sucessfully", Toast.LENGTH_SHORT).show();
                navController.navigateUp();

            }

        });
    }


    private void address() {
        Map<String, String> map = new HashMap<>();
        map.put("Full_name", Full_name);
        map.put("House", House);
        map.put("Mobile", Mobile);
        map.put("City", City);
        map.put("ZipCode", ZipCode);
        map.put("area", area);
        map.put("State", State);
        map.put("locality", locality);
        map.put("isDefault", isDefault);
        map.put("setIsSundayOpen", setIsSundayOpen);
        map.put("setIsSaturdayOpen", setIsSaturdayOpen);
        map.put("setAddressType", setAddressType);


        ApiUtils.add_address(requireActivity(), map, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
            }

            @Override
            public void onError(String s) {

                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean isAllFieldsFilled() {
        Full_name = fragmentAddressFragmentBinding.editTextTextPersonName.getText().toString().trim();
        House = fragmentAddressFragmentBinding.editTextTextPersonhouse.getText().toString().trim();
        Mobile = fragmentAddressFragmentBinding.editTextMobile.getText().toString().trim();
        City = fragmentAddressFragmentBinding.EditTextcity.getText().toString().trim();
        ZipCode = fragmentAddressFragmentBinding.EditTextPin.getText().toString().trim();
        area = fragmentAddressFragmentBinding.area.getText().toString().trim();
        State = fragmentAddressFragmentBinding.EditTextState.getText().toString().trim();
        locality = fragmentAddressFragmentBinding.EditTextLocality.getText().toString().trim();

        if (fragmentAddressFragmentBinding.defaultcheck.isChecked()) {
            isDefault = "1";
        } else {
            isDefault = "0";
        }

        if (fragmentAddressFragmentBinding.sunday.isChecked()) {
            setIsSundayOpen = "1";
        } else {
            setIsSundayOpen = "0";
        }

        if (fragmentAddressFragmentBinding.saturday.isChecked()) {
            setIsSaturdayOpen = "1";
        } else {
            setIsSaturdayOpen = "0";
        }

        if (TextUtils.isEmpty(Full_name)) {
            Toast.makeText(requireActivity(), "Enter Full Name", Toast.LENGTH_SHORT).show();
            fragmentAddressFragmentBinding.editTextTextPersonName.setError("Enter Full Name");
            return true;
        } else if (TextUtils.isEmpty(House)) {
            Toast.makeText(requireActivity(), "Enter House Number", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(Mobile) || Mobile.length() < 10) {
            Toast.makeText(requireActivity(), "Kindly Enter 10 Digits Mobile Number!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(State)) {
            Toast.makeText(requireActivity(), "Enter State", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(City)) {
            Toast.makeText(requireActivity(), "Enter City", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(ZipCode)) {
            Toast.makeText(requireActivity(), "Enter Zip Code", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(locality)) {
            Toast.makeText(requireActivity(), "Enter Locality", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(area)) {
            Toast.makeText(requireActivity(), "Enter Area", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }
}
