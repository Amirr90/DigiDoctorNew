package com.digidoctor.android.model;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.Objects;


public class SpecialityModel {

    private static final String TAG = "SpecialityModel";

    int id;
    int noOfDoctors;
    int memberId;
    String specialityName;
    String departmentName;
    String imagePath;
    String description;
    private String problemName;
    private Integer specialityId;
    private String doctorName;
    private Integer doctorId;
    private String isEmergency;
    private Integer fees;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Integer getFees() {
        return fees;
    }

    public String getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(String isEmergency) {
        this.isEmergency = isEmergency;
    }

    public void setNoOfDoctors(int noOfDoctors) {
        this.noOfDoctors = noOfDoctors;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(Integer specialityId) {
        this.specialityId = specialityId;
    }

    public String getDoctorName() {
        if (null == doctorName)
            return "";
        else
            return doctorName;
    }

    public String getNoOfDoctors() {
        return String.valueOf(noOfDoctors);
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProblemName() {
        if (null == problemName)
            return "";
        else
            return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public int getId() {
        return id;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialityModel that = (SpecialityModel) o;
        return getId() == that.getId() &&
                getSpecialityName().equals(that.getSpecialityName()) /*&&
                getImagePath().equals(that.getImagePath()) &&
                getDescription().equals(that.getDescription())*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSpecialityName(), getImagePath(), getDescription());
    }

    public static DiffUtil.ItemCallback<SpecialityModel> itemCallback = new DiffUtil.ItemCallback<SpecialityModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SpecialityModel oldItem, @NonNull SpecialityModel newItem) {
            return oldItem.getSpecialityName().equals(newItem.getSpecialityName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SpecialityModel oldItem, @NonNull SpecialityModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:loadSpecialityImage")
    public static void loadSpeImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.defualt_clinics_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadSpeImage: ");
            }
        }
        Log.d(TAG, "loadSpeImage: err");
    }
}
