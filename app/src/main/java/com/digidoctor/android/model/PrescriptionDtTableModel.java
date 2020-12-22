package com.digidoctor.android.model;

public class PrescriptionDtTableModel {

    private String dosageFormId;
    private String medicineName;
    private String medicineId;
    private String strength;
    private String frequencyId;
    private String doseUnitId;
    private String durationInDays;
    private String frequencyName;
    private String days;


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public void setFrequencyName(String frequencyName) {
        this.frequencyName = frequencyName;
    }

    public String getDosageFormId() {
        return dosageFormId;
    }

    public void setDosageFormId(String dosageFormId) {
        this.dosageFormId = dosageFormId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(String frequencyId) {
        this.frequencyId = frequencyId;
    }

    public String getDoseUnitId() {
        return doseUnitId;
    }

    public void setDoseUnitId(String doseUnitId) {
        this.doseUnitId = doseUnitId;
    }

    public String getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(String durationInDays) {
        this.durationInDays = durationInDays;
    }


    @Override
    public String toString() {
        return "PrescriptionDtTableModel{" +
                "dosageFormId='" + dosageFormId + '\'' +
                ", medicineName='" + medicineName + '\'' +
                ", medicineId='" + medicineId + '\'' +
                ", strength='" + strength + '\'' +
                ", frequencyId='" + frequencyId + '\'' +
                ", doseUnitId='" + doseUnitId + '\'' +
                ", durationInDays='" + durationInDays + '\'' +
                '}';
    }
}
