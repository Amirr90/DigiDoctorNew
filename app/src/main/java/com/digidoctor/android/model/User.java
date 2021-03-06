package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class User {


    String appointmentId;
    Integer pid;
    int specialityId;
    int amount;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("dob")
    @Expose
    private String dob;

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    @SerializedName("gender")
    @Expose
    private Integer gender;

    public Integer getGender() {
        return gender;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getIsExists() {
        return isExists;
    }

    @SerializedName("isExists")
    @Expose
    private Integer isExists;

    @SerializedName("primaryStatus")
    @Expose
    private Integer primaryStatus;

    @SerializedName("userLoginId")
    @Expose
    private Integer userLoginId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;

    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("serviceProviderType")
    @Expose
    private String serviceProviderType;
    @SerializedName("isEraUser")
    @Expose
    private Integer isEraUser;
    @SerializedName("countryCallingCode")
    @Expose
    private String countryCallingCode;
    @SerializedName("profilePhotoPath")
    @Expose
    private String profilePhotoPath;
    @SerializedName("clinicDetails")
    @Expose
    private String clinicDetails;

    @SerializedName("doctorsCount")
    @Expose
    private Integer doctorsCount;


    @SerializedName("uniqueNo")
    @Expose
    private String uniqueNo;


    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setIsExists(Integer isExists) {
        this.isExists = isExists;
    }

    public void setPrimaryStatus(Integer primaryStatus) {
        this.primaryStatus = primaryStatus;
    }

    public void setIsEraUser(Integer isEraUser) {
        this.isEraUser = isEraUser;
    }

    public void setCountryCallingCode(String countryCallingCode) {
        this.countryCallingCode = countryCallingCode;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public void setClinicDetails(String clinicDetails) {
        this.clinicDetails = clinicDetails;
    }

    public void setDoctorsCount(Integer doctorsCount) {
        this.doctorsCount = doctorsCount;
    }

    Integer memberId;

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getPrimaryStatus() {
        if (null == primaryStatus) {
            return 0;
        } else
            return primaryStatus;
    }

    public Integer getDoctorsCount() {
        return doctorsCount;
    }

    public String getClinicDetails() {
        return clinicDetails;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public String getCountryCallingCode() {
        return countryCallingCode;
    }

    public Integer getIsEraUser() {
        return isEraUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getName() {
        if (null == name)
            return "No Name";
        else
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getServiceProviderType() {
        return serviceProviderType;
    }

    public void setServiceProviderType(String serviceProviderType) {
        this.serviceProviderType = serviceProviderType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getAddress(), user.getAddress()) &&
                Objects.equals(getDob(), user.getDob()) &&
                Objects.equals(getGender(), user.getGender()) &&
                Objects.equals(getId(), user.getId()) &&
                Objects.equals(getIsExists(), user.getIsExists()) &&
                Objects.equals(getPrimaryStatus(), user.getPrimaryStatus()) &&
                Objects.equals(getUserLoginId(), user.getUserLoginId()) &&
                getName().equals(user.getName()) &&
                Objects.equals(getMobileNo(), user.getMobileNo()) &&
                Objects.equals(getEmailId(), user.getEmailId()) &&
                Objects.equals(getServiceProviderType(), user.getServiceProviderType()) &&
                Objects.equals(getIsEraUser(), user.getIsEraUser()) &&
                Objects.equals(getCountryCallingCode(), user.getCountryCallingCode()) &&
                Objects.equals(getProfilePhotoPath(), user.getProfilePhotoPath()) &&
                Objects.equals(getClinicDetails(), user.getClinicDetails()) &&
                Objects.equals(getDoctorsCount(), user.getDoctorsCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getDob(), getGender(), getId(), getIsExists(), getPrimaryStatus(), getUserLoginId(), getName(), getMobileNo(), getEmailId(), getServiceProviderType(), getIsEraUser(), getCountryCallingCode(), getProfilePhotoPath(), getClinicDetails(), getDoctorsCount());
    }

    @Override
    public String toString() {
        return "{" +
                "appointmentId='" + appointmentId + '\'' +
                ", pid='" + pid + '\'' +
                ", specialityId=" + specialityId +
                ", amount=" + amount +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", gender=" + gender +
                ", id=" + id +
                ", isExists=" + isExists +
                ", primaryStatus=" + primaryStatus +
                ", userLoginId=" + userLoginId +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", serviceProviderType='" + serviceProviderType + '\'' +
                ", isEraUser=" + isEraUser +
                ", countryCallingCode='" + countryCallingCode + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", clinicDetails='" + clinicDetails + '\'' +
                ", doctorsCount=" + doctorsCount +
                ", uniqueNo='" + uniqueNo + '\'' +
                ", memberId=" + memberId +
                '}';
    }

    public static DiffUtil.ItemCallback<User> itemCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getName().equalsIgnoreCase(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };
}
