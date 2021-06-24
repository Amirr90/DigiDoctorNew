package com.digidoctor.android.model;

import java.util.List;

public class SpecialityRes {
    int responseCode;
    String responseMessage;
    String text;
    List<SpecialityModel> responseValue;

    public String getText() {
        return text;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<SpecialityModel> getResponseValue() {
        return responseValue;
    }

}
