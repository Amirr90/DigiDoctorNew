package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.PopularproductviewBinding;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;

import java.util.List;

import static com.digidoctor.android.utility.utils.getPrimaryUser;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.PopularProductVH> {
    @NonNull

    private final List<ShopBycategoryModel.PopularProductList> popularProductLists;
    private final Activity ctx;
    NavController navController;

    public PopularProductAdapter(@NonNull List<ShopBycategoryModel.PopularProductList> popularProductLists, Activity ctx, NavController navController) {
        this.popularProductLists = popularProductLists;
        this.ctx = ctx;
        this.navController = navController;
    }

    public PopularProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PopularproductviewBinding popularproductviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.popularproductview, parent, false);
        return new PopularProductVH(popularproductviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final PopularProductVH holder, int position) {

        final ShopBycategoryModel.PopularProductList popularProductList = popularProductLists.get(position);
        holder.popularproductviewBinding.Tname.setText(popularProductList.getProductName());
        holder.popularproductviewBinding.pdescrip.setText(popularProductList.getShortDescription());

        Glide.with(ctx).load(popularProductList.getImageURL())
                .thumbnail(0.5f).placeholder(R.drawable.box_two)
                .into(holder.popularproductviewBinding.imageView4);


        holder.popularproductviewBinding.checkBox2.setOnClickListener(view -> Toast.makeText(ctx, "Product Added in Your WishList", Toast.LENGTH_SHORT).show());


        holder.popularproductviewBinding.imageView45.setOnClickListener(view -> Toast.makeText(ctx, "You Can add this product in cart", Toast.LENGTH_SHORT).show());


        holder.popularproductviewBinding.popularcl.setOnClickListener(view -> {

            User user = getPrimaryUser(ctx);
            Bundle bundle = new Bundle();
            bundle.putInt("productID", Integer.parseInt(popularProductList.getProductId()));
            bundle.putInt("member", Integer.parseInt(String.valueOf(user.getMemberId())));
            Log.d("TAG", "onClick: " + bundle);
            navController.navigate(R.id.action_onlinePharmacyFragment_to_productDetailsFragment, bundle);
        });
    }


    @Override
    public int getItemCount() {
        return popularProductLists.size();
    }

    public class PopularProductVH extends RecyclerView.ViewHolder {
        PopularproductviewBinding popularproductviewBinding;

        public PopularProductVH(@NonNull PopularproductviewBinding itemView) {

            super(itemView.getRoot());

            popularproductviewBinding = itemView;


        }
    }
}
