package com.digidoctor.android.model;

import java.util.ArrayList;
import java.util.List;

public class DemoResponse {
    Integer responseCode;
    String responseMessage;
    List<Object> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<?> getResponseValue() {
        return responseValue;
    }
}
