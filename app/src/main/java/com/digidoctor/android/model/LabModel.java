package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class LabModel {
    Integer pathalogyId;
    String pathologyName;
    String address;
    String contactNo;
    String workingHrsFrom;
    String workingHrsTo;
    String pincode;
    String latitude;
    String longitude;
    String logo;
    String stateName;
    String cityName;

    public Integer getPathalogyId() {
        return pathalogyId;
    }

    public String getPathologyName() {
        return pathologyName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getWorkingHrsFrom() {
        return workingHrsFrom;
    }

    public String getWorkingHrsTo() {
        return workingHrsTo;
    }

    public String getPincode() {
        return pincode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLogo() {
        return logo;
    }

    public String getStateName() {
        return stateName;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabModel labModel = (LabModel) o;
        return Objects.equals(pathalogyId, labModel.pathalogyId) &&
                Objects.equals(pathologyName, labModel.pathologyName) &&
                Objects.equals(address, labModel.address) &&
                Objects.equals(contactNo, labModel.contactNo) &&
                Objects.equals(workingHrsFrom, labModel.workingHrsFrom) &&
                Objects.equals(workingHrsTo, labModel.workingHrsTo) &&
                Objects.equals(pincode, labModel.pincode) &&
                Objects.equals(latitude, labModel.latitude) &&
                Objects.equals(longitude, labModel.longitude) &&
                Objects.equals(logo, labModel.logo) &&
                Objects.equals(stateName, labModel.stateName) &&
                Objects.equals(cityName, labModel.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathalogyId, pathologyName, address, contactNo, workingHrsFrom, workingHrsTo, pincode, latitude, longitude, logo, stateName, cityName);
    }


    public static DiffUtil.ItemCallback<LabModel> itemCallback = new DiffUtil.ItemCallback<LabModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull LabModel oldItem, @NonNull LabModel newItem) {
            return oldItem.getPathalogyId().equals(newItem.getPathalogyId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull LabModel oldItem, @NonNull LabModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
