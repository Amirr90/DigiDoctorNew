package com.digidoctor.android.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.digidoctor.android.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DoctorModel {

    int id;
    int drFee;
    int noofPatients;
    int isEraUser;
    int rating;
    int review;
    String drName;
    String speciality;
    String yearOfExperience;
    String hospitalName;
    String degree;
    String address;
    String profilePhotoPath;
    String departmentName;
    String description;
    String workingHours;

    public String getWorkingHours() {
        return workingHours;
    }

    public String getDescription() {
        return description;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getRating() {
        return rating;
    }

    public int getReview() {
        return review;
    }

    public int getIsEraUser() {
        return isEraUser;
    }

    public int getId() {
        return id;
    }

    public int getDrFee() {
        return drFee;
    }

    public int getNoofPatients() {
        return noofPatients;
    }

    public String getDrName() {
        return drName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getDegree() {
        return degree;
    }

    public String getAddress() {
        return address;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorModel that = (DoctorModel) o;
        return getId() == that.getId() &&
                getDrFee() == that.getDrFee() &&
                getNoofPatients() == that.getNoofPatients() &&
                getDrName().equals(that.getDrName()) &&
                // getSpeciality().equals(that.getSpeciality()) &&
                getYearOfExperience().equals(that.getYearOfExperience()) &&
                getHospitalName().equals(that.getHospitalName()) &&
                getDegree().equals(that.getDegree()) &&
                getAddress().equals(that.getAddress()) &&
                getProfilePhotoPath().equals(that.getProfilePhotoPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDrFee(), getNoofPatients(), getDrName(), getSpeciality(), getYearOfExperience(), getHospitalName(), getDegree(), getAddress(), getProfilePhotoPath());
    }

    public static DiffUtil.ItemCallback<DoctorModel> itemCallback = new DiffUtil.ItemCallback<DoctorModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull DoctorModel oldItem, @NonNull DoctorModel newItem) {
            return oldItem.getDrName().equals(newItem.getDrName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DoctorModel oldItem, @NonNull DoctorModel newItem) {
            return oldItem.equals(newItem);
        }
    };


    @BindingAdapter("android:loadDoctorImage")
    public static void loadDocImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Picasso.get()
                        .load(imagePath)
                        .placeholder(R.drawable.defualt_clinics_image)
                        .centerCrop()
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", drFee=" + drFee +
                ", noofPatients=" + noofPatients +
                ", isEraUser=" + isEraUser +
                ", rating=" + rating +
                ", review=" + review +
                ", drName='" + drName + '\'' +
                ", speciality='" + speciality + '\'' +
                ", yearOfExperience='" + yearOfExperience + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", degree='" + degree + '\'' +
                ", address='" + address + '\'' +
                ", profilePhotoPath='" + profilePhotoPath + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", description='" + description + '\'' +
                ", workingHours='" + workingHours + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDrFee(int drFee) {
        this.drFee = drFee;
    }

    public void setNoofPatients(int noofPatients) {
        this.noofPatients = noofPatients;
    }

    public void setIsEraUser(int isEraUser) {
        this.isEraUser = isEraUser;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }
}
