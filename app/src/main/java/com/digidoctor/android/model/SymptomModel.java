package com.digidoctor.android.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class SymptomModel {
    int problemId;
    String problemName;
    int isVisible;
    String displayIcon;

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
                getProblemName().equals(that.getProblemName()) &&
                getDisplayIcon().equals(that.getDisplayIcon());
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

