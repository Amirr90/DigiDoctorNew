package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.BannerviewBinding;

import java.util.List;

public class LabHomeBannerAdapter extends RecyclerView.Adapter<LabHomeBannerAdapter.HomeVH> {
    List<String> bannerImages;

    public LabHomeBannerAdapter(List<String> bannerImages) {
        this.bannerImages = bannerImages;
    }

    @NonNull
    @Override
    public LabHomeBannerAdapter.HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BannerviewBinding binding = BannerviewBinding.inflate(layoutInflater, parent, false);
        return new HomeVH(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull LabHomeBannerAdapter.HomeVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return bannerImages.size();
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        BannerviewBinding binding;


        public HomeVH(@NonNull BannerviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
