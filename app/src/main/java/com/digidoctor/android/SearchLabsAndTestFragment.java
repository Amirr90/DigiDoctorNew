package com.digidoctor.android;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digidoctor.android.databinding.FragmentSearchLabsAndTestBinding;
import com.digidoctor.android.databinding.SearchLabViewBinding;
import com.digidoctor.android.model.labmodel.SearchRes;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.viewHolder.LabViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchLabsAndTestFragment extends Fragment {


    FragmentSearchLabsAndTestBinding binding;
    SearchLabAdapter searchLabAdapter;
    LabViewModel viewModel;

    List<SearchRes.SearchModel.Details> alllabtestdata;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchLabsAndTestBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchLabAdapter = new SearchLabAdapter();
        binding.recSearch.setAdapter(searchLabAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(LabViewModel.class);
        viewModel.getTestAndPackageData().observe(getViewLifecycleOwner(), searchModels -> {
            if (null != searchModels && searchModels.size() > 0) {
                List<SearchRes.SearchModel.Details> details = searchModels.get(0).getDetails();
                searchLabAdapter.submitList(details);
                alllabtestdata = searchLabAdapter.getCurrentList();
            }

        });
        binding.editTextSearchLabs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence != null && charSequence.length() > 3) {
                    searchData(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                searchData(s.toString());
            }
        });


    }

    private void searchData(String keys) {

        if (null != alllabtestdata) {
            List<SearchRes.SearchModel.Details> filteredlist = new ArrayList<>();

            for (int i = 0; i < alllabtestdata.size(); i++) {
                SearchRes.SearchModel.Details d = alllabtestdata.get(i);
                if (d.getName().toLowerCase().contains(keys.toString().toLowerCase())) {
                    filteredlist.add(d);
                }
            }

            searchLabAdapter.submitList(filteredlist);
        }


    }

    private static class SearchLabAdapter extends ListAdapter<SearchRes.SearchModel.Details, SearchLabAdapter.SearchVH> {



        protected SearchLabAdapter() {
            super(SearchRes.SearchModel.Details.itemCallback);


        }

        @NonNull
        @Override
        public SearchLabAdapter.SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            SearchLabViewBinding binding = SearchLabViewBinding.inflate(inflater, parent, false);
            return new SearchVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchLabAdapter.SearchVH holder, int position) {


            holder.binding.setDetails(getItem(position));


        }



        public static class SearchVH extends RecyclerView.ViewHolder {
            SearchLabViewBinding binding;

            public SearchVH(@NonNull SearchLabViewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }


    }
}