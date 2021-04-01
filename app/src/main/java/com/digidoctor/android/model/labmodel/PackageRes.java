package com.digidoctor.android.model.labmodel;

import com.digidoctor.android.model.PackageModel;

import java.util.List;

public class PackageRes {
    int responseCode;
    String responseMessage;
    private List<Packages> responseValue = null;

    public static class Packages {
        List<PackageModel> packageDetails;
        public List<PackageModel> getPackageDetails() {
            return packageDetails;
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Packages> getResponseValue() {
        return responseValue;
    }
}
