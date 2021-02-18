package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.AvailableCoupneViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.pharmacyModel.AllCoupneModelResponse;

import java.util.List;

public class AllCoupneAdapter extends RecyclerView.Adapter<AllCoupneAdapter.ViewHolderVH> {
    List<AllCoupneModelResponse.GetCoupnedetails> coupnedetails;
    Activity activity;
    AdapterInterface adapterInterface;

    public AllCoupneAdapter(List<AllCoupneModelResponse.GetCoupnedetails> coupnedetails, Activity activity, AdapterInterface adapterInterface) {
        this.coupnedetails = coupnedetails;
        this.activity = activity;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AvailableCoupneViewBinding availableCoupneViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.available_coupne_view, parent, false);
        return new AllCoupneAdapter.ViewHolderVH(availableCoupneViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCoupneAdapter.ViewHolderVH holder, int position) {
        AllCoupneModelResponse.GetCoupnedetails responseValue = coupnedetails.get(position);
        holder.availableCoupneViewBinding.textView35.setText(responseValue.getCouponCode());
        holder.availableCoupneViewBinding.textView36.setText(responseValue.getDescription());
        holder.availableCoupneViewBinding.textView37.setText("Valid From " + responseValue.getValidityFrom() + " Valid To " + responseValue.getValidityTo());


        holder.availableCoupneViewBinding.textView34.setOnClickListener(view -> adapterInterface.onItemClicked(holder.availableCoupneViewBinding.textView35.getText().toString().trim()));

    }

    @Override
    public int getItemCount() {
        return coupnedetails.size();
    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        AvailableCoupneViewBinding availableCoupneViewBinding;

        public ViewHolderVH(@NonNull AvailableCoupneViewBinding itemView) {
            super(itemView.getRoot());

            availableCoupneViewBinding = itemView;
        }
    }
}
