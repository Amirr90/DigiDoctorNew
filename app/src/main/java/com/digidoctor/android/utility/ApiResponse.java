package com.digidoctor.android.utility;

import java.util.List;

public class ApiResponse {

    int responseCode;
    String responseMessage;
    List<?> responseValue;

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
