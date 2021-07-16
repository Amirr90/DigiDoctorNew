package com.digidoctor.android.view.fragments.lab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.labadapter.CartAdapter;
import com.digidoctor.android.databinding.FragmentCartListLabBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Cart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FragmentCartListLab extends Fragment implements CartInterface, AdapterInterface {
    private static final String TAG = "FragmentCartListLab";


    FragmentCartListLabBinding binding;
    NavController navController;
    Cart cart;
    CartAdapter adapter;
    List<CartModel> cartModelList;
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartListLabBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        //init ProgressDialog
        progressDialog = new ProgressDialog(requireContext());

        //init Cart Interface & Class
        cart = new Cart(requireActivity(), this);
        cart.getCart();


        //init Adapter
        cartModelList = new ArrayList<>();
        adapter = new CartAdapter(cartModelList, cart, this);

        //init Recyclerview
        binding.recCart.setAdapter(adapter);


        binding.btnContinueToLab.setOnClickListener(v -> navController.navigate(R.id.action_fragmentCartListLab_to_timeSlotForLabFragment));

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onFailed(String msg) {
        binding.mainCartViewGroup.setVisibility(View.GONE);
        binding.textView251.setText("Unable to get cart Data");
        binding.progressBar11.setVisibility(View.GONE);
    }

    @Override
    public void onCartItemAdded(Object obj) {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onCartItemDeleted(Object obj) {
        AppUtils.hideDialog();
        cart.getCart();
        Toast.makeText(requireActivity(), "Item Deleted Successfully !!", Toast.LENGTH_SHORT).show();

        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        List<CartModel> cartModels = (List<CartModel>) obj;
        if (cartModels.isEmpty()) {
            binding.mainCartViewGroup.setVisibility(View.GONE);
            binding.groupLoadingLay.setVisibility(View.VISIBLE);
            binding.textView251.setText("No Item in cart");
            binding.progressBar11.setVisibility(View.GONE);
        }


    }

    @Override
    public void cartItem(Object obj) {
        this.cartModelList.clear();

        List<CartModel> cartModelList = (List<CartModel>) obj;

        if (null != cartModelList && !cartModelList.isEmpty()) {
            this.cartModelList.addAll(cartModelList);
            adapter.notifyDataSetChanged();
            binding.mainCartViewGroup.setVisibility(View.VISIBLE);
            binding.groupLoadingLay.setVisibility(View.GONE);
        } else {
            binding.mainCartViewGroup.setVisibility(View.GONE);
            binding.textView251.setText("No Item in cart");
            binding.progressBar11.setVisibility(View.GONE);
            // Toast.makeText(requireActivity(), "Cart Is empty !!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onItemClicked(Object o) {
        CartModel cartModel = (CartModel) o;
        new AlertDialog.Builder(requireActivity()).setMessage("Delete from cart??")
                .setPositiveButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    progressDialog.setMessage("Deleting cart item");
                    progressDialog.show();
                    cart.deleteItemFromCart(cartModel.getCartId());
                }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).show();

    }
}