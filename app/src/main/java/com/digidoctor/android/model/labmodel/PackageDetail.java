package com.digidoctor.android.model.labmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PackageDetail {
    @SerializedName("packageId")
    @Expose
    private String packageId;
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
    private String mrp;
    @SerializedName("cartStatus")
    @Expose
    private String cartStatus;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
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

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus = cartStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageDetail that = (PackageDetail) o;
        return packageId.equals(that.packageId) &&
                noOfTests == that.noOfTests &&
                packagePrice == that.packagePrice &&
                discountPerc == that.discountPerc &&
                mrp == that.mrp &&
                Objects.equals(packageName, that.packageName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(cartStatus, that.cartStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, packageName, description, noOfTests, packagePrice, discountPerc, mrp, cartStatus);
    }

    @Override
    public String toString() {
        return "{" +
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


    public static DiffUtil.ItemCallback<PackageDetail> itemCallback = new DiffUtil.ItemCallback<PackageDetail>() {
        @Override
        public boolean areItemsTheSame(@NonNull PackageDetail oldItem, @NonNull PackageDetail newItem) {
            return oldItem.getPackageName().equals(newItem.getPackageName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PackageDetail oldItem, @NonNull PackageDetail newItem) {
            return oldItem.equals(newItem);
        }
    };
}
