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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentTestDetailsBinding;
import com.digidoctor.android.databinding.TestincludelayoutviewBinding;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.viewHolder.LabViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestDetailsFragment extends Fragment {
    private static final String TAG = "TestDetailsFragment";
    NavController navController;

    FragmentTestDetailsBinding fragmentTestDetailsBinding;
    String packageId;
    LabViewModel labViewModel;
    TestListAdapter testListAdapter;
    List<PackageModel.GroupDetails> detailsList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentTestDetailsBinding = FragmentTestDetailsBinding.inflate(getLayoutInflater());
        return fragmentTestDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if (getArguments() == null)
            return;

        //packageId = TestDetailsFragmentArgs.fromBundle(getArguments()).getPackageId();
        packageId = getArguments().getString("packageID");

        Log.d(TAG, "packageID: " + packageId);

        //init TestDetails Adapter
        detailsList = new ArrayList<>();
        testListAdapter = new TestListAdapter(detailsList);
        fragmentTestDetailsBinding.recTestList.setAdapter(testListAdapter);


        AppUtils.showRequestDialog(requireActivity());
        //init ViewModel
        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        labViewModel.getPackageData(packageId).observe(getViewLifecycleOwner(), packageDetail -> {
            AppUtils.hideDialog();
            if (null == packageDetail)
                Toast.makeText(requireActivity(), "No data found !!", Toast.LENGTH_SHORT).show();
            else {
                fragmentTestDetailsBinding.setPackages(packageDetail);
                detailsList.clear();
                detailsList.addAll(packageDetail.getGroupDetails());
                testListAdapter.notifyDataSetChanged();

            }

        });

    }

    private static class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.TestVH> {
        List<PackageModel.GroupDetails> testDetails;

        public TestListAdapter(List<PackageModel.GroupDetails> testDetails) {
            this.testDetails = testDetails;
        }

        @NonNull
        @Override
        public TestListAdapter.TestVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            TestincludelayoutviewBinding binding = TestincludelayoutviewBinding.inflate(layoutInflater, parent, false);
            return new TestVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TestListAdapter.TestVH holder, int position) {
            String groupName = testDetails.get(position).getGroupName();
            List<PackageModel.TestDetails> testNameList = testDetails.get(position).getTestDetails();
            StringBuilder nameAppended = new StringBuilder();
            for (PackageModel.TestDetails name : testNameList)
                nameAppended.append(name.getTestName()).append("\n");

            String finalString = "Group Name\n" + groupName + "\nTests Included\n" + nameAppended;

            holder.binding.setTestNameList(finalString);
        }

        @Override
        public int getItemCount() {
            if (null == testDetails)
                testDetails = new ArrayList<>();
            return testDetails.size();
        }

        public static class TestVH extends RecyclerView.ViewHolder {
            TestincludelayoutviewBinding binding;

            public TestVH(@NonNull TestincludelayoutviewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}