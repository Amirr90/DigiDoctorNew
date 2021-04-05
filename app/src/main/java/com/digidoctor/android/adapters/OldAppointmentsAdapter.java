
package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.OldAppointmentsViewsBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class OldAppointmentsAdapter extends RecyclerView.Adapter<OldAppointmentsAdapter.AppointmentsVH> {


    List<AppointmentModel> appointmentModels;
    AdapterInterface adapterInterface;
    private int originalHeight = 0;
    private boolean mIsViewExpanded = false;


    public OldAppointmentsAdapter(List<AppointmentModel> appointmentModels, AdapterInterface adapterInterface) {
        this.appointmentModels = appointmentModels;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public OldAppointmentsAdapter.AppointmentsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OldAppointmentsViewsBinding binding = OldAppointmentsViewsBinding.inflate(layoutInflater, parent, false);
        binding.setAdapterInterface(adapterInterface);
        return new AppointmentsVH(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull OldAppointmentsAdapter.AppointmentsVH holder, int position) {
        AppointmentModel appointmentModel = appointmentModels.get(position);
        holder.binding.setAppointment(appointmentModel);
        holder.binding.textView201.setText(AppUtils.parseDate(appointmentModel.getAppointDate(), "E, dd MMM yyyy"));

    }

    @Override
    public int getItemCount() {
        if (null == appointmentModels)
            appointmentModels = new ArrayList<>();
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
