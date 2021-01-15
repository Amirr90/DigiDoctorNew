package com.digidoctor.android.utility;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.BookAppointmentInterface;
import com.digidoctor.android.model.CheckSlotAvailabilityDataRes;
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.base.models.PaymentMode;
import com.payu.base.models.PaymentType;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.razorpay.Checkout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.ApiUtils.checkTimeSlotAvailability;
import static com.digidoctor.android.utility.ApiUtils.getTransactionNo;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_PAY_ON_VISIT;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_PAY_U_MONEY;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_RAZOR_PAYY;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_CASH;
import static com.digidoctor.android.utility.utils.APPOINTMENT_DATE;
import static com.digidoctor.android.utility.utils.APPOINTMENT_TIME;
import static com.digidoctor.android.utility.utils.KEY_AMOUNT;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.KEY_DOC_ID;
import static com.digidoctor.android.utility.utils.KEY_IS_ERA_USER;
import static com.digidoctor.android.utility.utils.KEY_PATIENT_NAME;
import static com.digidoctor.android.utility.utils.MEMBER_ID;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.logout;
import static com.digidoctor.android.view.fragments.digiDoctorFragments.BookAppointmentFragment.bookAppointment;
import static com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_NAME;
import static com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_STRING;

public class BookAppointment extends Credentials {

    private static final String TAG = "BookAppointment";
    private String MERCHANT_KEY = "y7cBem";
    private String SALT = "PxXF1pqL";

    private Integer payMode;

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
    private String dtPaymentTable;
    private String appointmentId;
    private String dob;
    private String gender;
    private String token;
    private String email;
    private String drFee;
    private String paymentId;
    private String trxId;
    private String paymentMode;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    Activity activity;
    BookAppointmentInterface bookAppointmentInterface;


    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

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
        if (null == patientName)
            return "";
        else return patientName;
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

    public String getDtPaymentTable() {
        if (dtPaymentTable == null)
            return "";
        else
            return dtPaymentTable;
    }

