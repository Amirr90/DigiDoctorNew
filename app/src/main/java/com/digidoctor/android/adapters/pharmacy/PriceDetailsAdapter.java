package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentCartListBinding;
import com.digidoctor.android.model.pharmacyModel.CartDetailsResponse;

import java.util.List;

public class PriceDetailsAdapter extends RecyclerView.Adapter<PriceDetailsAdapter.PriceDetailsVH> {
    private List<CartDetailsResponse.GetCartDetails.GetPriceDetails> getallPrice;
    private static final String TAG = "PriceDetailsAdapter";
    Activity activity;
    Context ctx;
    FragmentCartListBinding fragmentCartListBinding;

    public PriceDetailsAdapter(List<CartDetailsResponse.GetCartDetails.GetPriceDetails> getallPrice, Context ctx) {
        this.getallPrice = getallPrice;
        this.ctx = ctx;

    }

    @NonNull
    @Override
    public PriceDetailsAdapter.PriceDetailsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentCartListBinding fragmentCartListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.fragment_cart_list, parent, false);
        return new PriceDetailsAdapter.PriceDetailsVH(fragmentCartListBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull PriceDetailsAdapter.PriceDetailsVH holder, int position) {
        final CartDetailsResponse.GetCartDetails.GetPriceDetails nope = getallPrice.get(position);


    }

    @Override
    public int getItemCount() {

        return getallPrice.size();
    }

    public class PriceDetailsVH extends RecyclerView.ViewHolder {
        FragmentCartListBinding fragmentCartListBinding;

        public PriceDetailsVH(@NonNull FragmentCartListBinding fragmentCartListBinding) {
            super(fragmentCartListBinding.getRoot());
            this.fragmentCartListBinding = fragmentCartListBinding;
        }
    }
}
