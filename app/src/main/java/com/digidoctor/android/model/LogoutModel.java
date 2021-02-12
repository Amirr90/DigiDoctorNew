package com.digidoctor.android.model;

public class LogoutModel {
    private int responseCode;
    private String responseMessage;
    private Object token;
    private Object isCovid;
    private Object responseValue;
    private String mobileNo;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public Object getToken() {
        return token;
    }

    public Object getIsCovid() {
        return isCovid;
    }

    public Object getResponseValue() {
        return responseValue;
    }
}
