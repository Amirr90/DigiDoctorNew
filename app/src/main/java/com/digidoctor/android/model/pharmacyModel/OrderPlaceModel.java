package com.digidoctor.android.model.pharmacyModel;

public class OrderPlaceModel {

    String memberId;
    String addressId;
    String trancationNo;
    String paymentMode;
    String couponCode;
    String uniqueNo;


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

    @Override
    public String toString() {
        return "OrderPlaceModel{" +
                "memberId='" + memberId + '\'' +
                ", addressId='" + addressId + '\'' +
                ", trancationNo='" + trancationNo + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", uniqueNo='" + uniqueNo + '\'' +
                '}';
    }


}
