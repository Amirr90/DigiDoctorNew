package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentOneBinding;
import com.digidoctor.android.databinding.PatientInvestigationViewBinding;
import com.digidoctor.android.databinding.PatientViewSubTestViewBinding;
import com.digidoctor.android.model.EraInvestigationData;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class OneFragment extends Fragment {
    private static final String TAG = "OneFragment";

    FragmentOneBinding binding;
    EraInvestigationData.PatientTestResult testResult;

    public static OneFragment newInstance() {
        return new OneFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOneBinding.inflate(getLayoutInflater());
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Gson gson = new Gson();
        testResult = gson.fromJson(getArguments().getString("PatientTestResult"), EraInvestigationData.PatientTestResult.class);
        initViews();

    }

    private void initViews() {


        if (null != testResult)
            setUpViews(testResult.getResult());
    }

    private void setUpViews(List<EraInvestigationData.DtPatientResult> result) {

        //binding.recPatientRes.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));
        binding.recPatientRes.setAdapter(new PatientAdapter(result));
    }

    private List<TestDetails> convertJsonArrayIntoList(EraInvestigationData.DtPatientResult dtPatientResult) {
        List<TestDetails> testDetails = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(dtPatientResult.getTestDetails());
            for (int i = 0; i < jsonArray.length(); i++) {

                Gson gson = new Gson();
                TestDetails details = gson.fromJson(jsonArray.getJSONObject(i).toString(), TestDetails.class);
                testDetails.add(details);


            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, " err : convertJsonArrayIntoList: ");
        }


        return testDetails;
    }


    public class TestDetails {
        String subTestName;
        String result;
        String unitname;
        String resultRemark;
        Boolean isNormalResult;


        public Boolean getNormalResult() {
            return isNormalResult;
        }

        public void setNormalResult(Boolean normalResult) {
            isNormalResult = normalResult;
        }

        public String getResultRemark() {
            return resultRemark;
        }

        public void setResultRemark(String resultRemark) {
            this.resultRemark = resultRemark;
        }

        public String getSubTestName() {
            return subTestName;
        }

        public String getResult() {
            return result;
        }

        public String getUnitname() {
            return unitname;
        }

        public void setSubTestName(String subTestName) {
            this.subTestName = subTestName;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public void setUnitname(String unitname) {
            this.unitname = unitname;
        }

        @Override
        public String toString() {
            return "TestDetails{" +
                    "subTestName='" + subTestName + '\'' +
                    ", result='" + result + '\'' +
                    ", unitname='" + unitname + '\'' +
                    '}';
        }
    }

    private class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientVH> {
        List<EraInvestigationData.DtPatientResult> results;


        public PatientAdapter(List<EraInvestigationData.DtPatientResult> result) {
            this.results = result;
        }

        @NonNull
        @Override
        public PatientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            PatientInvestigationViewBinding binding = PatientInvestigationViewBinding.inflate(layoutInflater, parent, false);
            return new PatientVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PatientVH holder, int position) {
            holder.binding.setPatientResult(results.get(position));
            holder.binding.setPosition(String.valueOf(position + 1));
            holder.binding.recPatientSubTest.setAdapter(new PatientSubTestAdapter(convertJsonArrayIntoList(results.get(position))));
        }

        @Override
        public int getItemCount() {
            return null == results ? 0 : results.size();
        }

        public class PatientVH extends RecyclerView.ViewHolder {
            PatientInvestigationViewBinding binding;

            public PatientVH(@NonNull PatientInvestigationViewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    private class PatientSubTestAdapter extends RecyclerView.Adapter<PatientSubTestAdapter.PatientSubTestVH> {
        List<TestDetails> data;

        public PatientSubTestAdapter(List<TestDetails> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public PatientSubTestVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            PatientViewSubTestViewBinding subTestViewBinding = PatientViewSubTestViewBinding.inflate(layoutInflater, parent, false);
            return new PatientSubTestVH(subTestViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull PatientSubTestVH holder, int position) {
            holder.subTestViewBinding.setSubTest(data.get(position));
        }

        @Override
        public int getItemCount() {
            return null == data ? 0 : data.size();
        }

        public class PatientSubTestVH extends RecyclerView.ViewHolder {
            PatientViewSubTestViewBinding subTestViewBinding;

            public PatientSubTestVH(@NonNull PatientViewSubTestViewBinding subTestViewBinding) {
                super(subTestViewBinding.getRoot());
                this.subTestViewBinding = subTestViewBinding;
            }
        }
    }
}