package com.digidoctor.android.model;

public class PayModeModel {
    String docId;

    public PayModeModel(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public PayModeModel() {
    }
}
