package com.digidoctor.android.model.patientModel;

import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.utility.FilePath;

import java.util.ArrayList;
import java.util.List;

public class RecordingResponse {

    Integer responseCode;
    String responseMessage;
    List<FileModel> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<FileModel> getResponseValue() {
        return responseValue;
    }


}

