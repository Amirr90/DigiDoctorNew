package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class SymptomModel {
    int problemId;
    String problemName;
    int isVisible;
    String displayIcon;


    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    private String symptomID;
    private String doctorName;


    public String getSymptomID() {
        return symptomID;
    }

    public void setSymptomID(String symptomID) {
        this.symptomID = symptomID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getProblemId() {
        return problemId;
    }

    public String getProblemName() {
        return problemName;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public String getDisplayIcon() {
        return displayIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymptomModel that = (SymptomModel) o;
        return getProblemId() == that.getProblemId() &&
                getIsVisible() == that.getIsVisible() &&
                getProblemName().equals(that.getProblemName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProblemId(), getProblemName(), getIsVisible(), getDisplayIcon());
    }

    @Override
    public String toString() {
        return "SymptomModel{" +
                "problemId=" + problemId +
                ", problemName='" + problemName + '\'' +
                ", isVisible=" + isVisible +
                ", displayIcon='" + displayIcon + '\'' +
                '}';
    }

    public static DiffUtil.ItemCallback<SymptomModel> itemCallback = new DiffUtil.ItemCallback<SymptomModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SymptomModel oldItem, @NonNull SymptomModel newItem) {
            return oldItem.getProblemName().equals(newItem.getProblemName());

        }

        @Override
        public boolean areContentsTheSame(@NonNull SymptomModel oldItem, @NonNull SymptomModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}

