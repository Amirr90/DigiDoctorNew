package com.digidoctor.android.utility;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.AddInvestigationModel;
import com.digidoctor.android.model.AppointmentRes;
import com.digidoctor.android.model.CheckLoginRes;
import com.digidoctor.android.model.CheckSlotAvailabilityRes;
import com.digidoctor.android.model.CheckTimeSlotModel;
import com.digidoctor.android.model.DashBoardRes;
import com.digidoctor.android.model.Dashboard;
import com.digidoctor.android.model.DocBySpecialityRes;
import com.digidoctor.android.model.DocBySymptomsRes;
import com.digidoctor.android.model.GenerateOtpModel;
import com.digidoctor.android.model.GenerateOtpRes;
import com.digidoctor.android.model.GetAppointmentSlotsRes;
import com.digidoctor.android.model.GetMembersRes;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.GetPatientMedicationRes;
import com.digidoctor.android.model.InvestigationDataRes;
import com.digidoctor.android.model.InvestigationRes;
import com.digidoctor.android.model.Login;
import com.digidoctor.android.model.MedicineRes;
import com.digidoctor.android.model.MemberModel;
import com.digidoctor.android.model.OnlineAppointmentSlots;
import com.digidoctor.android.model.PrescriptionModel;
import com.digidoctor.android.model.Registration;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.SaveMultipleFileRes;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.model.TransactionModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.view.activity.PatientDashboard;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import static com.digidoctor.android.utility.utils.APPOINTMENT_DATE;
import static com.digidoctor.android.utility.utils.APPOINTMENT_TIME;
import static com.digidoctor.android.utility.utils.KEY_AMOUNT;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.KEY_DOC_ID;
import static com.digidoctor.android.utility.utils.KEY_DOC_NAME;
import static com.digidoctor.android.utility.utils.KEY_IS_ERA_USER;
import static com.digidoctor.android.utility.utils.KEY_PATIENT_NAME;
import static com.digidoctor.android.utility.utils.KEY_SYMPTOM_ID;
import static com.digidoctor.android.utility.utils.MEMBER_ID;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.getString;


public class ApiUtils {

