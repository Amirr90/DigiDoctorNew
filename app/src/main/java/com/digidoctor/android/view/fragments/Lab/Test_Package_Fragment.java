package com.digidoctor.android.view.fragments.Lab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.adapters.labadapter.HealthPackageListAdapter;
import com.digidoctor.android.databinding.TestPackagesFragmentBinding;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.viewHolder.LabViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test_Package_Fragment extends Fragment {
    NavController navController;

    TestPackagesFragmentBinding testPackagesFragmentBinding;
    HealthPackageListAdapter adapter;
    List<PackageDetail> packageDetailList;

    LabViewModel labViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        testPackagesFragmentBinding = TestPackagesFragmentBinding.inflate(getLayoutInflater());
        return testPackagesFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        //init LabViewModel
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);

        //init Adapter
        packageDetailList = new ArrayList<>();
        adapter = new HealthPackageListAdapter();


        //init RecyclerView
        testPackagesFragmentBinding.testrecyclerview.setAdapter(adapter);


        getPackageData();
    }

    private void getPackageData() {
        labViewModel.packageLiveData().observe(getViewLifecycleOwner(), packageDetails -> {
            packageDetailList.clear();
            if (packageDetails.isEmpty()) {
                Toast.makeText(requireActivity(), "Packages not found !!", Toast.LENGTH_SHORT).show();
            } else {
                adapter.submitList(packageDetailList);
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}
