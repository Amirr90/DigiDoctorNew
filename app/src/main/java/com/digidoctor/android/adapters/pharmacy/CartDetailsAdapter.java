package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.CartViewBinding;
import com.digidoctor.android.interfaces.ProductInterface;
import com.digidoctor.android.model.pharmacyModel.CartDetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class CartDetailsAdapter extends RecyclerView.Adapter<CartDetailsAdapter.CartDetailsAdapterVH> {
    public static final String TAG = "CartDetailsAdapter";
    public List<CartDetailsResponse.GetCartDetails> getcart;
    Activity activity;
    ProductInterface productInterface;

    public CartDetailsAdapter(List<CartDetailsResponse.GetCartDetails> getcart, Activity activity, ProductInterface productInterface) {
        this.getcart = getcart;
        this.activity = activity;
        this.productInterface = productInterface;
    }

    @NonNull
    @Override
    public CartDetailsAdapter.CartDetailsAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartViewBinding cartViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cart_view, parent, false);
        return new CartDetailsAdapterVH(cartViewBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull final CartDetailsAdapter.CartDetailsAdapterVH holder, final int position) {
        final CartDetailsResponse.GetCartDetails gc = getcart.get(position);
        holder.cartViewBinding.textView8.setText(gc.getBrandName());
        holder.cartViewBinding.textView9.setText(gc.getProductName());
        holder.cartViewBinding.textView10.setText(String.valueOf(gc.getAmount()));

        Glide.with(activity).load(gc.getProductImage()).placeholder(R.drawable.box_two).
                thumbnail(0.5f).into(holder.cartViewBinding.imageView5);

        holder.cartViewBinding.textView263.setText(gc.getQuantity());

        // holder.cartViewBinding.elegantNumberButton.setRange(0, 5);


        holder.cartViewBinding.TVcartremove.setOnClickListener(view -> {
            String productName = gc.getBrandName();
            new AlertDialog.Builder(activity).setTitle("Delete Product")
                    .setMessage("Are you sure want to remove " + productName + " from cart?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {

                                productInterface.onDeleteItemClick(gc);
                                notifyItemRemoved(position);
                                notifyDataSetChanged();

                            }
                    ).setNegativeButton("No", (dialogInterface, i) -> {

            }).show();


        });


        holder.cartViewBinding.textView262.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(String.valueOf(holder.cartViewBinding.textView263.getText()));
                if (count == 5) {
                    Toast.makeText(activity, "you have reached with maximum limit", Toast.LENGTH_SHORT).show();
                    return;
                }
                count++;
                holder.cartViewBinding.textView263.setText(String.valueOf(count));
                productInterface.onCartItemUpdate(gc, count);
            }
        });

        holder.cartViewBinding.textView261.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(String.valueOf(holder.cartViewBinding.textView263.getText()));

                if (count == 1) {
                    holder.cartViewBinding.textView263.setText("1");

                } else {
                    count -= 1;
                    holder.cartViewBinding.textView263.setText("" + count);
                    productInterface.onCartItemUpdate(gc, count);
                }


//                int count = Integer.parseInt(String.valueOf(holder.cartViewBinding.textView263.getText()));
//                count++;
//                holder.cartViewBinding.textView263.setText(String.valueOf(count));
//
//                productInterface.onCartItemUpdate(gc, count);
            }
        });

//        holder.cartViewBinding.elegantNumberButton.setOnValueChangeListener((view, oldValue, newValue) -> {
//
//
//            if (newValue == 5) {
//                Toast.makeText(activity, "you have reached with maximum limit", Toast.LENGTH_SHORT).show();
//            }
//
//            if (newValue == 0) {
//                new AlertDialog.Builder(activity)
//                        .setMessage("Product Will be removed from your cart.")
//                        .setPositiveButton("Yes", (dialogInterface, i) -> {
//                            productInterface.onDeleteItemClick(gc);
//                            //  getcart.remove(position);
//                            notifyItemRemoved(position);
//                            notifyDataSetChanged();
//                        }).setNegativeButton("No", (dialogInterface, i) -> holder.cartViewBinding.elegantNumberButton.setNumber(gc.getQuantity())).show();
//
//
//            } else {
//
//                productInterface.onCartItemUpdate(gc, newValue);
//            }
//
//        });

    }


    @Override
    public int getItemCount() {

        if (null == getcart)
            getcart = new ArrayList<>();
        return getcart.size();
    }

    public static class CartDetailsAdapterVH extends RecyclerView.ViewHolder {
        CartViewBinding cartViewBinding;

        public CartDetailsAdapterVH(@NonNull CartViewBinding itemView) {

            super(itemView.getRoot());

            cartViewBinding = itemView;
        }
    }
}
