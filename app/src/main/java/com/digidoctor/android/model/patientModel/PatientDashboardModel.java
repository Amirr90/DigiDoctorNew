package com.digidoctor.android.model.patientModel;

import java.util.List;

public class PatientDashboardModel {
    List<DashboardModel1> dashboardModel1ListAdapter;
    List<TopImage> topImage;
    List<HealthProductDetailsModel> healthProductDetails;
    List<TopClinicsModel> topClinics;
    List<BannerModel> bannerDetails;

    public List<BannerModel> getBannerDetails() {
        return bannerDetails;
    }

    public List<DashboardModel1> getDashboardModel1ListAdapter() {
        return dashboardModel1ListAdapter;
    }

    public List<TopImage> getTopImage() {
        return topImage;
    }

    public List<HealthProductDetailsModel> getHealthProductDetails() {
        return healthProductDetails;
    }

    public List<TopClinicsModel> getTopClinics() {
        return topClinics;
    }
}
