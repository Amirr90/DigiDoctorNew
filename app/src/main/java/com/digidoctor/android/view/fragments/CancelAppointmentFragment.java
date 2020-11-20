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
import com.digidoctor.android.databinding.FragmentCancelAppointmentBinding;
import com.digidoctor.android.view.activity.PatientDashboard;

import static com.digidoctor.android.utility.utils.KEY_CANCEL;
import static com.digidoctor.android.utility.utils.RE_SCHEDULE;


public class CancelAppointmentFragment extends Fragment {

    FragmentCancelAppointmentBinding cancelAppointmentBinding;
    NavController navController;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cancelAppointmentBinding = FragmentCancelAppointmentBinding.inflate(inflater, container, false);
        return cancelAppointmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        key = getArguments().getString("key");

        cancelAppointmentBinding.btnGoToDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PatientDashboard.getInstance() != null) {
                    PatientDashboard.getInstance().onSupportNavigateUp();
                }
            }
        });

        int tag;
        if (key.equalsIgnoreCase(KEY_CANCEL)) {
            tag = R.string.appointment_cancelled_successcully;
        } else if (key.equalsIgnoreCase(RE_SCHEDULE)) {
            tag = R.string.appointment_re_scheduled_successfully;
        } else tag = R.string.appointment;
        cancelAppointmentBinding.textView6.setText(tag);

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