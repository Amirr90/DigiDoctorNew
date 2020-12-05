package com.digidoctor.android.model;

public class TransactionModel {

    private String paymentAmount;
    private String patientName;
    private String memberID;
    private String userMobileNo;
    private String appointDate;
    private String appointTime;
    private String serviceProviderDetailsID;
    private String isEraUser;

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public String getServiceProviderDetailsID() {
        return serviceProviderDetailsID;
    }

    public void setServiceProviderDetailsID(String serviceProviderDetailsID) {
        this.serviceProviderDetailsID = serviceProviderDetailsID;
    }

    public String getIsEraUser() {
        return isEraUser;
    }

    public void setIsEraUser(String isEraUser) {
        this.isEraUser = isEraUser;
    }
}
