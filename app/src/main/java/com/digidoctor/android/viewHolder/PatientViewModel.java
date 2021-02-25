package com.digidoctor.android.viewHolder;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.digidoctor.android.model.AppointmentDetailsRes;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.DoctorModelRes;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.InvestigationModel;
import com.digidoctor.android.model.PatientDashboardModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;
import com.digidoctor.android.repositories.PatientRepo;

import java.util.HashMap;
import java.util.List;


public class PatientViewModel extends ViewModel {

    PatientRepo repo = new PatientRepo();


    public LiveData<List<ChatModel>> getChatData(AppointmentModel user) {
        return repo.getChatData(user);
    }

    public LiveData<List<AppointmentModel>> getAppointmentList(User user) {
        return repo.getAppointmentList(user);
    }

    public LiveData<List<AppointmentDetailsRes.Appointments>> getAppointmentDetails(User user) {
        return repo.getAppointmentDetails(user);
    }

    public LiveData<List<InvestigationModel>> getInvestigationData(User user) {
        return repo.getInvestigationData(user);
    }


    public LiveData<List<VitalResponse.VitalDateVise>> getVitals(VitalModel vitalModel, Activity activity) {
        return repo.getVitals(vitalModel, activity);
    }

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


    public LiveData<List<DoctorModel>> getDocBySpeciality(SpecialityModel specialityModel) {
        return repo.getDocBySpeciality(specialityModel);
    }

    public LiveData<List<DoctorModelRes>> getRecommendedDoctorsData(HashMap<String, String> map) {
        return repo.gerRecommendedDoctorsData(map);
    }


    //Lab Data
    public LiveData<LabDashBoardmodel> getLabDashboardModel(String lat, String lng) {
        return repo.getLabDashboardModel(lat, lng);
    }
}
