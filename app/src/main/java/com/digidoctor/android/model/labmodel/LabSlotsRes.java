package com.digidoctor.android.model.labmodel;

import java.util.List;

public class LabSlotsRes {
    Integer responseCode;
    String responseMessage;
    List<LabSlotModel> responseValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<LabSlotModel> getResponseValue() {
        return responseValue;
    }
}
