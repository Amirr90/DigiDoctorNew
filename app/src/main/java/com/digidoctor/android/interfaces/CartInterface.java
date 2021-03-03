package com.digidoctor.android.interfaces;

public interface CartInterface {
    void onFailed(String msg);

    void onCartItemAdded(Object obj);

    void onCartItemDeleted(Object obj);

    void cartItem(Object obj);


}
