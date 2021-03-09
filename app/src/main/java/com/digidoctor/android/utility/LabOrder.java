package com.digidoctor.android.utility;

import android.app.Activity;

import com.digidoctor.android.interfaces.LabOrderInterface;
import com.digidoctor.android.model.labmodel.LabOrderModel;

public class LabOrder {
    Activity activity;
    LabOrderModel orderModel;
    LabOrderInterface orderInterface;

    public LabOrder(Activity activity, LabOrderModel orderModel, LabOrderInterface orderInterface) {
        this.activity = activity;
        this.orderModel = orderModel;
        this.orderInterface = orderInterface;
    }

    public void placeOrder() {
        ApiUtils.placeOrder(orderModel, orderInterface);
    }

    public void getOrders() {
        ApiUtils.getOrders(orderModel, orderInterface);
    }

    public void cancelOrder() {
        ApiUtils.cancelOrder(orderModel, orderInterface);
    }
}
