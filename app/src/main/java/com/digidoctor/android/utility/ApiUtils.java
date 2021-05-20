package com.digidoctor.android.utility;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.digidoctor.android.AddMedicineReminderBottomFragment;
import com.digidoctor.android.R;
import com.digidoctor.android.adapters.labadapter.AddressRes;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.interfaces.LabOrderInterface;
import com.digidoctor.android.interfaces.NewApiInterface;
import com.digidoctor.android.model.AddInvestigationModel;
import com.digidoctor.android.model.AppointmentDetailsRes;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.AppointmentRes;
import com.digidoctor.android.model.CartRes;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.model.ChatResponse;
import com.digidoctor.android.model.CheckLoginRes;
import com.digidoctor.android.model.CheckSlotAvailabilityRes;
import com.digidoctor.android.model.CheckTimeSlotModel;
import com.digidoctor.android.model.DashBoardRes;
import com.digidoctor.android.model.Dashboard;
import com.digidoctor.android.model.DocBySpecialityRes;
import com.digidoctor.android.model.DocBySymptomsRes;
import com.digidoctor.android.model.DocModel;
import com.digidoctor.android.model.GenerateOtpModel;
import com.digidoctor.android.model.GenerateOtpRes;
import com.digidoctor.android.model.GetAppointmentSlotsRes;
import com.digidoctor.android.model.GetDocRevModelRes;
import com.digidoctor.android.model.GetMembersRes;
import com.digidoctor.android.model.GetOrderRes;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.GetPatientMedicationRes;
import com.digidoctor.android.model.InvestigationDataRes;
import com.digidoctor.android.model.InvestigationRes;
import com.digidoctor.android.model.Login;
import com.digidoctor.android.model.MedicineRes;
import com.digidoctor.android.model.MemberModel;
import com.digidoctor.android.model.OnlineAppointmentSlots;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.model.PayModeModel;
import com.digidoctor.android.model.PrescriptionModel;
import com.digidoctor.android.model.Registration;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.SaveMultipleFileRes;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SubmitProblemRes;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.SymptomsModel;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.model.SymptomsRes2;
import com.digidoctor.android.model.TransactionModel;
import com.digidoctor.android.model.UploadPresDataModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.model.WriteReviewModel;
import com.digidoctor.android.model.addProductRating;
import com.digidoctor.android.model.addProductRatingResponse;
import com.digidoctor.android.model.labmodel.ApiLabResponse;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.model.labmodel.LabOrderModel;
import com.digidoctor.android.model.labmodel.LabOrderRes;
import com.digidoctor.android.model.labmodel.LabRes;
import com.digidoctor.android.model.labmodel.LabSlotModel;
import com.digidoctor.android.model.labmodel.LabSlotsRes;
import com.digidoctor.android.model.labmodel.PackageRes;
import com.digidoctor.android.model.labmodel.PackagesRes;
import com.digidoctor.android.model.labmodel.SearchRes;
import com.digidoctor.android.model.patientModel.HomeIsolationReqModel;
import com.digidoctor.android.model.patientModel.HospitalAndPackageResponse2;
import com.digidoctor.android.model.patientModel.IsolationResponse;
import com.digidoctor.android.model.patientModel.MedicineReminderResponse;
import com.digidoctor.android.model.patientModel.RecordingResponse;
import com.digidoctor.android.model.pharmacyModel.AddAddressModel;
import com.digidoctor.android.model.pharmacyModel.AddAdressResponse;
import com.digidoctor.android.model.pharmacyModel.AddToCartModel;
import com.digidoctor.android.model.pharmacyModel.AddtoWishlist;
import com.digidoctor.android.model.pharmacyModel.AllCoupneModelResponse;
import com.digidoctor.android.model.pharmacyModel.AllWishListProduct;
import com.digidoctor.android.model.pharmacyModel.CartCount;
import com.digidoctor.android.model.pharmacyModel.CartDetailsResponse;
import com.digidoctor.android.model.pharmacyModel.CoupnemodelRes;
import com.digidoctor.android.model.pharmacyModel.CouponModel;
import com.digidoctor.android.model.pharmacyModel.DeleteAddress;
import com.digidoctor.android.model.pharmacyModel.DeleteItems;
import com.digidoctor.android.model.pharmacyModel.Fillter;
import com.digidoctor.android.model.pharmacyModel.GetAllProductResponse;
import com.digidoctor.android.model.pharmacyModel.Order;
import com.digidoctor.android.model.pharmacyModel.OrderDetailModel;
import com.digidoctor.android.model.pharmacyModel.OrderPlaceModel;
import com.digidoctor.android.model.pharmacyModel.OrderPlaceModelResponse;
import com.digidoctor.android.model.pharmacyModel.PharmacyModel;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.digidoctor.android.model.pharmacyModel.ProductModel;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;
import com.digidoctor.android.model.pharmacyModel.getfilltervarentmodel;
import com.digidoctor.android.model.pharmacyModel.shopbycategoryRes;
import com.digidoctor.android.repositories.PatientRepo;
import com.digidoctor.android.view.activity.PatientDashboard;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.isNetworkConnected;
import static com.digidoctor.android.utility.utils.logout;


@SuppressWarnings("deprecation")
public class ApiUtils {

    public static final int RESPONSE_SUCCESS = 1;
    public static final int RESPONSE_FAILED = 0;
    public static final int RESPONSE_LOGOUT = 2;

