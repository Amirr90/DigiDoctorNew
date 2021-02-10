package com.digidoctor.android.utility;

import com.digidoctor.android.model.patientModel.User;

import java.util.List;

public class ApiResponse {

    int responseCode;
    String responseMessage;
    List<User> responseValue;
    String token;

    public String getToken() {
        return token;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<?> getResponseValue() {
        return responseValue;
    }
}
