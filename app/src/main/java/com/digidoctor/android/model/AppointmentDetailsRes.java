package com.digidoctor.android.model;

import android.widget.ListAdapter;

import java.util.List;

public class AppointmentDetailsRes {
    String responseMessage;
    int responseCode;
    List<Appointments> responseValue;

    public String getResponseMessage() {
        return responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<Appointments> getResponseValue() {
        return responseValue;
    }

    public class Appointments {
        List<AppointmentModel> appointmentDetails;
        List<AppointmentModel> otherAppointments;

        public List<AppointmentModel> getAppointmentDetails() {
            return appointmentDetails;
        }

        public List<AppointmentModel> getOtherAppointments() {
            return otherAppointments;
        }
    }
}
