package com.digidoctor.android.model.labmodel;

import com.digidoctor.android.model.InvestigationDataRes;
import com.digidoctor.android.model.LabModel;

import java.util.List;

public class LabRes {
    int responseCode;
    String responseMessage;
    List<LabModel> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<LabModel> getResponseValue() {
        return responseValue;
    }
}
