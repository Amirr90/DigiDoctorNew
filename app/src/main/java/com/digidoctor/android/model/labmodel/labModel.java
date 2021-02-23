package com.digidoctor.android.model.labmodel;

public class labModel {

    String memberId;
    String uniqueNo;
    String latitude;
    String longitude;


    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public labModel(String memberId, String latitude, String longitude) {
        this.memberId = memberId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public labModel() {
    }

    @Override
    public String toString() {
        return "labModel{" +
                "memberId='" + memberId + '\'' +
                ", uniqueNo='" + uniqueNo + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
