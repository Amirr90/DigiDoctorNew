package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class AppointmentModel {

    private String appointmentId;
    private String isEraUser;
    private String doctorId;
    private String doctorName;
    private String drMobileNo;
    private String specialty;
    private String clinicName;
    private String doctorAddress;
    private String appointmentIdView;
    private String expiredStatus;
    private String profilePhotoPath;
    private String doctorFees;
    private String location;
    private String appointDate;
    private String appointTime;
    private String patientName;
    private String mobileNo;
    private String guardianName;
    private String stateName;
    private String cityName;
    private String address;
    private String pincode;
    private String problemName;
    private String serviceProviderDetailsId;
    private boolean isPrescribed;
    private String degree;
    private String latitude;
    private String longititude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongititude() {
        return longititude;
    }

    public String getDegree() {
        return degree;
    }

    public String getProblemName() {
        return problemName;
    }

    public String getServiceProviderDetailsId() {
        return doctorId;
    }

    public boolean isPrescribed() {
        return isPrescribed;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getIsEraUser() {
        return isEraUser;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public String getDrMobileNo() {
        return drMobileNo;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public String getAppointmentIdView() {
        return appointmentIdView;
    }

    public String getExpiredStatus() {
        return expiredStatus;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getDoctorFees() {
        return doctorFees;
    }

    public String getLocation() {
        return location;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public String getStateName() {
        return stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAddress() {
        return address;
    }

    public String getPincode() {
        return pincode;
    }


    public String getDoctorId() {
        return doctorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppointmentModel)) return false;
        AppointmentModel that = (AppointmentModel) o;
        return isPrescribed() == that.isPrescribed() &&
                Objects.equals(getAppointmentId(), that.getAppointmentId()) &&
                Objects.equals(getIsEraUser(), that.getIsEraUser()) &&
                Objects.equals(getDoctorId(), that.getDoctorId()) &&
                Objects.equals(getDoctorName(), that.getDoctorName()) &&
                Objects.equals(getDrMobileNo(), that.getDrMobileNo()) &&
                Objects.equals(getSpecialty(), that.getSpecialty()) &&
                Objects.equals(getClinicName(), that.getClinicName()) &&
                Objects.equals(getDoctorAddress(), that.getDoctorAddress()) &&
                Objects.equals(getAppointmentIdView(), that.getAppointmentIdView()) &&
                Objects.equals(getExpiredStatus(), that.getExpiredStatus()) &&
                Objects.equals(getProfilePhotoPath(), that.getProfilePhotoPath()) &&
                Objects.equals(getDoctorFees(), that.getDoctorFees()) &&
                Objects.equals(getLocation(), that.getLocation()) &&
                Objects.equals(getAppointDate(), that.getAppointDate()) &&
                Objects.equals(getAppointTime(), that.getAppointTime()) &&
                Objects.equals(getPatientName(), that.getPatientName()) &&
                Objects.equals(getMobileNo(), that.getMobileNo()) &&
                Objects.equals(getGuardianName(), that.getGuardianName()) &&
                Objects.equals(getStateName(), that.getStateName()) &&
                Objects.equals(getCityName(), that.getCityName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPincode(), that.getPincode()) &&
                Objects.equals(getServiceProviderDetailsId(), that.getServiceProviderDetailsId());
    }


    @Override
    public String toString() {
        return "{" +
                "appointmentId='" + appointmentId + '\'' +
                ", isEraUser='" + isEraUser + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", drMobileNo='" + drMobileNo + '\'' +
                ", specialty='" + specialty + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", doctorAddress='" + doctorAddress + '\'' +
                ", appointmentIdView='" + appointmentIdView + '\'' +
                ", expiredStatus='" + expiredStatus + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", doctorFees='" + doctorFees + '\'' +
                ", location='" + location + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", patientName='" + patientName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", problemName='" + problemName + '\'' +
                ", serviceProviderDetailsId='" + serviceProviderDetailsId + '\'' +
                ", isPrescribed=" + isPrescribed +
                ", degree='" + degree + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longititude='" + longititude + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAppointmentId(), getIsEraUser(), getDoctorId(), getDoctorName(), getDrMobileNo(), getSpecialty(), getClinicName(), getDoctorAddress(), getAppointmentIdView(), getExpiredStatus(), getProfilePhotoPath(), getDoctorFees(), getLocation(), getAppointDate(), getAppointTime(), getPatientName(), getMobileNo(), getGuardianName(), getStateName(), getCityName(), getAddress(), getPincode(), getServiceProviderDetailsId(), isPrescribed());
    }

    public static DiffUtil.ItemCallback<AppointmentModel> itemCallback = new DiffUtil.ItemCallback<AppointmentModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull AppointmentModel oldItem, @NonNull AppointmentModel newItem) {
            return oldItem.appointDate.equalsIgnoreCase(newItem.getAppointDate());
        }

        @Override
        public boolean areContentsTheSame(@NonNull AppointmentModel oldItem, @NonNull AppointmentModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
