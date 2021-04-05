package com.digidoctor.android.model;

public class addProductRatingResponse {
    String memberId;
    String productInfoCode;
    String review;
    String starRating;

    @Override
    public String toString() {
        return "addProductRatingResponse{" +
                "memberId='" + memberId + '\'' +
                ", productInfoCode='" + productInfoCode + '\'' +
                ", review='" + review + '\'' +
                ", starRating='" + starRating + '\'' +
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



