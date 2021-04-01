package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackagesRes {
    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseValue")
    @Expose
    private List<PackageDetail> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<PackageDetail> getResponseValue() {
        return responseValue;
    }
}
