package com.digidoctor.android.model.patientModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAttributeListDataResp {
    @SerializedName("problemId")
    @Expose
    private Integer problemID;
    @SerializedName("attributeId")
    @Expose
    private Integer attributeID;
    @SerializedName("attributeName")
    @Expose
    private String attributeName;
    @SerializedName("attributeDetails")
    @Expose
    private List<GetAttributeListModel> attribute = null;

    public Integer getProblemID() {
        return problemID;
    }

    public void setProblemID(Integer problemID) {
        this.problemID = problemID;
    }

    public Integer getAttributeID() {
        return attributeID;
    }

    public void setAttributeID(Integer attributeID) {
        this.attributeID = attributeID;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<GetAttributeListModel> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<GetAttributeListModel> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return attributeName;
    }
}
