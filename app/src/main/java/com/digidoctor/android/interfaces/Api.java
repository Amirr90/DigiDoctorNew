package com.digidoctor.android.interfaces;

import com.digidoctor.android.model.AddInvestigationModel;
import com.digidoctor.android.model.AppointmentRes;
import com.digidoctor.android.model.CheckLoginRes;
import com.digidoctor.android.model.CheckSlotAvailabilityRes;
import com.digidoctor.android.model.CheckTimeSlotModel;
import com.digidoctor.android.model.DashBoardRes;
import com.digidoctor.android.model.Dashboard;
import com.digidoctor.android.model.DemoResponse;
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
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.digidoctor.android.model.OnlineAppointmentSlots;
import com.digidoctor.android.model.PayModeModel;
import com.digidoctor.android.model.PrescriptionModel;
import com.digidoctor.android.model.Registration;
import com.digidoctor.android.model.RegistrationRes;
import com.digidoctor.android.model.Response;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.SaveMultipleFileRes;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.model.SpecialityRes;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.SymptomsRes;
import com.digidoctor.android.model.TransactionModel;
import com.digidoctor.android.model.UploadPresDataModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.utility.BookAppointment2;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Api {

    @POST("patientDasboard")
    Call<DashBoardRes> patientDasboard(
            @Body Dashboard dashboard);


    @POST("getDoctorProfileBySpeciality")
    Call<DocBySpecialityRes> getDoctorProfileBySpeciality(
            @Body SpecialityModel specialityModel
    );


    @POST("getAllDepartment")
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

    @POST("getPatientMedicationDetails")
    Call<DemoResponse> getPatientMedicationDetails2(
            @Body GetPatientMedicationMainModel model);

    @POST("updateMember")
    Call<CheckLoginRes> updateMember(
            @Body User model);


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


    @POST("addVital")
    Call<ResponseModel> addVitals(
            @Body VitalModel model);

    @POST("getPatientVitalList")
    Call<VitalResponse> getVitals(
            @Body VitalModel model);

    @POST("getpatientInvestigationDetails")
    Call<InvestigationRes> getInvestigationData(
            @Body User model);

    @POST("getPatientAppointmentList")
    Call<AppointmentRes> getAppointmentData(
            @Body User model);

    @POST("patientMedication")
    Call<VitalResponse> addPrescription(
            @Body PrescriptionModel model);

    @POST("patientInvestigation")
    Call<VitalResponse> addInvestigation(
            @Body AddInvestigationModel model);

    @POST("saveAttachmentAfterBooking")
    Call<SaveMultipleFileRes> saveAttachmentAfterBooking(
            @Body UploadPresDataModel model);

    @POST("logout")
    Call<LogoutModel> logout(
            @Body LogoutModel model);

    @POST("getPaymentMode")
    Call<Response> getPaymentMode(
            @Body PayModeModel model);


    @Multipart
    @POST("saveMultipleFile")
    Call<SaveMultipleFileRes> uploadImage(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part[] multipleFile


    );

    @POST("getAllMedicationDataList")
    Call<MedicineRes> getMedicationData();

    @POST("deleteMember")
    Call<CheckLoginRes> deleteMember(@Body User user);

    @POST("getPatientInvestigationOnloadDetails")
    Call<InvestigationDataRes> investigationData(@Body User model);


}


