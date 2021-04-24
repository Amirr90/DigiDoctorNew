
package com.digidoctor.android.view.fragments.lab;

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

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.labadapter.HealthPackageListAdapter;
import com.digidoctor.android.databinding.TestPackagesFragmentBinding;
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.interfaces.PackagesInterface;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Cart;
import com.digidoctor.android.viewHolder.LabViewModel;

import java.util.Objects;

public class Test_Package_Fragment extends Fragment implements CartInterface, PackagesInterface {
    NavController navController;

    TestPackagesFragmentBinding testPackagesFragmentBinding;
    HealthPackageListAdapter adapter;
    LabViewModel labViewModel;
    Cart cart;


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


        //int cart Class
        cart = new Cart(requireActivity(), this);

        //init Adapter
        adapter = new HealthPackageListAdapter(cart,this);


        //init RecyclerView
        testPackagesFragmentBinding.testrecyclerview.setAdapter(adapter);

        getPackageData();
    }

    private void getPackageData() {
        AppUtils.showRequestDialog(requireActivity());
        labViewModel.packageLiveData().observe(getViewLifecycleOwner(), packageDetails -> {
            if (packageDetails.isEmpty()) {
                Toast.makeText(requireActivity(), "Packages not found !!", Toast.LENGTH_SHORT).show();
            } else {
                adapter.submitList(packageDetails);
            }

            AppUtils.hideDialog();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void onCartItemAdded(Object obj) {
        Toast.makeText(requireActivity(), getString(R.string.added_successfully), Toast.LENGTH_SHORT).show();
        getPackageData();

    }

    @Override
    public void onCartItemDeleted(Object obj) {

    }

    @Override
    public void cartItem(Object obj) {
        navController.navigate(R.id.action_test_Package_Fragment_to_fragmentCartListLab);
    }

    @Override
    public void onItemClicked(Object obj) {
        Test_Package_FragmentDirections.ActionTestPackageFragmentToTestDetailsFRagment action = Test_Package_FragmentDirections.actionTestPackageFragmentToTestDetailsFRagment();
        action.setPackageId((String) obj);
        navController.navigate(action);
    }
}
