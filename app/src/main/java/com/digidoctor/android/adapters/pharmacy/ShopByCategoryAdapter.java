package com.digidoctor.android.adapters.pharmacy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ShopByCategoryViewBinding;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.CATEGORY_ID;

public class ShopByCategoryAdapter extends RecyclerView.Adapter<ShopByCategoryAdapter.ShopByCategoryVH> {

    private List<ShopBycategoryModel.CategoryModel> sbc;
    private Context ctx;

    public ShopByCategoryAdapter(List<ShopBycategoryModel.CategoryModel> sbc, Context ctx) {
        this.sbc = sbc;
        this.ctx = ctx;

    }

    /*public ShopByCategoryAdapter(List<ShopBycategoryModel.CategoryModel> sbc, Context ctx) {
        this.sbc = sbc;
        this.ctx = ctx;
    }*/

    @NonNull
    @Override
    public ShopByCategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        ShopByCategoryViewBinding shopByCategoryViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.shop_by_category_view, parent, false);
        return new ShopByCategoryVH(shopByCategoryViewBinding);


    }


    @Override
    public int getItemCount() {
        return sbc.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ShopByCategoryVH holder, int position) {

        final ShopBycategoryModel.CategoryModel categoryModel = sbc.get(position);

        holder.shopByCategoryViewBinding.textView59.setText(categoryModel.getCategoryName());

        Glide.with(ctx).load(categoryModel.getImagePath())
                .thumbnail(0.5f).placeholder(R.drawable.box_two)
                .into(holder.shopByCategoryViewBinding.imageView7);

        holder.shopByCategoryViewBinding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(CATEGORY_ID, String.valueOf(categoryModel.getCategoryId()));

                PatientDashboard.getInstance().navigate(R.id.action_onlinePharmacyFragment_to_allProductsFragment, bundle);

            }
        });

    }


    public static class ShopByCategoryVH extends RecyclerView.ViewHolder {
        ShopByCategoryViewBinding shopByCategoryViewBinding;

        ImageView imageView;

        public ShopByCategoryVH(@NonNull ShopByCategoryViewBinding itemView) {
            super(itemView.getRoot());

            shopByCategoryViewBinding = itemView;
        }

    }


    public List<ShopBycategoryModel.CategoryModel> getShopCateData() {
        if (null == sbc)
            sbc = new ArrayList<>();
        return sbc;
    }

}