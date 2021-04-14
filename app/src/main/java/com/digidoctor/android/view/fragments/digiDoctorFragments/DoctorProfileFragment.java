package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentDoctorShortProfileBinding;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import static com.digidoctor.android.utility.NewDashboardUtils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.getDocTiming;


public class DoctorProfileFragment extends Fragment {
    private static final String TAG = "DoctorShortProfileFragm";

    FragmentDoctorShortProfileBinding shortProfileBinding;

    PatientViewModel viewModel;

    NavController navController;

    DoctorModel doctorModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        shortProfileBinding = FragmentDoctorShortProfileBinding.inflate(inflater, container, false);
        return shortProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (getArguments() != null) {


            Gson gson = new Gson();
            doctorModel = gson.fromJson(getArguments().getString("docModel"), DoctorModel.class);

            Log.d(TAG, "onViewCreated: " + doctorModel.toString());
            shortProfileBinding.setDocMo(doctorModel);

            try {
                if (null != doctorModel.getWorkingHours())
                    shortProfileBinding.tvWorkingHours.setText(getDocTiming(doctorModel.getWorkingHours()).toString());
                else
                    shortProfileBinding.tvWorkingHours.setText(getString(R.string.slot_not_available));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "getData: " + e.getLocalizedMessage());
            }

            setBookAppointmentBtnEnability();

        } else PatientDashboard.getInstance().onSupportNavigateUp();


        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        shortProfileBinding.btnBookAppointment.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("docModel", getJSONFromModel(doctorModel));
            navController.navigate(R.id.action_doctorShortProfileFragment_to_chooseTimeFragment2, bundle);
        });


    }


    public void setBookAppointmentBtnEnability() {
        if (shortProfileBinding.tvWorkingHours.getText().equals(getString(R.string.slot_not_available))) {
            shortProfileBinding.btnBookAppointment.setBackgroundResource(R.drawable.disable_btn);
            shortProfileBinding.btnBookAppointment.setEnabled(false);
            shortProfileBinding.btnBookAppointment.setText(getString(R.string.slot_not_available));
            shortProfileBinding.btnBookAppointment.setBackgroundTintList(getResources().getColorStateList(R.color.GreyColo));


        } else {
            shortProfileBinding.btnBookAppointment.setBackgroundResource(R.drawable.round_for_search);
            shortProfileBinding.btnBookAppointment.setEnabled(true);
            shortProfileBinding.btnBookAppointment.setText(getString(R.string.book_appointment));
            shortProfileBinding.btnBookAppointment.setBackgroundTintList(getResources().getColorStateList(R.color.PrimaryColor));

        }

    }

    

}