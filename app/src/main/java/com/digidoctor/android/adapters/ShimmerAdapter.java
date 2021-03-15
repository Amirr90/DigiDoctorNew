package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShimmerAdapter extends RecyclerView.Adapter<ShimmerAdapter.ShimmerVH> {
    int layoutId;

    public ShimmerAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public ShimmerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new ShimmerVH(view);

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
