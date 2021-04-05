package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentMyWishListBinding;
import com.digidoctor.android.databinding.WishlistViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.pharmacyModel.AddToCartModel;
import com.digidoctor.android.model.pharmacyModel.AddtoWishlist;
import com.digidoctor.android.model.pharmacyModel.AllWishListProduct;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class AllWishListAdapter extends RecyclerView.Adapter<AllWishListAdapter.ViewholderVh> {

    List<AllWishListProduct.AllWishlist> allWishlist;
    Activity activity;


    public AllWishListAdapter(List<AllWishListProduct.AllWishlist> allWishlist, Activity activity) {
        this.activity = activity;
        this.allWishlist = allWishlist;
    }

    @NonNull
    @Override
    public ViewholderVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WishlistViewBinding wishlistViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.wishlist_view, parent, false);
        return new ViewholderVh(wishlistViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull AllWishListAdapter.ViewholderVh holder, int position) {
        final AllWishListProduct.AllWishlist allWishlists = allWishlist.get(position);
        holder.wishlistViewBinding.PName.setText(allWishlists.getProductName());
        holder.wishlistViewBinding.textView6.setText(allWishlists.getShortDescription());
        holder.wishlistViewBinding.textView7.setText(allWishlists.getMrp());

        Glide.with(activity)
                .load(allWishlists.getImageURL())
                .thumbnail(0.5f).placeholder(R.drawable.box_two)
                .into(holder.wishlistViewBinding.imageView4);


        holder.wishlistViewBinding.imageView14.setOnClickListener(view -> {


            AddtoWishlist addtoWishList = new AddtoWishlist();
            addtoWishList.setProductInfoCode(allWishlists.getProductInfoCode());
            addtoWishList.setMemberId(String.valueOf(utils.getPrimaryUser(activity).getMemberId()));
            String wishlistStatus = "0";
            addtoWishList(addtoWishList, wishlistStatus);

//              allWishlist.clear();

            allWishlist.remove(position);
            notifyDataSetChanged();
            Toast.makeText(activity, "Product Removed From Wishlist", Toast.LENGTH_SHORT).show();
        });

        holder.wishlistViewBinding.button9.setOnClickListener(view -> {


            AddToCartModel addToCartModel = new AddToCartModel();
            addToCartModel.setProductInfoCode(allWishlists.getProductInfoCode());
            addToCartModel.setMemberId(String.valueOf(utils.getPrimaryUser(activity).getMemberId()));
            String quantity = "1";
            addToCart(addToCartModel, quantity);


        });
        holder.wishlistViewBinding.wishlistview.setOnClickListener(view -> {
            User user = new User();
            Bundle bundle = new Bundle();
            bundle.putInt("productID", allWishlists.getProductId());
            bundle.putString("member", String.valueOf(user.getMemberId()));
            bundle.putString("Pinfo", allWishlists.getProductInfoCode());

            PatientDashboard.getInstance().navigate(R.id.action_fragmentAllWishLIstProduct_to_productDetailsFragment, bundle);

        });

    }

    public void addtoWishList(AddtoWishlist addtoWishlist, String wishlistStatus) {

        addtoWishlist.setIsWhislist(wishlistStatus);


        ApiUtils.addtowishlist(addtoWishlist, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {


            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailed: Add To WishList " + throwable.getLocalizedMessage());

            }
        });


    }


    public void addToCart(AddToCartModel addToCartModel, String quantity) {
        addToCartModel.setQuantity(quantity);
        ApiUtils.addtocart(addToCartModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                Toast.makeText(activity, "Product Added in your Cart", Toast.LENGTH_SHORT).show();
                PatientDashboard.getInstance().navigate(R.id.action_fragmentAllWishLIstProduct_to_cart_Details_Fragment);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onFailed: Add To Cart " + throwable.getLocalizedMessage());

            }
        });

    }

    @Override
    public int getItemCount() {
        return allWishlist.size();
    }

    public static class ViewholderVh extends RecyclerView.ViewHolder {

        WishlistViewBinding wishlistViewBinding;
        FragmentMyWishListBinding fragmentMyWishListBinding;
        ImageView imageView;

        public ViewholderVh(@NonNull WishlistViewBinding itemView) {
            super(itemView.getRoot());

            wishlistViewBinding = itemView;
        }
    }
}
