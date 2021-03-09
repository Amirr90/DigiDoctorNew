package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.digidoctor.android.utility.AppUtils.parseDate;

public class OnlineAppointmentModel {

    int doctorId;
    int isEraUser;
    String patientName;
    int expiredStatus;
    boolean isPrescribed;
    String appointmentIdView;
    String specialty;
    String appointDate;
    String appointTime;
    String problemName;
    String clinicAddress;
    String attachFile;
    String workingHours;
    int isVisit;
    Integer doctorFees;
    Integer firstAppointmentId;
    Integer reVisitTime;

    public Integer getReVisitTime() {
        return reVisitTime;
    }

    public Integer getFirstAppointmentId() {
        return firstAppointmentId;
    }

    public Integer getDoctorFees() {
        return doctorFees;
    }

    public int getIsVisit() {
        return isVisit;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public String getProblemName() {
        return problemName;
    }

    public String getAppointDate() {
        return parseDate(appointDate, "dd MMMM yyyy");
    }

    public String getAppointTime() {
        return appointTime;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getAppointmentIdView() {
        return appointmentIdView;
    }

    public boolean isPrescribed() {
        return isPrescribed;
    }

    public int getExpiredStatus() {
        return expiredStatus;
    }

    public String getPatientName() {
        return patientName;
    }

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
        return "{" +
                "doctorId=" + doctorId +
                ", isEraUser=" + isEraUser +
                ", patientName='" + patientName + '\'' +
                ", expiredStatus=" + expiredStatus +
                ", isPrescribed=" + isPrescribed +
                ", appointmentIdView='" + appointmentIdView + '\'' +
                ", specialty='" + specialty + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", problemName='" + problemName + '\'' +
                ", clinicAddress='" + clinicAddress + '\'' +
                ", attachFile='" + attachFile + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", isVisit=" + isVisit +
                ", doctorFees=" + doctorFees +
                ", firstAppointmentId=" + firstAppointmentId +
                ", reVisitTime=" + reVisitTime +
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
