package com.digidoctor.android.utility;

public interface AppointmentInterface {
    void onCancelAppointmentClicked(Object o);

    void onReScheduleClicked(Object o);

    void onCall(String number);

    void onGetDirection(Object o);
}
