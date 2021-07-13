package com.digidoctor.android.model;

public class CallModel {

    long callInitiatedTimestamp;
    String callStatus;
    String docId;
    String uid;
    public String doctorName;
    public String patientName;


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public CallModel() {
    }

    public long getCallInitiatedTimestamp() {
        return callInitiatedTimestamp;
    }

    public void setCallInitiatedTimestamp(long callInitiatedTimestamp) {
        this.callInitiatedTimestamp = callInitiatedTimestamp;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
