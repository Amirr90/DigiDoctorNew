package com.digidoctor.android.interfaces;


import com.digidoctor.android.model.OnlineAppointmentModel;

public interface BookAppointmentInterface {
    void onAppointmentBooked(OnlineAppointmentModel appointmentModel);

    void onFailed(String msg);

    void onError(String errorMsg);
}
