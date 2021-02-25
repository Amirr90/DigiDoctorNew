package com.digidoctor.android.model.labmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class CategoryModel {
    Integer categoryId;
    String categoryName;
    String categoryImage;

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryModel that = (CategoryModel) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(categoryImage, that.categoryImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, categoryImage);
    }

    public static DiffUtil.ItemCallback<CategoryModel> itemCallback = new DiffUtil.ItemCallback<CategoryModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategoryModel oldItem, @NonNull CategoryModel newItem) {
            return oldItem.getCategoryId().equals(newItem.getCategoryId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategoryModel oldItem, @NonNull CategoryModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
