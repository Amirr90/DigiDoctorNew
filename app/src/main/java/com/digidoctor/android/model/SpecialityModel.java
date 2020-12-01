package com.digidoctor.android.model;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.Objects;



public class SpecialityModel {

    private static final String TAG = "SpecialityModel";
    int id;
    String specialityName;
    String imagePath;
    String description;

    public int getId() {
        return id;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialityModel that = (SpecialityModel) o;
        return getId() == that.getId() &&
                getSpecialityName().equals(that.getSpecialityName()) &&
                getImagePath().equals(that.getImagePath()) &&
                getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSpecialityName(), getImagePath(), getDescription());
    }

    public static DiffUtil.ItemCallback<SpecialityModel> itemCallback = new DiffUtil.ItemCallback<SpecialityModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SpecialityModel oldItem, @NonNull SpecialityModel newItem) {
            return oldItem.getSpecialityName().equals(newItem.getSpecialityName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SpecialityModel oldItem, @NonNull SpecialityModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:loadSpecialityImage")
    public static void loadSpeImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.defualt_clinics_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadSpeImage: ");
            }
        }
        Log.d(TAG, "loadSpeImage: err");
    }
}