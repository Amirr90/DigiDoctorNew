package com.digidoctor.android.model;

import java.util.List;

public class GetOrderRes {
    int responseCode;
    String responseMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<getplacedorder> getResponseValue() {
        return responseValue;
    }

    List<getplacedorder> responseValue;


    public static class getplacedorder {


        String orderDetailsId;
        String productName;
        String brandName;
        String orderNo;
        String expectedDelievery;
        String imagePath;
        String orderStatus;
        int subTotal;
        int quantity;
        String address;
        String customerName;
        String customerMobile;

        public String getOrderDetailsId() {
            return orderDetailsId;
        }

        public void setOrderDetailsId(String orderDetailsId) {
            this.orderDetailsId = orderDetailsId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getExpectedDelievery() {
            return expectedDelievery;
        }

        public void setExpectedDelievery(String expectedDelievery) {
            this.expectedDelievery = expectedDelievery;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public int getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(int subTotal) {
            this.subTotal = subTotal;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerMobile() {
            return customerMobile;
        }

        public void setCustomerMobile(String customerMobile) {
            this.customerMobile = customerMobile;
        }


        @Override
        public String toString() {
            return "getplacedorder{" +
                    "orderDetailsId='" + orderDetailsId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", brandName='" + brandName + '\'' +
                    ", orderNo='" + orderNo + '\'' +
                    ", expectedDelievery='" + expectedDelievery + '\'' +
                    ", imagePath='" + imagePath + '\'' +
                    ", orderStatus='" + orderStatus + '\'' +
                    ", subTotal=" + subTotal +
                    ", quantity=" + quantity +
                    ", address='" + address + '\'' +
                    ", customerName='" + customerName + '\'' +
                    ", customerMobile='" + customerMobile + '\'' +
                    '}';
        }


    }
}

