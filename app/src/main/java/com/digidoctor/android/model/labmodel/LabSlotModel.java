package com.digidoctor.android.model.labmodel;

public class LabSlotModel {

    String id;
    String slot;
    String slotMasterId;


    String pincode;
    String date;
    String pathalogyId;

    public LabSlotModel(String pincode, String date, String pathalogyId) {
        this.pincode = pincode;
        this.date = date;
        this.pathalogyId = pathalogyId;
    }

    public LabSlotModel() {
    }

    public String getPincode() {
        return pincode;
    }

    public String getDate() {
        return date;
    }

    public String getPathalogyId() {
        return pathalogyId;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPathalogyId(String pathalogyId) {
        this.pathalogyId = pathalogyId;
    }

    public String getId() {
        return id;
    }

    public String getSlot() {
        return slot;
    }

    public String getSlotMasterId() {
        return slotMasterId;
    }
}
