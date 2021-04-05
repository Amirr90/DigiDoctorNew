package com.digidoctor.android.view.fragments.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.OrderplacedfragmentBinding;

public class OrderPLacedFRagment extends Fragment {
    OrderPLacedFRagment orderPLacedFRagment;
    OrderplacedfragmentBinding orderplacedfragmentBinding;
    NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        orderplacedfragmentBinding.textView133.setText("Congratulations! Your order " + getArguments().getString("orderNo") + " has been placed successfully");


        orderplacedfragmentBinding.button13.setOnClickListener(view1 -> navController.navigate(R.id.action_orderPLacedFRagment_to_onlinePharmacyFragment

        ));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        orderplacedfragmentBinding = OrderplacedfragmentBinding.inflate(getLayoutInflater());
        return orderplacedfragmentBinding.getRoot();

    }


}
