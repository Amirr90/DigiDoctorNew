package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.AddInvestigationViewBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.TestInformationModel;

import java.util.ArrayList;
import java.util.List;

public class AddInvestigationTestInformationAdapter extends RecyclerView.Adapter<AddInvestigationTestInformationAdapter.InvestigationVH> {

    private static final String TAG = "AddInvestigationTestInf";
    List<TestInformationModel> testInformationModels;
    OnClickListener clickListener;

    public AddInvestigationTestInformationAdapter(List<TestInformationModel> testInformationModels, OnClickListener clickListener) {
        this.testInformationModels = testInformationModels;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AddInvestigationTestInformationAdapter.InvestigationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddInvestigationViewBinding binding = AddInvestigationViewBinding.inflate(inflater, parent, false);
        return new InvestigationVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddInvestigationTestInformationAdapter.InvestigationVH holder, int position) {

        TestInformationModel testInformationModel = testInformationModels.get(position);
        Log.d(TAG, "onBindViewHolder: " + testInformationModel.toString());
        try {
            holder.binding.setInvestigation(testInformationModel);
            holder.binding.ivDeleteItem.setOnClickListener(view -> clickListener.onItemClick(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllItems() {
        testInformationModels.clear();
    }

    @Override
    public int getItemCount() {
        return testInformationModels.size();
    }

    public boolean addItem(TestInformationModel informationModel) {
        if (testInformationModels.contains(informationModel))
            return false;
        else {
            testInformationModels.add(informationModel);
            //notifyItemInserted(0);
            notifyDataSetChanged();
            return true;
        }
    }

    public List<TestInformationModel> deleteItems(int position) {
        if (testInformationModels.size() >= position) {
            testInformationModels.remove(position);
            notifyItemRemoved(0);
            notifyDataSetChanged();

        }
        return testInformationModels;
    }

    public class InvestigationVH extends RecyclerView.ViewHolder {
        AddInvestigationViewBinding binding;

        public InvestigationVH(@NonNull AddInvestigationViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public List<TestInformationModel> getTestInformationData() {
        return testInformationModels;
    }

    public int testInformationCount() {
        if (null == testInformationModels)
            testInformationModels = new ArrayList<>();
        return testInformationModels.size();
    }
}
