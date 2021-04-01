package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.TestPackageViewBinding;
import com.digidoctor.android.model.labmodel.PackageDetail;

public class HealthPackageListAdapter extends ListAdapter<PackageDetail, HealthPackageListAdapter.HealthVH> {
    public HealthPackageListAdapter() {
        super(PackageDetail.itemCallback);
    }

    @NonNull
    @Override
    public HealthVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TestPackageViewBinding binding = TestPackageViewBinding.inflate(layoutInflater, parent, false);
        return new HealthVH(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull HealthVH holder, int position) {
        PackageDetail packageDetail = getItem(position);
        holder.binding.setPackageDetails(packageDetail);

    }

    public static class HealthVH extends RecyclerView.ViewHolder {
        TestPackageViewBinding binding;

        public HealthVH(@NonNull TestPackageViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
