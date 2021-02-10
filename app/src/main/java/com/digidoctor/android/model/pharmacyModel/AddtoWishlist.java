package com.digidoctor.android.model.pharmacyModel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class AddtoWishlist extends BaseObservable {

    String memberId;


    String uniqueNo;
    String productInfoCode;
    String isWhislist;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getProductInfoCode() {
        return productInfoCode;
    }

    public void setProductInfoCode(String productInfoCode) {
        this.productInfoCode = productInfoCode;
    }

    @Bindable
    public String getIsWhislist() {
        return isWhislist;
    }

    @Bindable
    public void setIsWhislist(String isWhislist) {
        this.isWhislist = isWhislist;
    }

    @Override
    public String toString() {
        return "AddtoWishlist{" +
                "memberId='" + memberId + '\'' +
                ", uniqueNo='" + uniqueNo + '\'' +
                ", productInfoCode='" + productInfoCode + '\'' +
                ", isWhislist='" + isWhislist + '\'' +
                '}';
    }


}
