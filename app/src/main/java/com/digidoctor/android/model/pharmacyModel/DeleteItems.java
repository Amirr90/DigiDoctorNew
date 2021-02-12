package com.digidoctor.android.model.pharmacyModel;

public class DeleteItems {
    String cartId;

    public DeleteItems(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }
}
