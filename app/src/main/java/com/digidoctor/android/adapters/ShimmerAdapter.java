package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.SpecialityShimmerViewBinding;

public class ShimmerAdapter extends RecyclerView.Adapter<ShimmerAdapter.ShimmerVH> {
    @NonNull
    @Override
    public ShimmerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SpecialityShimmerViewBinding shimmerViewBinding = SpecialityShimmerViewBinding.inflate(layoutInflater, parent, false);
        return new ShimmerVH(shimmerViewBinding.getRoot());
    }
    @Override
    public void onBindViewHolder(@NonNull ShimmerVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }
    public static class ShimmerVH extends RecyclerView.ViewHolder {
        public ShimmerVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
