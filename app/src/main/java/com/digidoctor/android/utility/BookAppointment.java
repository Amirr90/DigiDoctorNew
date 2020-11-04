package com.digidoctor.android.utility;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_CASH;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_RAZOR_PAY;

public class BookAppointment {

    private static final String TAG = "BookAppointment";

    private String userMobileNo;
    private String memberId;
    private String patientName;
    private String mobileNo;
    private String guardianTypeId;
    private String guardianName;
    private String stateId;
    private String cityId;
    private String address;
    private String pincode;
    private String serviceProviderDetailsId;
    private String appointDate;
    private String appointTime;
    private String isEraUser;
    private String problemName;
    private String dtDataTable;
    private String appointmentId;
    private String dob;
    private String gender;
    private String token;
    private String email;
    private String drFee;
    private String paymentId;
    Activity activity;
    BookAppointmentInterface bookAppointmentInterface;


    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrFee() {
        return drFee;
    }

    public void setDrFee(String drFee) {
        this.drFee = drFee;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BookAppointment(Activity activity) {
        this.activity = activity;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getGuardianTypeId() {
        if (guardianTypeId == null)
            return "0";
        else
            return guardianTypeId;
    }

    public String getGuardianName() {
        if (guardianName == null)
            return "";
        else return guardianName;
    }

    public String getStateId() {
        if (stateId == null)
            return "0";
        else
            return stateId;
    }

    public String getCityId() {
        if (cityId == null)
            return "0";
        else
            return cityId;
    }

    public String getAddress() {
        if (address == null)
            return "";
        else
            return address;
    }

    public String getPincode() {
        if (pincode == null)
            return "0";
        else
            return pincode;
    }

    public String getServiceProviderDetailsId() {
        return serviceProviderDetailsId;
    }

    @NotNull
    public String getAppointDate() {
        return appointDate;
    }

    public String getAppointTime() {
        return appointTime;
    }

    public String getIsEraUser() {
        return isEraUser;
    }

    public String getProblemName() {
        return problemName;
    }

    public String getDtDataTable() {
        if (dtDataTable == null)
            return "";
        else
            return dtDataTable;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDob() {
        if (dob == null)
            return "1997-12-20";
        else
            return dob;
    }

    public String getGender() {
        if (gender == null)
            return "1";
        else
            return gender;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setGuardianTypeId(String guardianTypeId) {
        this.guardianTypeId = guardianTypeId;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public void setServiceProviderDetailsId(String serviceProviderDetailsId) {
        this.serviceProviderDetailsId = serviceProviderDetailsId;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public void setIsEraUser(String isEraUser) {
        this.isEraUser = isEraUser;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public void setDtDataTable(String dtDataTable) {
        this.dtDataTable = dtDataTable;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void initBooking(int payMode, BookAppointmentInterface bookAppointmentInterface) {
        this.bookAppointmentInterface = bookAppointmentInterface;
        switch (payMode) {
            case PAY_MODE_CASH:
                startBookingAppointment();
                break;
            case PAY_MODE_RAZOR_PAY: {
                startRazorPayBooking();
            }
        }
    }

    private void startRazorPayBooking() {

        Checkout.preload(activity);
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_VdBuKnBx67uxF8");

        String image = "https://digidoctor.in/assets/images/logonew.png";

        try {
            JSONObject options = new JSONObject();
            options.put("name", getPatientName());
            options.put("description", "" + System.currentTimeMillis());
            options.put("image", image);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "100");//pass amount in currency subunits
            options.put("prefill.email", getEmail());
            options.put("prefill.contact", getMobileNo());
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public String toString() {
        return "BookAppointment{" +
                "userMobileNo='" + userMobileNo + '\'' +
                ", memberId='" + memberId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", guardianTypeId='" + guardianTypeId + '\'' +
                ", guardianName='" + guardianName + '\'' +
                ", stateId='" + stateId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", serviceProviderDetailsId='" + serviceProviderDetailsId + '\'' +
                ", appointDate='" + appointDate + '\'' +
                ", appointTime='" + appointTime + '\'' +
                ", isEraUser='" + isEraUser + '\'' +
                ", problemName='" + problemName + '\'' +
                ", dtDataTable='" + dtDataTable + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", activity=" + activity +
                '}';
    }

    public void startBookingAppointment() {

        AppUtils.showRequestDialog(activity);
        Api iRestInterfaces = URLUtils.getAPIService();
        Call<OnlineAppointmentRes> call = iRestInterfaces.onlineAppointment(
                getToken(),
                getUserMobileNo(),
                getMemberId(),
                getPatientName(),
                getMobileNo(),
                getGuardianTypeId(),
                getGuardianName(),
                getStateId(),
                getCityId(),
                getAddress(),
                getPincode(),
                getServiceProviderDetailsId(),
                getAppointDate(),
                getAppointTime(),
                getIsEraUser(),
                getProblemName(),
                getDtDataTable(),
                getAppointmentId(),
                getDob(),
                getGender(),
                getPaymentId()
        );
        call.enqueue(new Callback<OnlineAppointmentRes>() {
            @Override
            public void onResponse(Call<OnlineAppointmentRes> call, Response<OnlineAppointmentRes> response) {
                AppUtils.hideDialog();
                if (response.code() == 200) {
                    if (response.body().getResponseCode() == 1) {
                        bookAppointmentInterface.onAppointmentBooked(response.body().getResponseValue().get(0));
                    } else bookAppointmentInterface.onError(response.body().getResponseMessage());
                } else bookAppointmentInterface.onError(response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<OnlineAppointmentRes> call, Throwable t) {
                AppUtils.hideDialog();
                bookAppointmentInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }


}
