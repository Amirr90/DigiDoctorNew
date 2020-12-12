package com.digidoctor.android.model;

import java.util.List;

public class MedicineRes {
    int responseCode;
    String responseMessage;
    List<MedicineModel>responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<MedicineModel> getResponseValue() {
        return responseValue;
    }
}
