package com.digidoctor.android.model;

import com.digidoctor.android.model.labmodel.CartModel;

import java.util.List;

public class CartRes {

    String responseMessage;
    Integer responseCode;
    List<CartModel> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public List<CartModel> getResponseValue() {
        return responseValue;
    }
}
