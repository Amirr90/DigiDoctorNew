package com.digidoctor.android.model.patientModel;

import java.util.List;

public class SymptomsRes {
    int responseCode;
    String responseMessage;
    List<SymptomModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<SymptomModel> getResponseValue() {
        return responseValue;
    }
}
