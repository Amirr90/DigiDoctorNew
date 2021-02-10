package com.digidoctor.android.adapters.pharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterforPharmacy extends  RecyclerView.Adapter<SliderAdapterforPharmacy.ViewHolder> {

    private ArrayList<ShopBycategoryModel.SliderImage> banner;
    private Context context;


    public SliderAdapterforPharmacy(List<ShopBycategoryModel.SliderImage> banner, Context context) {
        this.context = context;
        this.banner = (ArrayList<ShopBycategoryModel.SliderImage>) banner;

    }

    @NonNull
    @Override
    public SliderAdapterforPharmacy.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ShopBycategoryModel.SliderImage sliderImage = banner.get(position);
        Glide.with(context).load(sliderImage.getSliderImage())
                .thumbnail(0.5f)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return banner.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.banner);
        }
    }
}
