package com.digidoctor.android.model;

import java.util.ArrayList;
import java.util.List;

public class Response {

    Integer responseCode;
    String responseMessage;
    List<PaymentMode> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<?> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<PaymentMode> responseValue) {
        this.responseValue = responseValue;
    }
}
