package com.digidoctor.android.model.pharmacyModel;

public class Fillter {
    @Override
    public String toString() {
        return "Fillter{" +
                "categoryId='" + categoryId + '\'' +
                '}';
    }

    String categoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
