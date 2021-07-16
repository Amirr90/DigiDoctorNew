package com.digidoctor.android.interfaces;

import com.digidoctor.android.model.pharmacyModel.CoupnemodelRes;

import java.util.List;

public class RemoveCouponResponse {

    int responseCode;
    String responseMessage;
    List responseValue;

    @Override
    public String toString() {
        return "RemoveCouponResponse{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseValue='" + responseValue + '\'' +
                '}';
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List responseValue) {
        this.responseValue = responseValue;
    }
}
