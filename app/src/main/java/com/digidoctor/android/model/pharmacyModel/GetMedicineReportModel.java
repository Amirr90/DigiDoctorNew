package com.digidoctor.android.model.pharmacyModel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMedicineReportModel {


    @SerializedName("headingId")
    @Expose
    private Integer headingId;

    public Integer getHeadingId() {
        return headingId;
    }

    public void setHeadingId(Integer headingId) {
        this.headingId = headingId;
    }

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("body")
    @Expose
    private Object body = null;
    @SerializedName("reference")
    @Expose
    private String reference;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @NonNull
    @Override
    public String toString() {
        return heading;
    }
}
