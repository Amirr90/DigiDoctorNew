package com.digidoctor.android.model.pharmacyModel;

public class AddToCartModel {
    String memberId;
    String uniqueNo;
    String productInfoCode;
    String quantity;


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getProductInfoCode() {
        return productInfoCode;
    }

    public void setProductInfoCode(String productInfoCode) {
        this.productInfoCode = productInfoCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddToCartModel{" +
                "memberId='" + memberId + '\'' +
                ", uniqueNo='" + uniqueNo + '\'' +
                ", productInfoCode='" + productInfoCode + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }


}
