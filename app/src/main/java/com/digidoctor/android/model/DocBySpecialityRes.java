package com.digidoctor.android.model;

import java.util.List;

public class DocBySpecialityRes {

    int responseCode;
    String responseMessage;
    List<DoctorModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<DoctorModel> getResponseValue() {
        return responseValue;
    }
}
