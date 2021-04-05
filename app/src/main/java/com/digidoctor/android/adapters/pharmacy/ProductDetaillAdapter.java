package com.digidoctor.android.adapters.pharmacy;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentProductDetailsBinding;


public class ProductDetaillAdapter extends RecyclerView.Adapter<ProductDetaillAdapter.ProductDetaillVH> {
    Context ctx;

    public ProductDetaillAdapter(Context ctx) {
        this.ctx = ctx;

    }


    @NonNull
    @Override
    public ProductDetaillAdapter.ProductDetaillVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentProductDetailsBinding fragmentProductDetailsBinding = FragmentProductDetailsBinding.inflate(inflater, parent, false);
        return new ProductDetaillVH(fragmentProductDetailsBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetaillAdapter.ProductDetaillVH holder, int position) {
        holder.fragmentProductDetailsBinding.textView5.setPaintFlags(holder.fragmentProductDetailsBinding.textView5.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ProductDetaillVH extends RecyclerView.ViewHolder {
        FragmentProductDetailsBinding fragmentProductDetailsBinding;
        public ProductDetaillVH(@NonNull FragmentProductDetailsBinding itemView) {
            super(itemView.getRoot());


        }
    }
}
