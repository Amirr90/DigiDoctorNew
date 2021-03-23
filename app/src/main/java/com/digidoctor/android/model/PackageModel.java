package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class PackageModel {

    String packageId;
    String packagePrice;
    String discountPerc;
    String mrp;
    String cartStatus;
    String noOfTests;
    String packageName;
    String description;
    String testDetails;

    List<GroupDetails> groupDetails;

    public List<GroupDetails> getGroupDetails() {
        return groupDetails;
    }

    public void setGroupDetails(List<GroupDetails> groupDetails) {
        this.groupDetails = groupDetails;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getDiscountPerc() {
        return discountPerc;
    }

    public void setDiscountPerc(String discountPerc) {
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

    public String getNoOfTests() {
        return noOfTests;
    }

    public void setNoOfTests(String noOfTests) {
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
    public String toString() {
        return "{" +
                "packageId='" + packageId + '\'' +
                ", packagePrice='" + packagePrice + '\'' +
                ", discountPerc='" + discountPerc + '\'' +
                ", mrp='" + mrp + '\'' +
                ", cartStatus='" + cartStatus + '\'' +
                ", noOfTests='" + noOfTests + '\'' +
                ", packageName='" + packageName + '\'' +
                ", description='" + description + '\'' +
                ", testDetails='" + testDetails + '\'' +
                '}';
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


    public static class GroupDetails {
        String groupName;
        List<TestDetails> testDetails;

        public String getGroupName() {
            return groupName;
        }

        public List<TestDetails> getTestDetails() {
            return testDetails;
        }
    }

    public static class TestDetails {
        String testName;

        public String getTestName() {
            return testName;
        }
    }

}
