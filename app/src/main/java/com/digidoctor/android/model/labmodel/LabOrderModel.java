package com.digidoctor.android.model.labmodel;

public class LabOrderModel {

    private String memberId;
    private String addressId;
    private String trancationNo;
    private String paymentMode;
    private String couponCode;
    private String uniqueNo;
    private String dtPaymentTable;
    private String appointmentDate;
    private String appointmentTime;
    private String pathalogyId;
    private String visitType;
    Integer id;
    Integer finalAmount;
    String orderNo;
    String pathologyName;
    String testName;
    String orderDate;
    String orderStatus;



    public Integer getId() {
        return id;
    }

    public Integer getFinalAmount() {
        return finalAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getPathologyName() {
        return pathologyName;
    }

    public String getTestName() {
        return testName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getTrancationNo() {
        return trancationNo;
    }

    public void setTrancationNo(String trancationNo) {
        this.trancationNo = trancationNo;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getDtPaymentTable() {
        return dtPaymentTable;
    }

    public void setDtPaymentTable(String dtPaymentTable) {
        this.dtPaymentTable = dtPaymentTable;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getPathalogyId() {
        return pathalogyId;
    }

    public void setPathalogyId(String pathalogyId) {
        this.pathalogyId = pathalogyId;
    }


}
