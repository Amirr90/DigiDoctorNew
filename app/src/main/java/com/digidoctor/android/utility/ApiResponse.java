package com.digidoctor.android.utility;

public class ApiResponse {

    Integer responseCode;
    String responseMessage;
    Object responseValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getResponseValue() {
        return responseValue;
    }
}
