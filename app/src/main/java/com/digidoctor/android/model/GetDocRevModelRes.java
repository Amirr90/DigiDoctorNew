package com.digidoctor.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDocRevModelRes {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;

    @SerializedName("responseValue")
    @Expose
    private List<GetDoctorReviewList> responseValue = null;


    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<GetDoctorReviewList> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<GetDoctorReviewList> responseValue) {
        this.responseValue = responseValue;
    }

    public class GetDoctorReviewList {


        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("reviewDate")
        @Expose
        public String reviewDate;
        @SerializedName("review")
        @Expose
        public String review;
        @SerializedName("starRating")
        @Expose
        public Integer starRating;

        @Override
        public String toString() {
            return "GetDoctorReviewList{" +
                    "name='" + name + '\'' +
                    ", time='" + time + '\'' +
                    ", reviewDate='" + reviewDate + '\'' +
                    ", review='" + review + '\'' +
                    ", starRating=" + starRating +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(String reviewDate) {
            this.reviewDate = reviewDate;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public Integer getStarRating() {
            return starRating;
        }

        public void setStarRating(Integer starRating) {
            this.starRating = starRating;
        }
    }
}
