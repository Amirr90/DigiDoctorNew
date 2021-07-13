package com.digidoctor.android.view.fragments.lab;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;


import com.digidoctor.android.databinding.FragmentLabOrdersBinding;
import com.digidoctor.android.databinding.LabOrderListViewLayoutBinding;
import com.digidoctor.android.interfaces.LabOrderInterface;
import com.digidoctor.android.model.labmodel.LabOrderModel;
import com.digidoctor.android.model.labmodel.LabOrderRes;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.getUserForBooking;


public class LabOrdersFragment extends Fragment {
    private static final String TAG = "LabOrdersFragment";

    FragmentLabOrdersBinding binding;
    NavController navController;
    LabOrderAdapter adapter;

    int firsttime = 1;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLabOrdersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        AppUtils.showRequestDialog(requireActivity());
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                getData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getData(0);
        // binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0));
    }

    private void getData(int position) {
        LabOrderModel labModel = new LabOrderModel();
        labModel.setMemberId(String.valueOf(getUserForBooking(requireActivity()).getMemberId()));
        ApiUtils.getOrders(labModel, new LabOrderInterface() {
            @Override
            public void onOrderPlaced(Object obj) {

            }

            @Override
            public void onFailed(Object obj) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Failed To get Orders, try again !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void orders(Object obj) {
                AppUtils.hideDialog();
                try {
                    List<LabOrderRes.OrderType> orderTypeList = (List<LabOrderRes.OrderType>) obj;
                    if (null != orderTypeList && !orderTypeList.isEmpty()) {
                        List<LabOrderModel> labOrderModels = orderTypeList.get(0).getLabVisiit();


                        if (position == 0) {
                            adapter = new LabOrderAdapter(orderTypeList.get(0).getHomeVisit());
                            binding.recLabOrder.setAdapter(adapter);
                        } else if (position == 1) {
                            adapter = new LabOrderAdapter(orderTypeList.get(0).getLabVisiit());
                            binding.recLabOrder.setAdapter(adapter);
                        } else {
                            adapter = new LabOrderAdapter(orderTypeList.get(0).getLabVisiit());
                            binding.recLabOrder.setAdapter(adapter);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "orders: " + e.getLocalizedMessage());
                }


            }

            @Override
            public void onCancelOrder(Object obj) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        //  getData();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }


    private static class LabOrderAdapter extends RecyclerView.Adapter<LabOrderAdapter.LabVH> {
        List<LabOrderModel> labOrderModels;

        public LabOrderAdapter(List<LabOrderModel> labOrderModels) {
            this.labOrderModels = labOrderModels;
        }

        @NonNull
        @Override
        public LabOrderAdapter.LabVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LabOrderListViewLayoutBinding binding = LabOrderListViewLayoutBinding.inflate(inflater, parent, false);
            return new LabVH(binding);

        }

        @Override
        public void onBindViewHolder(@NonNull LabOrderAdapter.LabVH holder, int position) {

            holder.binding.setLabOrder(labOrderModels.get(position));
        }

        @Override
        public int getItemCount() {
            return null == labOrderModels ? 0 : labOrderModels.size();
        }

        public static class LabVH extends RecyclerView.ViewHolder {
            LabOrderListViewLayoutBinding binding;

            public LabVH(@NonNull LabOrderListViewLayoutBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}