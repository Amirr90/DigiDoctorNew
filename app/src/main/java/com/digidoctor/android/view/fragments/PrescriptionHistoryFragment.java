package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.PrescriptionAdapter;
import com.digidoctor.android.databinding.FragmentPrescriptionHistoryBinding;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AdapterInterface;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.List;

import static com.digidoctor.android.utility.utils.KEY_PRESCRIPTION_ID;
import static com.digidoctor.android.utility.utils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.getPrimaryUser;


public class PrescriptionHistoryFragment extends Fragment implements AdapterInterface {


    FragmentPrescriptionHistoryBinding prescriptionHistoryBinding;
    NavController navController;
    PrescriptionAdapter prescriptionAdapter;

    PatientViewModel viewModel;


    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prescriptionHistoryBinding = FragmentPrescriptionHistoryBinding.inflate(getLayoutInflater());
        return prescriptionHistoryBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        prescriptionAdapter = new PrescriptionAdapter(this);


        prescriptionHistoryBinding.prescriptionRec.setAdapter(prescriptionAdapter);


        viewModel.getPrescriptionData(requireActivity()).observe(getViewLifecycleOwner(), new Observer<List<GetPatientMedicationMainModel>>() {
            @Override
            public void onChanged(List<GetPatientMedicationMainModel> getPatientMedicationMainModels) {
                if (getPatientMedicationMainModels != null) {
                    prescriptionAdapter.submitList(getPatientMedicationMainModels);
                }
            }
        });


        try {
            user = getPrimaryUser(requireActivity());
            prescriptionHistoryBinding.setUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemClicked(Object o) {

        try {
            GetPatientMedicationMainModel getPatientMedicationMainModels = (GetPatientMedicationMainModel) o;

            Bundle bundle = new Bundle();
            bundle.putString("presModel", getJSONFromModel(getPatientMedicationMainModels));
            navController.navigate(R.id.action_prescriptionHistoryFragment_to_visitFragment, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}