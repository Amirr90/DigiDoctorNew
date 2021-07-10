package com.digidoctor.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.digidoctor.android.adapters.WalletAdapter;
import com.digidoctor.android.databinding.FragmentWalletBinding;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

public class WalletFragment extends Fragment {


    PatientViewModel viewModel;
    FragmentWalletBinding binding;
    WalletAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWalletBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        adapter = new WalletAdapter();
        binding.recWallet.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        binding.recWallet.setAdapter(adapter);
        viewModel.getWallet().observe(getViewLifecycleOwner(), walletModels -> adapter.submitList(walletModels));
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.showToolbar(requireActivity());
    }
}