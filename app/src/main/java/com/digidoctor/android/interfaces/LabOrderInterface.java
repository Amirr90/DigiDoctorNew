package com.digidoctor.android.interfaces;

public interface LabOrderInterface {

    void onOrderPlaced(Object obj);

    void onFailed(Object obj);

    void orders(Object obj);

    void onCancelOrder(Object obj);
}
