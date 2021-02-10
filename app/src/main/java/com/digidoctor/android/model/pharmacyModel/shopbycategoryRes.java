package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class shopbycategoryRes {
    int responseCode;

    public int getResponseCode() {
        return responseCode;


    }

    public String getResponseMessage() {
        return responseMessage;


    }

    public List<ShopBycategoryModel> getResponseValue() {
        return responseValue;


    }

    String responseMessage;
    List<ShopBycategoryModel> responseValue;

}
