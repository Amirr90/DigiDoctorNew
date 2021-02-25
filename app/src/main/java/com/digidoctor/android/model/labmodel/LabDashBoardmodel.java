package com.digidoctor.android.model.labmodel;

import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.model.PackageModel;

import java.util.List;

public class LabDashBoardmodel {
    List<SliderImage> sliderImage;
    List<CategoryModel> categoryDetails;
    List<PackageModel> packageDetails;
    List<BannerText> bannerText;
    List<LabModel> pathalogyDetails;

    public List<CategoryModel> getCategoryDetails() {
        return categoryDetails;
    }

    public List<PackageModel> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(List<PackageModel> packageDetails) {
        this.packageDetails = packageDetails;
    }


    public List<SliderImage> getSliderImage() {
        return sliderImage;
    }

    public void setSliderImage(List<SliderImage> sliderImage) {
        this.sliderImage = sliderImage;
    }

    public List<PackageModel> getPackageDetailList() {
        return packageDetails;
    }

    public void setPackageDetailList(List<PackageModel> packageDetailList) {
        packageDetails = packageDetailList;
    }

    public List<BannerText> getBannerText() {
        return bannerText;
    }

    public void setBannerText(List<BannerText> bannerText) {
        this.bannerText = bannerText;
    }

    public List<LabModel> getPathalogyDetails() {
        return pathalogyDetails;
    }

    public void setPathalogyDetails(List<LabModel> pathalogyDetails) {
        this.pathalogyDetails = pathalogyDetails;
    }


    public class SliderImage {

        String sliderImage;

        @Override
        public String toString() {
            return "SliderImage{" +
                    "sliderImage='" + sliderImage + '\'' +
                    '}';
        }

        public String getSliderImage() {
            return sliderImage;
        }

        public void setSliderImage(String sliderImage) {
            this.sliderImage = sliderImage;
        }
    }


}
