package com.digidoctor.android.model.patientModel;

public class Login {
    private String mobileNo;
    private String password;
    private String accessToken;
    private String serviceProviderTypeID;
    private String deviceToken;
    private String deviceType;
    private String appType;
    private String uniqueNOS;
    private String otp;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        if (null == password)
            return "";
        else
            return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {

        if (null == accessToken)
            return "";
        else
            return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getServiceProviderTypeID() {
        return serviceProviderTypeID;
    }

    public void setServiceProviderTypeID(String serviceProviderTypeID) {
        this.serviceProviderTypeID = serviceProviderTypeID;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUniqueNOS() {
        if (null == uniqueNOS)
            return "";
        return uniqueNOS;
    }

    public void setUniqueNOS(String uniqueNOS) {
        this.uniqueNOS = uniqueNOS;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


    @Override
    public String toString() {
        return "Login{" +
                "mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", serviceProviderTypeID='" + serviceProviderTypeID + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", appType='" + appType + '\'' +
                ", uniqueNOS='" + uniqueNOS + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
