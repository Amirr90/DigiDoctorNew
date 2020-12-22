package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.InvestigationViewBinding;
import com.digidoctor.android.databinding.InvestigationViewTwoBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.InvestigationModel;

import java.util.List;

/*
public class InvestigationAdapter extends ListAdapter<InvestigationModel, InvestigationAdapter.InvestigationVH> {
    OnClickListener onClickListener;
    public InvestigationAdapter(OnClickListener onClickListener) {
        super(InvestigationModel.itemCallback);
        this.onClickListener = onClickListener;
    }


    @NonNull
    @Override
    public InvestigationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        InvestigationViewBinding binding = InvestigationViewBinding.inflate(inflater, parent, false);
        binding.setClickListener(onClickListener);
        return new InvestigationVH(binding);
    }


    @Override
    public int getItemViewType(int position) {
        InvestigationModel model = getItem(position);
        if (null == model.getFilePath() || model.getFilePath().isEmpty())
            return 0;
        else return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull InvestigationVH holder, int position) {

        InvestigationModel investigationModel = getItem(position);
        try {

            holder.binding.setInvestigationModel(investigationModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class InvestigationVH extends RecyclerView.ViewHolder {
        InvestigationViewBinding binding;
        public InvestigationVH(@NonNull InvestigationViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
*/

public class InvestigationAdapter extends RecyclerView.Adapter {

    OnClickListener onClickListener;
    List<InvestigationModel> investigationModels;

    public InvestigationAdapter(OnClickListener onClickListener, List<InvestigationModel> investigationModels) {
        this.onClickListener = onClickListener;
        this.investigationModels = investigationModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            InvestigationViewBinding binding = InvestigationViewBinding.inflate(inflater, parent, false);
            binding.setClickListener(onClickListener);
            return new InvestigationVHOne(binding);
        } else {
            InvestigationViewTwoBinding binding = InvestigationViewTwoBinding.inflate(inflater, parent, false);
            binding.setClickListener(onClickListener);
            return new InvestigationVHTwo(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        InvestigationModel investigationModel = investigationModels.get(position);
        if (null == investigationModel.getFilePath() || investigationModel.getFilePath().isEmpty()) {
            InvestigationVHOne investigationVHOne = (InvestigationVHOne) holder;
            investigationVHOne.binding.setInvestigationModel(investigationModel);
        } else {
            InvestigationVHTwo investigationVHTwo = (InvestigationVHTwo) holder;
            investigationVHTwo.binding.setInvestigationModel(investigationModel);

        }
    }

    @Override
    public int getItemCount() {
        return investigationModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        InvestigationModel model = investigationModels.get(position);
        if (null == model.getFilePath() || model.getFilePath().isEmpty())
            return 0;
        else return 1;
    }

    public class InvestigationVHOne extends RecyclerView.ViewHolder {

        InvestigationViewBinding binding;

        public InvestigationVHOne(@NonNull InvestigationViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class InvestigationVHTwo extends RecyclerView.ViewHolder {

        InvestigationViewTwoBinding binding;

        public InvestigationVHTwo(@NonNull InvestigationViewTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