    public String getAppointmentId() {
        if (null == appointmentId)
            return "";
        else
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

    public void setDtPaymentTable(String dtPaymentTable) {
        this.dtPaymentTable = dtPaymentTable;
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


    public void initBooking(final int payMode, final BookAppointmentInterface bookAppointmentInterface) {
        this.bookAppointmentInterface = bookAppointmentInterface;

        setPayMode(payMode);
        setPaymentMode(getPaymentmode(payMode));
        //CheckTimeSlot Availability
        final Map<String, String> map = new HashMap<>();
        map.put(MOBILE_NUMBER, getUserMobileNo());
        map.put(MEMBER_ID, getMemberId());
        map.put(KEY_DOC_ID, getServiceProviderDetailsId());
        map.put(APPOINTMENT_DATE, getAppointDate());
        map.put(APPOINTMENT_TIME, getAppointTime());
        map.put(KEY_IS_ERA_USER, getIsEraUser());
        map.put(KEY_APPOINTMENT_ID, getAppointmentId());

        checkTimeSlotAvailability(map, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {
                Log.d(TAG, "onSuccess: " + obj);

                List<CheckSlotAvailabilityDataRes> response = (List<CheckSlotAvailabilityDataRes>) obj;
                if (response != null) {

                    if (response.get(0).getIsAvailable() == 1) {
                        if (payMode == PAY_MODE_CASH) {
                            startBookingAppointment(null);
                        } else getTrxId(map, payMode);
                    } else {
                        bookAppointmentInterface.onFailed(activity.getString(R.string.slot_not_available));
                    }
                }
            }

            @Override
            public void onError(String s) {
                Log.d(TAG, "onError: " + s);
                bookAppointmentInterface.onFailed(s);
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                bookAppointmentInterface.onFailed(throwable.getLocalizedMessage());
            }
        });


    }

    private String getPaymentmode(int payMode) {
        if (payMode == PAY_MODE_PAY_ON_VISIT)
            return "PayOnVisit";
        else if (payMode == PAY_MODE_PAY_U_MONEY)
            return "payUMoney";
        else if (payMode == PAY_MODE_RAZOR_PAYY)
            return "RazorPay";
        else return "";
    }


    private void getTrxId(Map<String, String> map, int payMode) {

        map.put(KEY_PATIENT_NAME, getPatientName());
        map.put(KEY_AMOUNT, getDrFee());

        getTransactionNo(map, activity, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<ResponseModel.HashModel> models = (List<ResponseModel.HashModel>) o;
                if (null != models) {
                    String tId = models.get(0).getTaxId();
                    setTrxId(tId);
                    utils.setString("txid", tId, activity);
                    if (payMode == PAY_MODE_PAY_U_MONEY)
                        startPayUBizPayment();
                    else
                        startRazorPayBooking(tId);
                }

            }

            @Override
            public void onError(String s) {
                bookAppointmentInterface.onFailed(s);
                try {
                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                        logout(PatientDashboard.getInstance(), true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                bookAppointmentInterface.onFailed(throwable.getLocalizedMessage());

            }
        });
    }

    private void startPayUBizPayment() {
        Log.d(TAG, "initPayUBizPayment: ");
        initPayUPayment(bookAppointmentInterface);

    }

    private void startRazorPayBooking(String tId) {

        Checkout.preload(activity);
        Checkout checkout = new Checkout();
        // checkout.setKeyID("rzp_test_41Dk0t9QjLuFZl");

        checkout.setKeyID("rzp_live_BwhTaXRxeklaAI");
        bookAppointment.setTrxId(tId);
        String image = "https://digidoctor.in/assets/images/logonew.png";

        int amount = Integer.parseInt(getDrFee()) * 100;
        try {
            JSONObject options = new JSONObject();
            options.put("name", getPatientName());
            options.put("description", "" + System.currentTimeMillis());
            options.put("image", image);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "" + amount);//pass amount in currency subunits
            options.put("prefill.email", getEmail());
            options.put("prefill.contact", getMobileNo());
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public String toString() {
        return "{" +
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
                ", dtPaymentTable='" + dtPaymentTable + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", drFee='" + drFee + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", trxId='" + trxId + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", activity=" + activity +
                ", bookAppointmentInterface=" + bookAppointmentInterface +
                '}';
    }

    public void startBookingAppointment(String rzrId) {

        Log.d(TAG, "startBookingAppointment: PayMode  " + getPayMode() + getPaymentMode());

        if (getPayMode() == PAY_MODE_PAY_U_MONEY)
            setDtPaymentTable(rzrId);
        else {
            String dtTableData = "[{\"paymentStatus\":\"success\",\"bankRefNo\":\"" + rzrId + "\",\"paymentAmount\":\"" + getDrFee() + "\",\"transactionNo\":\"" + getTrxId() + "\",\"isErauser\":" + getIsEraUser() + "}]";
            setDtPaymentTable(dtTableData);
        }


        BookAppointment2 appointment = getBookingAppointmentData();
        Log.d(TAG, "startBookingAppointment: " + appointment.toString());


        AppUtils.showRequestDialog(activity);

        Api iRestInterfaces = URLUtils.getAPIServiceNewAPI();

        Call<OnlineAppointmentRes> call = iRestInterfaces.onlineAppointment2(appointment);
        call.enqueue(new Callback<OnlineAppointmentRes>() {
            @Override
            public void onResponse(@NotNull Call<OnlineAppointmentRes> call, @NotNull Response<OnlineAppointmentRes> response) {
                AppUtils.hideDialog();
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getResponseCode() == 1) {
                        bookAppointmentInterface.onAppointmentBooked(response.body().getResponseValue().get(0));
                    } else bookAppointmentInterface.onError(response.body().getResponseMessage());
                } else
                    bookAppointmentInterface.onError(Objects.requireNonNull(response.errorBody()).toString());
            }

            @Override
            public void onFailure(@NotNull Call<OnlineAppointmentRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                bookAppointmentInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    private BookAppointment2 getBookingAppointmentData() {
        Gson gson = new Gson();
        BookAppointment appointment = this;

        BookAppointment2 appointment2 = gson.fromJson(appointment.toString(), BookAppointment2.class);
        Log.d(TAG, "getBookingAppointmentData: " + appointment2.toString());

        return appointment2;

    }


    public String getHash(String hashData) {
        String type = "SHA-512";
        //String hashSequence = MERCHANT_KEY + "|" + getTrxId() + "|" + getDrFee() + "|" + "onlineAppointment" + "|" + getPatientName() + "|" + getEmail() + "|" + "udf1|udf2|udf3|udf4|udf5|" + SALT;;
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashData.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "generateHash: " + hash.toString());
        return hash.toString();
    }

    public void initPayUPayment(BookAppointmentInterface bookAppointmentInterface) {

        HashMap<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(PayUCheckoutProConstants.CP_UDF1, "udf1");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF2, "udf2");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF3, "udf3");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF4, "udf4");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF5, "udf5");

        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();

