package com.digidoctor.android.model.pharmacyModel;

import java.util.List;
import java.util.Observable;

public class GetAllProductResponse {

    int responseCode;
    String responseMessage;
    List<GetProduct> responseValue;

    public int getResponseCode() {
        return responseCode;


    }

    public String getResponseMessage() {
        return responseMessage;


    }

    public List<GetProduct> getResponseValue() {
        return responseValue;


    }

    public class GetProduct extends Observable {

        int productId;
        String productName;
        String shortDescription;
        int mrp;
        int offeredPrice;
        String productInfoCode;
        String inCartStatus;
        String wishlistStatus;
        String imageURL;
        int starRating;

        public int getStarRating() {
            return starRating;
        }

        public void setStarRating(int starRating) {
            this.starRating = starRating;
        }

        public int getProductId() {
            return productId;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public int getMrp() {
            return mrp;
        }

        public int getOfferedPrice() {
            return offeredPrice;
        }

        public String getProductInfoCode() {
            return productInfoCode;
        }

        public String getInCartStatus() {
            return inCartStatus;
        }

        public String getWishlistStatus() {
            return wishlistStatus;
        }

        public void setWishlistStatus(String wishlistStatus) {
            this.wishlistStatus = wishlistStatus;
        }

        public String getImageURL() {
            return imageURL;
        }

        @Override
        public String toString() {
            return "GetProduct{" +
                    "productId='" + productId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", shortDescription='" + shortDescription + '\'' +
                    ", mrp='" + mrp + '\'' +
                    ", offeredPrice='" + offeredPrice + '\'' +
                    ", productInfoCode='" + productInfoCode + '\'' +
                    ", inCartStatus='" + inCartStatus + '\'' +
                    ", wishlistStatus='" + wishlistStatus + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    '}';
        }

        public String getProductName() {
            return productName;
        }


    }
}


