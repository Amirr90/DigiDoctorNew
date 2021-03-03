package com.digidoctor.android.model;

import java.util.List;

public class CartRes {

    String responseMessage;
    Integer responseCode;
    List<CartRes> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public List<CartRes> getResponseValue() {
        return responseValue;
    }
}
