package com.digidoctor.android.model.pharmacyModel;

public class CouponModel {
    String memberId;
    String couponCode;
    String cartAmount;

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public void setCartAmount(String cartAmount) {
        this.cartAmount = cartAmount;
    }

    public CouponModel(String memberId, String couponCode, String cartAmount) {
        this.memberId = memberId;
        this.couponCode = couponCode;
        this.cartAmount = cartAmount;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getCartAmount() {
        return cartAmount;
    }
}
