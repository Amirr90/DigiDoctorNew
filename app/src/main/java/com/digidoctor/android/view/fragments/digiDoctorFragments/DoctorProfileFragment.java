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
                shortProfileBinding.tvWorkingHours.setText(getDocTiming(doctorModel.getWorkingHours()).toString());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "getData: " + e.getLocalizedMessage());
            }

        } else PatientDashboard.getInstance().onSupportNavigateUp();


        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        shortProfileBinding.btnBookAppointment.setOnClickListener(v -> {
            Log.d(TAG, "onItemClick: " + doctorModel);
            Bundle bundle = new Bundle();
            bundle.putString("docModel", getJSONFromModel(doctorModel));
            navController.navigate(R.id.action_doctorShortProfileFragment_to_chooseTimeFragment2, bundle);
        });
    }

}