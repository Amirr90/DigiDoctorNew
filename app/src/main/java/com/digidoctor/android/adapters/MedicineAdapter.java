package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.AddPrescriptionViewBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.PrescriptionDtTableModel;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineVH> {
    private static final String TAG = "MedicineAdapter";
    List<PrescriptionDtTableModel> medicineModelList;
    OnClickListener onClickListener;

    public MedicineAdapter(List<PrescriptionDtTableModel> medicineModelList, OnClickListener onClickListener) {
        this.medicineModelList = medicineModelList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MedicineAdapter.MedicineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddPrescriptionViewBinding viewBinding = AddPrescriptionViewBinding.inflate(inflater, parent, false);

        return new MedicineVH(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.MedicineVH holder, final int position) {
        PrescriptionDtTableModel medicineDetailModel = medicineModelList.get(position);
        try {
            holder.viewBinding.setMedicineDetails(medicineDetailModel);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error On Setting Medicine details : " + e.getLocalizedMessage());
        }

        holder.viewBinding.ivDeleteItem.setOnClickListener(view -> onClickListener.onItemClick(position));
    }

    public void deleteAllItems(){
        medicineModelList.clear();
    }
    @Override
    public int getItemCount() {
        return medicineModelList.size();
    }

    public boolean addItems(PrescriptionDtTableModel medicineDetailModel) {
        if (medicineModelList.contains(medicineDetailModel))
            return false;
        else {
            medicineModelList.add(medicineDetailModel);
            notifyDataSetChanged();
            return true;
        }
    }

    public List<PrescriptionDtTableModel> deleteItems(int position) {
        if (medicineModelList.size() >= position) {
            medicineModelList.remove(position);
            notifyItemRemoved(0);
            notifyDataSetChanged();

        }
        return medicineModelList;
    }

    public List<PrescriptionDtTableModel> getMedicineData() {
        return medicineModelList;
    }

    public static class MedicineVH extends RecyclerView.ViewHolder {
        AddPrescriptionViewBinding viewBinding;

        public MedicineVH(@NonNull AddPrescriptionViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}
