package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.SubSpecialityViewBinding;
import com.digidoctor.android.model.DoctorModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class SubSpecialityAdapter extends ListAdapter<DoctorModel, SubSpecialityAdapter.SubSpecialityVH> {
    private static final String TAG = "SubSpecialityAdapter";
    SubSpecialityInterface subSpecialityInterface;

    public SubSpecialityAdapter(SubSpecialityInterface subSpecialityInterface) {
        super(DoctorModel.itemCallback);
        this.subSpecialityInterface = subSpecialityInterface;
    }

    @NonNull
    @Override
    public SubSpecialityVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SubSpecialityViewBinding subSpecialityViewBinding = SubSpecialityViewBinding.inflate(inflater, parent, false);
        subSpecialityViewBinding.setSubSpecialityInterface(subSpecialityInterface);
        return new SubSpecialityVH(subSpecialityViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubSpecialityVH holder, int position) {

        final DoctorModel doctorModel = getItem(position);
        holder.subSpecialityViewBinding.setDoctor(doctorModel);
        holder.subSpecialityViewBinding.btnBookAppointment.setOnClickListener(v -> {

            Gson gson = new Gson();
            String jsonString = gson.toJson(doctorModel);
            Log.d(TAG, "onClickDoctorModel: " + doctorModel.toString());
            try {
                JSONObject request = new JSONObject(jsonString);
                subSpecialityInterface.onItemClick(request.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });

    }


    public class SubSpecialityVH extends RecyclerView.ViewHolder {
        SubSpecialityViewBinding subSpecialityViewBinding;

        public SubSpecialityVH(SubSpecialityViewBinding subSpecialityViewBinding) {
            super(subSpecialityViewBinding.getRoot());
            this.subSpecialityViewBinding = subSpecialityViewBinding;
        }
    }

    public interface SubSpecialityInterface {
        void onItemClick(String item);
        void onShareProfile(DoctorModel doctorModel);
    }
}
