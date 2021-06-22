package com.digidoctor.android.utility;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.BookAppointmentInterface;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.User;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.base.models.PaymentMode;
import com.payu.base.models.PaymentType;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_NAME;
import static com.payu.checkoutpro.utils.PayUCheckoutProConstants.CP_HASH_STRING;

public class PayUMoney {
    private static final String TAG = "PayUMoney";
    private final String SALT = "PxXF1pqL";

    BookAppointment bookAppointment;
    User user;
    Activity activity;
    String problemName;
    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public PayUMoney(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBookAppointment(BookAppointment bookAppointment) {
        this.bookAppointment = bookAppointment;
    }

    public void initPayUPayment(BookAppointmentInterface bookAppointmentInterface) {


        User primaryUser = utils.getUserForBooking(App.context);
        HashMap<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(PayUCheckoutProConstants.CP_UDF1, "udf1");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF2, "udf2");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF3, "udf3");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF4, "udf4");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF5, "udf5");

        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();

        String MERCHANT_KEY = "y7cBem";
        builder.setAmount(bookAppointment.getAmount())
                .setIsProduction(true)
                .setProductInfo("onlineAppointment")
                .setKey(MERCHANT_KEY)
                .setPhone(user.getMobileNo())
                .setTransactionId(bookAppointment.getTrxId())
                .setFirstName(user.getName())
                .setEmail(user.getEmailId())
                .setSurl(getSurl())
                .setFurl(getFurl())
                .setAdditionalParams(additionalParams)
                .setUserCredential(primaryUser.getEmailId());

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
                        //bookAppointment.startBookingAppointment(payuResponse);

                        Log.d(TAG, "payuResponse: " + payuResponse);
                        bookEMC_Appointment(payuResponse, bookAppointmentInterface);


                    }

                    @Override
                    public void onPaymentFailure(@NotNull Object response) {
                        HashMap<String, Object> result = (HashMap<String, Object>) response;
                        String payUResponse = (String) result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE);
                        String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
                        bookAppointmentInterface.onFailed(merchantResponse);
                        Log.d(TAG, "onPaymentFailure: " + payUResponse);
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

            for (byte temp : mb) {
                String s = Integer.toHexString(temp);
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


    private void bookEMC_Appointment(String payuResponse, BookAppointmentInterface bookAppointmentInterface) {
        ApiRequestModel requestModel = new ApiRequestModel();
        requestModel.setDtPaymentTable(payuResponse);
        requestModel.setProblemName(problemName);
        ApiUtils.emcAppointment(requestModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                OnlineAppointmentModel onlineAppointmentModel = new OnlineAppointmentModel();
                bookAppointmentInterface.onAppointmentBooked(onlineAppointmentModel);
            }

            @Override
            public void onError(String s) {
                bookAppointmentInterface.onError(s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                bookAppointmentInterface.onFailed(throwable.getLocalizedMessage());
            }
        });
    }
}
