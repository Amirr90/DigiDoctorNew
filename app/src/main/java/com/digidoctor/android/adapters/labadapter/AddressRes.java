package com.digidoctor.android.adapters.labadapter;

import com.digidoctor.android.model.pharmacyModel.getaddressModel;

import java.util.List;

public class AddressRes {
    int responseCode;
    String responseMessage;
    List<Address> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Address> getResponseValue() {
        return responseValue;
    }
}
