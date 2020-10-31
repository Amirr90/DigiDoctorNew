package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRes {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;


    List<OtpModel> responseValue = new ArrayList<>();

    public List<OtpModel> getResponseValue() {
        return responseValue;
    }

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


    public class OtpModel {
        String mobileNo;
        String otp;

        public String getMobileNo() {
            return mobileNo;
        }

        public String getOtp() {
            return otp;
        }
    }
}
