package com.digidoctor.android.model.pharmacyModel;

public class PharmacyModel {

    public void setMemberId(String memberId) {
        this.memberId = memberId;


    }
    String uniqueNo;
    String memberId;
    String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    String orderDetailsId;
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    String cartId;


    public String getUniqueNo() {
        return uniqueNo;
    }

    public String getMemberId() {
        return memberId;
    }
}
