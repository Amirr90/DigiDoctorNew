package com.digidoctor.android.utility;

import android.app.Activity;
import android.util.Log;

import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.model.labmodel.CartModel;

public class Cart {
    private static final String TAG = "Cart";

    Activity activity;
    CartInterface cartInterface;

    public Cart(Activity activity, CartInterface cartInterface) {
        this.activity = activity;
        this.cartInterface = cartInterface;
    }

    public Cart() {
    }

    public void addItemToCart(String testId, String packageId) {
        CartModel cartModel = new CartModel();
        cartModel.setMemberId(utils.getPrimaryUser(activity).getMemberId());
        cartModel.setTestId(testId);
        cartModel.setPackageId(packageId);

        if (null == utils.getPrimaryUser(activity).getMemberId())
            cartModel.setUniqueNo(utils.getPrimaryUser(activity).getUniqueNo());

        Log.d(TAG, "addItemToCart: " + cartModel);
        AppUtils.showRequestDialog(activity);
        ApiUtils.addItemToCart(cartModel, cartInterface);
    }

    public void deleteItemFromCart(String cartId) {
        CartModel cartModel = new CartModel();
        cartModel.setCartId(cartId);
        ApiUtils.deleteFromCart(cartModel, cartInterface);
    }

    public void deleteAllItemFromCart() {

    }

    public void getCart() {
        ApiUtils.getCartData(activity, cartInterface);
    }


}
