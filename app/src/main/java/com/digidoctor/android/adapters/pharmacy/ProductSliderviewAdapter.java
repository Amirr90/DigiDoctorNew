package com.digidoctor.android.adapters.pharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.CustomesliderviewBinding;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ProductSliderviewAdapter extends SliderViewAdapter<ProductSliderviewAdapter.SliderAdapterVH> {


    private Context context;
    private List<ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider> mSliderItems;


    public ProductSliderviewAdapter(List<ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider> mSliderItems, Context context) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }


    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomesliderviewBinding customesliderviewBinding = CustomesliderviewBinding.inflate(inflater, parent, false);
        return new ProductSliderviewAdapter.SliderAdapterVH(customesliderviewBinding);

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

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        CustomesliderviewBinding customesliderviewBinding;
        ImageView imageView;

        public SliderAdapterVH(CustomesliderviewBinding itemView) {
            super(itemView.getRoot());
            this.customesliderviewBinding = itemView;


        }
    }
}
