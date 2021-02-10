package com.digidoctor.android.model.patientModel;

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
