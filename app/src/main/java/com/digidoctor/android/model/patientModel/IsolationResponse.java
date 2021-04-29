package com.digidoctor.android.model.patientModel;

import java.util.ArrayList;
import java.util.List;

public class IsolationResponse {

    Integer responseCode;
    String responseMessage;
    List<HomeIsolationReqModel> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<HomeIsolationReqModel> getResponseValue() {
        return responseValue;
    }
}
