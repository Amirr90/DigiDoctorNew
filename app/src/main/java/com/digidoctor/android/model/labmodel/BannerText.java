package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerText {


    @SerializedName("bannerText")
    @Expose
    private String bannerText;
    @SerializedName("callingNo")
    @Expose
    private String callingNo;

    public String getBannerText() {
        return bannerText;
    }

    public String getCallingNo() {
        return callingNo;
    }
}
