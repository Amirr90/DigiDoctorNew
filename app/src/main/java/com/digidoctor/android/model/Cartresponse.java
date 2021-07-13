package com.digidoctor.android.model;

import com.digidoctor.android.model.pharmacyModel.CartCount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cartresponse {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseValue")
    @Expose
    private List<LabCartCount> responseValue = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<LabCartCount> getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(List<LabCartCount> responseValue) {
        this.responseValue = responseValue;
    }

    private class LabCartCount {

        @SerializedName("cartCount")
        @Expose
        private Integer cartCount;

        @Override
        public String toString() {
            return "LabCartCount{" +
                    "cartCount=" + cartCount +
                    '}';
        }

        public Integer getCartCount() {
            return cartCount;
        }

        public void setCartCount(Integer cartCount) {
            this.cartCount = cartCount;
        }
    }
}
