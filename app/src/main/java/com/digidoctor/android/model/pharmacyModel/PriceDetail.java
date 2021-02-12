package com.digidoctor.android.model.pharmacyModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.digidoctor.android.BR;

public class PriceDetail extends BaseObservable {
    int totalProducts;
    Double totalMrp;
    Double saveAmount;
    Double delievryCharge;
    Double totalAmount;
    String couponCode;
    Double couponAmount;

    @Bindable
    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }


    @Bindable
    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    @Bindable
    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
        notifyPropertyChanged(BR.totalProducts);
    }

    @Bindable
    public Double getTotalMrp() {
        return totalMrp;
    }

    public void setTotalMrp(Double totalMrp) {
        this.totalMrp = totalMrp;
        notifyPropertyChanged(BR.totalMrp);

    }


    @Bindable
    public Double getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(Double saveAmount) {
        this.saveAmount = saveAmount;
        notifyPropertyChanged(BR.saveAmount);

    }


    @Bindable
    public Double getDelievryCharge() {
        return delievryCharge;
    }

    public void setDelievryCharge(Double delievryCharge) {
        this.delievryCharge = delievryCharge;
        notifyPropertyChanged(BR.delievryCharge);

    }


    @Bindable
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        notifyPropertyChanged(BR.totalAmount);

    }

    @Override
    public String toString() {
        return "PriceDetail{" +
                "totalProducts=" + totalProducts +
                ", totalMrp=" + totalMrp +
                ", saveAmount=" + saveAmount +
                ", delievryCharge=" + delievryCharge +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