    public static void getPatientDasboard(Dashboard dashboard, final ApiCallbackInterface apiCallbackInterface) {

        if (isNetworkConnected(App.context))
            try {
                final Api api = URLUtils.getAPIServiceForPatient();
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
        else {
            apiCallbackInterface.onError(App.context.getString(R.string.no_internet));
        }
    }


    public static void specialityData(String specialityName, final ApiCallbackInterface apiCallbackInterface) {

        SpecialityModel specialityModel = new SpecialityModel();
        specialityModel.setProblemName(specialityName);

        try {
//            if (PatientDashboard.getInstance() != null)
//                AppUtils.showRequestDialog(PatientDashboard.getInstance());

            final Api api = URLUtils.getAPIServiceForPatient();
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


    public static void getDocBySpecialityById(SpecialityModel specialityModel, final ApiCallbackInterface apiCallbackInterface) {
        try {
            final Api api = URLUtils.getAPIServiceForPatient();
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

            final Api api = URLUtils.getAPIServiceForPatient();

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
        symptomModel.setSymptomId(map.get(KEY_SYMPTOM_ID));
        symptomModel.setDoctorName(map.get(KEY_DOC_NAME));

        try {
//            if (PatientDashboard.getInstance() != null)
//                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIServiceForPatient();
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

            final Api api = URLUtils.getAPIServiceForPatient();
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<GenerateOtpRes> call = iRestInterfaces.generateOTPForPatient(generateOtpModel);

        call.enqueue(new Callback<GenerateOtpRes>() {
            @Override
            public void onResponse(@NotNull Call<GenerateOtpRes> call, @NotNull Response<GenerateOtpRes> response) {

                assert response.body() != null;
                if (response.code() == 200) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();

                        if (activity != null) {
                            Toast.makeText(activity, activity.getString(R.string.otp_sent_to_your), Toast.LENGTH_SHORT).show();
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        }
                    } else {

                        AppUtils.hideDialog();
                        if (activity != null)
                            Toast.makeText(activity, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

                        //  AppUtils.showToastSort(activity, response.body().getResponseMessage());
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

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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


        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();

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

                    } else if (response.body().getResponseCode() == RESPONSE_LOGOUT) {
                        logout(activity);
                    } else {
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

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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


    public static void checkTimeSlotAvailability(Map<String, Object> map, final ApiCallbackInterface apiCallbackInterface) {
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            CheckTimeSlotModel model = new CheckTimeSlotModel();
            model.setMemberId((String) map.get(MEMBER_ID));
            model.setServiceProviderDetailsId((String) map.get(KEY_DOC_ID));
            model.setAppointDate((String) map.get(APPOINTMENT_DATE));
            model.setAppointTime((String) map.get(APPOINTMENT_TIME));
            model.setUserMobileNo((String) map.get(MOBILE_NUMBER));
            model.setIsEraUser((String) map.get(KEY_IS_ERA_USER));
            model.setAppointmentId((String) map.get(KEY_APPOINTMENT_ID));
            //    model.setIsRevisit((Boolean) map.get(IS_REVISIT));

            Log.d("TAG", "checkTimeSlotAvailability: " + model.toString());

            Call<CheckSlotAvailabilityRes> specialityResCall = URLUtils.getAPIServiceForPatient().checkTimeSlotAvailability(model);
            specialityResCall.enqueue(new Callback<CheckSlotAvailabilityRes>() {
                @Override
                public void onResponse(@NotNull Call<CheckSlotAvailabilityRes> call, @NotNull Response<CheckSlotAvailabilityRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        CheckSlotAvailabilityRes resModel = response.body();
                        if (null != resModel) {
                            int responseCode = resModel.getResponseCode();
                            switch (responseCode) {
                                case RESPONSE_SUCCESS:
                                    apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                    break;
                                case RESPONSE_FAILED:
                                    apiCallbackInterface.onError(resModel.getResponseMessage());
                                    break;
                                case RESPONSE_LOGOUT:
                                    logout(PatientDashboard.getInstance());
                                    break;

                            }
                        }
                    } else {
                        apiCallbackInterface.onError(String.valueOf(response.code()));
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

    private static void reVisitAppointment(CheckTimeSlotModel model, ApiCallbackInterface apiCallbackInterface) {
        Call<CheckSlotAvailabilityRes> specialityResCall = URLUtils.getAPIServiceForPatient().checkTimeSlotAvailability(model);
    }

    @EverythingIsNonNull
    public static void getPatientMedicationDetails(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        GetPatientMedicationMainModel model = new GetPatientMedicationMainModel();
        User user = getPrimaryUser(activity);
        model.setMemberId(String.valueOf(user.getId()));

        Call<GetPatientMedicationRes> call = iRestInterfaces.getPatientMedicationDetails(model);

        call.enqueue(new Callback<GetPatientMedicationRes>() {
            @Override
            public void onResponse(@NotNull Call<GetPatientMedicationRes> call, Response<GetPatientMedicationRes> response) {

                if (response.code() == 200) {
                    GetPatientMedicationRes resModel = response.body();
                    if (null != resModel) {
                        int responseCode = resModel.getResponseCode();
                        switch (responseCode) {
                            case RESPONSE_SUCCESS:
                                apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                break;
                            case RESPONSE_FAILED:
                                apiCallbackInterface.onError(resModel.getResponseMessage());
                                break;
                            case RESPONSE_LOGOUT:
                                logout(PatientDashboard.getInstance());
                                break;

                        }
                    }
                } else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(@NotNull Call<GetPatientMedicationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    public static void getTransactionNo(Map<String, Object> map, Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        try {

            TransactionModel transactionModel = new TransactionModel();

            transactionModel.setPaymentAmount((String) map.get(KEY_AMOUNT));
            transactionModel.setPatientName((String) map.get(KEY_PATIENT_NAME));
            transactionModel.setMemberID((String) map.get(MEMBER_ID));
            transactionModel.setUserMobileNo((String) map.get(MOBILE_NUMBER));
            transactionModel.setAppointDate((String) map.get(APPOINTMENT_DATE));
            transactionModel.setAppointTime((String) map.get(APPOINTMENT_TIME));
            transactionModel.setServiceProviderDetailsID((String) map.get(KEY_DOC_ID));
            transactionModel.setIsEraUser((String) map.get(KEY_IS_ERA_USER));
            if (null != map.get("transactionType"))
                transactionModel.setTransactionType((String) map.get("transactionType"));

            AppUtils.showRequestDialog(activity);
            Log.d("TAG", "checkTimeSlotAvailability: " + map.toString());

            final Api api = URLUtils.getAPIServiceForPatient();
            Call<ResponseModel> specialityResCall = api.getBookingTransactionId(transactionModel);
            specialityResCall.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                    AppUtils.hideDialog();

                    if (response.code() == 200) {
                        ResponseModel resModel = response.body();
                        if (null != resModel) {
                            int responseCode = resModel.getResponseCode();
                            switch (responseCode) {
                                case RESPONSE_SUCCESS:
                                    apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                    break;
                                case RESPONSE_FAILED:
                                    apiCallbackInterface.onError(resModel.getResponseMessage());
                                    break;
                                case RESPONSE_LOGOUT:
                                    logout(PatientDashboard.getInstance());
                                    break;

                            }
                        }
                    } else {
                        apiCallbackInterface.onError(String.valueOf(response.code()));
                    }
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

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<RegistrationRes> call = iRestInterfaces.addMember(memberModel);

        call.enqueue(new Callback<RegistrationRes>() {
            @Override
            public void onResponse(@NotNull Call<RegistrationRes> call, @NotNull Response<RegistrationRes> response) {

                if (response.code() == 200) {
                    RegistrationRes resModel = response.body();
                    if (null != resModel) {
                        int responseCode = resModel.getResponseCode();
                        switch (responseCode) {
                            case RESPONSE_SUCCESS:
                                apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                break;
                            case RESPONSE_FAILED:
                                apiCallbackInterface.onError(resModel.getResponseMessage());
                                break;
                            case RESPONSE_LOGOUT:
                                logout(PatientDashboard.getInstance());
                                break;

                        }
                    }
                } else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
                }

            }

            @Override
            public void onFailure(@NotNull Call<RegistrationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    public static void addVitals(VitalModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<ResponseModel> call = iRestInterfaces.addVitals(model);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if (response.code() == 200) {
                    ResponseModel resModel = response.body();
                    if (null != resModel) {
                        int responseCode = resModel.getResponseCode();
                        switch (responseCode) {
                            case RESPONSE_SUCCESS:
                                apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                break;
                            case RESPONSE_FAILED:
                                apiCallbackInterface.onError(resModel.getResponseMessage());
                                break;
                            case RESPONSE_LOGOUT:
                                logout(PatientDashboard.getInstance());
                                break;

                        }
                    }
                } else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getVitalsList(VitalModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<VitalResponse> call = iRestInterfaces.getVitals(model);

        call.enqueue(new Callback<VitalResponse>() {
            @Override
            public void onResponse(@NotNull Call<VitalResponse> call, @NotNull Response<VitalResponse> response) {

                if (response.code() == 200) {
                    VitalResponse resModel = response.body();
                    if (null != resModel) {
                        int responseCode = resModel.getResponseCode();
                        switch (responseCode) {
                            case RESPONSE_SUCCESS:
                                apiCallbackInterface.onSuccess(resModel.getResponseValue());
                                break;
                            case RESPONSE_FAILED:
                                apiCallbackInterface.onError(String.valueOf(response.code()));
                                break;
                            case RESPONSE_LOGOUT:
                                logout(PatientDashboard.getInstance());
                                break;

                        }
                    }
                } else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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

    public static void getAppointmentDetails(User model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<AppointmentDetailsRes> call = iRestInterfaces.getAppointmentDetails(model);

        call.enqueue(new Callback<AppointmentDetailsRes>() {
            @Override
            public void onResponse(@NotNull Call<AppointmentDetailsRes> call, @NotNull Response<AppointmentDetailsRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    AppointmentDetailsRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseModel.getResponseMessage());
                    }
                } else
                    apiCallbackInterface.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<AppointmentDetailsRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void addPrescription(PrescriptionModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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

    public static void getPayMode(PayModeModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceNewAPIForPharmacy();
        Call<com.digidoctor.android.model.Response> call = iRestInterfaces.getPaymentMode(model);

        call.enqueue(new Callback<com.digidoctor.android.model.Response>() {
            @Override
            public void onResponse(@NotNull Call<com.digidoctor.android.model.Response> call, @NotNull Response<com.digidoctor.android.model.Response> response) {
                if ((response.code() == 200 && null != response.body())) {
                    com.digidoctor.android.model.Response responseBody = response.body();
                    if (responseBody.getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(responseBody.getResponseValue());
                    } else {
                        apiCallbackInterface.onError(responseBody.getResponseMessage());
                    }
                } else apiCallbackInterface.onError("" + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<com.digidoctor.android.model.Response> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void saveAttachmentAfterBooking(UploadPresDataModel model, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();

        Call<SaveMultipleFileRes> call = iRestInterfaces.saveAttachmentAfterBooking(model);

        call.enqueue(new Callback<SaveMultipleFileRes>() {
            @Override
            public void onResponse(@NotNull Call<SaveMultipleFileRes> call, @NotNull Response<SaveMultipleFileRes> response) {
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
                } else apiCallbackInterface.onError("" + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<SaveMultipleFileRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void uploadProfileImage(File imagFile, final ApiCallbackInterface apiCallbackInterface) {
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
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public static void getMedicineData(final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<CheckLoginRes> call = iRestInterfaces.deleteMember(user);

        call.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(@NotNull Call<CheckLoginRes> call, @NotNull Response<CheckLoginRes> response) {

                if (response.code() == 200) {
                    CheckLoginRes checkLoginRes = response.body();
                    if (null != checkLoginRes) {
                        int responseCode = checkLoginRes.getResponseCode();
                        switch (responseCode) {
                            case RESPONSE_SUCCESS:
                                apiCallbackInterface.onSuccess(checkLoginRes.getResponseValue());
                                break;
                            case RESPONSE_FAILED:
                                apiCallbackInterface.onError(checkLoginRes.getResponseMessage());
                                break;
                            case RESPONSE_LOGOUT:
                                logout(PatientDashboard.getInstance());
                                break;

                        }
                    }
                } else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
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
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();

        Call<CheckLoginRes> call = iRestInterfaces.updateMember(model);

        call.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(@NotNull Call<CheckLoginRes> call, Response<CheckLoginRes> response) {
                AppUtils.hideDialog();

                if (response.code() == 200) {
                    CheckLoginRes checkLoginRes = response.body();
                    if (null != checkLoginRes) {
                        int responseCode = checkLoginRes.getResponseCode();
                        switch (responseCode) {
                            case RESPONSE_SUCCESS:
                                apiCallbackInterface.onSuccess(checkLoginRes.getResponseValue());
                                break;
                            case RESPONSE_FAILED:
                                apiCallbackInterface.onError(checkLoginRes.getResponseMessage());
                                break;
                            case RESPONSE_LOGOUT:
                                logout(PatientDashboard.getInstance());
                                break;

                        }
                    }
                } else {
                    apiCallbackInterface.onError(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckLoginRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }


    public static Call<ChatResponse> sendMsg(ChatModel model) {
        return URLUtils.getAPIServiceForPatient().sendMsg(model);
    }

    public static Call<ChatResponse> getChatData(AppointmentModel model) {
        return URLUtils.getAPIServiceForPatient().getMsg(model);
    }


    public static void getChatResponse(Call<ChatResponse> call, NewApiInterface newApiInterface) {
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(@NotNull Call<ChatResponse> call, @NotNull Response<ChatResponse> response) {
                if (response.code() == 200) {
                    ChatResponse responseModel = response.body();
                    if (null == responseModel)
                        return;
                    if (responseModel.getResponseCode() == 1) {
                        newApiInterface.onSuccess(responseModel.getResponseValue());
                    } else newApiInterface.onFailed(responseModel.getResponseMessage());
                } else newApiInterface.onFailed(String.valueOf(response.code()));
            }

            @Override
            public void onFailure(@NotNull Call<ChatResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                newApiInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }


    //Pharmacy

    public static void getShopByCategory(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        Call<shopbycategoryRes> call = iRestInterfaces.getPharmacyDashboard(pharmacyModel);
        call.enqueue(new Callback<shopbycategoryRes>() {
            @Override
            public void onResponse(@NotNull Call<shopbycategoryRes> call, Response<shopbycategoryRes> response) {
                if (response.code() == 200)
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        apiCallbackInterface.onError(response.message());
                    }
                else apiCallbackInterface.onError(String.valueOf(response.code()));

            }

            @Override
            public void onFailure(@NotNull Call<shopbycategoryRes> call, Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getallpro(final Activity activity, PharmacyModel pharmacyModel, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<GetAllProductResponse> call = iRestInterfaces.getallpr(pharmacyModel);
        call.enqueue(new Callback<GetAllProductResponse>() {
            @Override
            public void onResponse(Call<GetAllProductResponse> call, Response<GetAllProductResponse> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<GetAllProductResponse> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

    public static void getProductdetailsbyProductID(ProductModel pId, Activity requireActivity, final ApiCallbackInterface apiCallbackInterface) {


        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(requireActivity);


        try {
            final Api api = URLUtils.getPharmacyApisRef();
            Call<ProductDetailModelResponse> productdetails = api.getproductdetails(pId);
            productdetails.enqueue(new Callback<ProductDetailModelResponse>() {
                @Override
                public void onResponse(@NotNull Call<ProductDetailModelResponse> call, @NotNull Response<ProductDetailModelResponse> response) {
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getResponseCode() == 1) {


                            AppUtils.hideDialog();
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<ProductDetailModelResponse> call, @NotNull Throwable t) {
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                    AppUtils.hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void getCartDetails(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));
        Call<CartDetailsResponse> call = iRestInterfaces.getcartdetails(pharmacyModel);
        call.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<CartDetailsResponse> call, @NotNull Response<CartDetailsResponse> response) {
                if (response.code() == 200) {
                    CartDetailsResponse cartDetailsResponse = response.body();
                    if (cartDetailsResponse != null) {
                        if (cartDetailsResponse.getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(cartDetailsResponse.getResponseValue());
                        } else {
                            apiCallbackInterface.onError(cartDetailsResponse.getResponseMessage());
                        }
                    } else {
                        apiCallbackInterface.onError("" + response.message());
                    }
                } else {
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CartDetailsResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);

            }
        });


    }

    public static void addtocart(AddToCartModel addToCartModel, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<CartDetailsResponse> call = iRestInterfaces.addcart(addToCartModel);

        call.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<CartDetailsResponse> call, @NotNull Response<CartDetailsResponse> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());

                        if (!response.body().getResponseValue().get(0).getProductDetails().isEmpty()) {
                            PatientDashboard.getInstance().updateCartCount(response.body().getResponseValue().get(0).getProductDetails().size());
                        }
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                } else apiCallbackInterface.onError(String.valueOf(response.code()));

            }

            @Override
            public void onFailure(@NotNull Call<CartDetailsResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });

    }

    public static void deleteItems(DeleteItems items, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<CartDetailsResponse> call = iRestInterfaces.deleteItems(items);

        call.enqueue(new Callback<CartDetailsResponse>() {
            @Override
            public void onResponse(@NotNull Call<CartDetailsResponse> call, @NotNull Response<CartDetailsResponse> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        CartDetailsResponse response1 = response.body();
                        if (response1.getResponseValue().isEmpty()) {
                            return;
                        }
                        if (null != response1.getResponseValue().get(0).getProductDetails()) {
                            PatientDashboard.getInstance().updateCartCount(response1.getResponseValue().get(0).getProductDetails().size());
                        }
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<CartDetailsResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });

    }


    public static void DeleteAddress(DeleteAddress deleteAddress, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<getaddressModel> call = iRestInterfaces.DeleteAddress(deleteAddress);

        call.enqueue(new Callback<getaddressModel>() {
            @Override
            public void onResponse(@NotNull Call<getaddressModel> call, @NotNull Response<getaddressModel> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<getaddressModel> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });

    }


    public static void add_address(final Activity activity, Map<String, String> map, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);

        AddAddressModel addAddressModel = new AddAddressModel();
        addAddressModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        addAddressModel.setName(map.get("Full_name"));
        addAddressModel.setMobileno(map.get("Mobile"));
        addAddressModel.setHouseNo(map.get("House"));
        addAddressModel.setPincode(map.get("ZipCode"));
        addAddressModel.setCity(map.get("City"));
        addAddressModel.setState(map.get("State"));
        addAddressModel.setArea(map.get("area"));
        addAddressModel.setLocality(map.get("locality"));
        addAddressModel.setIsDefault(map.get("isDefault"));
        addAddressModel.setIsSundayOpen(map.get("setIsSundayOpen"));
        addAddressModel.setIsSaturdayOpen(map.get("setIsSaturdayOpen"));
        addAddressModel.setAddressType("22");

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<AddAdressResponse> call = iRestInterfaces.AddAddress(addAddressModel);

        call.enqueue(new Callback<AddAdressResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddAdressResponse> call, @NotNull Response<AddAdressResponse> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        // apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        Toast.makeText(activity, "Address Added Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddAdressResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void getadddetails(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        Call<getaddressModel> call = iRestInterfaces.getadd(pharmacyModel);
        call.enqueue(new Callback<getaddressModel>() {
            @Override
            public void onResponse(Call<getaddressModel> call, Response<getaddressModel> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<getaddressModel> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });


    }

    public static void getcoupne(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId("23331");

        Call<AllCoupneModelResponse> call = iRestInterfaces.getCoupnedetails(pharmacyModel);
        call.enqueue(new Callback<AllCoupneModelResponse>() {
            @Override
            public void onResponse(Call<AllCoupneModelResponse> call, Response<AllCoupneModelResponse> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AllCoupneModelResponse> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void getwish(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        User user = new User();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        Call<AllWishListProduct> call = iRestInterfaces.getwishlist(pharmacyModel);
        call.enqueue(new Callback<AllWishListProduct>() {
            @Override
            public void onResponse(Call<AllWishListProduct> call, Response<AllWishListProduct> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<AllWishListProduct> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void addtowishlist(AddtoWishlist addtoWishlist, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<AllWishListProduct> call = iRestInterfaces.AddAswishlist(addtoWishlist);

        call.enqueue(new Callback<AllWishListProduct>() {
            @Override
            public void onResponse(@NotNull Call<AllWishListProduct> call, @NotNull Response<AllWishListProduct> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AllWishListProduct> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });

    }


    public static void getcartcountutils(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        Call<CartCount> call = iRestInterfaces.getcartcount(pharmacyModel);
        call.enqueue(new Callback<CartCount>() {
            @Override
            public void onResponse(@NotNull Call<CartCount> call, @NotNull Response<CartCount> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CartCount> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void orderPlaced(String addressId, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);
        OrderPlaceModel orderPlaceModel = new OrderPlaceModel();
        orderPlaceModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));
        //   addToCartModel.setUniqueNo(map.get("uniqueNo"));
        orderPlaceModel.setAddressId(addressId);
        orderPlaceModel.setTrancationNo("0");
        orderPlaceModel.setPaymentMode("COD");
        orderPlaceModel.setCouponCode("0");
        orderPlaceModel.setUniqueNo("0");

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<OrderPlaceModelResponse> call = iRestInterfaces.orderplace(orderPlaceModel);

        call.enqueue(new Callback<OrderPlaceModelResponse>() {
            @Override
            public void onResponse(@NotNull Call<OrderPlaceModelResponse> call, @NotNull Response<OrderPlaceModelResponse> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<OrderPlaceModelResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });

    }

    public static void getplacedorder(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        Call<GetOrderRes> call = iRestInterfaces.getorder(pharmacyModel);
        call.enqueue(new Callback<GetOrderRes>() {
            @Override
            public void onResponse(Call<GetOrderRes> call, Response<GetOrderRes> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<GetOrderRes> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });


    }


    public static void getorderdetails(String orderDetailsId, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Order order = new Order();

        order.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));
        order.setOrderDetailsId(orderDetailsId);

        Call<OrderDetailModel> call = iRestInterfaces.orderdetails(order);
        call.enqueue(new Callback<OrderDetailModel>() {
            @Override
            public void onResponse(Call<OrderDetailModel> call, Response<OrderDetailModel> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailModel> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });


    }


    public static void validateCoupon(final CouponModel couponModel, final ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<CoupnemodelRes> call = iRestInterfaces.coupnevalidation(couponModel);
        call.enqueue(new Callback<CoupnemodelRes>() {
            @Override
            public void onResponse(Call<CoupnemodelRes> call, Response<CoupnemodelRes> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                } else apiCallbackInterface.onError(String.valueOf(response.code()));


            }

            @Override
            public void onFailure(@NotNull Call<CoupnemodelRes> call, Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });

    }

    public static void update_Address(AddAddressModel addAddressModel, Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);


        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<getaddressModel> call = iRestInterfaces.updateaddress(addAddressModel);

        call.enqueue(new Callback<getaddressModel>() {
            @Override
            public void onResponse(@NotNull Call<getaddressModel> call, @NotNull Response<getaddressModel> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        AppUtils.hideDialog();
                        // apiCallbackInterface.onSuccess(response.body().getResponseValue());

                    } else {
                        AppUtils.hideDialog();
                        apiCallbackInterface.onError(response.body().getResponseMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<getaddressModel> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void CancelOrder(String orderDetailsId, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Order order = new Order();
        order.setOrderDetailsId(orderDetailsId);

        Call<OrderDetailModel> call = iRestInterfaces.cancelorder(order);
        call.enqueue(new Callback<OrderDetailModel>() {
            @Override
            public void onResponse(Call<OrderDetailModel> call, Response<OrderDetailModel> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrderDetailModel> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });


    }

    public static void getfilltervarient(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Fillter fillter = new Fillter();

        fillter.setCategoryId("21");

        Call<getfilltervarentmodel> call = iRestInterfaces.getFillterVarient(fillter);
        call.enqueue(new Callback<getfilltervarentmodel>() {
            @Override
            public void onResponse(@NotNull Call<getfilltervarentmodel> call, Response<getfilltervarentmodel> response) {
                if (response.isSuccessful() && Objects.requireNonNull(response.body()).getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<getfilltervarentmodel> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });


    }


    public static void add_rating(final Activity activity, Map<String, String> map, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);

        addProductRating addProductRating = new addProductRating();
        addProductRating.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));
        addProductRating.setProductInfoCode(map.get("pinfo"));
        addProductRating.setReview(map.get("review"));
        addProductRating.setStarRating(map.get("rating"));

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<addProductRatingResponse> call = iRestInterfaces.postproductrating(addProductRating);

        call.enqueue(new Callback<addProductRatingResponse>() {
            @Override
            public void onResponse(@NotNull Call<addProductRatingResponse> call, @NotNull Response<addProductRatingResponse> response) {

                if (response.code() == 200 && response.body() != null) {
                    if (response.isSuccessful()) {
                        AppUtils.hideDialog();
                        // apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        Toast.makeText(activity, "Thanks for Rating!", Toast.LENGTH_SHORT).show();

                    } else {
                        AppUtils.hideDialog();

                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<addProductRatingResponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    //Lab Apis
    public static Call<ApiLabResponse> getLabDashboard(Dashboard model) {
        return URLUtils.getLabApisRef().getLabDashboard(model);
    }


    public static void getCartData(Activity activity, CartInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getLabApisRef();
        User user = getPrimaryUser(activity);
        Call<CartRes> call = iRestInterfaces.cartDetails(user);
        call.enqueue(new Callback<CartRes>() {
            @Override
            public void onResponse(@NotNull Call<CartRes> call, @NotNull Response<CartRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    CartRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.cartItem(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onFailed(responseModel.getResponseMessage());
                    }
                } else {
                    apiCallbackInterface.onFailed("failed to get cart Data !!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CartRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void addItemToCart(CartModel model, CartInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<CartRes> call = iRestInterfaces.addToCart(model);
        call.enqueue(new Callback<CartRes>() {
            @Override
            public void onResponse(@NotNull Call<CartRes> call, @NotNull Response<CartRes> response) {
                AppUtils.hideDialog();
                if ((response.code() == 200 && null != response.body())) {
                    CartRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.onCartItemAdded(responseModel.getResponseValue());
                        /* apiCallbackInterface.cartItem(responseModel.getResponseValue());*/
                    } else {
                        apiCallbackInterface.onFailed(responseModel.getResponseMessage());
                    }
                } else {
                    apiCallbackInterface.onFailed("failed to add cart item !!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CartRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t.getLocalizedMessage());
            }
        });

    }

    public static void deleteFromCart(CartModel model, CartInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<CartRes> call = iRestInterfaces.deleteCart(model);
        call.enqueue(new Callback<CartRes>() {
            @Override
            public void onResponse(@NotNull Call<CartRes> call, @NotNull Response<CartRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    CartRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        apiCallbackInterface.cartItem(responseModel.getResponseValue());
                        apiCallbackInterface.onCartItemDeleted(responseModel.getResponseValue());
                    } else {
                        apiCallbackInterface.onFailed(responseModel.getResponseMessage());
                    }
                } else {
                    apiCallbackInterface.onFailed("failed to delete cart item !!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CartRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void placeOrder(LabOrderModel model, LabOrderInterface labOrderInterface) {
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<LabOrderRes> call = iRestInterfaces.placeOrder(model);
        call.enqueue(new Callback<LabOrderRes>() {
            @Override
            public void onResponse(@NotNull Call<LabOrderRes> call, @NotNull Response<LabOrderRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    LabOrderRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        labOrderInterface.onOrderPlaced(responseModel.getResponseValue());
                        labOrderInterface.orders(responseModel.getResponseValue());
                    } else {
                        labOrderInterface.onFailed(responseModel.getResponseMessage());
                    }
                } else {
                    labOrderInterface.onFailed("failed to place order !!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<LabOrderRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                labOrderInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void getOrders(LabOrderModel model, LabOrderInterface labOrderInterface) {
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<LabOrderRes> call = iRestInterfaces.getOrders(model);
        call.enqueue(new Callback<LabOrderRes>() {
            @Override
            public void onResponse(@NotNull Call<LabOrderRes> call, @NotNull Response<LabOrderRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    LabOrderRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        labOrderInterface.orders(responseModel.getResponseValue());
                    } else {
                        labOrderInterface.onFailed(responseModel.getResponseMessage());
                    }
                } else {
                    labOrderInterface.onFailed("failed to get orders !!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<LabOrderRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                labOrderInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void cancelOrder(LabOrderModel model, LabOrderInterface labOrderInterface) {
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<LabOrderRes> call = iRestInterfaces.getOrders(model);
        call.enqueue(new Callback<LabOrderRes>() {
            @Override
            public void onResponse(@NotNull Call<LabOrderRes> call, @NotNull Response<LabOrderRes> response) {
                if ((response.code() == 200 && null != response.body())) {
                    LabOrderRes responseModel = response.body();
                    if (responseModel.getResponseCode() == 1) {
                        labOrderInterface.orders(responseModel.getResponseValue());
                        labOrderInterface.onCancelOrder(responseModel.getResponseValue());
                    } else {
                        labOrderInterface.onFailed(responseModel.getResponseMessage());
                    }
                } else {
                    labOrderInterface.onFailed("failed to cancel order !!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<LabOrderRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                labOrderInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }


    public static void WriteReview(WriteReviewModel model, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<LabOrderRes> call = iRestInterfaces.writeAReview(model);

        call.enqueue(new Callback<LabOrderRes>() {
            @Override
            public void onResponse(@NotNull Call<LabOrderRes> call, @NotNull Response<LabOrderRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LabOrderRes> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });

    }


    public static void getDoctorsReviews(DocModel model, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<GetDocRevModelRes> call = iRestInterfaces.getdoctorreview(model);
        call.enqueue(new Callback<GetDocRevModelRes>() {
            @Override
            public void onResponse(@NotNull Call<GetDocRevModelRes> call, @NotNull Response<GetDocRevModelRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GetDocRevModelRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }


    public static void getPackageData(ApiCallbackInterface apiCallbackInterface) {
        User model = new User();
        model.setMemberId(getPrimaryUser().getMemberId());
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<PackagesRes> call = iRestInterfaces.getPackageData(model);
        call.enqueue(new Callback<PackagesRes>() {
            @Override
            public void onResponse(@NotNull Call<PackagesRes> call, @NotNull Response<PackagesRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<PackagesRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void loadPackageDataById(String packageID, ApiCallbackInterface apiCallbackInterface) {
        PackageModel packageModel = new PackageModel();
        packageModel.setPackageId(packageID);
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<PackageRes> call = iRestInterfaces.getPackageDataById(packageModel);
        call.enqueue(new Callback<PackageRes>() {
            @Override
            public void onResponse(@NotNull Call<PackageRes> call, @NotNull Response<PackageRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<PackageRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getLabData(ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<LabRes> call = iRestInterfaces.getLabData();
        call.enqueue(new Callback<LabRes>() {
            @Override
            public void onResponse(@NotNull Call<LabRes> call, @NotNull Response<LabRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<LabRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void searchLabsANdPackages(ApiCallbackInterface apiCallbackInterface) {

        User user = new User();
        user.setMemberId(getPrimaryUser().getMemberId());
        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<SearchRes> call = iRestInterfaces.getSearchData(user);
        call.enqueue(new Callback<SearchRes>() {
            @Override
            public void onResponse(@NotNull Call<SearchRes> call, @NotNull Response<SearchRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<SearchRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void getSymptomsNotification(String memberId, final PatientRepo.SymptomsNotificationInterface notificationInterface) {
        try {
            SymptomsModel symptomModel = new SymptomsModel();
            symptomModel.setMemberId(memberId);
            Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
            final Call<SymptomsRes2> resCall = iRestInterfaces.getPatientSymptomNotification(symptomModel);
            resCall.enqueue(new Callback<SymptomsRes2>() {
                @Override
                public void onResponse(@NotNull Call<SymptomsRes2> call, @NotNull Response<SymptomsRes2> response) {

                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            notificationInterface.onResponseSuccess(response.body().getResponseValue());
                        } else
                            Toast.makeText(App.context, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(App.context, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NotNull Call<SymptomsRes2> call, @NotNull Throwable t) {

                    Toast.makeText(App.context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void submitSymptomsRes(String toString, String memberId, ApiCallbackInterface apiCallbackInterface) {
        try {

            SymptomsModel symptomsModel = new SymptomsModel();
            symptomsModel.setMemberId(memberId);
            symptomsModel.setProblemName(toString);

            Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
            Call<SubmitProblemRes> problemResCall = iRestInterfaces.updatePatientSymptomNotification(symptomsModel);

            problemResCall.enqueue(new Callback<SubmitProblemRes>() {
                @Override
                public void onResponse(@NotNull Call<SubmitProblemRes> call, @NotNull Response<SubmitProblemRes> response) {
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            List<String> strings = new ArrayList<>();
                            strings.add("added");
                            apiCallbackInterface.onSuccess(strings);
                        } else
                            apiCallbackInterface.onError("" + response.body().getResponseCode());
                    } else
                        apiCallbackInterface.onError("" + response.errorBody());

                }

                @Override
                public void onFailure(@NotNull Call<SubmitProblemRes> call, @NotNull Throwable t) {

                    apiCallbackInterface.onFailed(t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getAllAddress(FragmentActivity activity, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        PharmacyModel pharmacyModel = new PharmacyModel();
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        Call<AddressRes> call = iRestInterfaces.getadd2(pharmacyModel);
        call.enqueue(new Callback<AddressRes>() {
            @Override
            public void onResponse(@NotNull Call<AddressRes> call, @NotNull Response<AddressRes> response) {
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddressRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });


    }

    public static void getLabTimeSlots(LabSlotModel model, ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getLabApisRef();
        Call<LabSlotsRes> call = iRestInterfaces.getLabTimeSlots(model);
        call.enqueue(new Callback<LabSlotsRes>() {
            @Override
            public void onResponse(@NotNull Call<LabSlotsRes> call, @NotNull Response<LabSlotsRes> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<LabSlotsRes> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void loadHospitalAndPackage(ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<HospitalAndPackageResponse2> call = iRestInterfaces.loadHospitalAndPackage();
        call.enqueue(new Callback<HospitalAndPackageResponse2>() {
            @Override
            public void onResponse(@NotNull Call<HospitalAndPackageResponse2> call, @NotNull Response<HospitalAndPackageResponse2> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<HospitalAndPackageResponse2> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void sendHomeIsolationRequest(HomeIsolationReqModel homeIsolationReqModel, ApiCallbackInterface apiCallbackInterface) {

        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<ResponseModel> call = iRestInterfaces.sendHomeIsolationRequest(homeIsolationReqModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        List<String> list = new ArrayList<>();
                        apiCallbackInterface.onSuccess(list);
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void isolationData(User user, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<IsolationResponse> call = iRestInterfaces.getIsolationReqData(user);
        call.enqueue(new Callback<IsolationResponse>() {
            @Override
            public void onResponse(@NotNull Call<IsolationResponse> call, @NotNull Response<IsolationResponse> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<IsolationResponse> call, @NotNull Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });
    }

    public static void MedicineReminderList(FragmentActivity fragmentActivity, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        User user = new User();
        user.setMemberId(getUserForBooking(fragmentActivity).getMemberId());
        Call<MedicineReminderResponse> call = iRestInterfaces.getMedicineReminderList(user);
        call.enqueue(new Callback<MedicineReminderResponse>() {
            @Override
            public void onResponse(@NotNull Call<MedicineReminderResponse> call, @NotNull Response<MedicineReminderResponse> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<MedicineReminderResponse> call, @NotNull Throwable t) {
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }

    public static void updateAlarm(AddMedicineReminderBottomFragment.UpdateAlarmModel alarmModel, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<ResponseModel> call = iRestInterfaces.updateAlarm(alarmModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }

    public static void getData(ApiRequestModel requestModel, ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<RecordingResponse> call = iRestInterfaces.getRecordingData(requestModel);
        call.enqueue(new Callback<RecordingResponse>() {
            @Override
            public void onResponse(@NotNull Call<RecordingResponse> call, @NotNull Response<RecordingResponse> response) {
                if (response.isSuccessful() && null != response.body()) {
                    if (response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else apiCallbackInterface.onError(response.body().getResponseMessage());
                } else apiCallbackInterface.onError("error : " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<RecordingResponse> call, @NotNull Throwable t) {
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }
}
