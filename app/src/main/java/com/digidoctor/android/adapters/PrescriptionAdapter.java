package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.PrescriptionViewBinding;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.interfaces.AdapterInterface;

public class PrescriptionAdapter extends ListAdapter<GetPatientMedicationMainModel, PrescriptionAdapter.PrescriptionVH> {
    AdapterInterface adapterInterface;

    public PrescriptionAdapter(AdapterInterface adapterInterface) {
        super(GetPatientMedicationMainModel.itemCallback);
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public PrescriptionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PrescriptionViewBinding prescriptionViewBinding = PrescriptionViewBinding.inflate(inflater, parent, false);

        prescriptionViewBinding.setAdapterInterface(adapterInterface);
        return new PrescriptionVH(prescriptionViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionVH holder, int position) {

        GetPatientMedicationMainModel mainModel = getItem(position);
        holder.prescriptionViewBinding.setGetPatientMedicationMainModel(mainModel);

    }

    public class PrescriptionVH extends RecyclerView.ViewHolder {
        PrescriptionViewBinding prescriptionViewBinding;

        public PrescriptionVH(PrescriptionViewBinding prescriptionViewBinding) {
            super(prescriptionViewBinding.getRoot());
            this.prescriptionViewBinding = prescriptionViewBinding;
        }
    }
}
