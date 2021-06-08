package com.digidoctor.android.utility;

import android.util.Log;

import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.EmergencyBookingListener;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.User;

import java.util.List;

public class EmergencyBooking {
    private static final String TAG = "EmergencyBooking";
    User user;
    EmergencyBookingListener bookingListener;
    String amount;
    BookAppointment bookAppointment;

    public BookAppointment getBookAppointment() {
        return bookAppointment;
    }

    public void setBookAppointment(BookAppointment bookAppointment) {
        this.bookAppointment = bookAppointment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void initEmergencyBooking(EmergencyBookingListener bookingListener) {
        this.bookingListener = bookingListener;
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


    }


}


