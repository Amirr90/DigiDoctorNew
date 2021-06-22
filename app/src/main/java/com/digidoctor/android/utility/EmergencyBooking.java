package com.digidoctor.android.utility;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.BookAppointmentInterface;
import com.digidoctor.android.interfaces.EmergencyBookingListener;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.User;

import java.util.List;

public class EmergencyBooking {
    private static final String TAG = "EmergencyBooking";
    User user;
    EmergencyBookingListener bookingListener;
    int amount;
    String problemName;

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    BookAppointment bookAppointment;
    Activity activity;

    public EmergencyBooking(FragmentActivity activity) {
        this.activity = activity;
    }


    public void setBookAppointment(BookAppointment bookAppointment) {
        this.bookAppointment = bookAppointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (null == user.getEmailId() || user.getEmailId().isEmpty())
            user.setEmailId(utils.CONSTANT_EMAIL_ID);
        this.user = user;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void initEmergencyBooking(EmergencyBookingListener bookingListener) {
        this.bookingListener = bookingListener;


        //converting date from dd/MM/yyyy to yyyy-MM-dd
        user.setDob(AppUtils.parseDate(user.getDob(), "yyyy-MM-dd", "dd/MM/yyyy"));

        //api to add user if not exist and return back trx ids for payment
        ApiUtils.emergencyAddUser(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<ResponseModel.HashModel> modelList = (List<ResponseModel.HashModel>) o;
                if (!modelList.isEmpty())
                    initPaymentGateway(modelList.get(0).getTaxId());
                else {
                    Log.d(TAG, "onEmergencyBookingFailed: transaction Id id not found try again !!");
                    bookingListener.onEmergencyBookingFailed("transaction Id id not found try again !!");
                }
            }

            @Override
            public void onError(String s) {
                Log.d(TAG, "onEmergencyBookingFailed: " + s);
                bookingListener.onEmergencyBookingFailed(s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d(TAG, "onEmergencyBookingFailed: " + throwable.getLocalizedMessage());
                bookingListener.onEmergencyBookingFailed("transaction Id id not found try again !!");
            }
        });
    }

    private void initPaymentGateway(String taxId) {

        Log.d(TAG, "initPaymentGateway: trxId " + taxId);

        //initiating PayUMoney Payment gateway
        PayUMoney payUMoney = new PayUMoney(activity);
        payUMoney.setProblemName(getProblemName());

        //setting trx id
        bookAppointment.setTrxId(taxId);

        //setting booking appointment class obj
        payUMoney.setBookAppointment(bookAppointment);

        //setting user class ob
        payUMoney.setUser(user);

        //trigger PayUMoney SDK
        payUMoney.initPayUPayment(new BookAppointmentInterface() {
            @Override
            public void onAppointmentBooked(OnlineAppointmentModel appointmentModel) {
                bookingListener.onEmergencyBookingSuccess(appointmentModel);
                Log.d(TAG, "onAppointmentBooked: success " + appointmentModel.toString());
            }

            @Override
            public void onFailed(String msg) {
                Log.d(TAG, "initPaymentGateway onFailed: " + msg);
                bookingListener.onEmergencyBookingFailed("booking failed, if amount deducted will revert back to your account in 24h");
            }

            @Override
            public void onError(String errorMsg) {
                Log.d(TAG, "onError: initPaymentGateway " + errorMsg);
                bookingListener.onEmergencyBookingFailed("booking failed, if amount deducted will revert back to your account in 24h");
            }
        });

    }


}


