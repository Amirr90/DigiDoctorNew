package com.digidoctor.android.model.patientModel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllProblemModel {
    private boolean isSelected = false;

    @SerializedName("problemId")
    @Expose
    private Integer id;
    @SerializedName("problemName")
    @Expose
    private String problemName;
    @SerializedName("isVisible")
    @Expose
    private Integer isVisible;

    public Integer getIsVisible() {
        return isVisible;
    }

    public Integer getId() {
        return id;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @NonNull
    @Override
    public String toString()
    {
        return problemName;
    }
}
