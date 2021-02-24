package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.HealthpackagelayoutBinding;
import com.digidoctor.android.model.PackageModel;

public class PackagesAdapter extends ListAdapter<PackageModel, PackagesAdapter.LabsVH> {

    public PackagesAdapter() {
        super(PackageModel.itemCallback);
    }

    @NonNull
    @Override
    public LabsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HealthpackagelayoutBinding binding = HealthpackagelayoutBinding.inflate(layoutInflater, parent, false);
        return new LabsVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabsVH holder, int position) {
        PackageModel packageModel = getItem(position);
        holder.binding.setPackageModel(packageModel);
    }

    public static class LabsVH extends RecyclerView.ViewHolder {
        HealthpackagelayoutBinding binding;
        public LabsVH(@NonNull HealthpackagelayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
