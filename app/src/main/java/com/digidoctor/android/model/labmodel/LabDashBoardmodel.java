package com.digidoctor.android.model.labmodel;

import java.util.List;

public class LabDashBoardmodel {


    List<SliderImage> sliderImage;
    List<PackageDetail> packageDetails;
    List<BannerText> bannerTextList;
    List<PathalogyDetail> pathalogyDetailList;


    public List<SliderImage> getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(List<SliderImage> sliderImage) {
        this.sliderImage = sliderImage;
    }

    public List<PackageDetail> getPackageDetailList() {
        return packageDetails;
    }

    public void setPackageDetailList(List<PackageDetail> packageDetailList) {
        packageDetails = packageDetailList;
    }

    public List<BannerText> getBannerTextList() {
        return bannerTextList;
    }

    public void setBannerTextList(List<BannerText> bannerTextList) {
        this.bannerTextList = bannerTextList;
    }

    public List<PathalogyDetail> getPathalogyDetailList() {
        return pathalogyDetailList;
    }

    public void setPathalogyDetailList(List<PathalogyDetail> pathalogyDetailList) {
        this.pathalogyDetailList = pathalogyDetailList;
    }


}
