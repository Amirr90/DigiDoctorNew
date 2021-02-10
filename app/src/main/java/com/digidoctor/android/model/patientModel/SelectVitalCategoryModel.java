package com.digidoctor.android.model.patientModel;

public class SelectVitalCategoryModel {
    private String title;
    private int image;
    private boolean isSelected;
    private String color;

    public SelectVitalCategoryModel(String title, int image, boolean isSelected, String color) {
        this.title = title;
        this.image = image;
        this.isSelected = isSelected;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getColor() {
        return color;
    }
}
