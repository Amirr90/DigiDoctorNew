package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WriteReviewModel {

    @SerializedName("serviceProviderDetailsId")
    @Expose
    private Integer serviceProviderDetailsId;
    @SerializedName("starRating")
    @Expose
    private Integer starRating;
    @SerializedName("memberId")
    @Expose
    private Integer memberId;
    @SerializedName("review")
    @Expose
    private String review;

    public Integer getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    public void setServiceProviderDetailsId(Integer serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public Integer getStarRating() {
        return starRating;
    }

    public void setStarRating(Integer starRating) {
        this.starRating = starRating;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}


