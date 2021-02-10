package com.digidoctor.android.interfaces;

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
import com.digidoctor.android.model.patientModel.OnlineAppointmentRes;
import com.digidoctor.android.model.patientModel.OnlineAppointmentSlots;
import com.digidoctor.android.model.patientModel.Registration;
import com.digidoctor.android.model.patientModel.RegistrationRes;
import com.digidoctor.android.model.patientModel.ResponseModel;
import com.digidoctor.android.model.patientModel.SpecialityModel;
import com.digidoctor.android.model.patientModel.SpecialityRes;
import com.digidoctor.android.model.patientModel.SymptomModel;
import com.digidoctor.android.model.patientModel.SymptomsRes;
import com.digidoctor.android.model.patientModel.TransactionModel;
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


