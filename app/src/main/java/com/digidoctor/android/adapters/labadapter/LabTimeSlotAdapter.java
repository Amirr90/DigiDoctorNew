package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.TimingViewSecondaryLabBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.labmodel.LabSlotModel;

import java.util.List;

public class LabTimeSlotAdapter extends RecyclerView.Adapter<LabTimeSlotAdapter.LAbSlotsVH> {
    List<LabSlotModel> slotModels;
    AdapterInterface adapterInterface;

    public LabTimeSlotAdapter(List<LabSlotModel> slotModels, AdapterInterface adapterInterface) {
        this.slotModels = slotModels;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public LabTimeSlotAdapter.LAbSlotsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TimingViewSecondaryLabBinding binding = TimingViewSecondaryLabBinding.inflate(inflater, parent, false);
        return new LAbSlotsVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabTimeSlotAdapter.LAbSlotsVH holder, int position) {
        holder.binding.setTimeDetailsModel(slotModels.get(position));

        LabSlotModel labSlotModel = slotModels.get(position);
        holder.binding.getRoot().setOnClickListener(v -> adapterInterface.onItemClicked(labSlotModel.getSlot()));
    }

    @Override
    public int getItemCount() {
        return null == slotModels ? 0 : slotModels.size();
    }

    public static class LAbSlotsVH extends RecyclerView.ViewHolder {
        TimingViewSecondaryLabBinding binding;

        public LAbSlotsVH(@NonNull TimingViewSecondaryLabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
