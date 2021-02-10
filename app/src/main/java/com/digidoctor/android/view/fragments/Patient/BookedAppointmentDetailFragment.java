package com.digidoctor.android.view.fragments.Patient;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentBookedAppointmentDetailBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.patientModel.OnlineAppointmentModel;
import com.digidoctor.android.interfaces.AppointmentInterface;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.cancelAppointment;
import static com.digidoctor.android.utility.utils.KEY_CANCEL;
import static com.digidoctor.android.utility.utils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.logout;


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
    public void onCancelAppointmentClicked(final Object object) {
        new AlertDialog.Builder(requireActivity())
                .setMessage(R.string.cancel_appointment)
                .setMessage(R.string.cancel_appointment_tag)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) object;
                        cancelAppointment(appointmentModel.getAppointmentId(), requireActivity(), new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(List<?> o) {
                                Bundle bundle = new Bundle();
                                bundle.putString("key", KEY_CANCEL);
                                navController.navigate(R.id.action_bookedAppointmentDetailFragment_to_cancelAppointmentFragment2, bundle);
                            }

                            @Override
                            public void onError(String s) {
                                try {
                                    if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                                        logout(PatientDashboard.getInstance(), true);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailed(Throwable throwable) {
                                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .show();

    }

    @Override
    public void onReScheduleClicked(Object object) {
        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) object;
        String model = getJSONFromModel(appointmentModel);
        Bundle bundle = new Bundle();
        bundle.putString("model", model);
        navController.navigate(R.id.action_bookedAppointmentDetailFragment_to_reScheduleFragment, bundle);


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