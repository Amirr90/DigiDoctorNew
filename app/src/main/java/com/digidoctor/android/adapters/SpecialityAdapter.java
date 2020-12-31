package com.digidoctor.android.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.SpecialitiyViewBinding;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;


public class SpecialityAdapter extends ListAdapter<SpecialityModel, SpecialityAdapter.SpecialityVH> {
    private static final String TAG = "SpecialityAdapter";

    Activity activity;

    public SpecialityAdapter(Activity activity) {
        super(SpecialityModel.itemCallback);
        this.activity = activity;
    }

    @NonNull
    @Override
    public SpecialityVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SpecialitiyViewBinding specialitiyViewBinding = SpecialitiyViewBinding.inflate(inflater, parent, false);
        return new SpecialityVH(specialitiyViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityVH holder, int position) {

        final SpecialityModel specialityModel = getItem(position);
        holder.specialitiyViewBinding.setSpeciality(specialityModel);


        holder.specialitiyViewBinding.llspeality.setOnClickListener(v -> {
            utils.hideSoftKeyboard(activity);
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(specialityModel.getId()));
            PatientDashboard.getInstance().navigate(R.id.action_specialitiesFragment2_to_subSpecialistFragment2, bundle);
        });


    }

    public class SpecialityVH extends RecyclerView.ViewHolder {
        SpecialitiyViewBinding specialitiyViewBinding;

        public SpecialityVH(SpecialitiyViewBinding specialitiyViewBinding) {
            super(specialitiyViewBinding.getRoot());
            this.specialitiyViewBinding = specialitiyViewBinding;
        }
    }


}
