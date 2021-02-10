package com.digidoctor.android.model.patientModel;

import java.util.List;

public class DashBoardRes {

    int responseCode;
    String responseMessage;
    List<PatientDashboardModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<PatientDashboardModel> getResponseValue() {
        return responseValue;
    }
}
