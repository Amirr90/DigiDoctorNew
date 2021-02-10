package com.digidoctor.android.adapters.labadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.model.labmodel.SliderImage;

import java.util.List;

public class sliderimageadapter extends RecyclerView.Adapter<sliderimageadapter.ViewHolderVH> {

    private List<SliderImage> banner;
    private Context context;

    public sliderimageadapter(List<SliderImage> banner, Context context) {
        this.banner = banner;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerview, parent, false);
        return new sliderimageadapter.ViewHolderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVH holder, int position) {
        final SliderImage sliderImage1 = banner.get(position);


        Log.d("TAG", "onBindViewHolder: " + sliderImage1);
        Glide.with(context).load(sliderImage1.getSliderImage())
                .thumbnail(0.5f)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return banner.size();
    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolderVH(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.banner);
        }
    }
}
