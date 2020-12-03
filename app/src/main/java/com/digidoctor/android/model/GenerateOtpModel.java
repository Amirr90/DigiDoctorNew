package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenerateOtpModel {

    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
