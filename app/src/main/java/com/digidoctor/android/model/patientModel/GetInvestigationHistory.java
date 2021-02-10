package com.digidoctor.android.model.patientModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInvestigationHistory {


  /*  private String getType;
    private String getPathologyName;
    private String getReceiptNo;
    private String getTestDate;
    private String filePath;
    private int subTestId;
    private String subTestName;
    private float testValue;
    private String range;
    private String unitName;
    private String testRemarks;*/

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}


