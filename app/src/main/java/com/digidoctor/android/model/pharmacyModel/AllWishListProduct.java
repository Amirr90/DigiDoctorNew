package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class AllWishListProduct {

    int responseCode;
    String responseMessage;

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

    public List<AllWishlist> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<AllWishlist> responseValue) {
        this.responseValue = responseValue;
    }

    List<AllWishlist> responseValue;

    public static class AllWishlist {
        int productId;
        String productName;
        String shortDescription;
        String mrp;
        String offeredPrice;
        String productInfoCode;
        String imageURL;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getMrp() {
            return mrp;
        }

        public void setMrp(String mrp) {
            this.mrp = mrp;
        }

        public String getOfferedPrice() {
            return offeredPrice;
        }

        public void setOfferedPrice(String offeredPrice) {
            this.offeredPrice = offeredPrice;
        }

        public String getProductInfoCode() {
            return productInfoCode;
        }

        public void setProductInfoCode(String productInfoCode) {
            this.productInfoCode = productInfoCode;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        @Override
        public String toString() {
            return "AllWishlist{" +
                    "productId=" + productId +
                    ", productName='" + productName + '\'' +
                    ", shortDescription='" + shortDescription + '\'' +
                    ", mrp=" + mrp +
                    ", offeredPrice=" + offeredPrice +
                    ", productInfoCode='" + productInfoCode + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    '}';
        }


    }
}
