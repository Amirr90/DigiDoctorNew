package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.SubSpecialityAdapter;
import com.digidoctor.android.databinding.FragmentSubSpecialistBinding;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.List;

import static com.digidoctor.android.utility.NewDashboardUtils.hideSoftKeyboard;

public class SubSpecialistFragment extends Fragment {

    private static final String TAG = "SubSpecialistFragment";

    FragmentSubSpecialistBinding subSpecialistBinding;

    NavController navController;

    SubSpecialityAdapter adapter;

    PatientViewModel viewModel;



    String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        subSpecialistBinding = FragmentSubSpecialistBinding.inflate(inflater, container, false);
        return subSpecialistBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        id = getArguments().getString("id");

        navController = Navigation.findNavController(view);

        adapter = new SubSpecialityAdapter(new SubSpecialityAdapter.SubSpecialityInterface() {
            @Override
            public void onItemClick(String item) {
                Log.d(TAG, "onItemClick: " + item);
                Bundle bundle = new Bundle();
                bundle.putString("docModel", item);
                navController.navigate(R.id.action_subSpecialistFragment_to_chooseTimeFragment2, bundle);
            }
        });

        subSpecialistBinding.subSpecialityRec.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        viewModel.getDocBySpeciality(id, null).observe(getViewLifecycleOwner(), new Observer<List<DoctorModel>>() {
            @Override
            public void onChanged(List<DoctorModel> doctorModels) {
                subSpecialistBinding.rlNoDocFound.setVisibility(doctorModels.isEmpty() ? View.VISIBLE : View.GONE);
                subSpecialistBinding.subSpecialityRec.setVisibility(doctorModels.isEmpty() ? View.GONE : View.VISIBLE);

                adapter.submitList(doctorModels);
            }
        });


        subSpecialistBinding.editTextTextSearchDocBySpeciality.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SubSpecialistFragment.this.performSearch(v.getText().toString());
                    hideSoftKeyboard(PatientDashboard.getInstance());
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch(String docName) {
        viewModel.getDocBySpeciality(id, docName).observe(getViewLifecycleOwner(), new Observer<List<DoctorModel>>() {
            @Override
            public void onChanged(List<DoctorModel> doctorModels) {

                try {
                    adapter.submitList(doctorModels);
                    subSpecialistBinding.rlNoDocFound.setVisibility(doctorModels.isEmpty() ? View.VISIBLE : View.GONE);
                    subSpecialistBinding.subSpecialityRec.setVisibility(doctorModels.isEmpty() ? View.GONE : View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                    subSpecialistBinding.rlNoDocFound.setVisibility(doctorModels.isEmpty() ? View.VISIBLE : View.GONE);
                    subSpecialistBinding.subSpecialityRec.setVisibility(doctorModels.isEmpty() ? View.GONE : View.VISIBLE);
                }

            }
        });
    }
}