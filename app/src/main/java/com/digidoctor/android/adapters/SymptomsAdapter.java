package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.SymptomsViewBinding;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.interfaces.AdapterInterface;

import static com.digidoctor.android.view.fragments.digiDoctorFragments.SymptomsFragment.symptomsIds;


public class SymptomsAdapter extends ListAdapter<SymptomModel, SymptomsAdapter.SymptomVH> {
    AdapterInterface adapterInterface;

    public SymptomsAdapter(AdapterInterface adapterInterface) {
        super(SymptomModel.itemCallback);
        this.adapterInterface = adapterInterface;
    }


    @NonNull
    @Override
    public SymptomVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SymptomsViewBinding symptomsViewBinding = SymptomsViewBinding.inflate(inflater, parent, false);
        symptomsViewBinding.setAdapterInterface(adapterInterface);
        return new SymptomVH(symptomsViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomVH holder, int position) {
        SymptomModel symptomModel = getItem(position);
        holder.symptomsViewBinding.setSymptoms(symptomModel);
        if (null != symptomsIds)
            holder.symptomsViewBinding.radioButton5.setChecked(symptomsIds.contains(String.valueOf(symptomModel.getProblemId())));

    }

    public class SymptomVH extends RecyclerView.ViewHolder {
        SymptomsViewBinding symptomsViewBinding;

        public SymptomVH(SymptomsViewBinding symptomsViewBinding) {
            super(symptomsViewBinding.getRoot());
            this.symptomsViewBinding = symptomsViewBinding;
        }
    }
}
