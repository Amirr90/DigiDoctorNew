package com.digidoctor.android.view.fragments;

import android.content.Intent;
import android.net.Uri;
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

        bookedAppointmentDetailBinding.setAppointment(this);

    }

    @Override
    public void onCancelAppointmentClicked(Object o) {
        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) o;
        cancelAppointment(appointmentModel.getAppointmentId());
    }

    @Override
    public void onReScheduleClicked(Object o) {

    }

    @Override
    public void onCall(String number) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + number));//change the number
        startActivity(callIntent);
    }

    @Override
    public void onGetDirection(Object o) {

        OnlineAppointmentModel model = (OnlineAppointmentModel) o;
        String lat = String.valueOf(model.getLatitude());
        String lng = String.valueOf(model.getLongititude());

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + lat + "," + lng);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

}