package com.digidoctor.android.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.SpecialitiyViewBinding;
import com.digidoctor.android.databinding.SpecialityBottomViewBinding;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;


public class SpecialityAdapter extends ListAdapter<SpecialityModel, RecyclerView.ViewHolder> {
    private static final String TAG = "SpecialityAdapter";
    private static final int VIEW_TYPE_ADD_BOTTOM = 23;
    private static final int VIEW_TYPE_NORMAL = 32;

    Activity activity;

    public SpecialityAdapter(Activity activity) {
        super(itemCallback);
        this.activity = activity;
    }

/*    @NonNull
    @Override
    public SpecialityVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SpecialitiyViewBinding specialitiyViewBinding = SpecialitiyViewBinding.inflate(inflater, parent, false);
        if (viewType == VIEW_TYPE_ADD_BOTTOM)
            return new Bottom
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


    }*/


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SpecialitiyViewBinding specialitiyViewBinding = SpecialitiyViewBinding.inflate(inflater, parent, false);
        SpecialityBottomViewBinding bottomViewBinding = SpecialityBottomViewBinding.inflate(inflater, parent, false);
        if (viewType == VIEW_TYPE_ADD_BOTTOM)
            return new SpecialityBottomVH(bottomViewBinding);
        else
            return new SpecialityVH(specialitiyViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ADD_BOTTOM) {
            SpecialityBottomVH specialityBottomVH = (SpecialityBottomVH) holder;
        } else {
            SpecialityVH specialityVH = (SpecialityVH) holder;
            final SpecialityModel specialityModel = getItem(position);
            specialityVH.specialitiyViewBinding.setSpeciality(specialityModel);


            specialityVH.specialitiyViewBinding.llspeality.setOnClickListener(v -> {
                utils.hideSoftKeyboard(activity);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(specialityModel.getId()));
                PatientDashboard.getInstance().navigate(R.id.action_specialitiesFragment2_to_subSpecialistFragment2, bundle);
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_NORMAL;
    }

    public static class SpecialityVH extends RecyclerView.ViewHolder {
        SpecialitiyViewBinding specialitiyViewBinding;

        public SpecialityVH(SpecialitiyViewBinding specialitiyViewBinding) {
            super(specialitiyViewBinding.getRoot());
            this.specialitiyViewBinding = specialitiyViewBinding;
        }
    }

    public static class SpecialityBottomVH extends RecyclerView.ViewHolder {
        SpecialityBottomViewBinding bottomViewBinding;


        public SpecialityBottomVH(SpecialityBottomViewBinding bottomViewBinding) {
            super(bottomViewBinding.getRoot());
            this.bottomViewBinding = bottomViewBinding;
        }
    }


    public static DiffUtil.ItemCallback<SpecialityModel> itemCallback = new DiffUtil.ItemCallback<SpecialityModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull SpecialityModel oldItem, @NonNull SpecialityModel newItem) {
            return oldItem.getDepartmentName().equals(newItem.getDepartmentName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SpecialityModel oldItem, @NonNull SpecialityModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
