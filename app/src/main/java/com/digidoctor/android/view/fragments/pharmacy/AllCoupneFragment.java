package com.digidoctor.android.view.fragments.pharmacy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.pharmacy.AllCoupneAdapter;
import com.digidoctor.android.databinding.FragmentApplyCoupneBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.AllCoupneModelResponse;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.view.fragments.pharmacy.Cart_Details_Fragment.COUPON_CODE;

public class AllCoupneFragment extends Fragment implements AdapterInterface {
    FragmentApplyCoupneBinding fragmentApplyCoupneBinding;
    NavController navController;
    AllCoupneAdapter allCoupneAdapter;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        final List<AllCoupneModelResponse.GetCoupnedetails> coupne = new ArrayList<>();

        allCoupneAdapter = new AllCoupneAdapter(coupne, requireActivity(), this);

        fragmentApplyCoupneBinding.recyclerView.setAdapter(allCoupneAdapter);


        ApiUtils.getcoupne(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<AllCoupneModelResponse.GetCoupnedetails> models = (List<AllCoupneModelResponse.GetCoupnedetails>) o;

                coupne.addAll(models);
                allCoupneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), ""+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), ""+throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentApplyCoupneBinding = FragmentApplyCoupneBinding.inflate(getLayoutInflater());

        return fragmentApplyCoupneBinding.getRoot();
    }

    @Override
    public void onItemClicked(Object o) {
        String code = (String) o;
        Bundle bundle = new Bundle();
        bundle.putString(COUPON_CODE, code);
        navController.navigate(R.id.action_allCoupneFragment_to_cart_Details_Fragment, bundle);
    }
}
