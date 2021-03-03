package com.digidoctor.android.utility;

import android.app.Activity;

import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.model.labmodel.CartModel;

public class Cart {

    Activity activity;
    CartInterface cartInterface;

    public Cart(Activity activity) {
        this.activity = activity;
    }

    public Cart() {
    }

    public void addItemToCart(String testId, String packageId) {
        CartModel cartModel = new CartModel();
        cartModel.setMemberId(utils.getPrimaryUser(activity).getMemberId());
        cartModel.setTestId(testId);

        if (null != utils.getPrimaryUser(activity).getUniqueNo())
            cartModel.setUniqueNo(utils.getPrimaryUser(activity).getUniqueNo());
        cartModel.setTestId(packageId);
        ApiUtils.addItemToCart(cartModel, cartInterface);
    }

    public void deleteFromCart(String cartId) {
        CartModel cartModel = new CartModel();
        cartModel.setCartId(cartId);
        ApiUtils.deleteFromCart(cartModel, cartInterface);
    }

    public void deleteAllFromCart() {

    }

    public void getCart() {
        ApiUtils.getCartData(activity, cartInterface);
    }


}
