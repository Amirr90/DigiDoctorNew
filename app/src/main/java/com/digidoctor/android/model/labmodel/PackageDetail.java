package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageDetail {
    @SerializedName("packageId")
    @Expose
    private int packageId;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("noOfTests")
    @Expose
    private int noOfTests;
    @SerializedName("packagePrice")
    @Expose
    private int packagePrice;
    @SerializedName("discountPerc")
    @Expose
    private int discountPerc;
    @SerializedName("mrp")
    @Expose
    private int mrp;
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

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
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

    public int getNoOfTests() {
        return noOfTests;
    }

    public void setNoOfTests(int noOfTests) {
        this.noOfTests = noOfTests;
    }

    public int getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(int packagePrice) {
        this.packagePrice = packagePrice;
    }

    public int getDiscountPerc() {
        return discountPerc;
    }

    public void setDiscountPerc(int discountPerc) {
        this.discountPerc = discountPerc;
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public int getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(int cartStatus) {
        this.cartStatus = cartStatus;
    }
}
