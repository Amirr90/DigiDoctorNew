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
    private List<Packages> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Packages> getResponseValue() {
        return responseValue;
    }

    public static class Packages {
        public List<PackageDetail> getPackageDetails() {
            return packageDetails;
        }

        List<PackageDetail> packageDetails;
    }
}
