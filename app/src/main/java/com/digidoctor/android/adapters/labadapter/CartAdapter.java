package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.CarListLabViewLayoutBinding;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    List<CartModel> cartModelList;
    Cart cart;

    public CartAdapter(List<CartModel> cartModelList, Cart cart) {
        this.cartModelList = cartModelList;
        this.cart = cart;
    }

    @NonNull
    @Override
    public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CarListLabViewLayoutBinding binding = CarListLabViewLayoutBinding.inflate(layoutInflater, parent, false);
        return new CartVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartVH holder, int position) {
        CartModel cartModel = cartModelList.get(position);
        holder.binding.setCartModel(cartModel);

        holder.binding.btnDeleteCartItem.setOnClickListener(v -> {
            AppUtils.showRequestDialog(App.context);
            cart.deleteItemFromCart(cartModel.getCartId());
        });


    }

    @Override
    public int getItemCount() {
        if (null == cartModelList)
            cartModelList = new ArrayList<>();
        return cartModelList.size();
    }

    public static class CartVH extends RecyclerView.ViewHolder {
        CarListLabViewLayoutBinding binding;

        public CartVH(@NonNull CarListLabViewLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
