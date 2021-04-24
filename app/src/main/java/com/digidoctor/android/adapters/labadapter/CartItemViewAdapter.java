package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.ReviewOrderCartViewBinding;
import com.digidoctor.android.model.labmodel.CartModel;

import java.util.List;

public class CartItemViewAdapter extends RecyclerView.Adapter<CartItemViewAdapter.CartVH> {
    List<CartModel> cartModelList;

    public CartItemViewAdapter(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartItemViewAdapter.CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewOrderCartViewBinding binding = ReviewOrderCartViewBinding.inflate(inflater, parent, false);
        return new CartVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewAdapter.CartVH holder, int position) {
        holder.binding.setCartModel(cartModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return cartModelList == null ? 0 : cartModelList.size();
    }

    public static class CartVH extends RecyclerView.ViewHolder {
        ReviewOrderCartViewBinding binding;

        public CartVH(@NonNull ReviewOrderCartViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
