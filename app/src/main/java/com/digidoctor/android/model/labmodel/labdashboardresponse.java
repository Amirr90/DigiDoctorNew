package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class labdashboardresponse {

    @SerializedName("responseValue")
    @Expose
    public List<LabDashBoardmodel> responseValue = null;
    @SerializedName("responseMessage")
    @Expose
    public String responseMessage;
    @SerializedName("responseCode")
    @Expose
    public Integer responseCode;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<LabDashBoardmodel> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<LabDashBoardmodel> responseValue) {
        this.responseValue = responseValue;
    }


}
