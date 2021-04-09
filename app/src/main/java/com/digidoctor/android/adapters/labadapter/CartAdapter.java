package com.digidoctor.android.adapters.labadapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.CarListLabViewLayoutBinding;
import com.digidoctor.android.databinding.TestNameViewBinding;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartVH> {
    private static final String TAG = "CartAdapter";
    List<CartModel> cartModelList;
    Cart cart;
    InnerTestListAdapter innerTestListAdapter;
    Activity activity;

    public CartAdapter(List<CartModel> cartModelList, Cart cart, Activity activity) {
        this.cartModelList = cartModelList;
        this.cart = cart;
        this.activity = activity;

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
            new AlertDialog.Builder(activity).setMessage("Delete from cart??")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        cart.deleteItemFromCart(cartModel.getCartId());
                    }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();

        });

        try {
            List<CartModel.Test> test = cartModel.getPackageTestList();
            innerTestListAdapter = new InnerTestListAdapter(test);
            holder.binding.recTest.setAdapter(innerTestListAdapter);
            innerTestListAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onBindViewHolder: " + e.getLocalizedMessage());
        }

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

    private static class InnerTestListAdapter extends RecyclerView.Adapter<InnerTestListAdapter.InnerVH> {
        List<CartModel.Test> test;

        public InnerTestListAdapter(List<CartModel.Test> test) {
            this.test = test;
        }

        @NonNull
        @Override
        public InnerTestListAdapter.InnerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            TestNameViewBinding binding = TestNameViewBinding.inflate(inflater, parent, false);
            return new InnerVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull InnerTestListAdapter.InnerVH holder, int position) {

            holder.binding.setTest(test.get(position));
            holder.binding.getRoot().setOnClickListener(v -> {
                // Toast.makeText(App.context, test.get(position).getId(), Toast.LENGTH_SHORT).show();
            });
        }

        @Override
        public int getItemCount() {
            if (null == test)
                test = new ArrayList<>();
            return test.size();
        }

        public static class InnerVH extends RecyclerView.ViewHolder {
            TestNameViewBinding binding;

            public InnerVH(@NonNull TestNameViewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
