package com.digidoctor.android.model.pharmacyModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddAdressModel {

    List<AddAdressModel> getAddress;
    String memberId;
    String name;
    String mobileno;
    String houseNo;
    String area;
    String pincode;
    String state;
    String city;
    String locality;
    String isDefault;
    String addressType;
    String isSundayOpen;
    String isSaturdayOpen;
    String addressId;

    public List<AddAdressModel> getGetAddress() {
        return getAddress;
    }

    public void setGetAddress(List<AddAdressModel> getAddress) {
        this.getAddress = getAddress;
    }

    @NotNull
    @Override
    public String toString() {
        return "AddAdressModel{" +
                "getAddress=" + getAddress +
                ", memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", mobileno='" + mobileno + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", area='" + area + '\'' +
                ", pincode='" + pincode + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", locality='" + locality + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", addressType='" + addressType + '\'' +
                ", isSundayOpen='" + isSundayOpen + '\'' +
                ", isSaturdayOpen='" + isSaturdayOpen + '\'' +
                ", addressId='" + addressId + '\'' +
                '}';
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


    public String getIsDefault() {
        return isDefault;
    }


    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getIsSundayOpen() {
        return isSundayOpen;
    }

    public void setIsSundayOpen(String isSundayOpen) {
        this.isSundayOpen = isSundayOpen;
    }

    public String getIsSaturdayOpen() {
        return isSaturdayOpen;
    }

    public void setIsSaturdayOpen(String isSaturdayOpen) {
        this.isSaturdayOpen = isSaturdayOpen;
    }


}
