package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.AddressViewForLabBinding;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;

import java.util.List;

public class LabAddressAdapter extends RecyclerView.Adapter<LabAddressAdapter.AddressVH> {
    List<Address> addressList;

    public LabAddressAdapter(List<Address> addressList) {
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public LabAddressAdapter.AddressVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddressViewForLabBinding binding = AddressViewForLabBinding.inflate(inflater, parent, false);
        return new AddressVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabAddressAdapter.AddressVH holder, int position) {
        holder.binding.setAddress(addressList.get(position));
    }

    @Override
    public int getItemCount() {
        return null == addressList ? 0 : addressList.size();
    }

    public static class AddressVH extends RecyclerView.ViewHolder {
        AddressViewForLabBinding binding;

        public AddressVH(@NonNull AddressViewForLabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
