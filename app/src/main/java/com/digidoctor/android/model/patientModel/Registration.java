package com.digidoctor.android.model.patientModel;

public class Registration {

    private Integer callingCodeID;
    private long serviceProviderTypeID;
    private String name;
    private String mobileNo;
    private String password;
    private String dob;
    private long gender;
    private long otp;
    private long countryID;
    private long stateID;
    private long cityID;
    private String deviceToken;
    private long deviceType;
    private String emailID;
    private String appType;
    private String address;
    private String profilePhotoPath;


    public void setCallingCodeID(Integer callingCodeID) {
        this.callingCodeID = callingCodeID;
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

    public long getCallingCodeID() {
        if (callingCodeID == null)
            return 101;
        else
            return callingCodeID;
    }


    public long getServiceProviderTypeID() {
        return serviceProviderTypeID;
    }

    public void setServiceProviderTypeID(long serviceProviderTypeID) {
        this.serviceProviderTypeID = serviceProviderTypeID;
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

    public long getCountryID() {
        return countryID;
    }

    public void setCountryID(long countryID) {
        this.countryID = countryID;
    }

    public long getStateID() {
        return stateID;
    }

    public void setStateID(long stateID) {
        this.stateID = stateID;
    }

    public long getCityID() {
        return cityID;
    }

    public void setCityID(long cityID) {
        this.cityID = cityID;
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

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
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
                "callingCodeID=" + callingCodeID +
                ", serviceProviderTypeID=" + serviceProviderTypeID +
                ", name='" + name + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", dob='" + dob + '\'' +
                ", gender=" + gender +
                ", otp=" + otp +
                ", countryID=" + countryID +
                ", stateID=" + stateID +
                ", cityID=" + cityID +
                ", deviceToken='" + deviceToken + '\'' +
                ", deviceType=" + deviceType +
                ", emailID='" + emailID + '\'' +
                ", appType='" + appType + '\'' +
                ", address='" + address + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                '}';
    }
}
