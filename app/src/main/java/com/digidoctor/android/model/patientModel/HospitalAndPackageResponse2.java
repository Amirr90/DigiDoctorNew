package com.digidoctor.android.model.patientModel;

import java.util.List;

public class HospitalAndPackageResponse2 {

    Integer responseCode;
    String responseMessage;
    List<HospitalAndPackageResponse> responseValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<HospitalAndPackageResponse> getResponseValue() {
        return responseValue;
    }
}
