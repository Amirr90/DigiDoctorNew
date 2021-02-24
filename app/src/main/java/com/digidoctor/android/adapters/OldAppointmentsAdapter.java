package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.OldAppointmentsViewsBinding;
import com.digidoctor.android.model.AppointmentModel;

import java.util.List;

public class OldAppointmentsAdapter extends RecyclerView.Adapter<OldAppointmentsAdapter.AppointmentsVH> {

    public OldAppointmentsAdapter(List<AppointmentModel> appointmentModels) {
        this.appointmentModels = appointmentModels;
    }

    List<AppointmentModel> appointmentModels;

    @NonNull
    @Override
    public OldAppointmentsAdapter.AppointmentsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OldAppointmentsViewsBinding binding = OldAppointmentsViewsBinding.inflate(layoutInflater, parent, false);
        return new AppointmentsVH(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull OldAppointmentsAdapter.AppointmentsVH holder, int position) {
        AppointmentModel appointmentModel = appointmentModels.get(position);


    }

    @Override
    public int getItemCount() {
        return appointmentModels.size();
    }

    public static class AppointmentsVH extends RecyclerView.ViewHolder {
        OldAppointmentsViewsBinding binding;

        public AppointmentsVH(@NonNull OldAppointmentsViewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
