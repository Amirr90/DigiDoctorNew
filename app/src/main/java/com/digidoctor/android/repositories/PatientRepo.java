package com.digidoctor.android.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digidoctor.android.R;
import com.digidoctor.android.model.DashboardModel1;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.DoctorModelRes;
import com.digidoctor.android.model.PatientDashboardModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.utility.ApiCallbackInterface;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.getDocBySpecialityById;
import static com.digidoctor.android.utility.ApiUtils.getPatientDasboard;
import static com.digidoctor.android.utility.ApiUtils.getRecommendedDoc;
import static com.digidoctor.android.utility.ApiUtils.getSymptomWithIconsData;
import static com.digidoctor.android.utility.ApiUtils.specialityData;

public class PatientRepo {

    public MutableLiveData<PatientDashboardModel> patientDashboardModelMutableLiveData;
    public MutableLiveData<List<SpecialityModel>> specialityModelMutableLiveData;
    public MutableLiveData<List<SymptomModel>> symptomsModelMutableLiveData;
    public MutableLiveData<List<DoctorModel>> doctorModelMutableLiveData;
    public MutableLiveData<List<DoctorModelRes>> recommendedDoctorModelMutableLiveData;


    public LiveData<List<DoctorModelRes>> gerRecommendedDoctorsData(String id, String docName) {
        if (recommendedDoctorModelMutableLiveData == null) {
            recommendedDoctorModelMutableLiveData = new MutableLiveData<>();
        }
        loadRecommendedDocData(id, docName);
        return recommendedDoctorModelMutableLiveData;

    }

    private void loadRecommendedDocData(String id, String docName) {
        getRecommendedDoc(id, docName, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<DoctorModelRes> doctorModelRes = (List<DoctorModelRes>) o;
                if (null != doctorModelRes) {
                    recommendedDoctorModelMutableLiveData.setValue(doctorModelRes);
                    if (doctorModelRes.get(0).getPopularDoctor().isEmpty()
                            && doctorModelRes.get(0).getRecomendedDoctor().isEmpty())
                        Toast.makeText(PatientDashboard.getInstance(), R.string.no_doc_found, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(String s) {
                Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<DoctorModel>> getDocBySpeciality(String id, String docName) {
        if (doctorModelMutableLiveData == null)
            doctorModelMutableLiveData = new MutableLiveData<>();
        loadDocBySpecialityData(id, docName);
        return doctorModelMutableLiveData;

    }

    private void loadDocBySpecialityData(String id, String docName) {
        getDocBySpecialityById(id, docName, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                if (null != (List<DoctorModel>) o)
                    doctorModelMutableLiveData.setValue((List<DoctorModel>) o);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public LiveData<List<SymptomModel>> getSymptomsData() {
        if (symptomsModelMutableLiveData == null) {
            symptomsModelMutableLiveData = new MutableLiveData<>();
        }
        loadSymptomsData();
        return symptomsModelMutableLiveData;
    }

    private void loadSymptomsData() {


        getSymptomWithIconsData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                try {
                    List<SymptomModel> specialityModel = (List<SymptomModel>) o;
                    symptomsModelMutableLiveData.setValue(specialityModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public LiveData<List<SpecialityModel>> getSpecialityData() {
        if (specialityModelMutableLiveData == null)
            specialityModelMutableLiveData = new MutableLiveData<>();
        loadSpecialityData();
        return specialityModelMutableLiveData;
    }

    private void loadSpecialityData() {

        specialityData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                try {
                    List<SpecialityModel> specialityModel = (List<SpecialityModel>) o;
                    specialityModelMutableLiveData.setValue(specialityModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });


    }

    public LiveData<PatientDashboardModel> getDashboardData(String lat, String lng) {
        if (patientDashboardModelMutableLiveData == null) {
            patientDashboardModelMutableLiveData = new MutableLiveData<>();

        }
        loadPatientDashboardData(lat, lng);
        return patientDashboardModelMutableLiveData;
    }

    private void loadPatientDashboardData(String lat, String lng) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(PatientDashboard.getInstance());
        List<DashboardModel1> dashboardModel1s = new ArrayList<>();
        dashboardModel1s.add(new DashboardModel1("Specialities"));
        dashboardModel1s.add(new DashboardModel1("Symptoms"));
        dashboardModel1s.add(new DashboardModel1("Tests"));
        dashboardModel1s.add(new DashboardModel1("Pharmacy"));

        String mobile = "9044865611";
        getPatientDasboard(lat, lng, mobile, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                PatientDashboardModel dashboardModel = (PatientDashboardModel) o.get(0);
                if (null != dashboardModel) {
                    patientDashboardModelMutableLiveData.setValue(dashboardModel);
                }
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                if (PatientDashboard.getInstance() != null)
                    Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                if (PatientDashboard.getInstance() != null)
                    Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

