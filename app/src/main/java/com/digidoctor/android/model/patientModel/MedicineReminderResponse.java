package com.digidoctor.android.model.patientModel;

import java.util.ArrayList;
import java.util.List;

public class MedicineReminderResponse {
    Integer responseCode;
    String responseMessage;
    List<MedicineReminderModel> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<MedicineReminderModel> getResponseValue() {
        return responseValue;
    }
}
