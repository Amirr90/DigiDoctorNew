package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.VitalHistoryTypeViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.VitalTypeModel;

import java.util.List;

public class VitalTypeAdapter extends RecyclerView.Adapter<VitalTypeAdapter.VitalTypeVH> {
    List<VitalTypeModel> vitalTypeModelList;
    AdapterInterface adapterInterface;

    public VitalTypeAdapter(List<VitalTypeModel> vitalTypeModelList, AdapterInterface adapterInterface) {
        this.vitalTypeModelList = vitalTypeModelList;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public VitalTypeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VitalHistoryTypeViewBinding typeViewBinding = VitalHistoryTypeViewBinding.inflate(inflater, parent, false);
        typeViewBinding.setAdapterInterface(adapterInterface);
        return new VitalTypeVH(typeViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VitalTypeVH holder, int position) {
        try {
            VitalTypeModel typeModel = vitalTypeModelList.get(position);
            holder.typeViewBinding.setVitalType(typeModel);
            holder.typeViewBinding.imageView14.setImageResource(typeModel.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return vitalTypeModelList.size();
    }

    public static class VitalTypeVH extends RecyclerView.ViewHolder {
        VitalHistoryTypeViewBinding typeViewBinding;

        public VitalTypeVH(VitalHistoryTypeViewBinding typeViewBinding) {
            super(typeViewBinding.getRoot());
            this.typeViewBinding = typeViewBinding;
        }
    }

}
