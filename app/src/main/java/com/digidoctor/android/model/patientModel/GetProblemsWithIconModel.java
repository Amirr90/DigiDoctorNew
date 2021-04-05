package com.digidoctor.android.model.patientModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProblemsWithIconModel {
    private boolean isSelected = false;

    @SerializedName("problemId")
    @Expose
    private Integer problemId;
    @SerializedName("problemName")
    @Expose
    private String problemName;
    @SerializedName("isVisible")
    @Expose
    private Integer isVisible;
    @SerializedName("displayIcon")
    @Expose
    private String displayIcon;

    public Integer getProblemId() {
        return problemId;
    }

    public String getProblemName() {
        return problemName;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
