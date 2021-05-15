package com.digidoctor.android.model.patientModel;

public class MedicineReminderModel {

    private Integer medicineId;
    private String medicineName;
    private String dosageFormName;
    private String strength;
    private String unitName;
    private String frequencyName;
    private Integer durationInDays;
    private Integer isReminder;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Integer getIsReminder() {
        return null == isReminder ? 0 : isReminder;

    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosageFormName() {
        return dosageFormName;
    }

    public String getStrength() {
        return strength;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getFrequencyName() {
        return frequencyName;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }
}
