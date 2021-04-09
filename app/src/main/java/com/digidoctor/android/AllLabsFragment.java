package com.digidoctor.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.adapters.labadapter.ViewAllLabAdapter;
import com.digidoctor.android.databinding.FragmentAllLabsBinding;
import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.viewHolder.LabViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public class AllLabsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AllLabsFragment";

    FragmentAllLabsBinding binding;
    NavController navController;
    LabViewModel viewModel;
    ViewAllLabAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllLabsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        adapter = new ViewAllLabAdapter(this);
        binding.recLabs.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(LabViewModel.class);
        viewModel.getAllLabs().observe(getViewLifecycleOwner(), labModels -> {
            if (null != labModels) {
                adapter.submitList(labModels);
            } else Log.d(TAG, "onViewCreated: null");
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onClick(View v) {

    }
}