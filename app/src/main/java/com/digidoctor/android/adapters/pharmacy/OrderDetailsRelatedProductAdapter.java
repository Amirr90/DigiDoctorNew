package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.OrderListViewBinding;
import com.digidoctor.android.model.pharmacyModel.OrderDetailModel;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class OrderDetailsRelatedProductAdapter extends RecyclerView.Adapter<OrderDetailsRelatedProductAdapter.ViewholderVH> {
    Activity activity;
    List<OrderDetailModel.Orderdetaillist.relatedProducts> getrelatedproducts;

    public OrderDetailsRelatedProductAdapter(Activity activity, List<OrderDetailModel.Orderdetaillist.relatedProducts> getrelatedproducts) {
        this.activity = activity;
        this.getrelatedproducts = getrelatedproducts;
    }

    @NonNull
    @Override
    public ViewholderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderListViewBinding orderListViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_view, parent, false);
        return new OrderDetailsRelatedProductAdapter.ViewholderVH(orderListViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewholderVH holder, int position) {

        final OrderDetailModel.Orderdetaillist.relatedProducts relatedProducts = getrelatedproducts.get(position);

        holder.orderListViewBinding.textView8.setText(relatedProducts.getBrandName());
        holder.orderListViewBinding.textView9.setText(relatedProducts.getProductName());

        holder.orderListViewBinding.textView140.setText("Expected Delivery:" + relatedProducts.getExpectedDelievery());

        Glide.with(activity).load(relatedProducts.getImagePath()).placeholder(R.drawable.box_two)
                .into(holder.orderListViewBinding.imageView5);


        holder.orderListViewBinding.orderplaced.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("OrderDetailsID", String.valueOf(relatedProducts.getId()));
            PatientDashboard.getInstance().navigate(R.id.action_orderDetailsFragment_self, bundle);
        });
    }


    @Override
    public int getItemCount() {
        return getrelatedproducts.size();
    }

    public class ViewholderVH extends RecyclerView.ViewHolder {
        OrderListViewBinding orderListViewBinding;


        public ViewholderVH(@NonNull OrderListViewBinding itemView) {

            super(itemView.getRoot());

            orderListViewBinding = itemView;
        }
    }
}
