package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAppointmentDoneBinding;

public class AppointmentDoneFragment extends Fragment {


    FragmentAppointmentDoneBinding appointmentDoneBinding;
    NavController navController;
    String appointmentModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        appointmentDoneBinding = FragmentAppointmentDoneBinding.inflate(inflater, container, false);
        return appointmentDoneBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appointmentModel = getArguments().getString("appointmentModel");
        navController = Navigation.findNavController(view);
        appointmentDoneBinding.btnBookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("appointmentModel", appointmentModel);
                navController.navigate(R.id.action_appointmentDoneFragment_to_bookedAppointmentDetailFragment, bundle);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}