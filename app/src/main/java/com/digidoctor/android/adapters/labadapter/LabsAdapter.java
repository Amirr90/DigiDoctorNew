package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.CertifiedLabViewBinding;
import com.digidoctor.android.interfaces.onLabItemClick;
import com.digidoctor.android.model.LabModel;

public class LabsAdapter extends ListAdapter<LabModel, LabsAdapter.LabVH> {

    onLabItemClick onClickListener;

    public LabsAdapter(onLabItemClick onClickListener) {
        super(LabModel.itemCallback);
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public LabVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CertifiedLabViewBinding binding = CertifiedLabViewBinding.inflate(layoutInflater, parent, false);
        return new LabVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabVH holder, int position) {
        LabModel labModel = getItem(position);
        holder.binding.setLabModel(labModel);
        holder.binding.lay.setOnClickListener(v -> {
            onClickListener.onLabClick(labModel);
        });

    }

    public static class LabVH extends RecyclerView.ViewHolder {
        CertifiedLabViewBinding binding;

        public LabVH(@NonNull CertifiedLabViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }
}
