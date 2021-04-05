package com.digidoctor.android.model.patientModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAttributeListModel {
    private boolean isSelected = false;

    @SerializedName("attributeValueId")
    @Expose
    private Integer attributeValueId;
    @SerializedName("attributeId")
    @Expose
    private Integer attributeID;
    @SerializedName("attributeValue")
    @Expose
    private String attributeValue;
    @SerializedName("problemId")
    @Expose
    private String problemId;

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public Integer getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(Integer attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public Integer getAttributeId() {
        return attributeID;
    }

    public void setAttributeID(Integer attributeID) {
        this.attributeID = attributeID;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public String toString() {
        return attributeValue;
    }
}
