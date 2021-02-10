package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageDetail {
    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("noOfTests")
    @Expose
    private Integer noOfTests;
    @SerializedName("packagePrice")
    @Expose
    private Integer packagePrice;
    @SerializedName("discountPerc")
    @Expose
    private Integer discountPerc;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("cartStatus")
    @Expose
    private Integer cartStatus;

    @Override
    public String toString() {
        return "PackageDetail{" +
                "packageId=" + packageId +
                ", packageName='" + packageName + '\'' +
                ", description='" + description + '\'' +
                ", noOfTests=" + noOfTests +
                ", packagePrice=" + packagePrice +
                ", discountPerc=" + discountPerc +
                ", mrp=" + mrp +
                ", cartStatus=" + cartStatus +
                '}';
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoOfTests() {
        return noOfTests;
    }

    public void setNoOfTests(Integer noOfTests) {
        this.noOfTests = noOfTests;
    }

    public Integer getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(Integer packagePrice) {
        this.packagePrice = packagePrice;
    }

    public Integer getDiscountPerc() {
        return discountPerc;
    }

    public void setDiscountPerc(Integer discountPerc) {
        this.discountPerc = discountPerc;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public Integer getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(Integer cartStatus) {
        this.cartStatus = cartStatus;
    }
}
