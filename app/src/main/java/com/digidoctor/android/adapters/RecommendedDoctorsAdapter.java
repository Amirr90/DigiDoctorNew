package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.DocViewBinding;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.utility.AdapterInterface;


public class RecommendedDoctorsAdapter extends ListAdapter<DoctorModel, RecommendedDoctorsAdapter.RecommendedVH> {
    AdapterInterface adapterInterface;

    public RecommendedDoctorsAdapter(AdapterInterface adapterInterface) {
        super(DoctorModel.itemCallback);
        this.adapterInterface = adapterInterface;
    }


    @NonNull
    @Override
    public RecommendedVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DocViewBinding docViewBinding = DocViewBinding.inflate(inflater, parent, false);
        docViewBinding.setRecommendedInterface(adapterInterface);
        return new RecommendedVH(docViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedVH holder, int position) {

        DoctorModel doctorModel = getItem(position);

        holder.docViewBinding.setDoc(doctorModel);
    }

    public class RecommendedVH extends RecyclerView.ViewHolder {
        DocViewBinding docViewBinding;

        public RecommendedVH(DocViewBinding docViewBinding) {
            super(docViewBinding.getRoot());
            this.docViewBinding = docViewBinding;
        }
    }
}
