package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class CoupnemodelRes {
    int responseCode;
    String responseMessage;
    List<CouponModelList> responseValue;

    public List<CouponModelList> getResponseValue() {
        return responseValue;
    }


    @Override
    public String toString() {
        return "CoupnemodelRes{" +
                "responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseValue='" + responseValue + '\'' +
                '}';
    }

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


    public class CouponModelList {

        String couponStatus;

        @Override
        public String toString() {
            return "CouponModelList{" +
                    "couponStatus='" + couponStatus + '\'' +
                    '}';
        }

        public String getCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(String couponStatus) {
            this.couponStatus = couponStatus;
        }
    }
}
