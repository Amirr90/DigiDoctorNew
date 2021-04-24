package com.digidoctor.android.interfaces;

import com.digidoctor.android.model.LabModel;

public interface PaymentInterface {

    void onPaymentSuccess(Object obj, LabModel labModel, LabOrderInterface anInterface);

    void onPaymentFailed(String msg);
}
