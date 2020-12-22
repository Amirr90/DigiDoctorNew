package com.digidoctor.android.model;

import java.util.List;

public class AppointmentRes {

    String responseMessage;
    int responseCode;
    List<AppointmentModel> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<AppointmentModel> getResponseValue() {
        return responseValue;
    }
}
