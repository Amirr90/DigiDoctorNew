package com.digidoctor.android.model;

public class Registration {

    private Integer callingCodeId;
    private long serviceProviderTypeId;
    private String name;
    private String mobileNo;
    private String password;
    private String dob;
    private long gender;
    private long otp;
    private long countryId;
    private long stateId;
    private long cityId;
    private String deviceToken;
    private long deviceType;
    private String emailId;
    private String appType;
    private String address;
    private String profilePhotoPath;


    public void setCallingCodeId(Integer callingCodeId) {
        this.callingCodeId = callingCodeId;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCallingCodeId() {
        if (callingCodeId == null)
            return 101;
        else
            return callingCodeId;
    }


    public long getServiceProviderTypeId() {
        return serviceProviderTypeId;
    }

    public void setServiceProviderTypeId(long serviceProviderTypeId) {
        this.serviceProviderTypeId = serviceProviderTypeId;
    }

    public String getName() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getGender() {
        return gender;
    }

    public void setGender(long gender) {
        this.gender = gender;
    }

    public long getOtp() {
        return otp;
    }

    public void setOtp(long otp) {
        this.otp = otp;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public long getDeviceType() {
        return 1;
    }

    public void setDeviceType(long deviceType) {
        this.deviceType = deviceType;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAppType() {
        if (null == appType)
            return "DD";
        else
            return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "callingCodeID=" + callingCodeId +
                ", serviceProviderTypeID=" + serviceProviderTypeId +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", gender=" + gender +
                ", otp=" + otp +
                ", countryID=" + countryId +
                ", stateID=" + stateId +
                ", cityID=" + cityId +
                ", deviceToken='" + deviceToken + '\'' +
                ", deviceType=" + deviceType +
                ", emailID='" + emailId + '\'' +
                ", appType='" + appType + '\'' +
                ", address='" + address + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                '}';
    }
}
