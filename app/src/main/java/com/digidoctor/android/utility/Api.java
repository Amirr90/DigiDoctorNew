package com.digidoctor.android.utility;

import com.digidoctor.android.model.CheckLoginRes;
import com.digidoctor.android.model.DashBoardRes;
import com.digidoctor.android.model.DocBySpecialityRes;
import com.digidoctor.android.model.DocBySymptomsRes;
import com.digidoctor.android.model.GenerateOtpRes;
import com.digidoctor.android.model.GetAppointmentSlotsRes;
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomsRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("patientDasboard")
    Call<DashBoardRes> patientDasboard(
            @Field("Lat") String lat,
            @Field("Long") String lon);

    @FormUrlEncoded
    @POST("getDoctorProfileBySpeciality")
    Call<DocBySpecialityRes> getDoctorProfileBySpeciality(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobile,
            @Field("specialityId") String id,
            @Field("doctorName") String doctorName
    );

    @FormUrlEncoded
    @POST("getSpeciality")
    Call<SpecialityRes> getSpeciality(@Field("userMobileNo") String memberId);

    @FormUrlEncoded
    @POST("getDoctorProfileBySymptom")
    Call<DocBySymptomsRes> getDoctorProfileBySymptom(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobile,
            @Field("symptomId") String id,
            @Field("doctorName") String doctorName
    );

    @FormUrlEncoded
    @POST("getProblemsWithIcon")
    Call<SymptomsRes> getProblemsWithIcon(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobile,
            @Field("specialityId") String id);


    @FormUrlEncoded
    @POST("getOnlineAppointmentSlots")
    Call<GetAppointmentSlotsRes> getOnlineAppointmentSlots(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobileNo,
            @Field("serviceProviderDetailsId") String serviceProviderDetailsId,
            @Field("appointDate") String appointDate,
            @Field("isEraUser") String isEraUser
    );

    @FormUrlEncoded
    @POST("cancelAppointment")
    Call<RegistrationRes> cancelAppointment(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobileNo,
            @Field("appointmentId") String appointmentId
    );


    @FormUrlEncoded
    @POST("onlineAppointment")
    Call<OnlineAppointmentRes> onlineAppointment(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobileNo,
            @Field("memberId") String memberId,
            @Field("patientName") String patientName,
            @Field("mobileNo") String mobileNo,
            @Field("guardianTypeId") String guardianTypeId,
            @Field("guardianName") String guardianName,
            @Field("stateId") String stateId,
            @Field("cityId") String cityId,
            @Field("address") String address,
            @Field("pincode") String pincode,
            @Field("serviceProviderDetailsId") String serviceProviderDetailsId,
            @Field("appointDate") String appointDate,
            @Field("appointTime") String appointTime,
            @Field("isEraUser") String isEraUser,
            @Field("problemName") String problemName,
            @Field("dtDataTable") String dtDataTable,
            @Field("appointmentId") String appointmentId,
            @Field("dob") String dob,
            @Field("gender") String gender

    );

    @FormUrlEncoded
    @POST("generateOTPForPatient")
    Call<GenerateOtpRes> generateOTPForPatient(
            @Field("mobileNo") String mobileNo);

    @FormUrlEncoded
    @POST("checkLogin")
    Call<CheckLoginRes> checkLogin(
            @Field("mobileNo") String mobileNo,
            @Field("password") String password,
            @Field("serviceProviderTypeId") String serviceProviderTypeId,
            @Field("deviceToken") String deviceToken,
            @Field("deviceType") String deviceType,
            @Field("otp") String otp,
            @Field("appType") String appType
    );
}
