package com.digidoctor.android.interfaces;

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
import com.digidoctor.android.model.Login;
import com.digidoctor.android.model.MemberModel;
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.digidoctor.android.model.OnlineAppointmentSlots;
import com.digidoctor.android.model.Registration;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.model.TransactionModel;
import com.digidoctor.android.utility.BookAppointment;
import com.digidoctor.android.utility.BookAppointment2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Api {

    @POST("patientDasboard")
    Call<DashBoardRes> patientDasboard(
            @Body Dashboard dashboard);


    @POST("getDoctorProfileBySpeciality")
    Call<DocBySpecialityRes> getDoctorProfileBySpeciality(
            @Body SpecialityModel specialityModel
    );


    @POST("getSpeciality")
    Call<SpecialityRes> getSpeciality(
            @Body SpecialityModel specialityModel);

    @POST("getDoctorProfileBySymptom")
    Call<DocBySymptomsRes> getDoctorProfileBySymptom(
            @Body SymptomModel symptomModel);


    @POST("getProblemsWithIcon")
    Call<SymptomsRes> getProblemsWithIcon(
            @Body SymptomModel symptomModel);


    @POST("getOnlineAppointmentSlots")
    Call<GetAppointmentSlotsRes> getOnlineAppointmentSlots(
            @Body OnlineAppointmentSlots slots);

    @FormUrlEncoded
    @POST("cancelAppointment")
    Call<RegistrationRes> cancelAppointment(
            @Header("x-access-token") String token,
            @Field("userMobileNo") String userMobileNo,
            @Field("appointmentId") String appointmentId
    );


   /* @FormUrlEncoded
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
            @Field("dtPaymentTable") String dtDataTable,
            @Field("appointmentId") String appointmentId,
            @Field("dob") String dob,
            @Field("gender") String gender,
            @Field("paymentId") String paymentId

    );
*/

    @POST("onlineAppointment")
    Call<OnlineAppointmentRes> onlineAppointment2(@Body BookAppointment2 appointment);

    @POST("generateOTPForPatient")
    Call<GenerateOtpRes> generateOTPForPatient(
            @Body GenerateOtpModel otpModel);

    @POST("checkLogin")
    Call<CheckLoginRes> checkLogin(
            @Body Login login);


    @POST("patientRegistration")
    Call<CheckLoginRes> patientRegistration(
            @Body Registration registration);

    @POST("getPatientMedicationDetails")
    Call<GetPatientMedicationRes> getPatientMedicationDetails(
            @Body GetPatientMedicationMainModel model);


    @POST("checkTimeSlotAvailability")
    Call<CheckSlotAvailabilityRes> checkTimeSlotAvailability(
            @Body CheckTimeSlotModel model);


    @POST("getTransactionNo")
    Call<ResponseModel> getBookingTransactionId(
            @Body TransactionModel transactionModel);


    @POST("getMembers")
    Call<GetMembersRes> getMembers(
            @Body MemberModel memberModel);

    @POST("addMember")
    Call<RegistrationRes> addMember(
            @Body MemberModel memberModel);


}


