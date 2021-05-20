package com.digidoctor.android.model;

import com.digidoctor.android.model.patientModel.BlogModel;

import java.util.List;

public class PatientDashboardModel {
    List<DashboardModel1> dashboardModel1ListAdapter;
    List<TopImage> topImage;
    List<HealthProductDetailsModel> healthProductDetails;
    List<TopClinicsModel> topClinics;
    List<BannerModel> bannerDetails;
    List<BlogModel> blogDetails;
    List<DoctorModel> doctorDetails;
    List<DDStatsModel> countDetails;

    public List<DDStatsModel> getCountDetails() {
        return countDetails;
    }

    public List<DoctorModel> getDoctorDetails() {
        return doctorDetails;
    }

    public List<BlogModel> getBlogDetails() {
        return blogDetails;
    }

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
