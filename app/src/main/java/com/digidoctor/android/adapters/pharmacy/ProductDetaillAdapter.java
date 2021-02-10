package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentProductDetailsBinding;
import com.digidoctor.android.databinding.RatingAndReviewViewBinding;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;

import java.util.List;


public class ProductDetaillAdapter extends RecyclerView.Adapter<ProductDetaillAdapter.ProductDetaillVH> {
    private List<ProductDetailModelResponse.ProductDetailsList.ProductDetail> getallProduct;
    private static final String TAG = "ProductDetaillAdapter";
    Activity activity;
    Context ctx;

    public ProductDetaillAdapter(List<ProductDetailModelResponse.ProductDetailsList.ProductDetail> getallProduct, Context ctx) {
        this.getallProduct = getallProduct;
        this.ctx = ctx;

    }


    @NonNull
    @Override
    public ProductDetaillAdapter.ProductDetaillVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentProductDetailsBinding fragmentProductDetailsBinding = FragmentProductDetailsBinding.inflate(inflater, parent, false);
        return new ProductDetaillAdapter.ProductDetaillVH(fragmentProductDetailsBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetaillAdapter.ProductDetaillVH holder, int position) {
        final ProductDetailModelResponse.ProductDetailsList.ProductDetail nope = getallProduct.get(position);
        //   holder.ratingAndReviewViewBinding.textView101.setText(snmnkjsgd.getName());
        //holder.fragmentProductDetailsBinding.textView73.setText(Html.fromHtml(nope.getDescription()));
      //  holder.fragmentProductDetailsBinding.textView73.setText(Html.fromHtml(nope.getDescription()));

        holder.fragmentProductDetailsBinding.textView5.setPaintFlags(holder.fragmentProductDetailsBinding.textView5.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductDetaillVH extends RecyclerView.ViewHolder {
        FragmentProductDetailsBinding fragmentProductDetailsBinding;
        RatingAndReviewViewBinding ratingAndReviewViewBinding;

        public ProductDetaillVH(@NonNull FragmentProductDetailsBinding itemView) {
            super(itemView.getRoot());


        }
    }
}
