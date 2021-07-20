package com.digidoctor.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class LabModel implements Parcelable {
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

    protected LabModel(Parcel in) {
        if (in.readByte() == 0) {
            pathalogyId = null;
        } else {
            pathalogyId = in.readInt();
        }
        pathologyName = in.readString();
        address = in.readString();
        contactNo = in.readString();
        workingHrsFrom = in.readString();
        workingHrsTo = in.readString();
        pincode = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        logo = in.readString();
        stateName = in.readString();
        cityName = in.readString();
    }

    public static final Creator<LabModel> CREATOR = new Creator<LabModel>() {
        @Override
        public LabModel createFromParcel(Parcel in) {
            return new LabModel(in);
        }

        @Override
        public LabModel[] newArray(int size) {
            return new LabModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (pathalogyId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pathalogyId);
        }
        dest.writeString(pathologyName);
        dest.writeString(address);
        dest.writeString(contactNo);
        dest.writeString(workingHrsFrom);
        dest.writeString(workingHrsTo);
        dest.writeString(pincode);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(logo);
        dest.writeString(stateName);
        dest.writeString(cityName);
    }
}
