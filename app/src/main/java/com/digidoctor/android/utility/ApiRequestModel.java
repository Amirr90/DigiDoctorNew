package com.digidoctor.android.utility;

public class ApiRequestModel {

    int memberId;
    String recordingType;
    String PID;
    String dtPaymentTable;
    String problemName;


    public String getDtPaymentTable() {
        return dtPaymentTable;
    }

    public void setDtPaymentTable(String dtPaymentTable) {
        this.dtPaymentTable = dtPaymentTable;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(String recordingType) {
        this.recordingType = recordingType;
    }
}
