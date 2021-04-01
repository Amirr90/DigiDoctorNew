package com.digidoctor.android.adapters.labadapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.HealthpackagelayoutBinding;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.utility.Cart;
import com.digidoctor.android.utility.utils;

public class PackagesAdapter extends ListAdapter<PackageModel, PackagesAdapter.LabsVH> {
    private static final String TAG = "PackagesAdapter";
    Cart cart;

    public PackagesAdapter(Cart cart) {
        super(PackageModel.itemCallback);
        this.cart = cart;
    }

    @NonNull
    @Override
    public LabsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HealthpackagelayoutBinding binding = HealthpackagelayoutBinding.inflate(layoutInflater, parent, false);
        return new LabsVH(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LabsVH holder, int position) {
        PackageModel packageModel = getItem(position);
        holder.binding.setPackageModel(packageModel);
        holder.binding.btnAddToCart.setOnClickListener(v -> {
            String packageId = String.valueOf(packageModel.getPackageId());
            if (holder.binding.btnAddToCart.getText().toString().equals(utils.ADD_TO_CART))
                cart.addItemToCart("", packageId);
            else cart.onCartItemClicked(packageId);

        });

        try {
            String testCount = String.valueOf(packageModel.getGroupDetails().get(position).getTestDetails().size());
            holder.binding.tvTestCount.setText("Includes " + testCount + " tests");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onBindViewHolder: " + e.getLocalizedMessage());
        }


    }

    public static class LabsVH extends RecyclerView.ViewHolder {
        HealthpackagelayoutBinding binding;

        public LabsVH(@NonNull HealthpackagelayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
