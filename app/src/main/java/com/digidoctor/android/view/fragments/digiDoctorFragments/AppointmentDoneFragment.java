package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAppointmentDoneBinding;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AppointmentDoneFragment extends Fragment {


    FragmentAppointmentDoneBinding appointmentDoneBinding;
    NavController navController;
    String appointmentModelString;
    OnlineAppointmentModel onlineAppointmentModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        appointmentDoneBinding = FragmentAppointmentDoneBinding.inflate(inflater, container, false);
        return appointmentDoneBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null == getArguments())
            return;
        appointmentModelString = getArguments().getString("appointmentModel");

        Gson gson = new Gson();
        onlineAppointmentModel = gson.fromJson(appointmentModelString, OnlineAppointmentModel.class);


        navController = Navigation.findNavController(view);
        appointmentDoneBinding.btnBookingDetails.setOnClickListener(view1 -> {

            Bundle bundle = new Bundle();
            bundle.putString("appointmentModel", appointmentModelString);
            navController.navigate(R.id.action_appointmentDoneFragment_to_bookedAppointmentDetailFragment, bundle);
        });

        appointmentDoneBinding.setAppointment(onlineAppointmentModel);

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}