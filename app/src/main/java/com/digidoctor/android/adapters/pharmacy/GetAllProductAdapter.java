package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.AllproductviewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.pharmacyModel.AddToCartModel;
import com.digidoctor.android.model.pharmacyModel.AddtoWishlist;
import com.digidoctor.android.model.pharmacyModel.GetAllProductResponse;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class GetAllProductAdapter extends RecyclerView.Adapter<GetAllProductAdapter.GetAllProductVH> {

    private static final String TAG = "GetAllProductAdapter";
    String infocode, wishliststatus;
    private int lastPosition = -1;
    Activity activity;
    private List<GetAllProductResponse.GetProduct> getall;
    private List<GetAllProductResponse.GetProduct> exampleListFull;
    private Context ctx;

    public GetAllProductAdapter(List<GetAllProductResponse.GetProduct> getall, Activity activity) {
        this.getall = getall;
        this.activity = activity;


    }


    @NonNull
    @Override
    public GetAllProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AllproductviewBinding allproductviewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.allproductview, parent, false);
        return new GetAllProductVH(allproductviewBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull final GetAllProductVH holder, int position) {

        GetAllProductResponse.GetProduct getAllProductModel = getall.get(position);
        holder.allproductviewBinding.PName.setText(getAllProductModel.getProductName());
        holder.allproductviewBinding.textView6.setText(getAllProductModel.getShortDescription());
        holder.allproductviewBinding.textView55.setText("\u20B9" + getAllProductModel.getMrp());
        holder.allproductviewBinding.textView7.setText(String.valueOf(getAllProductModel.getOfferedPrice()));

        int discount = getAllProductModel.getMrp() - getAllProductModel.getOfferedPrice();

        holder.allproductviewBinding.textView56.setText(String.valueOf(discount + " off"));

//        holder.allproductviewBinding.imageView4.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_transition_anim));
//
//        holder.allproductviewBinding.ck.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_transition_anim));

        setAnimation(holder.itemView, position);
        infocode = getAllProductModel.getProductInfoCode();
        wishliststatus = getAllProductModel.getWishlistStatus();


        Bundle bundle = new Bundle();
        bundle.putString("productImage", getAllProductModel.getImageURL());

        Glide.with(activity).load(getAllProductModel.getImageURL()).placeholder(R.drawable.box_two)
                .thumbnail(0.5f).into(holder.allproductviewBinding.imageView4);


        if (getAllProductModel.getWishlistStatus().equals("1")) {
            holder.allproductviewBinding.checkBox2.setChecked(true);
        }

        holder.allproductviewBinding.allproductlayout.setOnClickListener(v -> {
            User user = new User();
            Bundle bundle1 = new Bundle();
            bundle1.putInt("productID", getAllProductModel.getProductId());
            bundle1.putString("member", String.valueOf(user.getMemberId()));
            bundle1.putString("Pinfo", getAllProductModel.getProductInfoCode());

            PatientDashboard.getInstance().navigate(R.id.action_allProductsFragment_to_productDetailsFragment, bundle1);
        });

        holder.allproductviewBinding.tvAddcart.setOnClickListener(v -> {
            if (getAllProductModel.getInCartStatus().equals("0")) {
                AddToCartModel addToCartModel = new AddToCartModel();
                addToCartModel.setProductInfoCode(getAllProductModel.getProductInfoCode());
                addToCartModel.setMemberId(String.valueOf(utils.getPrimaryUser(activity).getMemberId()));
                String quantity = "1";
                addToCart(addToCartModel, quantity);
            } else {

                Toast.makeText(activity, "Product is Already in your cart", Toast.LENGTH_SHORT).show();
            }

        });

        holder.allproductviewBinding.checkBox2.setOnClickListener(view -> {

            if (getAllProductModel.getWishlistStatus().equals("0")) {
                AddtoWishlist addtoWishList = new AddtoWishlist();
                addtoWishList.setProductInfoCode(getAllProductModel.getProductInfoCode());
                addtoWishList.setMemberId(String.valueOf(utils.getPrimaryUser(activity).getMemberId()));
                String wishlistStatus = "1";
                addtoWishList(addtoWishList, wishlistStatus);

            } else if (getAllProductModel.getWishlistStatus().equals("1")) {
                AddtoWishlist addtoWishList = new AddtoWishlist();
                addtoWishList.setProductInfoCode(getAllProductModel.getProductInfoCode());
                addtoWishList.setMemberId(String.valueOf(utils.getPrimaryUser(activity).getMemberId()));
                String wishlistStatus = "0";
                addtoWishList(addtoWishList, wishlistStatus);
                Toast.makeText(activity, "Product Removed From Wishlist", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void addtoWishList(AddtoWishlist addtoWishlist, String wishlistStatus) {

        addtoWishlist.setIsWhislist(wishlistStatus);


        ApiUtils.addtowishlist(addtoWishlist, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                Toast.makeText(activity, "Product Added in your WishList", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: Add To WishList " + throwable.getLocalizedMessage());

            }
        });


    }

    public void addToCart(AddToCartModel addToCartModel, String quantity) {
        addToCartModel.setQuantity(quantity);
        ApiUtils.addtocart(addToCartModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                Toast.makeText(activity, "Product Added in your Cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: Add To Cart " + throwable.getLocalizedMessage());

            }
        });

    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return getall.size();
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String character = charSequence.toString();
                if (character.isEmpty()) {
                    getall = exampleListFull;
                } else {
                    List<GetAllProductResponse.GetProduct> filteredList = new ArrayList<>();
                    for (GetAllProductResponse.GetProduct item : exampleListFull) {
                        if (item.getProductName().toLowerCase().contains(character.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    getall = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = getall;
                return results;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                getall = (ArrayList<GetAllProductResponse.GetProduct>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public void updateList(List<GetAllProductResponse.GetProduct> temp) {
        getall = temp;
        notifyDataSetChanged();

    }


    public static class GetAllProductVH extends RecyclerView.ViewHolder {
        AllproductviewBinding allproductviewBinding;
        ImageView wish;

        public GetAllProductVH(@NonNull AllproductviewBinding itemView) {

            super(itemView.getRoot());
            allproductviewBinding = itemView;

        }
    }

}
