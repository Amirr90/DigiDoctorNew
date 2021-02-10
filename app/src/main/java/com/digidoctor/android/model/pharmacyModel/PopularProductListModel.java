package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class PopularProductListModel {

    String productId;
    String productName;
    String shortDescription;
    String imageURL;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    List<PopularProductList> popularproductlist;

    public List<PopularProductList> getpopularproducts() {
        return popularproductlist;
    }
    public class PopularProductList{
        String productId;

        public String getProductName() {
            return productName;
        }

        String productName;
        String shortDescription;
        String imageURL;

        @Override
        public String toString() {
            return "PopularProductList{" +
                    "productId='" + productId + '\'' +
                    ", productName='" + productName + '\'' +
                    ", shortDescription='" + shortDescription + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    '}';
        }
    }
}
