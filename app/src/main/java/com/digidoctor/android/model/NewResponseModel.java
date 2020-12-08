package com.digidoctor.android.model;

public class NewResponseModel {

    private boolean isSuccessful;
    private String[] errorMessages;
    private long statusCode;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String[] getErrorMessages() {
        return errorMessages;
    }

    public long getStatusCode() {
        return statusCode;
    }
}
