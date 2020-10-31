package com.digidoctor.android.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.digidoctor.android.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class HealthProductDetailsModel {

    int id;
    String categoryName;
    String imagePath;

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthProductDetailsModel that = (HealthProductDetailsModel) o;
        return getId() == that.getId() &&
                getCategoryName().equals(that.getCategoryName()) &&
                getImagePath().equals(that.getImagePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCategoryName(), getImagePath());
    }


    public static DiffUtil.ItemCallback<HealthProductDetailsModel> itemCallback = new DiffUtil.ItemCallback<HealthProductDetailsModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull HealthProductDetailsModel oldItem, @NonNull HealthProductDetailsModel newItem) {
            return oldItem.getCategoryName().equals(newItem.getCategoryName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull HealthProductDetailsModel oldItem, @NonNull HealthProductDetailsModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:loadHealthProductImage")
    public static void loadImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            Picasso.get().load(imagePath).placeholder(R.drawable.defualt_clinics_image).into(imageView);
        }
    }
}