        builder.setAmount(getDrFee())
                .setIsProduction(true)
                .setProductInfo("onlineAppointment")
                .setKey(MERCHANT_KEY)
                .setPhone(getMobileNo())
                .setTransactionId(getTrxId())
                .setFirstName(getPatientName())
                .setEmail(getEmail())
                .setSurl(getSurl())
                .setFurl(getFurl())
                .setAdditionalParams(additionalParams)
                .setUserCredential(getEmail());

        ArrayList<PaymentMode> checkoutOrderList = new ArrayList<>();
        checkoutOrderList.add(new PaymentMode(PaymentType.UPI, PayUCheckoutProConstants.CP_GOOGLE_PAY));
        checkoutOrderList.add(new PaymentMode(PaymentType.WALLET, PayUCheckoutProConstants.CP_PHONEPE));
        checkoutOrderList.add(new PaymentMode(PaymentType.WALLET, PayUCheckoutProConstants.CP_PAYTM));
        PayUCheckoutProConfig payUCheckoutProConfig = new PayUCheckoutProConfig();
        payUCheckoutProConfig.setPaymentModesOrder(checkoutOrderList);

        PayUPaymentParams payUPaymentParams = builder.build();
        PayUCheckoutPro.open(
                activity,
                payUPaymentParams,
                payUCheckoutProConfig,
                new PayUCheckoutProListener() {
                    @Override
                    public void onPaymentSuccess(@NotNull Object o) {
                        HashMap<String, Object> result = (HashMap<String, Object>) o;
                        String payuResponse = (String) result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                        bookAppointment.startBookingAppointment(payuResponse);
                        Log.d(TAG, "payuResponse: " + payuResponse);


                    }

                    @Override
                    public void onPaymentFailure(@NotNull Object response) {
                        HashMap<String, Object> result = (HashMap<String, Object>) response;
                        String payuResponse = (String) result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                        String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
                        bookAppointmentInterface.onFailed(merchantResponse);
                        Log.d(TAG, "onPaymentFailure: " + payuResponse);
                    }

                    @Override
                    public void onPaymentCancel(boolean b) {
                        if (b)
                            bookAppointmentInterface.onFailed("PaymentCancel");
                        Log.d(TAG, "onPaymentCancel: " + b);

                    }

                    @Override
                    public void onError(@NotNull ErrorResponse errorResponse) {
                        String errorMessage = errorResponse.getErrorMessage();
                        bookAppointmentInterface.onFailed(errorMessage);
                        Log.d(TAG, "onError: " + errorMessage);

                    }

                    @Override
                    public void generateHash(@NotNull HashMap<String, String> map, @NotNull com.payu.ui.model.listeners.PayUHashGenerationListener payUHashGenerationListener) {
                        String hashName = map.get(CP_HASH_NAME);
                        String hashData = map.get(CP_HASH_STRING);

                        if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                            hashData += SALT;

                            String hash = getSHA(hashData);
                            if (!TextUtils.isEmpty(hash)) {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(hashName, hash);
                                payUHashGenerationListener.onHashGenerated(hashMap);
                                Log.d(TAG, ": HashName: " + hashName);
                                Log.d(TAG, ": hashData: " + hashData);
                                Log.d(TAG, ": hash: " + hash);
                            } else Log.d(TAG, "generateHash is Empty: ");


                        }


                    }

                    @Override
                    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {

                    }
                });
    }

    private String getSHA(String str) {
        MessageDigest md;
        String out = "";
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(str.getBytes());
            byte[] mb = md.digest();

            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return out;

    }


    private String getFurl() {
        return "https://payuresponse.firebaseapp.com/failure";
    }

    private String getSurl() {
        return "https://payuresponse.firebaseapp.com/success";
    }
}
