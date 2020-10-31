package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.SpecialityAdapter;
import com.digidoctor.android.databinding.FragmentSpecialitiesBinding;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.List;

import static com.digidoctor.android.utility.utils.hideSoftKeyboard;


public class SpecialitiesFragment extends Fragment {
    FragmentSpecialitiesBinding specialitiesBinding;

    SpecialityAdapter specialityAdapter;
    PatientViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        specialitiesBinding = FragmentSpecialitiesBinding.inflate(inflater, container, false);
        return specialitiesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        specialityAdapter = new SpecialityAdapter();
        specialitiesBinding.specRec.setAdapter(specialityAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        viewModel.getSpecialityData().observe(getViewLifecycleOwner(), new Observer<List<SpecialityModel>>() {
            @Override
            public void onChanged(List<SpecialityModel> specialityModels) {
                if (null != specialityModels) {
                    specialityAdapter.submitList(specialityModels);
                } else PatientDashboard.getInstance().onSupportNavigateUp();

            }
        });

        specialitiesBinding.editTextTextPersonName4.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SpecialitiesFragment.this.performSearch(v.getText().toString());
                    hideSoftKeyboard(PatientDashboard.getInstance());
                    return true;
                }
                return false;
            }
        });


    }

    private void performSearch(String s) {
        Toast.makeText(PatientDashboard.getInstance(), "Searching " + s, Toast.LENGTH_SHORT).show();
    }


}