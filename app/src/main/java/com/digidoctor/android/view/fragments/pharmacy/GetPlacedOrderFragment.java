package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
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

import com.digidoctor.android.adapters.pharmacy.GetPlacedOrderAdapter;
import com.digidoctor.android.databinding.FragmentMyOrderListBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.patientModel.GetOrderRes;
import com.digidoctor.android.utility.ApiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetPlacedOrderFragment extends Fragment {
    private static final String TAG = "GetPlacedOrderFragment";
    FragmentMyOrderListBinding
            fragmentMyOrderListBinding;
    NavController navController;
    GetPlacedOrderAdapter getPlacedOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentMyOrderListBinding = FragmentMyOrderListBinding.inflate(getLayoutInflater());
        return fragmentMyOrderListBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        final List<GetOrderRes.getplacedorder> getplacedorders = new ArrayList<>();

        getPlacedOrderAdapter = new GetPlacedOrderAdapter(requireActivity(), getplacedorders);

        fragmentMyOrderListBinding.getplacedorder.setAdapter(getPlacedOrderAdapter);
        getPlacedOrderAdapter.notifyDataSetChanged();

        ApiUtils.getplacedorder(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<GetOrderRes.getplacedorder> models = (List<GetOrderRes.getplacedorder>) o;
                //   Log.d(TAG, "CategoryList: " + models.get(0).getCategoryList().toString());
                getplacedorders.clear();

                getplacedorders.addAll(models);
                Collections.reverse(getplacedorders);
                getPlacedOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
