package com.digidoctor.android.adapters.pharmacy;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.CategoryViewBinding;
import com.digidoctor.android.databinding.HealthpackagelayoutBinding;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;

import java.util.List;

import static com.digidoctor.android.utility.utils.CATEGORY_ID;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryVH> {


    NavController navController;
    private List<ShopBycategoryModel.CategoryModel> sbc;
    private Activity activity;

    public AllCategoryAdapter(List<ShopBycategoryModel.CategoryModel> sbc, Activity activity, NavController navController) {
        this.sbc = sbc;
        this.activity = activity;
        this.navController = navController;
    }


    @NonNull
    @Override
    public AllCategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryViewBinding categoryViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.category_view, parent, false);
        return new AllCategoryAdapter.AllCategoryVH(categoryViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryVH holder, int position) {
        final ShopBycategoryModel.CategoryModel categoryModel = sbc.get(position);
        holder.categoryViewBinding.textView72.setText(categoryModel.getCategoryName());
        Glide.with(activity).load(categoryModel.getImagePath())
                .thumbnail(0.5f).placeholder(R.drawable.box_two)
                .into(holder.categoryViewBinding.imageView26);


        holder.categoryViewBinding.categoryview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(CATEGORY_ID, String.valueOf(categoryModel.getCategoryId()));
                navController.navigate(R.id.action_allCategoryFragment_to_allProductsFragment, bundle);


            }
        });
    }


    @Override
    public int getItemCount() {
        return sbc.size();
    }

    public static class AllCategoryVH extends RecyclerView.ViewHolder {

        ImageView imageView;
        CategoryViewBinding categoryViewBinding;

        public AllCategoryVH(@NonNull CategoryViewBinding itemView) {


            super(itemView.getRoot());
            categoryViewBinding = itemView;

        }
    }
}