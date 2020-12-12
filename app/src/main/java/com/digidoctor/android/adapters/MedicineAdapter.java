package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.AddPrescriptionViewBinding;
import com.digidoctor.android.model.MedicineModel;

import java.util.List;
/*

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
*/


public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineVH> {
    private static final String TAG = "MedicineAdapter";
    List<MedicineModel.MedicineDetailModel> medicineModelList;

    public MedicineAdapter(List<MedicineModel.MedicineDetailModel> medicineModelList) {
        this.medicineModelList = medicineModelList;
    }

    @NonNull
    @Override
    public MedicineAdapter.MedicineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddPrescriptionViewBinding viewBinding = AddPrescriptionViewBinding.inflate(inflater, parent, false);
        return new MedicineVH(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.MedicineVH holder, int position) {
        MedicineModel.MedicineDetailModel medicineDetailModel = medicineModelList.get(position);
        try {
            holder.viewBinding.setMedicineDetails(medicineDetailModel);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error On Setting Medicine details : " + e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return medicineModelList.size();
    }

    public boolean addItems(MedicineModel.MedicineDetailModel medicineDetailModel) {
        if (medicineModelList.contains(medicineDetailModel))
            return false;
        else {
            medicineModelList.add(medicineDetailModel);
            notifyDataSetChanged();
            return true;
        }
    }

    public boolean deleteItems(MedicineModel.MedicineDetailModel medicineDetailModel) {
        if (medicineModelList.contains(medicineDetailModel)) {
            medicineModelList.remove(medicineDetailModel);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
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
