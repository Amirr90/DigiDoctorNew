package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class popuplarproductRes {

    int responseCode;

    public int getResponseCode() {
        return responseCode;


    }

    public String getResponseMessage() {
        return responseMessage;


    }

    public List<PopularProductListModel> getResponseValue() {
        return responseValue;


    }

    String responseMessage;
    List<PopularProductListModel> responseValue;

}


