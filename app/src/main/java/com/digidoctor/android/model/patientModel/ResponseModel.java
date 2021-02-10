package com.digidoctor.android.model.patientModel;

import java.util.ArrayList;
import java.util.List;

public class ResponseModel {

    Integer responseCode;
    String responseMessage;
    List<HashModel> responseValue = new ArrayList<>();

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<HashModel> getResponseValue() {
        return responseValue;
    }

    public class HashModel {
        String hash;
        String taxId;

        public String getHash() {
            return hash;
        }

        public String getTaxId() {
            return taxId;
        }
    }
}
