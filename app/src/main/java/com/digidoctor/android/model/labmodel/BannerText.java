package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannerText {


    @SerializedName("bannerText")
    @Expose
    private String bannerText;

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    @Override
    public String toString() {
        return "BannerText{" +
                "bannerText='" + bannerText + '\'' +
                '}';
    }
}
