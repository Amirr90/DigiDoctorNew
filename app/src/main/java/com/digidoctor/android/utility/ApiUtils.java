package com.digidoctor.android.utility;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.CheckLoginRes;
import com.digidoctor.android.model.CheckSlotAvailabilityRes;
import com.digidoctor.android.model.DashBoardRes;
import com.digidoctor.android.model.DocBySpecialityRes;
import com.digidoctor.android.model.DocBySymptomsRes;
import com.digidoctor.android.model.GenerateOtpRes;
import com.digidoctor.android.model.GetAppointmentSlotsRes;
import com.digidoctor.android.model.GetMembersRes;
import com.digidoctor.android.model.GetPatientMedicationRes;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.model.User;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.utils.APPOINTMENT_DATE;
import static com.digidoctor.android.utility.utils.APPOINTMENT_TIME;
import static com.digidoctor.android.utility.utils.KEY_AGE;
import static com.digidoctor.android.utility.utils.KEY_AMOUNT;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.KEY_DOB;
import static com.digidoctor.android.utility.utils.KEY_DOC_ID;
import static com.digidoctor.android.utility.utils.KEY_DOC_NAME;
import static com.digidoctor.android.utility.utils.KEY_GENDER;
import static com.digidoctor.android.utility.utils.KEY_IS_ERA_USER;
import static com.digidoctor.android.utility.utils.KEY_PATIENT_NAME;
import static com.digidoctor.android.utility.utils.KEY_SYMPTOM_ID;
import static com.digidoctor.android.utility.utils.MEMBER_ID;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.fcmToken;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.getString;
import static com.digidoctor.android.utility.utils.isNetworkConnected;
import static com.digidoctor.android.utility.utils.logout;


public class ApiUtils {

