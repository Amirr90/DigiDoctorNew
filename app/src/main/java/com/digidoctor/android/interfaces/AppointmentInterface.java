package com.digidoctor.android.interfaces;

public interface AppointmentInterface {
    void onCancelAppointmentClicked(Object o);

    void onReScheduleClicked(Object o);

    void onCall(String number);

    void onGetDirection(Object o);
}
