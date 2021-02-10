package com.digidoctor.android.view.fragments.Patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentVisitBinding;
import com.digidoctor.android.databinding.VisitViewBinding;
import com.digidoctor.android.model.patientModel.GetPatientMedicationAdviceModel;
import com.digidoctor.android.model.patientModel.GetPatientMedicationMainModel;
import com.digidoctor.android.model.patientModel.GetPatientMedicationMedicineModel;
import com.google.gson.Gson;

import java.util.List;


public class VisitFragment extends Fragment {

    private static final String TAG = "VisitFragment";

    String modelString;
    FragmentVisitBinding fragmentVisitBinding;
    NavController navController;
    GetPatientMedicationMainModel getPatientMedicationMainModels;
    List<GetPatientMedicationAdviceModel> adviseDetails;
    private List<GetPatientMedicationMedicineModel> medicineDetails;

    MedicationAdapter medicationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentVisitBinding = FragmentVisitBinding.inflate(getLayoutInflater());
        return fragmentVisitBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        modelString = getArguments().getString("presModel");


        Gson gson = new Gson();
        getPatientMedicationMainModels = gson.fromJson(modelString, GetPatientMedicationMainModel.class);

        //setting Data

        try {
            adviseDetails = getPatientMedicationMainModels.getAdviseDetails();
            medicineDetails = getPatientMedicationMainModels.getMedicineDetails();

            fragmentVisitBinding.setMedication(getPatientMedicationMainModels);
            fragmentVisitBinding.setAdviseDetails(adviseDetails.get(0));

            medicationAdapter = new MedicationAdapter(medicineDetails);
            fragmentVisitBinding.medicationRec.setAdapter(medicationAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }


        fragmentVisitBinding.tvViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != getPatientMedicationMainModels.getFilePath() && !getPatientMedicationMainModels.getFilePath().isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("filePath", getPatientMedicationMainModels.getFilePath());
                    navController.navigate(R.id.action_visitFragment_to_showFileOrPdfFragment, bundle);

                }
            }
        });

    }

    private class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationVH> {

        List<GetPatientMedicationMedicineModel> medicineDetails;

        public MedicationAdapter(List<GetPatientMedicationMedicineModel> medicineDetails) {
            this.medicineDetails = medicineDetails;
        }

        @NonNull
        @Override
        public MedicationAdapter.MedicationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VisitViewBinding visitViewBinding = VisitViewBinding.inflate(getLayoutInflater());
            return new MedicationVH(visitViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull MedicationAdapter.MedicationVH holder, int position) {

            try {
                GetPatientMedicationMedicineModel medicineModel = medicineDetails.get(position);
                holder.visitViewBinding.setMedicineDetail(medicineModel);


            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onBindViewHolder: " + e.getLocalizedMessage());
            }
        }

        @Override
        public int getItemCount() {
            return medicineDetails.size();
        }

        public class MedicationVH extends RecyclerView.ViewHolder {
            VisitViewBinding visitViewBinding;

            public MedicationVH(VisitViewBinding visitViewBinding) {
                super(visitViewBinding.getRoot());
                this.visitViewBinding = visitViewBinding;
            }
        }
    }
}