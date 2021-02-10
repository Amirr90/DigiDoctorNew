package com.digidoctor.android.model.patientModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class User {

    int memberId;

    public int getMemberId() {
        return memberId;
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

    public Integer getPrimaryStatus() {
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
        return "User{" +
                "memberId=" + memberId +
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
