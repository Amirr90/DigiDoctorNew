package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckLoginRes {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;


    @SerializedName("responseValue")
    @Expose
    private List<User> responseValue = null;


    @SerializedName("token")
    @Expose
    private String token;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<User> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<User> responseValue) {
        this.responseValue = responseValue;
    }

    public String getToken() {
        return token;
    }

}
