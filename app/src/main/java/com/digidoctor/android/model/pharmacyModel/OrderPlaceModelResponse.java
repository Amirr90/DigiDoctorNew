package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class OrderPlaceModelResponse {
    int responseCode;

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

    public List<GetOrderPlaced> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<GetOrderPlaced> responseValue) {
        this.responseValue = responseValue;
    }

    String responseMessage;
    List<GetOrderPlaced> responseValue;

    public static class GetOrderPlaced {


        String orderNo;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public String toString() {
            return "GetOrderPlaced{" +
                    "orderNo='" + orderNo + '\'' +
                    '}';
        }


    }
}
