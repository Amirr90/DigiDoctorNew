package com.digidoctor.android.model.patientModel;

import java.util.List;

public class SpecialityRes {
    int responseCode;
    String responseMessage;
    List<SpecialityModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<SpecialityModel> getResponseValue() {
        return responseValue;
    }
}
