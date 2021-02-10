package com.digidoctor.android.utility;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.addproductratingresponse;
import com.digidoctor.android.model.addproductreating;
import com.digidoctor.android.model.labmodel.labdashboardresponse;
import com.digidoctor.android.model.labmodel.labmodel;
import com.digidoctor.android.model.patientModel.CheckLoginRes;
import com.digidoctor.android.model.patientModel.CheckSlotAvailabilityRes;
import com.digidoctor.android.model.patientModel.CheckTimeSlotModel;
import com.digidoctor.android.model.patientModel.DashBoardRes;
import com.digidoctor.android.model.patientModel.Dashboard;
import com.digidoctor.android.model.patientModel.DocBySpecialityRes;
import com.digidoctor.android.model.patientModel.DocBySymptomsRes;
import com.digidoctor.android.model.patientModel.GenerateOtpModel;
import com.digidoctor.android.model.patientModel.GenerateOtpRes;
import com.digidoctor.android.model.patientModel.GetAppointmentSlotsRes;
import com.digidoctor.android.model.patientModel.GetMembersRes;
import com.digidoctor.android.model.patientModel.GetOrderRes;
import com.digidoctor.android.model.patientModel.GetPatientMedicationMainModel;
import com.digidoctor.android.model.patientModel.GetPatientMedicationRes;
import com.digidoctor.android.model.patientModel.Login;
import com.digidoctor.android.model.patientModel.MemberModel;
import com.digidoctor.android.model.patientModel.OnlineAppointmentSlots;
import com.digidoctor.android.model.patientModel.Registration;
import com.digidoctor.android.model.patientModel.RegistrationRes;
import com.digidoctor.android.model.patientModel.ResponseModel;
import com.digidoctor.android.model.patientModel.SpecialityModel;
import com.digidoctor.android.model.patientModel.SpecialityRes;
import com.digidoctor.android.model.patientModel.SymptomModel;
import com.digidoctor.android.model.patientModel.SymptomsRes;
import com.digidoctor.android.model.patientModel.TransactionModel;
import com.digidoctor.android.model.patientModel.User;
import com.digidoctor.android.model.pharmacyModel.AddAdressModel;
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
import com.digidoctor.android.view.activity.PatientDashboard;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import static com.digidoctor.android.utility.utils.isNetworkConnected;


public class ApiUtils {

