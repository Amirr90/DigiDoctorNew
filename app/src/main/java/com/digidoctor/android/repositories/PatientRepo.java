package com.digidoctor.android.repositories;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digidoctor.android.R;
import com.digidoctor.android.database.AppDao;
import com.digidoctor.android.database.AppDatabase;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ApiInterface;
import com.digidoctor.android.interfaces.EraInvestigationApiInterface;
import com.digidoctor.android.interfaces.NewApiInterface;
import com.digidoctor.android.model.AppointmentDetailsRes;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.model.Dashboard;
import com.digidoctor.android.model.DocBySpecialityRes;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.DoctorModelRes;
import com.digidoctor.android.model.EraInvestigationData;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.InvestigationModel;
import com.digidoctor.android.model.MedicineModel;
import com.digidoctor.android.model.NavModel;
import com.digidoctor.android.model.PatientDashboardModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.model.WalletModel;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;
import com.digidoctor.android.model.patientModel.HospitalAndPackageResponse;
import com.digidoctor.android.model.patientModel.SymptomsNotificationModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Response;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
import static com.digidoctor.android.utility.utils.getPrimaryUser;
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
    public MutableLiveData<List<InvestigationModel>> investigationMutableLiveData;
    public MutableLiveData<List<SpecialityModel>> specialityEmcModelMutableLiveData;

    public MutableLiveData<List<AppointmentModel>> appointmentMutableLiveData;

    public MutableLiveData<List<AppointmentDetailsRes.Appointments>> appointmentMutableLivedetails;

    public MutableLiveData<List<ChatModel>> chatMutableLiveData;
    public MutableLiveData<List<HospitalAndPackageResponse>> loadHospitalAndPackageList;

    public MutableLiveData<List<SymptomsNotificationModel>> symptomsNotificationModelMutableLiveData;
    public MutableLiveData<List<SymptomModel>> allDepartmentData;


    //Lab member Variable
    public MutableLiveData<LabDashBoardmodel> labDashboardModelMutableLiveData;


    public MutableLiveData<String> specialityText;


    AppDao appDao;
    MutableLiveData<MedicineModel> mutableLiveDataMedicine;

    public PatientRepo(Context application) {
        AppDatabase userDatabase = AppDatabase.getDatabase(application);
        appDao = userDatabase.getAppDao();
        MedicineModel medicineModel = new MedicineModel();
        medicineModel.setMedicineList((List<MedicineModel.MedicineDetailModel>) appDao.getAllMedicineList());
        mutableLiveDataMedicine.setValue(medicineModel);
    }

    public PatientRepo() {
    }

    public LiveData<List<ChatModel>> getChatData(AppointmentModel user) {
        if (chatMutableLiveData == null) {
            chatMutableLiveData = new MutableLiveData<>();
        }
        loadChatData(user);
        return chatMutableLiveData;

    }

    public LiveData<String> getSpecialityText() {
        if (null == specialityText)
            specialityText = new MutableLiveData<>();
        return specialityText;
    }

    private void loadChatData(AppointmentModel user) {

        ApiUtils.getChatResponse(ApiUtils.getChatData(user), new NewApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<ChatModel> chatModels = (List<ChatModel>) obj;
                chatMutableLiveData.setValue(chatModels);

            }

            @Override
            public void onFailed(String msg) {
                Log.d("loadChatData", "onFailed: " + msg);
            }
        });
    }

    public LiveData<List<AppointmentModel>> getAppointmentList(User user) {
        if (appointmentMutableLiveData == null) {
            appointmentMutableLiveData = new MutableLiveData<>();
        }
        loadAppointmentData(user);
        return appointmentMutableLiveData;

    }

    public LiveData<List<AppointmentDetailsRes.Appointments>> getAppointmentDetails(User user) {
        if (appointmentMutableLivedetails == null) {
            appointmentMutableLivedetails = new MutableLiveData<>();
        }
        loadAppointmentDetails(user);
        return appointmentMutableLivedetails;

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

    private void loadAppointmentDetails(User user) {
        ApiUtils.getAppointmentDetails(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {
                List<AppointmentDetailsRes.Appointments> models = (List<AppointmentDetailsRes.Appointments>) obj;
                Log.d("loadAppointmentDetails", "onSuccess: " + models.get(0));
                appointmentMutableLivedetails.setValue(models);
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

        AppUtils.showRequestDialog(activity);
        getPatientMedicationDetails(activity, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<GetPatientMedicationMainModel> patientMedicationMainModels = (List<GetPatientMedicationMainModel>) o;
                if (patientMedicationMainModels != null) {
                    prescriptionModelMutableLiveData.setValue(patientMedicationMainModels);
                }
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
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
                AppUtils.hideDialog();
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

    public LiveData<List<DoctorModel>> getDocBySpeciality(SpecialityModel specialityModel) {
        if (doctorModelMutableLiveData == null)
            doctorModelMutableLiveData = new MutableLiveData<>();
        loadDocBySpecialityData(specialityModel);
        return doctorModelMutableLiveData;

    }

    private void loadDocBySpecialityData(SpecialityModel specialityModel) {

        getDocBySpecialityById(specialityModel, new ApiUtils.SpecialityInterface() {
            @Override
            public void onSuccess(Object o) {
                AppUtils.hideDialog();
                DocBySpecialityRes res = (DocBySpecialityRes) o;
                if (null != res) {
                    doctorModelMutableLiveData.setValue(res.getResponseValue());
                }

            }

            @Override
            public void onFailed(String msg) {
                AppUtils.hideDialog();
                Toast.makeText(PatientDashboard.getInstance(), msg, Toast.LENGTH_SHORT).show();
                try {
                    if (msg.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), msg, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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


        getSymptomWithIconsData(symptomName, new ApiUtils.SpecialityInterface() {
            @Override
            public void onSuccess(Object o) {
                SymptomsRes res = (SymptomsRes) o;

                try {
                    symptomsModelMutableLiveData.setValue(res.getResponseValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String s) {
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

        });


    }

    public LiveData<List<SpecialityModel>> getSpecialityData(String specialityName) {
        if (specialityModelMutableLiveData == null)
            specialityModelMutableLiveData = new MutableLiveData<>();

        loadSpecialityData(specialityName);
        return specialityModelMutableLiveData;
    }

    private void loadSpecialityData(String specialityName) {

        specialityData(specialityName, new ApiUtils.SpecialityInterface() {
            @Override
            public void onSuccess(Object o) {
                try {
                    SpecialityRes res = (SpecialityRes) o;
                    specialityModelMutableLiveData.setValue(res.getResponseValue());
                    if (null == specialityText)
                        specialityText = new MutableLiveData<>();
                    specialityText.setValue(res.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String s) {
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                        Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });


    }

    public LiveData<PatientDashboardModel> getDashboardData(String lat, String lng) {
        if (patientDashboardModelMutableLiveData == null) {
            patientDashboardModelMutableLiveData = new MutableLiveData<>();
            Dashboard dashboard = new Dashboard();
            dashboard.setLat(lat);
            dashboard.setLat(lng);
            loadPatientDashboardData(dashboard);

        }


        return patientDashboardModelMutableLiveData;
    }

    private void loadPatientDashboardData(Dashboard dashboard) {
        getPatientDasboard(dashboard, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                PatientDashboardModel dashboardModel = (PatientDashboardModel) o.get(0);
                if (null != dashboardModel) {
                    patientDashboardModelMutableLiveData.setValue(dashboardModel);
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

                Toast.makeText(PatientDashboard.getInstance(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    public LiveData<LabDashBoardmodel> getLabDashboardModel(String lat, String lng) {
        if (labDashboardModelMutableLiveData == null) {
            labDashboardModelMutableLiveData = new MutableLiveData<>();
        }


        Dashboard dashboard = new Dashboard();
        dashboard.setLat(lat);
        dashboard.setLat(lng);
        dashboard.setMemberId(String.valueOf(getPrimaryUser(PatientDashboard.getInstance()).getMemberId()));
        loadLabDashboardData(dashboard);
        return labDashboardModelMutableLiveData;
    }

    private void loadLabDashboardData(Dashboard dashboard) {
        showRequestDialog(PatientDashboard.getInstance());
        Response.getDashboardResponse(ApiUtils.getLabDashboard(dashboard), new ApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                hideDialog();
                List<LabDashBoardmodel> dashboardData = (List<LabDashBoardmodel>) obj;
                if (null == dashboardData || dashboardData.isEmpty())
                    return;

                LabDashBoardmodel labDashBoardmodel = dashboardData.get(0);
                if (null == labDashboardModelMutableLiveData)
                    labDashboardModelMutableLiveData = new MutableLiveData<>();

                labDashboardModelMutableLiveData.setValue(labDashBoardmodel);

            }

            @Override
            public void onFailed(String msg) {
                hideDialog();
                Log.d("TAG", "onFailed: " + msg);
            }
        });


    }

    public LiveData<List<SymptomsNotificationModel>> getSymptomsNotificationData(String memberId) {
        if (symptomsNotificationModelMutableLiveData == null)
            symptomsNotificationModelMutableLiveData = new MutableLiveData<>();
        loadNotificationData(memberId);

        return symptomsNotificationModelMutableLiveData;
    }

    private void loadNotificationData(String memberId) {


        ApiUtils.getSymptomsNotification(memberId, symptomsNotificationModelsList -> {

            if (symptomsNotificationModelMutableLiveData == null)
                symptomsNotificationModelMutableLiveData = new MutableLiveData<>();
            symptomsNotificationModelMutableLiveData.setValue(symptomsNotificationModelsList);
        });
    }

    public LiveData<List<HospitalAndPackageResponse>> getHospitalAndPackageList() {

        if (loadHospitalAndPackageList == null) {
            loadHospitalAndPackageList = new MutableLiveData<>();
            loadHospitalAndPackageList();
        }
        return loadHospitalAndPackageList;
    }


    private void loadHospitalAndPackageList() {
        ApiUtils.loadHospitalAndPackage(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                loadHospitalAndPackageList.setValue((List<HospitalAndPackageResponse>) o);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(App.context, "failed to load hospital list !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(App.context, "something went wrong, try again !!", Toast.LENGTH_SHORT).show();
                Log.d("loadHospitalAnd", "onFailed: " + throwable.getLocalizedMessage());
            }
        });
    }

    public LiveData<List<SymptomModel>> getAppDepartmentData(String symptomName) {


        if (allDepartmentData == null) {
            allDepartmentData = new MutableLiveData<>();
        }
        loadAllDepartmentData(symptomName);
        return allDepartmentData;
    }

    private void loadAllDepartmentData(String symptomName) {
        ApiUtils.getSymptomWithIconsData(symptomName, new ApiUtils.SpecialityInterface() {
            @Override
            public void onSuccess(Object o) {
                SymptomsRes res = (SymptomsRes) o;
                try {
                    allDepartmentData.setValue(res.getResponseValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(String s) {
                Toast.makeText(App.context, s, Toast.LENGTH_SHORT).show();
            }

        });
    }

    public LiveData<List<SpecialityModel>> getEmcDepartmentData() {
        if (null == specialityEmcModelMutableLiveData)
            specialityEmcModelMutableLiveData = new MutableLiveData<>();
        loadEmcData();
        return specialityEmcModelMutableLiveData;
    }

    private void loadEmcData() {
        ApiUtils.loadEmcData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                try {
                    List<SpecialityModel> specialityModel = (List<SpecialityModel>) o;
                    specialityEmcModelMutableLiveData.setValue(specialityModel);
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


    public MutableLiveData<EraInvestigationData> eraInvestigationDataMutableLiveData;

    public LiveData<EraInvestigationData> eraInvestigationData(String pid, Activity activity) {
        if (null == eraInvestigationDataMutableLiveData)
            eraInvestigationDataMutableLiveData = new MutableLiveData<>();
        loadEraInvestigationData(pid, activity);
        return eraInvestigationDataMutableLiveData;
    }

    private void loadEraInvestigationData(String pid, Activity activity) {
        AppUtils.showRequestDialog(activity);
        ApiUtils.loadEraInvestigationData(pid, new EraInvestigationApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                AppUtils.hideDialog();
                EraInvestigationData eraInvestigationData = (EraInvestigationData) obj;
                if (null != eraInvestigationData) {
                    if (eraInvestigationData.getInvestigationResult().isEmpty())
                        Toast.makeText(activity, "No investigation !!", Toast.LENGTH_SHORT).show();
                    eraInvestigationDataMutableLiveData.setValue(eraInvestigationData);
                }
            }

            @Override
            public void onFailed(String msg) {
                Log.d("TAG", "loadEraInvestigationData: " + msg);
                Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
                AppUtils.hideDialog();
            }
        });
    }


    public MutableLiveData<List<NavModel>> menuItemData;

    public LiveData<List<NavModel>> getMenuData() {
        if (null == menuItemData) {
            menuItemData = new MutableLiveData<>();
            new GetMenuFroUser(menuItemData).execute();
        }
        return menuItemData;
    }


    public LiveData<MedicineModel> getAllMedicineData() {
        if (null == mutableLiveDataMedicine) {
            mutableLiveDataMedicine = new MutableLiveData<>();
            initMedicineData();
        }

        return mutableLiveDataMedicine;
    }

    private void initMedicineData() {
        new InsertMedicineDataInTable(appDao, mutableLiveDataMedicine).execute();
    }

    MutableLiveData<List<WalletModel>> mutableWalletData;

    public LiveData<List<WalletModel>> getWallet() {
        if (null == mutableWalletData)
            mutableWalletData = new MutableLiveData<>();
        loadWallet();
        return mutableWalletData;
    }

    private void loadWallet() {
        ApiUtils.getWalletData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<WalletModel> responseValue = (List<WalletModel>) o;
                mutableWalletData.setValue(responseValue);
            }

            @Override
            public void onError(String s) {
                hideDialog();

            }

            @Override
            public void onFailed(Throwable throwable) {
                hideDialog();

            }
        });
    }

    MutableLiveData<List<DocumentSnapshot>> mutableCallLogs;

    public LiveData<List<DocumentSnapshot>> getCallLogs() {
        if (null == mutableCallLogs)
            mutableCallLogs = new MutableLiveData<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = utils.getUserForBooking(App.context);
        db.collection(AppUtils.VIDEO_CALLS).whereEqualTo("uid", "" + user.getId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (null != queryDocumentSnapshots && !queryDocumentSnapshots.isEmpty())
                    mutableCallLogs.setValue(queryDocumentSnapshots.getDocuments());
            }
        });

        return mutableCallLogs;
    }


    public static class InsertMedicineDataInTable extends AsyncTask<Void, Void, Void> {

        AppDao appDao;
        MutableLiveData<MedicineModel> mutableLiveDataMedicine;

        public InsertMedicineDataInTable(AppDao appDao, MutableLiveData<MedicineModel> mutableLiveDataMedicine) {
            this.appDao = appDao;
            this.mutableLiveDataMedicine = mutableLiveDataMedicine;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ApiUtils.getMedicineData(new ApiCallbackInterface() {
                @Override
                public void onSuccess(List<?> o) {
                    appDao.deleteAllUser();
                    List<MedicineModel> responseValue = (List<MedicineModel>) o;
                    for (int a = 0; a < responseValue.get(0).getMedicineList().size(); a++) {
                        appDao.addMedicineData(responseValue.get(0).getMedicineList().get(a));
                    }


                    /*for (int a = 0; a < responseValue.get(0).getFrequencyList().size(); a++) {
                        appDao.addMedicineData(responseValue.get(0).getFrequencyList().get(a));
                    }*/


                }

                @Override
                public void onError(String s) {
                    hideDialog();

                }

                @Override
                public void onFailed(Throwable throwable) {
                    hideDialog();

                }
            });
            return null;
        }
    }

    public interface SymptomsNotificationInterface {
        void onResponseSuccess(List<SymptomsNotificationModel> symptomsNotificationModelsList);
    }


    public static class GetMenuFroUser extends AsyncTask<Void, Void, Void> {

        public MutableLiveData<List<NavModel>> menuItemData;

        public GetMenuFroUser(MutableLiveData<List<NavModel>> menuItemData) {
            this.menuItemData = menuItemData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ApiUtils.getMenuData(new ApiCallbackInterface() {
                @Override
                public void onSuccess(List<?> o) {
                    List<NavModel> navModels = (List<NavModel>) o;
                    menuItemData.setValue(navModels);
                }

                @Override
                public void onError(String s) {

                }

                @Override
                public void onFailed(Throwable throwable) {

                }
            });
            return null;
        }
    }
}

