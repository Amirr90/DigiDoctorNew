package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.AddPrescriptionViewBinding;
import com.digidoctor.android.model.MedicineModel;

public class MedicineAdapter extends ListAdapter<MedicineModel.MedicineDetailModel, MedicineAdapter.MedicineVH> {
    private static final String TAG = "MedicineAdapter";
    public MedicineAdapter() {
        super(MedicineModel.MedicineDetailModel.itemCallback);
    }

    @NonNull
    @Override
    public MedicineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddPrescriptionViewBinding viewBinding = AddPrescriptionViewBinding.inflate(inflater, parent, false);
        return new MedicineVH(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineVH holder, int position) {
        MedicineModel.MedicineDetailModel medicineDetailModel = getItem(position);
        try {
            holder.viewBinding.setMedicineDetails(medicineDetailModel);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error On Setting Medicine details : " + e.getLocalizedMessage());
        }
    }

    public class MedicineVH extends RecyclerView.ViewHolder {
        AddPrescriptionViewBinding viewBinding;

        public MedicineVH(@NonNull AddPrescriptionViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}
