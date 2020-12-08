package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.VitalListAdapter;
import com.digidoctor.android.databinding.FragmentVitalChartBinding;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class VitalChartFragment extends Fragment {
    private static final String TAG = "VitalChartFragment";

    FragmentVitalChartBinding chartBinding;
    NavController navController;
    PatientViewModel viewModel;

    String VitalId = null;
    VitalListAdapter adapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        chartBinding = FragmentVitalChartBinding.inflate(getLayoutInflater());
        return chartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        VitalId = "-1";
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        final VitalModel vitalModel = new VitalModel();
        User user = utils.getPrimaryUser(requireActivity());
        vitalModel.setMemberId(String.valueOf(user.getId()));
        vitalModel.setVitalId(VitalId);

        adapter = new VitalListAdapter();
        chartBinding.recVitalList.setAdapter(adapter);
        chartBinding.recVitalList.addItemDecoration(new
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
        viewModel.getVitals(vitalModel, requireActivity()).observe(getViewLifecycleOwner(), new Observer<List<VitalResponse.VitalDateVise>>() {
            @Override
            public void onChanged(List<VitalResponse.VitalDateVise> vitalResponse) {
                Log.d(TAG, "onChanged: " + vitalResponse.get(0).getVitalDate());
                adapter.submitList(vitalResponse);
            }
        });
    }


}