package com.digidoctor.android.model;

import java.util.List;

public class UploadImageRes {

    int responseCode;
    String responseMessage;
    List<UploadImageModel> responseValue;

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

    public List<UploadImageModel> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<UploadImageModel> responseValue) {
        this.responseValue = responseValue;
    }
}
