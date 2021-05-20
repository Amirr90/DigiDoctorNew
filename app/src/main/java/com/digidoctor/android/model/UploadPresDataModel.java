package com.digidoctor.android.model;

public class UploadPresDataModel {

    private String appointmentId;
    private String dtDataTable;
    private String recordingType;

    public String getRecordingType() {
        return recordingType;
    }

    public void setRecordingType(String recordingType) {
        this.recordingType = recordingType;
    }

    public UploadPresDataModel() {
    }

    public UploadPresDataModel(String appointmentId, String dtDataTable) {
        this.appointmentId = appointmentId;
        this.dtDataTable = dtDataTable;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDtDataTable() {
        return dtDataTable;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }
}
