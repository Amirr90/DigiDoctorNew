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
    public List<BannerText> getBannerText() {
        return bannerText;
    }
    public List<LabModel> getPathalogyDetails() {
        return pathalogyDetails;
    }




    public static class SliderImage {

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

    }


}
