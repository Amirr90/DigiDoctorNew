package com.digidoctor.android.model.labmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryDetail {

    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryImage")
    @Expose
    private String categoryImage;


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    @Override
    public String toString() {
        return "CategoryDetail{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                '}';
    }

}
