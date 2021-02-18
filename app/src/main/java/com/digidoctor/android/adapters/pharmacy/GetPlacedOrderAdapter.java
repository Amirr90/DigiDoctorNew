package com.digidoctor.android.adapters.pharmacy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.OrderListViewBinding;
import com.digidoctor.android.model.GetOrderRes;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class GetPlacedOrderAdapter extends RecyclerView.Adapter<GetPlacedOrderAdapter.GetPlacedOrderAdapterVH> {


    Activity activity;
    List<GetOrderRes.getplacedorder> getplacedorders;

    public GetPlacedOrderAdapter(Activity activity, List<GetOrderRes.getplacedorder> getplacedorders) {
        this.activity = activity;
        this.getplacedorders = getplacedorders;
    }


    @NonNull
    @Override
    public GetPlacedOrderAdapter.GetPlacedOrderAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderListViewBinding orderListViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_view, parent, false);
        return new GetPlacedOrderAdapter.GetPlacedOrderAdapterVH(orderListViewBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull GetPlacedOrderAdapterVH holder, int position) {


        final GetOrderRes.getplacedorder getplacedorder = getplacedorders.get(position);
        holder.orderListViewBinding.textView8.setText(getplacedorder.getBrandName());
        holder.orderListViewBinding.textView9.setText(getplacedorder.getProductName());
        holder.orderListViewBinding.textView10.setText(getplacedorder.getOrderStatus());
        holder.orderListViewBinding.textView140.setText("Expected Delivery: " + getplacedorder.getExpectedDelievery());
        Glide.with(activity).load(getplacedorder.getImagePath()).placeholder(R.drawable.box_two)
                .thumbnail(0.5f).into(holder.orderListViewBinding.imageView5);


        if (getplacedorder.getOrderStatus().equals("Cancelled")) {
            holder.orderListViewBinding.textView10.setTextColor(Color.RED);
            holder.orderListViewBinding.textView10.setText("Cancelled");
            holder.orderListViewBinding.textView140.setVisibility(View.GONE);
        } else {
            holder.orderListViewBinding.textView10.setTextColor(Color.parseColor("#008000"));
        }

        if (getplacedorder.getOrderStatus().equals("Delivered")) {
            holder.orderListViewBinding.textView140.setVisibility(View.GONE);
            holder.orderListViewBinding.textView10.setText(getplacedorder.getOrderStatus());
        }

        holder.orderListViewBinding.orderplaced.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("OrderDetailsID", getplacedorder.getOrderDetailsId());
            PatientDashboard.getInstance().navigate(R.id.action_getPlacedOrderFragment_to_orderDetailsFragment, bundle);
        });

    }


    @Override
    public int getItemCount() {
        return getplacedorders.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class GetPlacedOrderAdapterVH extends RecyclerView.ViewHolder {

        OrderListViewBinding orderListViewBinding;


        public GetPlacedOrderAdapterVH(@NonNull OrderListViewBinding itemView) {
            super(itemView.getRoot());

            orderListViewBinding = itemView;
        }
    }
}
