package com.digidoctor.android.model;

import java.util.List;

public class DocBySymptomsRes {

    int responseCode;
    String responseMessage;
    List<DoctorModelRes> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<DoctorModelRes> getResponseValue() {
        return responseValue;
    }
}
