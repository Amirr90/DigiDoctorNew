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

import com.digidoctor.android.adapters.pharmacy.filltervarientadapter;
import com.digidoctor.android.databinding.Fillterfragment1Binding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.getfilltervarentmodel;
import com.digidoctor.android.utility.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class filltervarientfragment1 extends Fragment {

    NavController navController;
    Fillterfragment1Binding fillterfragment1Binding;

    filltervarientadapter adapter;

    final List<getfilltervarentmodel.filltervarientList> allvarient = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fillterfragment1Binding = Fillterfragment1Binding.inflate(getLayoutInflater());
        return fillterfragment1Binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        navController = Navigation.findNavController(view);

        adapter = new filltervarientadapter(allvarient, requireActivity());

        fillterfragment1Binding.recyclerView.setAdapter(adapter);


        ApiUtils.getfilltervarient(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<getfilltervarentmodel.filltervarientList> models = (List<getfilltervarentmodel.filltervarientList>) o;
                allvarient.addAll(models);
                adapter.notifyDataSetChanged();
                Toast.makeText(requireActivity(), ""+models.get(0).getVarientName(), Toast.LENGTH_SHORT).show();

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


//

    }
}
