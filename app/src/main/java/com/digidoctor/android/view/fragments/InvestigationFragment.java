package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.InvestigationAdapter;
import com.digidoctor.android.databinding.FragmentInvestigationBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.InvestigationModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InvestigationFragment extends Fragment implements OnClickListener {

    FragmentInvestigationBinding investigationBinding;
    NavController navController;

    PatientViewModel viewModel;

    InvestigationAdapter adapter;
    List<InvestigationModel> submitList;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        investigationBinding = FragmentInvestigationBinding.inflate(getLayoutInflater());
        return investigationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        submitList = new ArrayList<>();
        adapter = new InvestigationAdapter(this, submitList);

        investigationBinding.recInvestigation.setAdapter(adapter);

        AppUtils.showRequestDialog(requireActivity());
        User user = new User();
        user.setMemberId(utils.getPrimaryUser(requireActivity()).getId());
        viewModel.getInvestigationData(user).observe(getViewLifecycleOwner(), investigationModels -> {

            submitList.addAll(investigationModels);
            adapter.notifyDataSetChanged();
            AppUtils.hideDialog();
        });

        investigationBinding.tvAddManually.setOnClickListener(view1 -> navController.navigate(R.id.action_investigationFragment_to_addInvestigationFragment));

    }


    @Override
    public void onItemClick(Object object) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

    }
}