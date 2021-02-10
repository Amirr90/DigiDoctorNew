package com.digidoctor.android.viewHolder;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.digidoctor.android.model.patientModel.DoctorModel;
import com.digidoctor.android.model.patientModel.DoctorModelRes;
import com.digidoctor.android.model.patientModel.GetPatientMedicationMainModel;
import com.digidoctor.android.model.patientModel.PatientDashboardModel;
import com.digidoctor.android.model.patientModel.SpecialityModel;
import com.digidoctor.android.model.patientModel.SymptomModel;
import com.digidoctor.android.model.patientModel.User;
import com.digidoctor.android.repositories.PatientRepo;

import java.util.HashMap;
import java.util.List;


public class PatientViewModel extends ViewModel {

    PatientRepo repo = new PatientRepo();

    public LiveData<List<User>> getMemberList(Activity activity) {
        return repo.getMemberList(activity);
    }

    public LiveData<List<GetPatientMedicationMainModel>> getPrescriptionData(Activity activity) {
        return repo.getPrescriptionData(activity);
    }

    public LiveData<PatientDashboardModel> getDashboardData(String lat, String lng) {
        return repo.getDashboardData(lat, lng);
    }

    public LiveData<List<SpecialityModel>> getSpecialityData(String specialityName) {
        return repo.getSpecialityData(specialityName);
    }

    public LiveData<List<SymptomModel>> getSymptomsData(String symptomName) {
        return repo.getSymptomsData(symptomName);
    }


    public LiveData<List<DoctorModel>> getDocBySpeciality(String id, String docName) {
        return repo.getDocBySpeciality(id, docName);
    }

    public LiveData<List<DoctorModelRes>> getRecommendedDoctorsData(HashMap<String, String> map) {
        return repo.gerRecommendedDoctorsData(map);
    }

}
