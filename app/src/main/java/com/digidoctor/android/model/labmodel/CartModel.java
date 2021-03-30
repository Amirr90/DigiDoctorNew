package com.digidoctor.android.model.labmodel;

public class CartModel {
    String testId;
    String packageId;
    String uniqueNo;
    String cartId;
    String memberId;
    String name;

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
}
