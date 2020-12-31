package com.digidoctor.android.repositories;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.Dashboard;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.DoctorModelRes;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.InvestigationModel;
import com.digidoctor.android.model.MedicineModel;
import com.digidoctor.android.model.PatientDashboardModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.getDocBySpecialityById;
import static com.digidoctor.android.utility.ApiUtils.getPatientDasboard;
import static com.digidoctor.android.utility.ApiUtils.getPatientMedicationDetails;
import static com.digidoctor.android.utility.ApiUtils.getRecommendedDoc;
import static com.digidoctor.android.utility.ApiUtils.getSymptomWithIconsData;
import static com.digidoctor.android.utility.ApiUtils.specialityData;
import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.logout;

public class PatientRepo {

    public MutableLiveData<PatientDashboardModel> patientDashboardModelMutableLiveData;
    public MutableLiveData<List<SpecialityModel>> specialityModelMutableLiveData;
    public MutableLiveData<List<SymptomModel>> symptomsModelMutableLiveData;
    public MutableLiveData<List<DoctorModel>> doctorModelMutableLiveData;
    public MutableLiveData<List<DoctorModelRes>> recommendedDoctorModelMutableLiveData;
    public MutableLiveData<List<GetPatientMedicationMainModel>> prescriptionModelMutableLiveData;
    public MutableLiveData<List<User>> memberModelMutableLiveData;
    public MutableLiveData<List<VitalResponse.VitalDateVise>> vitalsMutableLiveData;
    public MutableLiveData<List<MedicineModel.MedicineDetailModel>> medicineMutableLiveData;
    public MutableLiveData<List<InvestigationModel>> investigationMutableLiveData;
    public MutableLiveData<List<AppointmentModel>> appointmentMutableLiveData;


    public LiveData<List<AppointmentModel>> getAppointmentList(User user) {
        if (appointmentMutableLiveData == null) {
            appointmentMutableLiveData = new MutableLiveData<>();
        }
        loadAppointmentData(user);
        return appointmentMutableLiveData;

    }

    private void loadAppointmentData(User user) {
        ApiUtils.getAppointmentData(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {
                List<AppointmentModel> models = (List<AppointmentModel>) obj;
                appointmentMutableLiveData.setValue(models);
            }

            @Override
            public void onError(String s) {
                Log.d("InvestigationData", "onError: " + s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("InvestigationData", "onFailed: " + throwable.getLocalizedMessage());
            }
        });
    }

    public LiveData<List<InvestigationModel>> getInvestigationData(User user) {
        if (investigationMutableLiveData == null) {
            investigationMutableLiveData = new MutableLiveData<>();
        }
        loadInvestigationData(user);
        return investigationMutableLiveData;

    }

    private void loadInvestigationData(User user) {

        ApiUtils.getInvestigationData(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {
                List<InvestigationModel> models = (List<InvestigationModel>) obj;
                investigationMutableLiveData.setValue(models);
            }

            @Override
            public void onError(String s) {
                Log.d("InvestigationData", "onError: " + s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("InvestigationData", "onFailed: " + throwable.getLocalizedMessage());
            }
        });
    }

    public LiveData<List<VitalResponse.VitalDateVise>> getVitals(VitalModel vitalModel, Activity activity) {
        if (vitalsMutableLiveData == null) {
            vitalsMutableLiveData = new MutableLiveData<>();
        }
        loadVitalData(vitalModel, activity);
        return vitalsMutableLiveData;

    }

    private void loadVitalData(final VitalModel vitalModel, final Activity activity) {

        showRequestDialog(activity);
        ApiUtils.getVitalsList(vitalModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                hideDialog();
                List<VitalResponse.VitalDateVise> vitalResponse = (List<VitalResponse.VitalDateVise>) o;
                vitalsMutableLiveData.setValue(vitalResponse);
            }

            @Override
            public void onError(String s) {
                hideDialog();
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                hideDialog();
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public LiveData<List<User>> getMemberList(Activity activity) {
        if (memberModelMutableLiveData == null) {
            memberModelMutableLiveData = new MutableLiveData<>();
        }
        loadMemberData(activity);
        return memberModelMutableLiveData;

    }

    private void loadMemberData(final Activity activity) {
        ApiUtils.GetMembersRes(activity, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<User> userList = (List<User>) o;
                if (null != userList && !userList.isEmpty()) {
                    memberModelMutableLiveData.setValue(userList);
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public LiveData<List<GetPatientMedicationMainModel>> getPrescriptionData(Activity activity) {
        if (prescriptionModelMutableLiveData == null) {
            prescriptionModelMutableLiveData = new MutableLiveData<>();
        }
        loadPrescriptionData(activity);
        return prescriptionModelMutableLiveData;

    }

    private void loadPrescriptionData(final Activity activity) {

        getPatientMedicationDetails(activity, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<GetPatientMedicationMainModel> patientMedicationMainModels = (List<GetPatientMedicationMainModel>) o;
                if (patientMedicationMainModels != null) {
                    prescriptionModelMutableLiveData.setValue(patientMedicationMainModels);
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public LiveData<List<DoctorModelRes>> gerRecommendedDoctorsData(HashMap<String, String> map) {
        if (recommendedDoctorModelMutableLiveData == null) {
            recommendedDoctorModelMutableLiveData = new MutableLiveData<>();
        }
        loadRecommendedDocData(map);
        return recommendedDoctorModelMutableLiveData;

    }

    private void loadRecommendedDocData(HashMap<String, String> map) {
        getRecommendedDoc(map, new ApiCallbackInterface() {
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

                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public LiveData<List<SymptomModel>> getSymptomsData(String symptomName) {
        if (symptomsModelMutableLiveData == null) {
            symptomsModelMutableLiveData = new MutableLiveData<>();
        }
        loadSymptomsData(symptomName);
        return symptomsModelMutableLiveData;
    }

    private void loadSymptomsData(String symptomName) {


        getSymptomWithIconsData(symptomName, new ApiCallbackInterface() {
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
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public LiveData<List<SpecialityModel>> getSpecialityData(String specialityName) {
        if (specialityModelMutableLiveData == null)
            specialityModelMutableLiveData = new MutableLiveData<>();

        loadSpecialityData(specialityName);
        return specialityModelMutableLiveData;
    }

    private void loadSpecialityData(String specialityName) {

        specialityData(specialityName, new ApiCallbackInterface() {
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

                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        Dashboard dashboard = new Dashboard();
        dashboard.setLat(lat);
        dashboard.setLat(lng);

        loadPatientDashboardData(dashboard);
        return patientDashboardModelMutableLiveData;
    }

    private void loadPatientDashboardData(Dashboard dashboard) {
        if (PatientDashboard.getInstance() != null)
            showRequestDialog(PatientDashboard.getInstance());
       /* final List<DashboardModel1> dashboardModel1s = new ArrayList<>();
        dashboardModel1s.add(new DashboardModel1("Specialities"));
        dashboardModel1s.add(new DashboardModel1("Symptoms"));
        dashboardModel1s.add(new DashboardModel1("Tests"));
        dashboardModel1s.add(new DashboardModel1("Pharmacy"));*/
        getPatientDasboard(dashboard, new ApiCallbackInterface() {
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
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

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

