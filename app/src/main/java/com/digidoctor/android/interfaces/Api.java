package com.digidoctor.android.interfaces;

import com.digidoctor.android.model.AddInvestigationModel;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.AppointmentRes;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.model.ChatResponse;
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
import com.digidoctor.android.model.GetOrderRes;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.GetPatientMedicationRes;
import com.digidoctor.android.model.InvestigationDataRes;
import com.digidoctor.android.model.InvestigationRes;
import com.digidoctor.android.model.Login;
import com.digidoctor.android.model.LogoutModel;
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
import com.digidoctor.android.model.addproductratingresponse;
import com.digidoctor.android.model.addproductreating;
import com.digidoctor.android.model.labmodel.labdashboardresponse;
import com.digidoctor.android.model.labmodel.labmodel;
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

    @POST("revisitAppointment")
    Call<OnlineAppointmentRes> revisitAppointment(@Body BookAppointment2 appointment);


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
    Call<DemoResponse> getPatientMedicationDetails2(@Body GetPatientMedicationMainModel model);

    @POST("patientDoctorChatting")
    Call<ChatResponse> sendMsg(@Body ChatModel model);

    @POST("getPatientDoctorChatting")
    Call<ChatResponse> getMsg(@Body AppointmentModel model);

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


    //Pharmacy


    @POST("patientDasboard")
    Call<shopbycategoryRes> getPharmacyDashboard(
            @Body PharmacyModel pharmacyModel);


    @POST("getAllProducts")
    Call<GetAllProductResponse> getallpr(
            @Body PharmacyModel pharmacyModel);


    @POST("getProductDetails")
    Call<ProductDetailModelResponse> getproductdetails(
            @Body ProductModel model);


    @POST("cartDetails")
    Call<CartDetailsResponse> getcartdetails(
            @Body PharmacyModel pharmacyModel);


    @POST("addToCart")
    Call<CartDetailsResponse> addcart(
            @Body AddToCartModel addToCartModel);

    @POST("deleteCartItem")
    Call<CartDetailsResponse> deleteItems(
            @Body DeleteItems models);

    @POST("addAddress")
    Call<AddAdressResponse> AddAddress(
            @Body AddAdressModel addAdressModel);


    @POST("deleteAddress")
    Call<getaddressModel> DeleteAddress(@Body DeleteAddress deleteAddress);

    @POST("getAddress")
    Call<getaddressModel> getadd(
            @Body PharmacyModel pharmacyModel);

    @POST("couponDetails")
    Call<AllCoupneModelResponse> getCoupnedetails(@Body PharmacyModel pharmacyModel);

    @POST("getWhislistProducts")
    Call<AllWishListProduct> getwishlist(@Body PharmacyModel pharmacyModel);


    @POST("assignProductAsWhislist")
    Call<AllWishListProduct> AddAswishlist(@Body AddtoWishlist addtoWishlist);


    @POST("cartCount")
    Call<CartCount> getcartcount(@Body PharmacyModel pharmacyModel);


    @POST("placeOrder")
    Call<OrderPlaceModelResponse> orderplace(@Body OrderPlaceModel ORDER_PLACE_MODEL);

    @POST("getOrders")
    Call<GetOrderRes> getorder(@Body PharmacyModel pharmacyModel);


    @POST("getOrderDetails")
    Call<OrderDetailModel> orderdetails(@Body Order order);

    @POST("validateCoupon")
    Call<CoupnemodelRes> coupnevalidation(@Body CouponModel couponModel);


    @POST("updateAddress")
    Call<getaddressModel> updateaddress(@Body AddAdressModel pharmacyModel);


    @POST("cancelOrder")
    Call<OrderDetailModel> cancelorder(@Body Order order);

    @POST("getFilterVarients")
    Call<getfilltervarentmodel> getFillterVarient(@Body Fillter fillter);

    @POST("productRating")
    Call<addproductratingresponse> postproductrating(@Body addproductreating addproductreating);


    @POST("Lab/labDasboard")
    Call<labdashboardresponse> getlabdashboard(
            @Body labmodel labmodel);



}


