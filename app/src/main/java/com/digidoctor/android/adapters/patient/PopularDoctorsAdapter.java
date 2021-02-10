package com.digidoctor.android.adapters.patient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.PopularDocViewBinding;
import com.digidoctor.android.model.patientModel.DoctorModel;
import com.digidoctor.android.interfaces.AdapterInterface;


public class PopularDoctorsAdapter extends ListAdapter<DoctorModel, PopularDoctorsAdapter.PopularVH> {
    AdapterInterface adapterInterface;

    public PopularDoctorsAdapter(AdapterInterface adapterInterface) {
        super(DoctorModel.itemCallback);
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PopularDocViewBinding popularDocViewBinding = PopularDocViewBinding.inflate(inflater, parent, false);
        popularDocViewBinding.setRecommendedInterface(adapterInterface);
        return new PopularVH(popularDocViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularVH holder, int position) {

        DoctorModel doctorModel = getItem(position);
        holder.popularDocViewBinding.setDoctor(doctorModel);


        if (null != doctorModel.getSpeciality() && !doctorModel.getSpeciality().isEmpty())
            holder.popularDocViewBinding.textView77.setVisibility(View.VISIBLE);

        else holder.popularDocViewBinding.textView77.setVisibility(View.GONE);


    }

    public class PopularVH extends RecyclerView.ViewHolder {
        PopularDocViewBinding popularDocViewBinding;

        public PopularVH(PopularDocViewBinding popularDocViewBinding) {
            super(popularDocViewBinding.getRoot());
            this.popularDocViewBinding = popularDocViewBinding;
        }
    }
}
