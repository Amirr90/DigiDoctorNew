package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.AppointmentViewBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.AppointmentModel;

import static com.digidoctor.android.utility.utils.getDateFromDate;
import static com.digidoctor.android.utility.utils.getDayFromDate;
import static com.digidoctor.android.utility.utils.getMonthFromDate;

public class AppointmentAdapter extends ListAdapter<AppointmentModel, AppointmentAdapter.AppointmentVH> {

    OnClickListener onClickListener;

    public AppointmentAdapter(OnClickListener onClickListener) {
        super(AppointmentModel.itemCallback);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AppointmentViewBinding binding = AppointmentViewBinding.inflate(inflater, parent, false);
        binding.setClickListener(onClickListener);
        return new AppointmentVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentVH holder, int position) {

        AppointmentModel appointmentModel = getItem(position);
        holder.binding.setAppointment(appointmentModel);
        String incomingDate = appointmentModel.getAppointDate();//yyyy/mm/dd
        holder.binding.tvDay.setText(getDayFromDate(incomingDate));
        holder.binding.tvDate.setText(getDateFromDate(incomingDate));
        holder.binding.tvMonth.setText(getMonthFromDate(incomingDate));

    }


    public static class AppointmentVH extends RecyclerView.ViewHolder {
        AppointmentViewBinding binding;

        public AppointmentVH(@NonNull AppointmentViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
