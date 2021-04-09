package com.digidoctor.android.view.fragments.Lab;

import android.os.Bundle;
import android.util.Log;
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
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Cart;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FragmentCartListLab extends Fragment implements CartInterface {
    private static final String TAG = "FragmentCartListLab";


    FragmentCartListLabBinding binding;
    NavController navController;
    Cart cart;
    CartAdapter adapter;
    List<CartModel> cartModelList;


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


        //init Cart Interface & Class
        cart = new Cart(requireActivity(), this);
        cart.getCart();


        //init Adapter
        cartModelList = new ArrayList<>();
        adapter = new CartAdapter(cartModelList, cart, requireActivity());

        //init Recyclerview
        binding.recCart.setAdapter(adapter);


        binding.btnContinueToLab.setOnClickListener(v -> navController.navigate(R.id.action_fragmentCartListLab_to_allLabsFragment));

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onFailed(String msg) {
        Log.d(TAG, "onFailed: " + msg);
    }

    @Override
    public void onCartItemAdded(Object obj) {
        Log.d(TAG, "onCartItemAdded: ");
    }

    @Override
    public void onCartItemDeleted(Object obj) {
        AppUtils.hideDialog();
        Toast.makeText(requireActivity(), "Item Deleted Successfully !!", Toast.LENGTH_SHORT).show();
        cart.getCart();
    }

    @Override
    public void cartItem(Object obj) {
        this.cartModelList.clear();
        Log.d(TAG, "cartItem: " + obj);
        List<CartModel> cartModelList = (List<CartModel>) obj;
        if (null != cartModelList && !cartModelList.isEmpty()) {
            this.cartModelList.addAll(cartModelList);
        } else Toast.makeText(requireActivity(), "Cart Is empty !!", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
}