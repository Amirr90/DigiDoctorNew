package com.digidoctor.android.adapters.pharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.CustomesliderviewBinding;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ProductSliderViewAdapter extends SliderViewAdapter<ProductSliderViewAdapter.SliderAdapterVH> {


    private final Context context;
    private final List<ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider> mSliderItems;


    public ProductSliderViewAdapter(List<ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider> mSliderItems, Context context) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }


    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomesliderviewBinding customesliderviewBinding = CustomesliderviewBinding.inflate(inflater, parent, false);
        return new SliderAdapterVH(customesliderviewBinding);

    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        final ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider productDetailsSlider = mSliderItems.get(position);

        Glide.with(context)
                .load(productDetailsSlider
                        .getImagePath())
                .thumbnail(0.5f).placeholder(R.drawable.box_two)
                .into(viewHolder.customesliderviewBinding.ivAutoImageSlider);


    }

    public static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        CustomesliderviewBinding customesliderviewBinding;

        public SliderAdapterVH(CustomesliderviewBinding itemView) {
            super(itemView.getRoot());
            this.customesliderviewBinding = itemView;


        }
    }
}
