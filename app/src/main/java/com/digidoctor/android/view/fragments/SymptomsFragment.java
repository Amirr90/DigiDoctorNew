package com.digidoctor.android.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.SymptomsAdapter;
import com.digidoctor.android.databinding.FragmentSymptomsBinding;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.ArrayList;
import java.util.List;


public class SymptomsFragment extends Fragment {

    FragmentSymptomsBinding symptoms2Binding;
    PatientViewModel viewModel;
    SymptomsAdapter symptomsAdapter;
    NavController navController;
    private static final String TAG = "SymptomsFragment";

    public static List<String> symptomsIds = new ArrayList<>();

    String symptomName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        symptoms2Binding = FragmentSymptomsBinding.inflate(inflater, container, false);
        return symptoms2Binding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);


        symptomsAdapter = new SymptomsAdapter(new AdapterInterface() {
            @Override
            public void onItemClicked(Object o) {
                SymptomModel symptomModel = (SymptomModel) o;
                if (null == symptomModel)
                    return;
                String id = String.valueOf(symptomModel.getProblemId());
                if (symptomsIds.contains(id)) {
                    symptomsIds.remove(id);
                } else symptomsIds.add(id);

            }

        });

        symptoms2Binding.symptomsRec.setAdapter(symptomsAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        getSymptomData(symptomName);

        symptoms2Binding.btnProceedOnSymptomPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_symptomsFragment2_to_recommendedDoctorsFragment2);
            }
        });


        symptoms2Binding.btnProceedOnSymptomPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (symptomsIds.isEmpty()) {
                    Toast.makeText(requireActivity(), R.string.select_symptoms, Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder ids = new StringBuilder();
                for (String id : symptomsIds)
                    ids.append(id + ",");
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", ids.toString());
                    Log.d(TAG, "onClick: SymptomsId: " + ids.toString());
                    navController.navigate(R.id.action_symptomsFragment2_to_recommendedDoctorsFragment2, bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onItemClicked: " + e.getLocalizedMessage());
                }
            }
        });


        symptoms2Binding.editTextTextSearchSymptom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence != null && charSequence.length() > 3) {
                    symptoms2Binding.progressBar2.setVisibility(View.VISIBLE);
                    getSymptomData(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getSymptomData(String symptomName) {

        viewModel.getSymptomsData(symptomName).observe(getViewLifecycleOwner(), new Observer<List<SymptomModel>>() {
            @Override
            public void onChanged(List<SymptomModel> symptomModels) {
                symptomsAdapter.submitList(symptomModels);
                symptoms2Binding.progressBar2.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        symptomsIds.clear();
    }
}