    public static void getPatientDasboard(Dashboard dashboard,
                                          final ApiCallbackInterface apiCallbackInterface) {

        try {
            final Api api = URLUtils.getAPIServiceNewAPI();
            Call<DashBoardRes> dashBoardResCall = api.patientDasboard(dashboard);

            dashBoardResCall.enqueue(new Callback<DashBoardRes>() {
                @Override
                public void onResponse(Call<DashBoardRes> call, Response<DashBoardRes> response) {
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else {
                            apiCallbackInterface.onError(response.body().getResponseMessage());

                            if (isNetworkConnected(PatientDashboard.getInstance())) {

                            } else {
                                AppUtils.showToastSort(PatientDashboard.getInstance(), PatientDashboard.getInstance().getResources().getString(R.string.noInternetConnection));
                            }
                        }
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<DashBoardRes> call, Throwable t) {
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
                public void onResponse(Call<GetAppointmentSlotsRes> call, Response<GetAppointmentSlotsRes> response) {
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<GetAppointmentSlotsRes> call, Throwable t) {
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
            final Api api = URLUtils.getAPIService();
            Call<DocBySymptomsRes> specialityResCall = api.getDoctorProfileBySymptom(symptomModel);
            specialityResCall.enqueue(new Callback<DocBySymptomsRes>() {
                @Override
                public void onResponse(@NotNull Call<DocBySymptomsRes> call, @NotNull Response<DocBySymptomsRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
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
        Api iRestInterfaces = URLUtils.getAPIService();
        Call<RegistrationRes> call = iRestInterfaces.cancelAppointment(
                getString(TOKEN, activity),
                user.getMobileNo(),
                appointmentId);

        call.enqueue(new Callback<RegistrationRes>() {
            @Override
            public void onResponse(Call<RegistrationRes> call, Response<RegistrationRes> response) {
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
            public void onFailure(Call<RegistrationRes> call, Throwable t) {
                AppUtils.hideDialog();
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
                apiCallbackInterface.onFailed(t);

            }
        });
    }

    public static void getOTP(GenerateOtpModel generateOtpModel, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

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
            public void onResponse(Call<GetMembersRes> call, Response<GetMembersRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    AppUtils.showToastSort(activity, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<GetMembersRes> call, Throwable t) {
                AppUtils.hideDialog();
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

        Api iRestInterfaces = URLUtils.getAPIService();
        final Call<CheckLoginRes> register = iRestInterfaces.patientRegistration(registration);

        register.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(Call<CheckLoginRes> call, Response<CheckLoginRes> response) {
                AppUtils.hideDialog();
                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
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
                        AppUtils.showToastSort(activity, response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<CheckLoginRes> call, Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }


    public static void checkTimeSlotAvailability(Map<String, String> map, Activity activity, final ApiCallbackInterface apiCallbackInterface) {
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


    public static void getPatientMedicationDetails(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();

        GetPatientMedicationMainModel model = new GetPatientMedicationMainModel();
        model.setMemberId("207549");

        Call<GetPatientMedicationRes> call = iRestInterfaces.getPatientMedicationDetails(model);

        call.enqueue(new Callback<GetPatientMedicationRes>() {
            @Override
            public void onResponse(Call<GetPatientMedicationRes> call, Response<GetPatientMedicationRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();

                    if (activity != null)
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {

                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<GetPatientMedicationRes> call, Throwable t) {
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
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
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
                }

            }

            @Override
            public void onFailure(@NotNull Call<RegistrationRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


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

    public static void getProductdetailsbyProductID(ProductModel pId, Activity requireActivity, final ApiCallbackInterface apiCallbackInterface) throws JSONException {


        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(requireActivity);

//
//        model.setProductId(Integer.parseInt(pId));
//        model.setSizeId(model.getSizeId());
//        //  model.setSizeId(sizeId);


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

        AddAdressModel addAdressModel = new AddAdressModel();
        addAdressModel.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));

        addAdressModel.setName(map.get("Full_name"));
        addAdressModel.setMobileno(map.get("Mobile"));
        addAdressModel.setHouseNo(map.get("House"));
        addAdressModel.setPincode(map.get("ZipCode"));
        addAdressModel.setCity(map.get("City"));
        addAdressModel.setState(map.get("State"));
        addAdressModel.setArea(map.get("area"));
        addAdressModel.setLocality(map.get("locality"));
        addAdressModel.setIsDefault(map.get("isDefault"));
        addAdressModel.setIsSundayOpen(map.get("setIsSundayOpen"));
        addAdressModel.setIsSaturdayOpen(map.get("setIsSaturdayOpen"));
        addAdressModel.setAddressType("22");

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<AddAdressResponse> call = iRestInterfaces.AddAddress(addAdressModel);

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

    public static void update_Address(AddAdressModel addAdressModel, Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);


        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<getaddressModel> call = iRestInterfaces.updateaddress(addAdressModel);

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
        ;
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
            public void onFailure(Call<OrderDetailModel> call, Throwable t) {
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
            public void onResponse(Call<getfilltervarentmodel> call, Response<getfilltervarentmodel> response) {
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

        addproductreating addproductreating = new addproductreating();
        addproductreating.setMemberId(String.valueOf(getPrimaryUser(activity).getMemberId()));
        addproductreating.setProductInfoCode(map.get("pinfo"));
        addproductreating.setReview(map.get("review"));
        addproductreating.setStarrating(map.get("rating"));

        Api iRestInterfaces = URLUtils.getPharmacyApisRef();
        Call<addproductratingresponse> call = iRestInterfaces.postproductrating(addproductreating);

        call.enqueue(new Callback<addproductratingresponse>() {
            @Override
            public void onResponse(@NotNull Call<addproductratingresponse> call, @NotNull Response<addproductratingresponse> response) {

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
            public void onFailure(@NotNull Call<addproductratingresponse> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void getlabdash(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        Api iRestInterfaces = URLUtils.getlabapisRef();
        labmodel labmodel = new labmodel();
        labmodel.setMemberId("221261");

        Call<labdashboardresponse> call = iRestInterfaces.getlabdashboard(labmodel);
        call.enqueue(new Callback<labdashboardresponse>() {
            @Override
            public void onResponse(@NotNull Call<labdashboardresponse> call, Response<labdashboardresponse> response) {
                if (response.code() == 200)
                    if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                        apiCallbackInterface.onSuccess(response.body().getResponseValue());
                    } else {
                        apiCallbackInterface.onError(response.message());
                    }
                else apiCallbackInterface.onError(String.valueOf(response.code()));

            }

            @Override
            public void onFailure(@NotNull Call<labdashboardresponse> call, Throwable t) {
                apiCallbackInterface.onFailed(t);
            }
        });
    }


}