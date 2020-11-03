package com.digidoctor.android.viewHolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.DoctorModelRes;
import com.digidoctor.android.model.PatientDashboardModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.repositories.PatientRepo;

import java.util.List;


public class PatientViewModel extends ViewModel {

    PatientRepo repo = new PatientRepo();

    public LiveData<PatientDashboardModel> getDashboardData(String lat, String lng) {
        return repo.getDashboardData(lat, lng);
    }

    public LiveData<List<SpecialityModel>> getSpecialityData() {
        return repo.getSpecialityData();
    }

    public LiveData<List<SymptomModel>> getSymptomsData() {
        return repo.getSymptomsData();
    }


    public LiveData<List<DoctorModel>> getDocBySpeciality(String id, String docName) {
        return repo.getDocBySpeciality(id, docName);
    }

    public LiveData<List<DoctorModelRes>> getRecommendedDoctorsData(String id, String docName) {
        return repo.gerRecommendedDoctorsData(id, docName);
    }
}
