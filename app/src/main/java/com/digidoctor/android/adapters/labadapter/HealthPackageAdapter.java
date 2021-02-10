package com.digidoctor.android.adapters.labadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.HealthpackagelayoutBinding;
import com.digidoctor.android.model.labmodel.PackageDetail;

import java.util.List;

public class HealthPackageAdapter extends RecyclerView.Adapter<HealthPackageAdapter.ViewHolderVH> {

    private List<PackageDetail> getpackagedetails;
    private Context context;

    public HealthPackageAdapter(List<PackageDetail> getpackagedetails, Context context) {
        this.getpackagedetails = getpackagedetails;
        this.context = context;
    }

    @NonNull
    @Override
    public HealthPackageAdapter.ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HealthpackagelayoutBinding healthpackagelayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.healthpackagelayout, parent, false);
        return new HealthPackageAdapter.ViewHolderVH(healthpackagelayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull HealthPackageAdapter.ViewHolderVH holder, int position) {

        final PackageDetail packageDetail = getpackagedetails.get(position);

        holder.healthpackagelayoutBinding.textView146.setText(packageDetail.getPackageName());
        holder.healthpackagelayoutBinding.textView147.setText(packageDetail.getDescription());
        holder.healthpackagelayoutBinding.textView148.setText(packageDetail.getNoOfTests());
        holder.healthpackagelayoutBinding.textView149.setText(packageDetail.getPackagePrice());

    }

    @Override
    public int getItemCount() {
        return getpackagedetails.size();
    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        HealthpackagelayoutBinding healthpackagelayoutBinding;

        public ViewHolderVH(@NonNull HealthpackagelayoutBinding itemView) {
            super(itemView.getRoot());
            healthpackagelayoutBinding = itemView;
        }
    }
}
