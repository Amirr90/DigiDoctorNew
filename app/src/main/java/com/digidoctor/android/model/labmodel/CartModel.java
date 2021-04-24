package com.digidoctor.android.model.labmodel;

import java.util.List;

public class CartModel {
    String testId;
    String packageId;
    String uniqueNo;
    String cartId;
    String memberId;
    String name;
    String pathalogyAddress;
    String pathologyName;
    String price;
    String pathalogyId;
    String pathImage;

    public String getPathalogyId() {
        return pathalogyId;
    }

    public String getPathImage() {
        return pathImage;
    }

    List<Test> packageTestList;
    List<CartAmountModel> cartAmount;


    public String getPathologyName() {
        return pathologyName;
    }

    public String getPathalogyAddress() {
        return pathalogyAddress;
    }

    public String getPrice() {
        return price;
    }

    public List<CartAmountModel> getCartAmount() {
        return cartAmount;
    }

    public List<Test> getPackageTestList() {
        return packageTestList;
    }

    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = String.valueOf(memberId);
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public static class Test {
        String testName;
        String id;

        public String getId() {
            return id;
        }

        public String getTestName() {
            return testName;
        }
    }

    public static class CartAmountModel {
        Integer totalAmount;

        public Integer getTotalAmount() {
            return totalAmount;
        }
    }
}
