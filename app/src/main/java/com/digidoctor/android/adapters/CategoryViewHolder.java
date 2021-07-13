package com.digidoctor.android.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.CallHistoryViewBinding;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public CallHistoryViewBinding binding;

    public CategoryViewHolder(@NonNull CallHistoryViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
