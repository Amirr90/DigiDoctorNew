package com.digidoctor.android.adapters.labadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.BannerviewBinding;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;

import java.util.List;

public class LabSliderAdapter extends RecyclerView.Adapter<LabSliderAdapter.LabsSliderVH> {
    private static final String TAG = "LabSliderAdapter";
    List<LabDashBoardmodel.SliderImage> imageLists;

    public LabSliderAdapter(List<LabDashBoardmodel.SliderImage> imageLists) {
        this.imageLists = imageLists;
    }

    @NonNull
    @Override
    public LabsSliderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BannerviewBinding binding = BannerviewBinding.inflate(layoutInflater, parent, false);
        return new LabsSliderVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabsSliderVH holder, int position) {

        LabDashBoardmodel.SliderImage imageUrl = imageLists.get(position);

        Log.d(TAG, "onBindViewHolder: "+imageUrl.getSliderImage());
        holder.binding.setBanner(imageUrl.getSliderImage());
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    public static class LabsSliderVH extends RecyclerView.ViewHolder {
        BannerviewBinding binding;

        public LabsSliderVH(@NonNull BannerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
