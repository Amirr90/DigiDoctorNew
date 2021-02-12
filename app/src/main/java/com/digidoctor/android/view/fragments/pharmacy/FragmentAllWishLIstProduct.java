package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
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

import com.digidoctor.android.adapters.pharmacy.AllWishListAdapter;
import com.digidoctor.android.databinding.FragmentMyWishListBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.AllWishListProduct;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllWishLIstProduct extends Fragment {
    public static final String TAG = "FragmentAllWishLIstProduct";

    FragmentMyWishListBinding fragmentMyWishListBinding;
    NavController navController;
    AllWishListAdapter allWishListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentMyWishListBinding = FragmentMyWishListBinding.inflate(getLayoutInflater());
        return fragmentMyWishListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        navController = Navigation.findNavController(view);

        final List<AllWishListProduct.AllWishlist> allWishlists = new ArrayList<>();

        allWishListAdapter = new AllWishListAdapter(allWishlists, requireActivity());

        fragmentMyWishListBinding.wishrec.setAdapter(allWishListAdapter);

        ApiUtils.getwish(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<AllWishListProduct.AllWishlist> models = (List<AllWishListProduct.AllWishlist>) o;

                if (models.isEmpty())
                    return;


                Log.d("TAG", "onSuccess:" + models.get(0).getProductName());
                allWishlists.clear();
                allWishlists.addAll(models);
                int i = allWishListAdapter.getItemCount();

                fragmentMyWishListBinding.textView52.setText(i + " items in wishlist");

                allWishListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
