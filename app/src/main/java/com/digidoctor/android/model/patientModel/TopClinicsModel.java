package com.digidoctor.android.model.patientModel;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.digidoctor.android.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class TopClinicsModel {
    String name;
    String address;
    String ProfilePhotoPath;
    String stateName;
    String cityName;

    public String getStateName() {
        return stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getProfilePhotoPath() {
        return ProfilePhotoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopClinicsModel that = (TopClinicsModel) o;
        return getName().equals(that.getName()) &&
                getAddress().equals(that.getAddress()) /*&&
                getProfilePhotoPath().equals(that.getProfilePhotoPath())*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getProfilePhotoPath());
    }

    public static DiffUtil.ItemCallback<TopClinicsModel> itemCallback = new DiffUtil.ItemCallback<TopClinicsModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull TopClinicsModel oldItem, @NonNull TopClinicsModel newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TopClinicsModel oldItem, @NonNull TopClinicsModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:loadClinicImage")
    public static void loadImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Picasso.get()
                        .load(imagePath)
                        .placeholder(R.drawable.defualt_clinics_image)
                        .centerCrop()
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "TopClinicsModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ProfilePhotoPath='" + ProfilePhotoPath + '\'' +
                ", stateName='" + stateName + '\'' +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
