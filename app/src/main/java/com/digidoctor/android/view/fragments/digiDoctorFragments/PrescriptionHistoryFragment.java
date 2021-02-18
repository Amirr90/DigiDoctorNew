package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.PrescriptionAdapter;
import com.digidoctor.android.databinding.FragmentPrescriptionHistoryBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.digidoctor.android.utility.utils.fadeIn;
import static com.digidoctor.android.utility.utils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.getPrimaryUser;


public class PrescriptionHistoryFragment extends Fragment implements AdapterInterface {




    FragmentPrescriptionHistoryBinding prescriptionHistoryBinding;
    NavController navController;
    PrescriptionAdapter prescriptionAdapter;

    PatientViewModel viewModel;

    User user;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prescriptionHistoryBinding = FragmentPrescriptionHistoryBinding.inflate(getLayoutInflater());
        return prescriptionHistoryBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prescriptionHistoryBinding.getRoot().setAnimation(fadeIn(requireActivity()));
        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        prescriptionAdapter = new PrescriptionAdapter(this);


        prescriptionHistoryBinding.prescriptionRec.setAdapter(prescriptionAdapter);


        viewModel.getPrescriptionData(requireActivity()).observe(getViewLifecycleOwner(), getPatientMedicationMainModels -> {
            if (getPatientMedicationMainModels != null) {
                prescriptionAdapter.submitList(getPatientMedicationMainModels);
            }
        });


        try {
            user = getPrimaryUser(requireActivity());
            prescriptionHistoryBinding.setUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


        prescriptionHistoryBinding.tvAddManually.setOnClickListener(view1 -> navController.navigate(R.id.action_prescriptionHistoryFragment_to_addPrescriptionManuallyFragment));


    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

    }

    @Override
    public void onItemClicked(Object o) {

        try {
            GetPatientMedicationMainModel getPatientMedicationMainModels = (GetPatientMedicationMainModel) o;
            Bundle bundle = new Bundle();
            bundle.putString("presModel", getJSONFromModel(getPatientMedicationMainModels));
            Log.d("TAG", "onItemClickedString: " + getJSONFromModel(getPatientMedicationMainModels));
            navController.navigate(R.id.action_prescriptionHistoryFragment_to_visitFragment, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}