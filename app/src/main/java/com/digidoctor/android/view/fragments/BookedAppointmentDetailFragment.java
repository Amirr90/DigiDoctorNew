package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.FragmentBookedAppointmentDetailBinding;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.utility.AppointmentInterface;
import com.google.gson.Gson;

import static com.digidoctor.android.utility.ApiUtils.cancelAppointment;


public class BookedAppointmentDetailFragment extends Fragment implements AppointmentInterface {

    private static final String TAG = "BookedAppointmentDetail";
    OnlineAppointmentModel appointmentModel;

    FragmentBookedAppointmentDetailBinding bookedAppointmentDetailBinding;

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bookedAppointmentDetailBinding = FragmentBookedAppointmentDetailBinding.inflate(inflater, container, false);
        return bookedAppointmentDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        String docString = getArguments().getString("appointmentModel");
        Gson gson = new Gson();
        appointmentModel = gson.fromJson(docString, OnlineAppointmentModel.class);

        bookedAppointmentDetailBinding.setAppointmentModel(appointmentModel);

        bookedAppointmentDetailBinding.setCancelAppointment(this);

    }

    @Override
    public void onCancelAppointmentClicked(Object o) {
        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) o;
        cancelAppointment(appointmentModel.getAppointmentId());
    }

    @Override
    public void onReScheduleClicked(Object o) {

    }

}