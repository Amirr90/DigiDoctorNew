package com.digidoctor.android.model.labmodel;

import java.util.List;

public class LabOrderRes {


    String responseMessage;
    Integer responseCode;
    List<LabOrderModel> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public List<LabOrderModel> getResponseValue() {
        return responseValue;
    }


}

