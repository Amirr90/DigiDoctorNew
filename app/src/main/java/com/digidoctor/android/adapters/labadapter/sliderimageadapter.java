package com.digidoctor.android.adapters.labadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;

import java.util.List;

public class sliderimageadapter extends RecyclerView.Adapter<sliderimageadapter.ViewHolderVH> {

    private final List<LabDashBoardmodel.SliderImage> banner;
    private final Context context;

    public sliderimageadapter(List<LabDashBoardmodel.SliderImage> banner, Context context) {
        this.banner = banner;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerview, parent, false);
        return new ViewHolderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVH holder, int position) {
        final LabDashBoardmodel.SliderImage sliderImage1 = banner.get(position);


        Glide.with(context).load(sliderImage1.getSliderImage())
                .thumbnail(0.5f)
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return banner.size();
    }

    public static class ViewHolderVH extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolderVH(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.banner);
        }
    }
}
