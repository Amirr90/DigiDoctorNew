package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.digidoctor.android.adapters.pharmacy.AddressAdapter;
import com.digidoctor.android.databinding.FragmentUpdateAddressBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.AddAdressModel;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;


public class UpdateAddressFragment extends Fragment {

    NavController navController;
    FragmentUpdateAddressBinding fragmentUpdateAddressBinding;

    AddressAdapter addressAdapter;
    List<getaddressModel.getaddressDetails> addressget = new ArrayList<>();

    String AddressId = null, MemberID = null, name, mobile, addresstype, house, area, pincode, state, city, locality, isdefault, issaturdayopen, issundayopen;
    String Full_nameET, HouseET, MobileET, CityET, ZipCodeET, areaET, StateET, localityET, isDefaultET, issundayopenET, IssaturdayOpenET;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        if (getArguments() == null)
            return;
//
        AddressId = String.valueOf(getArguments().get("AddressID"));
        MemberID = String.valueOf(getArguments().get("MemberId"));
        name = String.valueOf(getArguments().get("name"));
        mobile = String.valueOf(getArguments().get("mobileNo"));
        house = String.valueOf(getArguments().get("houseNo"));
        area = String.valueOf(getArguments().get("area"));
        pincode = String.valueOf(getArguments().get("pincode"));
        state = String.valueOf(getArguments().get("state"));
        city = String.valueOf(getArguments().get("city"));
        locality = String.valueOf(getArguments().get("locality"));
        isdefault = String.valueOf(getArguments().get("isDefault"));
        issaturdayopen = String.valueOf(getArguments().get("isSaturdayOpen"));
        issundayopen = String.valueOf(getArguments().get("isSundayOpen"));
        addresstype = String.valueOf(getArguments().get("adtype"));


        getaddress();

        fragmentUpdateAddressBinding.btnUpdateProfile.setOnClickListener(view1 -> {

            if (!isAllFieldsFilled()) {
                address();

                Toast.makeText(requireActivity(), "Address Updated Successfully !!", Toast.LENGTH_SHORT).show();
                addressget.clear();
                navController.navigate(R.id.action_updateAddressFragment_to_allAddressFragment);


            }

        });
    }

    public void getaddress() {

        ApiUtils.getadddetails(requireActivity(), new ApiCallbackInterface() {
            @Override


            public void onSuccess(List<?> o) {
                List<getaddressModel.getaddressDetails> models = (List<getaddressModel.getaddressDetails>) o;


                if (models.isEmpty())
                    return;

                addressget.clear();
                addressget.addAll(models);

                Log.d("TAG", "onSuccessAddressUpdate: " + addressget.get(0).getName() + addressget.get(0).getArea());


                fragmentUpdateAddressBinding.editTextTextPersonName.setText(name);
                fragmentUpdateAddressBinding.editTextTextPersonhouse.setText(house);
                fragmentUpdateAddressBinding.editTextMobile.setText(mobile);
                fragmentUpdateAddressBinding.area.setText(area);
                fragmentUpdateAddressBinding.EditTextPin.setText(pincode);
                fragmentUpdateAddressBinding.EditTextState.setText(state);
                fragmentUpdateAddressBinding.EditTextcity.setText(city);
                fragmentUpdateAddressBinding.EditTextLocality.setText(locality);

                if (isdefault.equals("true")) {
                    fragmentUpdateAddressBinding.defaultcheck.setChecked(true);
                }
                if (issaturdayopen.equals("true")) {
                    fragmentUpdateAddressBinding.isaturday.setChecked(true);
                }
                if (issundayopen.equals("true")) {
                    fragmentUpdateAddressBinding.sunday.setChecked(true);
                }


            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void address() {


        AddAdressModel addAdressModel = new AddAdressModel();
        addAdressModel.setAddressId(AddressId);
        addAdressModel.setMemberId(MemberID);
        addAdressModel.setName(Full_nameET);
        addAdressModel.setHouseNo(HouseET);
        addAdressModel.setMobileno(MobileET);
        addAdressModel.setCity(CityET);
        addAdressModel.setPincode(ZipCodeET);
        addAdressModel.setArea(areaET);
        addAdressModel.setState(StateET);
        addAdressModel.setLocality(localityET);
        addAdressModel.setIsDefault(isDefaultET);
        addAdressModel.setAddressType(addresstype);
        addAdressModel.setIsSaturdayOpen(IssaturdayOpenET);
        addAdressModel.setIsSundayOpen(issundayopenET);

        ApiUtils.update_Address(addAdressModel, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                getaddress();

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


    public boolean isAllFieldsFilled() {
        Full_nameET = fragmentUpdateAddressBinding.editTextTextPersonName.getText().toString().trim();
        HouseET = fragmentUpdateAddressBinding.editTextTextPersonhouse.getText().toString().trim();
        MobileET = fragmentUpdateAddressBinding.editTextMobile.getText().toString().trim();
        CityET = fragmentUpdateAddressBinding.EditTextcity.getText().toString().trim();
        ZipCodeET = fragmentUpdateAddressBinding.EditTextPin.getText().toString().trim();
        areaET = fragmentUpdateAddressBinding.area.getText().toString().trim();
        StateET = fragmentUpdateAddressBinding.EditTextState.getText().toString().trim();
        localityET = fragmentUpdateAddressBinding.EditTextLocality.getText().toString().trim();


        if (fragmentUpdateAddressBinding.defaultcheck.isChecked()) {
            isDefaultET = "1";
        } else {
            isDefaultET = "0";
        }
        if (fragmentUpdateAddressBinding.sunday.isChecked()) {
            issundayopenET = "1";
        } else {
            issundayopenET = "0";
        }

        if (fragmentUpdateAddressBinding.isaturday.isChecked()) {
            IssaturdayOpenET = "1";
        } else {
            IssaturdayOpenET = "0";
        }

        if (TextUtils.isEmpty(Full_nameET)) {
            Toast.makeText(requireActivity(), "Enter Full Name", Toast.LENGTH_SHORT).show();
            fragmentUpdateAddressBinding.editTextTextPersonName.setError("Enter Full Name");
            return true;
        } else if (TextUtils.isEmpty(HouseET)) {
            Toast.makeText(requireActivity(), "Enter House Number", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(MobileET) || MobileET.length() < 10) {
            Toast.makeText(requireActivity(), "Kindly Enter 10 Digits Mobile Number!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(StateET)) {
            Toast.makeText(requireActivity(), "Enter State", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(CityET)) {
            Toast.makeText(requireActivity(), "Enter City", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(ZipCodeET)) {
            Toast.makeText(requireActivity(), "Enter Zip Code", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(localityET)) {
            Toast.makeText(requireActivity(), "Enter Locality", Toast.LENGTH_SHORT).show();
            return true;
        } else if (TextUtils.isEmpty(areaET)) {
            Toast.makeText(requireActivity(), "Enter Area", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentUpdateAddressBinding = FragmentUpdateAddressBinding.inflate(getLayoutInflater());

        return fragmentUpdateAddressBinding.getRoot();
    }

}