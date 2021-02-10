package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.SimilarproductviewBinding;
import com.digidoctor.android.model.patientModel.User;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class SimilarproductADapter extends RecyclerView.Adapter<SimilarproductADapter.SimilarproductADapterVH> {
    public static final String TAG = "SimilarproductADapter";
    public List<ProductDetailModelResponse.ProductDetailsList.SimilarProduct> getSimilarProduct;
    Activity activity;
    Context ctx;

    public SimilarproductADapter(List<ProductDetailModelResponse.ProductDetailsList.SimilarProduct> getSimilarProduct, Context ctx) {
        this.getSimilarProduct = getSimilarProduct;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public SimilarproductADapter.SimilarproductADapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SimilarproductviewBinding allproductviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.similarproductview, parent, false);
        return new SimilarproductADapter.SimilarproductADapterVH(allproductviewBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull SimilarproductADapter.SimilarproductADapterVH holder, int position) {
        ProductDetailModelResponse.ProductDetailsList.SimilarProduct sp = getSimilarProduct.get(position);
        holder.allproductviewBinding.PName.setText(sp.getProductName());
        holder.allproductviewBinding.textView6.setText(sp.getShortDescription());
        holder.allproductviewBinding.textView55.setText(sp.getMrp());
        holder.allproductviewBinding.textView7.setText(String.valueOf(sp.getOfferedPrice()));

        Glide.with(ctx).load(sp.getImageURL()).placeholder(R.drawable.box_two).into(holder.allproductviewBinding.imageView4);


        holder.allproductviewBinding.similarcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                Bundle bundle = new Bundle();
                bundle.putInt("productID", sp.getProductId());
                bundle.putString("member", String.valueOf(user.getMemberId()));
                bundle.putString("Pinfo", sp.getProductInfoCode());

                PatientDashboard.getInstance().navigate(R.id.action_productDetailsFragment_self, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getSimilarProduct.size();
    }

    public class SimilarproductADapterVH extends RecyclerView.ViewHolder {
        SimilarproductviewBinding allproductviewBinding;

        public SimilarproductADapterVH(@NonNull SimilarproductviewBinding itemView) {

            super(itemView.getRoot());
            allproductviewBinding = itemView;

        }
    }
}
