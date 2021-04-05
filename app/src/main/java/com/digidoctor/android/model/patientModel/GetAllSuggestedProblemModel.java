package com.digidoctor.android.model.patientModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllSuggestedProblemModel {
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

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