    public static void getPatientDasboard(Dashboard dashboard,
                                          final ApiCallbackInterface apiCallbackInterface) {

        try {
            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<DashBoardRes> dashBoardResCall = api.patientDasboard(dashboard);

            dashBoardResCall.enqueue(new Callback<DashBoardRes>() {
                @Override
                public void onResponse(@NotNull Call<DashBoardRes> call, @NotNull Response<DashBoardRes> response) {
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else {
                            apiCallbackInterface.onError(response.body().getResponseMessage());
                        }
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<DashBoardRes> call, @NotNull Throwable t) {
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void specialityData(String specialityName, final ApiCallbackInterface apiCallbackInterface) {

        SpecialityModel specialityModel = new SpecialityModel();
        specialityModel.setProblemName(specialityName);

        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());

            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<SpecialityRes> specialityResCall = api.getSpeciality(specialityModel);
            specialityResCall.enqueue(new Callback<SpecialityRes>() {
                @Override
                public void onResponse(@NotNull Call<SpecialityRes> call, @NotNull Response<SpecialityRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<SpecialityRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getDocBySpecialityById(String id, String docName, final ApiCallbackInterface apiCallbackInterface) {

        SpecialityModel specialityModel = new SpecialityModel();
        specialityModel.setSpecialityID(Integer.parseInt(id));
        specialityModel.setDoctorName(docName);
        try {
            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<DocBySpecialityRes> specialityResCall = api.getDoctorProfileBySpeciality(specialityModel);
            specialityResCall.enqueue(new Callback<DocBySpecialityRes>() {
                @Override
                public void onResponse(@NotNull Call<DocBySpecialityRes> call, @NotNull Response<DocBySpecialityRes> response) {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<DocBySpecialityRes> call, @NotNull Throwable t) {
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getDoctorsTimeSlots(String docId, String date, String isEraUser, final ApiCallbackInterface apiCallbackInterface) {
        try {

            final Api api = URLUtils.getAPIServiceNewAPI();

            OnlineAppointmentSlots slotsModel = new OnlineAppointmentSlots();
            slotsModel.setAppointDate(date);
            slotsModel.setServiceProviderDetailsId(docId);
            slotsModel.setIsEraUser(isEraUser);

            Call<GetAppointmentSlotsRes> getAppointmentSlotsResCall = api.getOnlineAppointmentSlots(slotsModel);
            getAppointmentSlotsResCall.enqueue(new Callback<GetAppointmentSlotsRes>() {
                @Override
                public void onResponse(@NotNull Call<GetAppointmentSlotsRes> call, @NotNull Response<GetAppointmentSlotsRes> response) {
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<GetAppointmentSlotsRes> call, @NotNull Throwable t) {
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void getRecommendedDoc(HashMap<String, String> map, final ApiCallbackInterface apiCallbackInterface) {

        SymptomModel symptomModel = new SymptomModel();
        symptomModel.setSymptomID(map.get(KEY_SYMPTOM_ID));
        symptomModel.setDoctorName(map.get(KEY_DOC_NAME));

        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<DocBySymptomsRes> specialityResCall = api.getDoctorProfileBySymptom(symptomModel);
            specialityResCall.enqueue(new Callback<DocBySymptomsRes>() {
                @Override
                public void onResponse(@NotNull Call<DocBySymptomsRes> call, @NotNull Response<DocBySymptomsRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<DocBySymptomsRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getSymptomWithIconsData(String symptomName, final ApiCallbackInterface apiCallbackInterface) {

        try {

            SymptomModel symptomModel = new SymptomModel();
            symptomModel.setProblemName(symptomName);

            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());

            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<SymptomsRes> specialityResCall = api.getProblemsWithIcon(symptomModel);
            specialityResCall.enqueue(new Callback<SymptomsRes>() {
                @Override
                public void onResponse(@NotNull Call<SymptomsRes> call, @NotNull Response<SymptomsRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<SymptomsRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void cancelAppointment(String appointmentId, Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);

        User user = getPrimaryUser(activity);
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<RegistrationRes> call = iRestInterfaces.cancelAppointment(
                getString(TOKEN, activity),
                user.getMobileNo(),
                appointmentId);

        call.enqueue(new Callback<RegistrationRes>() {
            @Override
            public void onResponse(@NotNull Call<RegistrationRes> call, @NotNull Response<RegistrationRes> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    if (PatientDashboard.getInstance() != null)
                        AppUtils.showToastSort(PatientDashboard.getInstance(), PatientDashboard.getInstance().getString(R.string.appointment_canceled));
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());

                } else {
                    AppUtils.hideDialog();
                    if (PatientDashboard.getInstance() != null)
                        AppUtils.showToastSort(PatientDashboard.getInstance(), response.body().getResponseMessage());
                    apiCallbackInterface.onError(response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(@NotNull Call<RegistrationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
                apiCallbackInterface.onFailed(t);

            }
        });
    }

    public static void getOTP(GenerateOtpModel generateOtpModel, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<GenerateOtpRes> call = iRestInterfaces.generateOTPForPatient(generateOtpModel);

        call.enqueue(new Callback<GenerateOtpRes>() {
            @Override
            public void onResponse(@NotNull Call<GenerateOtpRes> call, @NotNull Response<GenerateOtpRes> response) {

                assert response.body() != null;
                if (response.code() == 200) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();

                        if (activity != null)
                            AppUtils.showToastSort(activity, activity.getString(R.string.otp_sent_to_your));
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {

                        AppUtils.hideDialog();
                        if (activity != null)
                            AppUtils.showToastSort(activity, response.body().getResponseMessage());
                    }
                } else
                    Toast.makeText(activity, "error  " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NotNull Call<GenerateOtpRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void GetMembersRes(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);

        User user = getPrimaryUser(activity);

        MemberModel memberModel = new MemberModel();

        memberModel.setUserLoginId(String.valueOf(user.getUserLoginId()));

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<GetMembersRes> call = iRestInterfaces.getMembers(memberModel);

        call.enqueue(new Callback<GetMembersRes>() {
            @Override
            public void onResponse(@NotNull Call<GetMembersRes> call, @NotNull Response<GetMembersRes> response) {

                if (response.code() == 200 && null != response.body()) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        AppUtils.hideDialog();
                        AppUtils.showToastSort(activity, response.body().getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onError(response.message());

            }

            @Override
            public void onFailure(@NotNull Call<GetMembersRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void checkLogin(Login login, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {


        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();

        final Call<CheckLoginRes> checkLogin = iRestInterfaces.checkLogin(login);

        checkLogin.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(@NotNull Call<CheckLoginRes> call, @NotNull Response<CheckLoginRes> response) {
                AppUtils.hideDialog();
                assert response.body() != null;
                if (response.code() == 200) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        if (response.body().getResponseCode() == 1) {

                            utils.setString(TOKEN, response.body().getToken(), activity);

                            apiCallbackInterface.onSuccess(response.body().getResponseValue());

                            Toast.makeText(activity, R.string.logged_in, Toast.LENGTH_SHORT).show();

                        } else apiCallbackInterface.onError(response.body().getResponseMessage());

                    } else {
                        if (activity != null)
                            AppUtils.showToastSort(activity, response.body().getResponseMessage());
                    }
                } else
                    Toast.makeText(activity, "error " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(@NotNull Call<CheckLoginRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }


    public static void patientRegistration(Registration registration,
                                           final Activity activity, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        final Call<CheckLoginRes> register = iRestInterfaces.patientRegistration(registration);

        register.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(@NotNull Call<CheckLoginRes> call, @NotNull Response<CheckLoginRes> response) {
                AppUtils.hideDialog();
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        //Adding Token After Registration
                        try {

                            utils.setString(TOKEN, response.body().getToken(), activity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());

                } else {
                    if (activity != null)
                        AppUtils.showToastSort(activity, response.message());
                }

            }

            @Override
            public void onFailure(@NotNull Call<CheckLoginRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }


    public static void checkTimeSlotAvailability(Map<String, String> map, final ApiCallbackInterface apiCallbackInterface) {
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());


            CheckTimeSlotModel model = new CheckTimeSlotModel();
            model.setMemberId(map.get(MEMBER_ID));
            model.setServiceProviderDetailsId(map.get(KEY_DOC_ID));
            model.setAppointDate(map.get(APPOINTMENT_DATE));
            model.setAppointTime(map.get(APPOINTMENT_TIME));
            model.setUserMobileNo(map.get(MOBILE_NUMBER));
            model.setIsEraUser(map.get(KEY_IS_ERA_USER));
            model.setAppointmentId(map.get(KEY_APPOINTMENT_ID));

            Log.d("TAG", "checkTimeSlotAvailability: " + model.toString());
            final Api api = URLUtils.getAPIServiceNewAPI();

            Call<CheckSlotAvailabilityRes> specialityResCall = api.checkTimeSlotAvailability(model);
            specialityResCall.enqueue(new Callback<CheckSlotAvailabilityRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckSlotAvailabilityRes> call, @NotNull Response<CheckSlotAvailabilityRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        assert response.body() != null;
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else {
                        assert response.errorBody() != null;
                        apiCallbackInterface.onError(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CheckSlotAvailabilityRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(t.getLocalizedMessage());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EverythingIsNonNull
    public static void getPatientMedicationDetails(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();

        GetPatientMedicationMainModel model = new GetPatientMedicationMainModel();
        User user = getPrimaryUser(activity);
        model.setMemberId(String.valueOf(user.getId()));

        Call<GetPatientMedicationRes> call = iRestInterfaces.getPatientMedicationDetails(model);

        call.enqueue(new Callback<GetPatientMedicationRes>() {
            @Override
            public void onResponse(@NotNull Call<GetPatientMedicationRes> call, Response<GetPatientMedicationRes> response) {

                if (response.isSuccessful() && null != response.body()) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {

                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }

            }

            @Override
            public void onFailure(@NotNull Call<GetPatientMedicationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    public static void getTransactionNo(Map<String, String> map, Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        try {

            TransactionModel transactionModel = new TransactionModel();

            transactionModel.setPaymentAmount(map.get(KEY_AMOUNT));
            transactionModel.setPatientName(map.get(KEY_PATIENT_NAME));
            transactionModel.setMemberID(map.get(MEMBER_ID));
            transactionModel.setUserMobileNo(map.get(MOBILE_NUMBER));
            transactionModel.setAppointDate(map.get(APPOINTMENT_DATE));
            transactionModel.setAppointTime(map.get(APPOINTMENT_TIME));
            transactionModel.setServiceProviderDetailsID(map.get(KEY_DOC_ID));
            transactionModel.setIsEraUser(map.get(KEY_IS_ERA_USER));

            AppUtils.showRequestDialog(activity);
            Log.d("TAG", "checkTimeSlotAvailability: " + map.toString());

            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<ResponseModel> specialityResCall = api.getBookingTransactionId(transactionModel);
            specialityResCall.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && null != response.body()) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(t.getLocalizedMessage());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addMember(final Activity activity, Map<String, String> map, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);

        User user = getPrimaryUser(activity);

        MemberModel memberModel = new MemberModel();

        memberModel.setUserLoginId(String.valueOf(user.getUserLoginId()));
        memberModel.setName(map.get("name"));
        memberModel.setMobileNo(map.get("mobile"));
        memberModel.setGender(map.get("gender"));
        memberModel.setDob(map.get("dob"));
        memberModel.setProfilePhotoPath(map.get("profilePath"));
        memberModel.setCountryId(map.get("0"));
        memberModel.setAddress(map.get("address"));

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<RegistrationRes> call = iRestInterfaces.addMember(memberModel);

        call.enqueue(new Callback<RegistrationRes>() {
            @Override
            public void onResponse(@NotNull Call<RegistrationRes> call, @NotNull Response<RegistrationRes> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onError(response.message());

            }

            @Override
            public void onFailure(@NotNull Call<RegistrationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    public static void addVitals(VitalModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<ResponseModel> call = iRestInterfaces.addVitals(model);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if ((response.code() == 200 && null != response.body())) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getVitalsList(VitalModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<VitalResponse> call = iRestInterfaces.getVitals(model);

        call.enqueue(new Callback<VitalResponse>() {
            @Override
            public void onResponse(@NotNull Call<VitalResponse> call, @NotNull Response<VitalResponse> response) {
                if ((response.code() == 200 && null != response.body())) {
                    VitalResponse responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<VitalResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getInvestigationData(User model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<InvestigationRes> call = iRestInterfaces.getInvestigationData(model);

        call.enqueue(new Callback<InvestigationRes>() {
            @Override
            public void onResponse(@NotNull Call<InvestigationRes> call, @NotNull Response<InvestigationRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    InvestigationRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<InvestigationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getAppointmentData(User model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<AppointmentRes> call = iRestInterfaces.getAppointmentData(model);

        call.enqueue(new Callback<AppointmentRes>() {
            @Override
            public void onResponse(@NotNull Call<AppointmentRes> call, @NotNull Response<AppointmentRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    AppointmentRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<AppointmentRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void addPrescription(PrescriptionModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<VitalResponse> call = iRestInterfaces.addPrescription(model);

        call.enqueue(new Callback<VitalResponse>() {
            @Override
            public void onResponse(@NotNull Call<VitalResponse> call, @NotNull Response<VitalResponse> response) {
                if ((response.code() == 200 && null != response.body())) {
                    VitalResponse prescriptionResponse = response.body();
                    if (prescriptionResponse.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(prescriptionResponse.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(prescriptionResponse.getResponseMessage());
                    }
                } else apiCallbackInterface.onError("" + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<VitalResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void submitInvestigation(AddInvestigationModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<VitalResponse> call = iRestInterfaces.addInvestigation(model);

        call.enqueue(new Callback<VitalResponse>() {
            @Override
            public void onResponse(@NotNull Call<VitalResponse> call, @NotNull Response<VitalResponse> response) {
                if ((response.code() == 200 && null != response.body())) {
                    VitalResponse prescriptionResponse = response.body();
                    if (prescriptionResponse.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(prescriptionResponse.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(prescriptionResponse.getResponseMessage());
                    }
                } else apiCallbackInterface.onError("" + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<VitalResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void uploadProfileImage(File imagFile, final ApiCallbackInterface apiCallbackInterface) throws IOException {
        MultipartBody.Part[] fileParts = new MultipartBody.Part[1];
        try {
            MediaType mediaType = MediaType.parse("image/*");

            RequestBody fileBody;

            fileBody = RequestBody.create(mediaType, imagFile);

            fileParts[0] = MultipartBody.Part.createFormData("files", imagFile.getName(), fileBody);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("files", toRequestBody("files"));

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPIForDoctor();
        Call<SaveMultipleFileRes> call = iRestInterfaces.uploadImage(map, fileParts);


        call.enqueue(new Callback<SaveMultipleFileRes>() {
            @Override
            public void onResponse(@NotNull Call<SaveMultipleFileRes> call, @NotNull Response<SaveMultipleFileRes> response) {

                if ((response.code() == 200 && null != response.body())) {
                    SaveMultipleFileRes prescriptionResponse = response.body();
                    if (prescriptionResponse.getResponseCode() == 1) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().getResponseData());
                            String fileName = jsonArray.getJSONObject(0).getString("filePath");
                            List<String> strings = new ArrayList<>();
                            strings.add(fileName);
                            apiCallbackInterface.onSuccess(strings);

                        } catch (Exception e) {
                            e.printStackTrace();
                            apiCallbackInterface.onError(e.getLocalizedMessage());
                        }

                    } else {
                        apiCallbackInterface.onError(prescriptionResponse.getResponseMessage());
                    }
                } else apiCallbackInterface.onError("image Not supported");

            }

            @Override
            public void onFailure(@NotNull Call<SaveMultipleFileRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void uploadMultipleFile(List<String> uris, Activity activity, final ApiCallbackInterface apiCallbackInterface) throws IOException {

        MultipartBody.Part[] fileParts = new MultipartBody.Part[uris.size()];

        for (int a = 0; a < uris.size(); a++) {

            File imagFile = FileUtil.from(activity, Uri.parse(uris.get(a)));

            try {
                MediaType mediaType = MediaType.parse("image/*");

                RequestBody fileBody;

                fileBody = RequestBody.create(mediaType, imagFile);

                fileParts[a] = MultipartBody.Part.createFormData("files", imagFile.getName(), fileBody);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, RequestBody> map = new HashMap<>();
        map.put("files", toRequestBody("files"));

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPIForDoctor();
        Call<SaveMultipleFileRes> call = iRestInterfaces.uploadImage(map, fileParts);

        AppUtils.showRequestDialog(activity);
        call.enqueue(new Callback<SaveMultipleFileRes>() {
            @Override
            public void onResponse(@NotNull Call<SaveMultipleFileRes> call, @NotNull Response<SaveMultipleFileRes> response) {

                AppUtils.hideDialog();
                if ((response.code() == 200 && null != response.body())) {
                    SaveMultipleFileRes prescriptionResponse = response.body();
                    if (prescriptionResponse.getResponseCode() == 1) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().getResponseData());
                            Log.d("TAG", "UploadImageRes: " + jsonArray.toString());
                            List<String> strings = new ArrayList<>();
                            strings.add(jsonArray.toString());
                            apiCallbackInterface.onSuccess(strings);

                        } catch (Exception e) {
                            e.printStackTrace();
                            apiCallbackInterface.onError(e.getLocalizedMessage());
                        }

                    } else {
                        apiCallbackInterface.onError(prescriptionResponse.getResponseMessage());
                    }
                } else apiCallbackInterface.onError("image Not supported");

            }

            @Override
            public void onFailure(@NotNull Call<SaveMultipleFileRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static void getMedicineData(final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<MedicineRes> call = iRestInterfaces.getMedicationData();

        call.enqueue(new Callback<MedicineRes>() {
            @Override
            public void onResponse(@NotNull Call<MedicineRes> call, @NotNull Response<MedicineRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    MedicineRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MedicineRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void deleteMember(User user, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<CheckLoginRes> call = iRestInterfaces.deleteMember(user);

        call.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(@NotNull Call<CheckLoginRes> call, @NotNull Response<CheckLoginRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    CheckLoginRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckLoginRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void investigationData(final ApiCallbackInterface apiCallbackInterface) {
        User user = new User();
        user.setMemberId(0);
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        Call<InvestigationDataRes> call = iRestInterfaces.investigationData(user);


        call.enqueue(new Callback<InvestigationDataRes>() {
            @Override
            public void onResponse(@NotNull Call<InvestigationDataRes> call, @NotNull Response<InvestigationDataRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    InvestigationDataRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<InvestigationDataRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    @EverythingIsNonNull
    public static void getPatientMedicationDetail(String appId, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();
        GetPatientMedicationMainModel model = new GetPatientMedicationMainModel();
        model.setAppointmentId(Integer.parseInt(appId));
        Call<GetPatientMedicationRes> call = iRestInterfaces.getPatientMedicationDetails(model);

        call.enqueue(new Callback<GetPatientMedicationRes>() {
            @Override
            public void onResponse(@NotNull Call<GetPatientMedicationRes> call, Response<GetPatientMedicationRes> response) {

                if (response.isSuccessful() && null != response.body()) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {

                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }

            }

            @Override
            public void onFailure(@NotNull Call<GetPatientMedicationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    @EverythingIsNonNull
    public static void updateMember(User model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();

        Call<CheckLoginRes> call = iRestInterfaces.updateMember(model);

        call.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(@NotNull Call<CheckLoginRes> call, Response<CheckLoginRes> response) {

                if (response.code() == 200)
                    if (response.isSuccessful() && null != response.body()) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.message());
                    }

                else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckLoginRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

}
