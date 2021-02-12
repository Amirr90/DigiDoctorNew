package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PathalogyDetail {


    @SerializedName("pathalogyId")
    @Expose
    private Integer pathalogyId;
    @SerializedName("pathologyName")
    @Expose
    private String pathologyName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("workingHrsFrom")
    @Expose
    private String workingHrsFrom;
    @SerializedName("workingHrsTo")
    @Expose
    private String workingHrsTo;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("cityName")
    @Expose
    private String cityName;


    public Integer getPathalogyId() {
        return pathalogyId;
    }

    public void setPathalogyId(Integer pathalogyId) {
        this.pathalogyId = pathalogyId;
    }

    public String getPathologyName() {
        return pathologyName;
    }

    public void setPathologyName(String pathologyName) {
        this.pathologyName = pathologyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getWorkingHrsFrom() {
        return workingHrsFrom;
    }

    public void setWorkingHrsFrom(String workingHrsFrom) {
        this.workingHrsFrom = workingHrsFrom;
    }

    public String getWorkingHrsTo() {
        return workingHrsTo;
    }

    public void setWorkingHrsTo(String workingHrsTo) {
        this.workingHrsTo = workingHrsTo;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "PathalogyDetail{" +
                "pathalogyId=" + pathalogyId +
                ", pathologyName='" + pathologyName + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", workingHrsFrom='" + workingHrsFrom + '\'' +
                ", workingHrsTo='" + workingHrsTo + '\'' +
                ", pincode='" + pincode + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", logo='" + logo + '\'' +
                ", stateName='" + stateName + '\'' +
                ", cityName='" + cityName + '\'' +
                '}';
    }

}
