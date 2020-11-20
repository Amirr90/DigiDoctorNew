package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlineAppointmentModel {

    int doctorId;
    int isEraUser;

    public int getDoctorId() {
        return doctorId;
    }

    public int getIsEraUser() {
        return isEraUser;
    }

    @SerializedName("appointmentId")
    @Expose
    private String appointmentId;
    @SerializedName("visitDate")
    @Expose
    private String visitDate;
    @SerializedName("visitTime")
    @Expose
    private String visitTime;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("clinicName")
    @Expose
    private String clinicName;
    private String profilePhotoPath;
    private String latitude;
    private String longititude;
    private String clinicMobileNo;
    private String address;
    private String degree;
    private String specialityName;
    private String appointmentStatus;

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getAddress() {
        return address;
    }

    public String getDegree() {
        return degree;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongititude() {
        return longititude;
    }

    public String getClinicMobileNo() {
        return clinicMobileNo;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    String memberName;

    public String getMemberName() {
        return memberName;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public String getMsg() {
        return msg;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }


    @Override
    public String toString() {
        return "OnlineAppointmentModel{" +
                "doctorId=" + doctorId +
                ", isEraUser=" + isEraUser +
                ", appointmentId='" + appointmentId + '\'' +
                ", visitDate='" + visitDate + '\'' +
                ", visitTime='" + visitTime + '\'' +
                ", msg='" + msg + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longititude='" + longititude + '\'' +
                ", clinicMobileNo='" + clinicMobileNo + '\'' +
                ", address='" + address + '\'' +
                ", degree='" + degree + '\'' +
                ", specialityName='" + specialityName + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
