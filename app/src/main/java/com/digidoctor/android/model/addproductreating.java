package com.digidoctor.android.model;

import org.jetbrains.annotations.NotNull;

public class addproductreating {

    String memberId;
    String productInfoCode;
    String review;
    String starrating;

    @NotNull
    @Override
    public String toString() {
        return "addproductreating{" +
                "memberId='" + memberId + '\'' +
                ", productInfoCode='" + productInfoCode + '\'' +
                ", review='" + review + '\'' +
                ", starrating='" + starrating + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getProductInfoCode() {
        return productInfoCode;
    }

    public void setProductInfoCode(String productInfoCode) {
        this.productInfoCode = productInfoCode;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getStarrating() {
        return starrating;
    }

    public void setStarrating(String starrating) {
        this.starrating = starrating;
    }
}

