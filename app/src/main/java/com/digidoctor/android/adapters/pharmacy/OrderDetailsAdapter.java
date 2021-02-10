package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentOrderDetailsBinding;
import com.digidoctor.android.model.pharmacyModel.OrderDetailModel;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsAdapterVH> {

    List<OrderDetailModel.Orderdetaillist.ProductDetails> getorder;
    Activity activity;

    public OrderDetailsAdapter(List<OrderDetailModel.Orderdetaillist.ProductDetails> getorder, Activity activity) {
        this.getorder = getorder;
        this.activity = activity;
    }

    public OrderDetailsAdapter.OrderDetailsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentOrderDetailsBinding fragmentProductDetailsBinding = FragmentOrderDetailsBinding.inflate(inflater, parent, false);
        return new OrderDetailsAdapter.OrderDetailsAdapterVH(fragmentProductDetailsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.OrderDetailsAdapterVH holder, int position) {
        OrderDetailModel.Orderdetaillist.ProductDetails orderdetaillist = getorder.get(position);
        // holder.fragmentOrderDetailsBinding.textView113.setText(orderdetaillist.getBrandName());
        holder.fragmentOrderDetailsBinding.textView113.setText(orderdetaillist.getBrandName());
        // holder.fragmentOrderDetailsBinding.setOrderDetails(orderdetaillist);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OrderDetailsAdapterVH extends RecyclerView.ViewHolder {


        FragmentOrderDetailsBinding fragmentOrderDetailsBinding;

        public OrderDetailsAdapterVH(@NonNull FragmentOrderDetailsBinding itemView) {
            super(itemView.getRoot());

            fragmentOrderDetailsBinding = itemView;
        }
    }
}
