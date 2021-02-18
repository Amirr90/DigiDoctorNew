package com.digidoctor.android.model.pharmacyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllCoupneModelResponse {

    int responseCode;
    String responseMessage;
    List<GetCoupnedetails> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<GetCoupnedetails> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<GetCoupnedetails> responseValue) {
        this.responseValue = responseValue;
    }

    public class GetCoupnedetails {

        int id;
        String couponCode;
        String validityFrom;
        String validityTo;
        String description;
        int couponAmount;
        int minShopping;

        @NotNull
        @Override
        public String toString() {
            return "GetCoupnedetails{" +
                    "id=" + id +
                    ", couponCode='" + couponCode + '\'' +
                    ", validityFrom='" + validityFrom + '\'' +
                    ", validityTo='" + validityTo + '\'' +
                    ", description='" + description + '\'' +
                    ", couponAmount=" + couponAmount +
                    ", minShopping=" + minShopping +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getValidityFrom() {
            return validityFrom;
        }

        public void setValidityFrom(String validityFrom) {
            this.validityFrom = validityFrom;
        }

        public String getValidityTo() {
            return validityTo;
        }

        public void setValidityTo(String validityTo) {
            this.validityTo = validityTo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(int couponAmount) {
            this.couponAmount = couponAmount;
        }

        public int getMinShopping() {
            return minShopping;
        }

        public void setMinShopping(int minShopping) {
            this.minShopping = minShopping;
        }


    }
}
