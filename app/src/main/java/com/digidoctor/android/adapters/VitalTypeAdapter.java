package com.digidoctor.android.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.VitalHistoryTypeViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.VitalTypeModel;
import com.digidoctor.android.utility.App;

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
        if (position == 0) {
            holder.typeViewBinding.mainLayout.setBackgroundResource(R.color.colorPrimary);
            holder.typeViewBinding.textView34.setTextColor(Color.WHITE);
            holder.typeViewBinding.textView36.setTextColor(Color.WHITE);
        } else {
            holder.typeViewBinding.mainLayout.setBackgroundResource(R.color.color_grey);
            holder.typeViewBinding.textView34.setTextColor(App.context.getResources().getColor(R.color.TextPrimaryColor));
            holder.typeViewBinding.textView36.setTextColor(App.context.getResources().getColor(R.color.TextSecondaryColor));
        }

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
