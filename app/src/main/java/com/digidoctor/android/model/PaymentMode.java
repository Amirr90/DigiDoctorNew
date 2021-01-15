package com.digidoctor.android.model;

public class PaymentMode {
    Integer id;
    String paymentMode;
    String image;

    public Integer getId() {
        return id;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
