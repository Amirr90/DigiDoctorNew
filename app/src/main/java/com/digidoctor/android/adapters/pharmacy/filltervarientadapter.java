package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.BrandviewBinding;
import com.digidoctor.android.model.pharmacyModel.getfilltervarentmodel;

import java.util.List;

public class filltervarientadapter extends RecyclerView.Adapter<filltervarientadapter.ViewHolderVH> {
    public List<getfilltervarentmodel.filltervarientList> getvarient;

    public filltervarientadapter(List<getfilltervarentmodel.filltervarientList> getvarient, Activity activity) {
        this.getvarient = getvarient;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BrandviewBinding brandviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.brandview, parent, false);
        return new filltervarientadapter.ViewHolderVH(brandviewBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVH holder, int position) {

        final getfilltervarentmodel.filltervarientList filltervarientList = getvarient.get(position);
        holder.brandviewBinding.textView.setText(filltervarientList.getVarientName());


    }

    @Override
    public int getItemCount() {
        return getvarient.size();


    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {
        BrandviewBinding brandviewBinding;

        public ViewHolderVH(@NonNull BrandviewBinding itemView) {
            super(itemView.getRoot());
            brandviewBinding = itemView;


        }
    }
}
