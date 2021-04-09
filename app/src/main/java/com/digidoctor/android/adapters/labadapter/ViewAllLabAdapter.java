package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.DiagnosticLabsViewBinding;
import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.utility.App;

public class ViewAllLabAdapter extends ListAdapter<LabModel, ViewAllLabAdapter.LabsVH> {
    View.OnClickListener onClickListener;

    public ViewAllLabAdapter(View.OnClickListener onClickListener) {
        super(LabModel.itemCallback);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public LabsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DiagnosticLabsViewBinding binding = DiagnosticLabsViewBinding.inflate(inflater, parent, false);
        return new LabsVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabsVH holder, int position) {
        holder.binding.setLabModel(getItem(position));
        holder.binding.getRoot().setOnClickListener(v -> {
            onClickListener.onClick(v);
        });
    }

    public static class LabsVH extends RecyclerView.ViewHolder {
        DiagnosticLabsViewBinding binding;

        public LabsVH(@NonNull DiagnosticLabsViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
