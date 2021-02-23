package com.digidoctor.android.model.labmodel;

import java.util.List;

public class ApiLabResponse {
    Integer responseCode;
    String responseMessage;
    List<LabDashBoardmodel> responseValue;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<LabDashBoardmodel> getResponseValue() {
        return responseValue;
    }
}
