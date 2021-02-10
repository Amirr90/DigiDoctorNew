package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.PopularproductviewBinding;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;
import com.digidoctor.android.model.patientModel.User;

import java.util.List;

import static com.digidoctor.android.utility.utils.getPrimaryUser;

public class TopSearchProductListAdapter extends RecyclerView.Adapter<TopSearchProductListAdapter.TopSearchProductList> {
    private List<ShopBycategoryModel.TopSearchproductList> topSearchproductLists;
    private Activity ctx;
    NavController navController;

    public TopSearchProductListAdapter(List<ShopBycategoryModel.TopSearchproductList> topSearchproductLists, Activity ctx, NavController navController) {
        this.topSearchproductLists = topSearchproductLists;
        this.ctx = ctx;
        this.navController = navController;
    }


    @NonNull
    @Override
    public TopSearchProductList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PopularproductviewBinding popularproductviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.popularproductview, parent, false);
        return new TopSearchProductList(popularproductviewBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull final TopSearchProductList holder, int position) {
        final ShopBycategoryModel.TopSearchproductList topSearchproductList = topSearchproductLists.get(position);
        holder.popularproductviewBinding.Tname.setText(topSearchproductList.getProductName());
        holder.popularproductviewBinding.pdescrip.setText(topSearchproductList.getShortDescription());

        Glide.with(ctx).load(topSearchproductList.getImageURL()).thumbnail(0.5f).placeholder(R.drawable.box_two).into(holder.popularproductviewBinding.imageView4);
        holder.popularproductviewBinding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "Product Added in Your WishList", Toast.LENGTH_SHORT).show();
                holder.popularproductviewBinding.imageView3.setImageResource(R.drawable.wishactive);

            }
        });
        holder.popularproductviewBinding.textView57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx, "You Can add this product in cart", Toast.LENGTH_SHORT).show();

            }
        });

        holder.popularproductviewBinding.popularcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = getPrimaryUser(ctx);
                Bundle bundle = new Bundle();
                bundle.putInt("productID", Integer.parseInt(topSearchproductList.getProductId()));
                bundle.putInt("member", Integer.parseInt(String.valueOf(user.getMemberId())));
                Log.d("TAG", "onClick: " + bundle);
                navController.navigate(R.id.action_onlinePharmacyFragment_to_productDetailsFragment, bundle);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topSearchproductLists.size();
    }

    public class TopSearchProductList extends RecyclerView.ViewHolder {
        PopularproductviewBinding popularproductviewBinding;

        public TopSearchProductList(@NonNull PopularproductviewBinding itemView) {
            super(itemView.getRoot());

            popularproductviewBinding = itemView;
        }
    }
}
