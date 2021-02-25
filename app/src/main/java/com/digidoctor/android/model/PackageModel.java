package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class PackageModel {

    Integer packageId;
    Integer packagePrice;
    Integer discountPerc;
    Integer mrp;
    Integer cartStatus;
    Integer noOfTests;
    String packageName;
    String description;
    String testDetails;


    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
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

    public Integer getNoOfTests() {
        return noOfTests;
    }

    public void setNoOfTests(Integer noOfTests) {
        this.noOfTests = noOfTests;
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

    public String getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(String testDetails) {
        this.testDetails = testDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageModel that = (PackageModel) o;
        return Objects.equals(packageId, that.packageId) &&
                Objects.equals(packagePrice, that.packagePrice) &&
                Objects.equals(discountPerc, that.discountPerc) &&
                Objects.equals(mrp, that.mrp) &&
                Objects.equals(cartStatus, that.cartStatus) &&
                Objects.equals(noOfTests, that.noOfTests) &&
                Objects.equals(packageName, that.packageName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(testDetails, that.testDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, packagePrice, discountPerc, mrp, cartStatus, noOfTests, packageName, description, testDetails);
    }

    public static DiffUtil.ItemCallback<PackageModel> itemCallback = new DiffUtil.ItemCallback<PackageModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull PackageModel oldItem, @NonNull PackageModel newItem) {
            return oldItem.getPackageName().equals(newItem.getPackageName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PackageModel oldItem, @NonNull PackageModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public String toString() {
        return "{" +
                "packageId=" + packageId +
                ", packagePrice=" + packagePrice +
                ", discountPerc=" + discountPerc +
                ", mrp=" + mrp +
                ", cartStatus=" + cartStatus +
                ", noOfTests=" + noOfTests +
                ", packageName='" + packageName + '\'' +
                ", description='" + description + '\'' +
                ", testDetails='" + testDetails + '\'' +
                '}';
    }
}