    public static void getPatientDasboard(String lat, String lon, String userMobileNumber,
                                          final ApiCallbackInterface apiCallbackInterface) {

        try {
            final Api api = URLUtils.getAPIService();
            Call<DashBoardRes> dashBoardResCall = api.patientDasboard(

                    lat, lon);

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
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIService();
            Call<SpecialityRes> specialityResCall = api.getSpeciality("", specialityName);
            specialityResCall.enqueue(new Callback<SpecialityRes>() {
                @Override
                public void onResponse(Call<SpecialityRes> call, Response<SpecialityRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<SpecialityRes> call, Throwable t) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getDocBySpecialityById(String id, String docName, final ApiCallbackInterface apiCallbackInterface) {
        try {
            final Api api = URLUtils.getAPIService();
            Call<DocBySpecialityRes> specialityResCall = api.getDoctorProfileBySpeciality(
                    "",
                    "",
                    id,
                    docName);
            specialityResCall.enqueue(new Callback<DocBySpecialityRes>() {
                @Override
                public void onResponse(Call<DocBySpecialityRes> call, Response<DocBySpecialityRes> response) {
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {

                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<DocBySpecialityRes> call, Throwable t) {
                    apiCallbackInterface.onError(t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void getDoctorsTimeSlots(String docId, String date, String isEraUser, final ApiCallbackInterface apiCallbackInterface) {
        try {

            final Api api = URLUtils.getAPIService();
            Call<GetAppointmentSlotsRes> getAppointmentSlotsResCall = api.getOnlineAppointmentSlots(
                    "",
                    "9044865611",
                    docId, date, isEraUser);
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
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIService();
            Call<DocBySymptomsRes> specialityResCall = api.getDoctorProfileBySymptom(
                    "",
                    "",
                    map.get(KEY_SYMPTOM_ID),
                    map.get(KEY_DOC_NAME),
                    map.get(KEY_DOB),
                    map.get(KEY_GENDER),
                    map.get(KEY_AGE)
            );
            specialityResCall.enqueue(new Callback<DocBySymptomsRes>() {
                @Override
                public void onResponse(Call<DocBySymptomsRes> call, Response<DocBySymptomsRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<DocBySymptomsRes> call, Throwable t) {
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
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIService();
            Call<SymptomsRes> specialityResCall = api.getProblemsWithIcon(
                    "",
                    "9044865611",
                    "0",
                    symptomName);
            specialityResCall.enqueue(new Callback<SymptomsRes>() {
                @Override
                public void onResponse(Call<SymptomsRes> call, Response<SymptomsRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<SymptomsRes> call, Throwable t) {
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

    public static void getOTP(String mobileNo, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getAPIService();
        Call<GenerateOtpRes> call = iRestInterfaces.generateOTPForPatient(mobileNo);

        call.enqueue(new Callback<GenerateOtpRes>() {
            @Override
            public void onResponse(Call<GenerateOtpRes> call, Response<GenerateOtpRes> response) {

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

            }

            @Override
            public void onFailure(Call<GenerateOtpRes> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }


    public static void GetMembersRes(final Activity activity, final ApiCallbackInterface apiCallbackInterface) {
        AppUtils.showRequestDialog(activity);

        User user = getPrimaryUser(activity);
        Api iRestInterfaces = URLUtils.getAPIService();
        Call<GetMembersRes> call = iRestInterfaces.getMembers(
                getString(TOKEN, activity),
                getString(MOBILE_NUMBER, activity),
                String.valueOf(user.getUserLoginId())
        );

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


    public static void checkLogin(String mobileNo,
                                  String otp, final Activity activity, final ApiCallbackInterface apiCallbackInterface) {


        String serviceProviderTypeId = "6";
        String deviceType = "1";
        Api iRestInterfaces = URLUtils.getAPIService();
        final Call<CheckLoginRes> checkLogin = iRestInterfaces.checkLogin(mobileNo, "", serviceProviderTypeId,
                utils.getString(fcmToken, activity),
                deviceType,
                otp,
                "DD");

        checkLogin.enqueue(new Callback<CheckLoginRes>() {
            @Override
            public void onResponse(Call<CheckLoginRes> call, Response<CheckLoginRes> response) {
                AppUtils.hideDialog();
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

            }

            @Override
            public void onFailure(Call<CheckLoginRes> call, Throwable t) {
                AppUtils.hideDialog();
                apiCallbackInterface.onError(t.getLocalizedMessage());
            }
        });
    }


    public static void patientRegistration(String mobile, String name, String email, String dob, String gender, String address,
                                           final Activity activity, final ApiCallbackInterface apiCallbackInterface) {

        String callingCodeId = "101";
        Api iRestInterfaces = URLUtils.getAPIService();
        final Call<CheckLoginRes> register = iRestInterfaces.patientRegistration(callingCodeId,
                mobile,
                email,
                name,
                gender,
                dob,
                address,
                "");

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
            Log.d("TAG", "checkTimeSlotAvailability: " + map.toString());
            final Api api = URLUtils.getAPIService();
            Call<CheckSlotAvailabilityRes> specialityResCall = api.checkTimeSlotAvailability(
                    getString(TOKEN, activity),
                    map.get(MOBILE_NUMBER),
                    map.get(MEMBER_ID),
                    map.get(KEY_DOC_ID),
                    map.get(APPOINTMENT_DATE),
                    map.get(APPOINTMENT_TIME),
                    map.get(KEY_IS_ERA_USER),
                    map.get(KEY_APPOINTMENT_ID)
            );
            specialityResCall.enqueue(new Callback<CheckSlotAvailabilityRes>() {
                @Override
                public void onResponse(Call<CheckSlotAvailabilityRes> call, Response<CheckSlotAvailabilityRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200) {
                        if (response.body().getResponseCode() == 1) {
                            apiCallbackInterface.onSuccess(response.body().getResponseValue());
                        } else apiCallbackInterface.onError(response.body().getResponseMessage());
                    } else apiCallbackInterface.onError(response.errorBody().toString());
                }

                @Override
                public void onFailure(Call<CheckSlotAvailabilityRes> call, Throwable t) {
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

        Api iRestInterfaces = URLUtils.getAPIService();
        Call<GetPatientMedicationRes> call = iRestInterfaces.getPatientMedicationDetails(
                getString(TOKEN, activity),
                getString(MOBILE_NUMBER, activity),
                // getString("id", activity)
                "207549"
        );

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

            AppUtils.showRequestDialog(activity);
            Log.d("TAG", "checkTimeSlotAvailability: " + map.toString());
            final Api api = URLUtils.getAPIService();
            Call<ResponseModel> specialityResCall = api.getBookingTransactionId(
                    map.get(KEY_AMOUNT),
                    map.get(KEY_PATIENT_NAME),
                    map.get(MEMBER_ID),
                    map.get(MOBILE_NUMBER),
                    map.get(APPOINTMENT_DATE),
                    map.get(APPOINTMENT_TIME),
                    map.get(KEY_DOC_ID),
                    map.get(KEY_IS_ERA_USER)

            );
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
        Api iRestInterfaces = URLUtils.getAPIService();
        Call<RegistrationRes> call = iRestInterfaces.addMember(
                getString(TOKEN, activity),
                getString(MOBILE_NUMBER, activity),
                String.valueOf(user.getUserLoginId()),
                map.get("name"),
                map.get("mobile"),
                map.get("gender"),
                map.get("dob"),
                map.get("profilePath"),
                "0",
                map.get("address")
        );

        call.enqueue(new Callback<RegistrationRes>() {
            @Override
            public void onResponse(Call<RegistrationRes> call, Response<RegistrationRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else {
                    AppUtils.hideDialog();
                    apiCallbackInterface.onError(response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<RegistrationRes> call, Throwable t) {
                AppUtils.hideDialog();
            }
        });
    }

}