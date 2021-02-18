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

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.pharmacy.AddressAdapter;
import com.digidoctor.android.databinding.GetalladdressBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllAddressFragment extends Fragment {

    GetalladdressBinding getalladdressBinding;
    NavController navController;
    AddressAdapter addressAdapter;
    List<getaddressModel.getaddressDetails> addAdressModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getalladdressBinding = GetalladdressBinding.inflate(getLayoutInflater());

        return getalladdressBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        navController = Navigation.findNavController(view);


        addressAdapter = new AddressAdapter(addAdressModels, requireActivity());

        getalladdressBinding.AllAddressrecyclerview.setAdapter(addressAdapter);


        AppUtils.hideDialog();


        getalladdressBinding.textView143.setOnClickListener(view1 -> navController.navigate(R.id.action_allAddressFragment_to_addressFragment));


        getaddress();
    }

    public void getaddress() {

        ApiUtils.getadddetails(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<getaddressModel.getaddressDetails> models = (List<getaddressModel.getaddressDetails>) o;


                if (models.isEmpty()) {
                    getalladdressBinding.AllAddressrecyclerview.setVisibility(View.GONE);
                    getalladdressBinding.noaddress.setVisibility(View.VISIBLE);
                }

                addAdressModels.clear();
                addAdressModels.addAll(models);
                addressAdapter.notifyDataSetChanged();


            }


            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();

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
        AppUtils.hideDialog();
        getaddress();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }
}
