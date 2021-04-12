package com.digidoctor.android.model;

import com.digidoctor.android.model.patientModel.SymptomsNotificationModel;

import java.util.List;

public class SymptomsRes2 {
    int responseCode;
    String responseMessage;
    List<SymptomsNotificationModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<SymptomsNotificationModel> getResponseValue() {
        return responseValue;
    }
}
