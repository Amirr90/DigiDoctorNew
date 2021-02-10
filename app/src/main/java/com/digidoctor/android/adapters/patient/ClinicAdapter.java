package com.digidoctor.android.adapters.patient;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.DashBoardViewHorizontal2Binding;
import com.digidoctor.android.model.patientModel.TopClinicsModel;


public class ClinicAdapter extends ListAdapter<TopClinicsModel, ClinicAdapter.DashboardVH> {
    public ClinicAdapter() {
        super(TopClinicsModel.itemCallback);
    }

    @NonNull
    @Override
    public DashboardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DashBoardViewHorizontal2Binding dashBoardViewBinding = DashBoardViewHorizontal2Binding.inflate(inflater, parent, false);
        return new DashboardVH(dashBoardViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardVH holder, int position) {

        TopClinicsModel topClinicsModel = getItem(position);
        holder.dashBoardViewBinding.setClinics(topClinicsModel);

    }

    public class DashboardVH extends RecyclerView.ViewHolder {
        DashBoardViewHorizontal2Binding dashBoardViewBinding;


        public DashboardVH(DashBoardViewHorizontal2Binding dashBoardViewBinding) {
            super(dashBoardViewBinding.getRoot());
            this.dashBoardViewBinding = dashBoardViewBinding;
        }
    }
}
