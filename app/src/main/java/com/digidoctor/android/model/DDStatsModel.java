package com.digidoctor.android.model;

public class DDStatsModel {
    String description;
    String image;
    Integer doctorsCount;
    Integer hospitalCount;
    Integer userCount;

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getDoctorsCount() {
        return null == doctorsCount ? 0 : doctorsCount;
    }

    public Integer getHospitalCount() {
        return null == hospitalCount ? 0 : hospitalCount;
    }

    public Integer getUserCount() {
        return null == userCount ? 0 : userCount;
    }
}
