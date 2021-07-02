package com.digidoctor.android.model;

import java.util.List;

public class MenuResponse {
    int responseCode;
    String responseMessage;
    String text;
    List<NavModel> responseValue;

    public String getText() {
        return text;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<NavModel> getResponseValue() {
        return responseValue;
    }
}
