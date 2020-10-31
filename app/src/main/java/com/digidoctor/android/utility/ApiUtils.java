package com.digidoctor.android.utility;

import android.app.Activity;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.model.CheckLoginRes;
import com.digidoctor.android.model.DashBoardRes;
import com.digidoctor.android.model.DocBySpecialityRes;
import com.digidoctor.android.model.DocBySymptomsRes;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.GenerateOtpRes;
import com.digidoctor.android.model.GetAppointmentSlotsRes;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.fcmToken;
import static com.digidoctor.android.utility.utils.isNetworkConnected;


public class ApiUtils {

    public static void getPatientDasboard(String lat, String lon, String userMobileNumber, final ApiCallbackInterface apiCallbackInterface) {

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


    public static void specialityData(final ApiCallbackInterface apiCallbackInterface) {
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIService();
            Call<SpecialityRes> specialityResCall = api.getSpeciality("");
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


    public static void getRecommendedDoc(String id, String docName, final ApiCallbackInterface apiCallbackInterface) {
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIService();
            Call<DocBySymptomsRes> specialityResCall = api.getDoctorProfileBySymptom(
                    "",
                    "",
                    id, docName);
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


    public static void getSymptomWithIconsData(final ApiCallbackInterface apiCallbackInterface) {
        try {
            if (PatientDashboard.getInstance() != null)
                AppUtils.showRequestDialog(PatientDashboard.getInstance());
            final Api api = URLUtils.getAPIService();
            Call<SymptomsRes> specialityResCall = api.getProblemsWithIcon(
                    "",
                    "9044865611",
                    "0");
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


    public static void cancelAppointment(String appointmentId) {
        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(PatientDashboard.getInstance());

        Api iRestInterfaces = URLUtils.getAPIService();
        Call<RegistrationRes> call = iRestInterfaces.cancelAppointment(
                "",
                "9044865611",
                appointmentId);

        call.enqueue(new Callback<RegistrationRes>() {
            @Override
            public void onResponse(Call<RegistrationRes> call, Response<RegistrationRes> response) {

                if (response.isSuccessful() && response.body().getResponseCode() == 1) {
                    AppUtils.hideDialog();
                    if (PatientDashboard.getInstance() != null)
                        AppUtils.showToastSort(PatientDashboard.getInstance(), PatientDashboard.getInstance().getString(R.string.appointment_canceled));

                } else {

                    AppUtils.hideDialog();
                    if (PatientDashboard.getInstance() != null)
                        AppUtils.showToastSort(PatientDashboard.getInstance(), response.body().getResponseMessage());
                }

            }

            @Override
            public void onFailure(Call<RegistrationRes> call, Throwable t) {
                AppUtils.hideDialog();
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


    public static void checkLogin(String mobileNo,
                                  String otp,
                                  final Activity activity,
                                  final ApiCallbackInterface apiCallbackInterface) {


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

                        Toast.makeText(activity, response.body().getResponseMessage(), Toast.LENGTH_SHORT).show();

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


}
