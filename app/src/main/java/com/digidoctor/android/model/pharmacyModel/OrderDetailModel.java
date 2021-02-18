package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class OrderDetailModel {

    int responseCode;
    String responseMessage;
    List<Orderdetaillist> responseValue;

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Orderdetaillist> getResponseValue() {
        return responseValue;
    }

    public static class Orderdetaillist {


        List<ProductDetails> productDetails;
        List<relatedProducts> relatedProducts;
        List<PriceDetails> priceDetails;
        List<GetOrderStatus> orderStatus;

        public List<PriceDetails> getPriceDetails() {
            return priceDetails;
        }

        public List<relatedProducts> getRelatedProducts() {
            return relatedProducts;
        }

        public List<ProductDetails> getProductDetails() {
            return productDetails;
        }

        public List<GetOrderStatus> getOrderStatus() {
            return orderStatus;
        }


        public class ProductDetails {
            int orderDetailsId;
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
            String productInfoCode;

            @Override
            public String toString() {
                return "ProductDetails{" +
                        "orderDetailsId=" + orderDetailsId +
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
                        ", productInfoCode='" + productInfoCode + '\'' +
                        '}';
            }

            public String getProductInfoCode() {
                return productInfoCode;
            }

            public void setProductInfoCode(String productInfoCode) {
                this.productInfoCode = productInfoCode;
            }

            public int getOrderDetailsId() {
                return orderDetailsId;
            }

            public void setOrderDetailsId(int orderDetailsId) {
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
        }

        public class GetOrderStatus {

            String orderStatus;
            String orderDate;
            String shippiingDate;
            String packingDate;
            String deliveryDate;
            String cancelledDate;

            @Override
            public String toString() {
                return "GetOrderStatus{" +
                        "orderStatus='" + orderStatus + '\'' +
                        ", orderDate='" + orderDate + '\'' +
                        ", shippiingDate='" + shippiingDate + '\'' +
                        ", packingDate='" + packingDate + '\'' +
                        ", deliveryDate='" + deliveryDate + '\'' +
                        ", cancelledDate='" + cancelledDate + '\'' +
                        '}';
            }

            public String getOrderStatus() {
                return null == orderStatus ? "" : orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getOrderDate() {
                return null == orderDate ? "" : orderDate;
            }

            public void setOrderDate(String orderDate) {
                this.orderDate = orderDate;
            }

            public String getShippiingDate() {
                return shippiingDate;
            }

            public void setShippiingDate(String shippiingDate) {
                this.shippiingDate = shippiingDate;
            }

            public String getPackingDate() {
                return null == packingDate ? "" : packingDate;
            }

            public void setPackingDate(String packingDate) {
                this.packingDate = packingDate;
            }

            public String getDeliveryDate() {
                return null == deliveryDate ? "" : deliveryDate;
            }

            public void setDeliveryDate(String deliveryDate) {
                this.deliveryDate = deliveryDate;
            }

            public String getCancelledDate() {
                return null == cancelledDate ? "" : cancelledDate;
            }

            public void setCancelledDate(String cancelledDate) {
                this.cancelledDate = cancelledDate;
            }
        }

        public class relatedProducts {


            String productName;
            String brandName;
            String expectedDelievery;
            String imagePath;
            int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            @Override
            public String toString() {
                return "relatedProducts{" +
                        "productName='" + productName + '\'' +
                        ", brandName='" + brandName + '\'' +
                        ", expectedDelievery='" + expectedDelievery + '\'' +
                        ", imagePath='" + imagePath + '\'' +
                        '}';
            }
        }

        public class PriceDetails {
            int totalProducts;
            int mrp;
            int finalAmount;
            int youSave;
            int subTotal;
            String paymentMode;
            int totalQuantity;

            @Override
            public String toString() {
                return "PriceDetails{" +
                        "totalProducts=" + totalProducts +
                        ", mrp=" + mrp +
                        ", finalAmount=" + finalAmount +
                        ", youSave=" + youSave +
                        ", subTotal=" + subTotal +
                        ", paymentMode='" + paymentMode + '\'' +
                        ", totalQuantity=" + totalQuantity +
                        '}';
            }

            public int getTotalQuantity() {
                return totalQuantity;
            }

            public void setTotalQuantity(int totalQuantity) {
                this.totalQuantity = totalQuantity;
            }

            public int getTotalProducts() {
                return totalProducts;
            }

            public void setTotalProducts(int totalProducts) {
                this.totalProducts = totalProducts;
            }

            public int getMrp() {
                return mrp;
            }

            public void setMrp(int mrp) {
                this.mrp = mrp;
            }

            public int getFinalAmount() {
                return finalAmount;
            }

            public void setFinalAmount(int finalAmount) {
                this.finalAmount = finalAmount;
            }

            public int getYouSave() {
                return youSave;
            }

            public void setYouSave(int youSave) {
                this.youSave = youSave;
            }

            public int getSubTotal() {
                return subTotal;
            }

            public void setSubTotal(int subTotal) {
                this.subTotal = subTotal;
            }

            public String getPaymentMode() {
                return paymentMode;
            }

            public void setPaymentMode(String paymentMode) {
                this.paymentMode = paymentMode;
            }
        }

    }
}