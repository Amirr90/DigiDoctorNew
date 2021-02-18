package com.digidoctor.android.model;

import org.jetbrains.annotations.NotNull;

public class addProductRating {

    String memberId;
    String productInfoCode;
    String review;
    String starRating;

    @NotNull
    @Override
    public String toString() {
        return "addProductRating{" +
                "memberId='" + memberId + '\'' +
                ", productInfoCode='" + productInfoCode + '\'' +
                ", review='" + review + '\'' +
                ", starrating='" + starRating + '\'' +
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

    public String getStarRating() {
        return starRating;
    }

    public void setStarRating(String starRating) {
        this.starRating = starRating;
    }
}

