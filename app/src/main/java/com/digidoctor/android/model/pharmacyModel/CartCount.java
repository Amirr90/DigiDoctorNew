package com.digidoctor.android.model.pharmacyModel;

import java.util.List;

public class CartCount {


    int responseCode;
    String responseMessage;
    List<CartcountList> CartcountList;

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

    public List<CartcountList> getResponseValue() {
        return CartcountList;
    }

    public void setResponseValue(List<CartcountList> responseValue) {
        this.CartcountList = responseValue;
    }

    public List<CartCount.CartcountList> getCartcountList() {
        return CartcountList;
    }

    public class CartcountList extends CartCount   {

        int cartCount;

        public int getCartCount() {
            return cartCount;
        }

        public void setCartCount(int cartCount) {
            this.cartCount = cartCount;
        }


    }
}
