package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;
import java.util.Objects;

public class MedicineModel {

    List<MedicineDetailModel> medicineList;
    List<MedicineFrequencyModel> frequencyList;

    public List<MedicineDetailModel> getMedicineList() {
        return medicineList;
    }

    public List<MedicineFrequencyModel> getFrequencyList() {
        return frequencyList;
    }

    public static class MedicineDetailModel {

        private String id;
        private String medicineName;
        private String strength;
        private String doseUnitId;
        private String unitName;
        private String frequencyId;
        private String frequencyName;
        private String dosageId;
        private String formName;
        private String days;

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setMedicineName(String medicineName) {
            this.medicineName = medicineName;
        }

        public void setStrength(String strength) {
            this.strength = strength;
        }

        public void setDoseUnitId(String doseUnitId) {
            this.doseUnitId = doseUnitId;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }

        public void setFrequencyId(String frequencyId) {
            this.frequencyId = frequencyId;
        }

        public void setFrequencyName(String frequencyName) {
            this.frequencyName = frequencyName;
        }

        public void setDosageId(String dosageId) {
            this.dosageId = dosageId;
        }

        public void setFormName(String formName) {
            this.formName = formName;
        }

        public String getId() {
            return id;
        }

        public String getMedicineName() {
            return medicineName;
        }

        public String getStrength() {
            return strength;
        }

        public String getDoseUnitId() {
            return doseUnitId;
        }

        public String getUnitName() {
            return unitName;
        }

        public String getFrequencyId() {
            return frequencyId;
        }

        public String getFrequencyName() {
            return frequencyName;
        }

        public String getDosageId() {
            return dosageId;
        }

        public String getFormName() {
            return formName;
        }


        @Override
        public String toString() {
            return "MedicineDetailModel{" +
                    "id='" + id + '\'' +
                    ", medicineName='" + medicineName + '\'' +
                    ", strength='" + strength + '\'' +
                    ", doseUnitId='" + doseUnitId + '\'' +
                    ", unitName='" + unitName + '\'' +
                    ", frequencyId='" + frequencyId + '\'' +
                    ", frequencyName='" + frequencyName + '\'' +
                    ", dosageId='" + dosageId + '\'' +
                    ", formName='" + formName + '\'' +
                    ", days='" + days + '\'' +
                    '}';
        }


    }

    public class MedicineFrequencyModel {

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
