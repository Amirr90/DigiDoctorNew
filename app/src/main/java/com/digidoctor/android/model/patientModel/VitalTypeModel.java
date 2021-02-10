package com.digidoctor.android.model.patientModel;

public class VitalTypeModel {
    String title;
    private String description;
    int image;

    public VitalTypeModel(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
