package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.SubSpecialityAdapter;
import com.digidoctor.android.databinding.FragmentSubSpecialistBinding;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import static com.digidoctor.android.utility.AppUtils.shareDocProfile;
import static com.digidoctor.android.utility.NewDashboardUtils.getJSONFromModel;
import static com.digidoctor.android.utility.NewDashboardUtils.hideSoftKeyboard;

public class SubSpecialistFragment extends Fragment implements SubSpecialityAdapter.SubSpecialityInterface {

    private static final String TAG = "SubSpecialistFragment";

    FragmentSubSpecialistBinding subSpecialistBinding;

    NavController navController;

    SubSpecialityAdapter adapter;

    PatientViewModel viewModel;


    String id;

    SpecialityModel specialityModel = new SpecialityModel();

    SubSpecialistFragmentArgs fragmentArgs;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        subSpecialistBinding = FragmentSubSpecialistBinding.inflate(inflater, container, false);
        return subSpecialistBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
            id = getArguments().getString("id") == null ? "0" : getArguments().getString("id");
        else {
            Log.d(TAG, "onViewCreated: no Argument Found");
            return;
        }


        fragmentArgs = SubSpecialistFragmentArgs.fromBundle(getArguments());

        specialityModel.setDoctorId(fragmentArgs.getDocId());
        specialityModel.setSpecialityID(Integer.parseInt(id));


        navController = Navigation.findNavController(view);

        adapter = new SubSpecialityAdapter(this);

        subSpecialistBinding.subSpecialityRec.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        viewModel.getDocBySpeciality(specialityModel).observe(getViewLifecycleOwner(), doctorModels -> {
            subSpecialistBinding.rlNoDocFound.setVisibility(doctorModels.isEmpty() ? View.VISIBLE : View.GONE);
            subSpecialistBinding.subSpecialityRec.setVisibility(doctorModels.isEmpty() ? View.GONE : View.VISIBLE);

            adapter.submitList(doctorModels);
        });


        subSpecialistBinding.editTextTextSearchDocBySpeciality.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                SubSpecialistFragment.this.performSearch(v.getText().toString());
                hideSoftKeyboard(PatientDashboard.getInstance());
                return true;
            }
            return false;
        });

        subSpecialistBinding.editTextTextSearchDocBySpeciality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (null != charSequence) {
                    SubSpecialistFragment.this.performSearch(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void performSearch(String docName) {

        SpecialityModel specialityModel = new SpecialityModel();
        specialityModel.setSpecialityID(Integer.valueOf(id));

        specialityModel.setDoctorName(docName);

        viewModel.getDocBySpeciality(specialityModel).observe(getViewLifecycleOwner(), doctorModels -> {

            try {
                adapter.submitList(doctorModels);
                subSpecialistBinding.rlNoDocFound.setVisibility(doctorModels.isEmpty() ? View.VISIBLE : View.GONE);
                subSpecialistBinding.subSpecialityRec.setVisibility(doctorModels.isEmpty() ? View.GONE : View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
                subSpecialistBinding.rlNoDocFound.setVisibility(doctorModels.isEmpty() ? View.VISIBLE : View.GONE);
                subSpecialistBinding.subSpecialityRec.setVisibility(doctorModels.isEmpty() ? View.GONE : View.VISIBLE);
            }

        });
    }

    @Override
    public void onItemClick(String item) {
        hideSoftKeyboard(requireActivity());
        Bundle bundle = new Bundle();
        bundle.putString("docModel", item);
        navController.navigate(R.id.action_subSpecialistFragment_to_chooseTimeFragment2, bundle);
    }

    @Override
    public void onShareProfile(DoctorModel doctorModel) {
        shareDocProfile(doctorModel, requireActivity());
    }

    @Override
    public void onViewDocProfile(DoctorModel doctorModel) {
        Bundle bundle = new Bundle();
        bundle.putString("docModel", getJSONFromModel(doctorModel));
        Log.d(TAG, "onItemClicked: " + doctorModel.toString());
        try {
            Log.d(TAG, "onItemClicked: " + getJSONFromModel(doctorModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
        navController.navigate(R.id.action_subSpecialistFragment_to_doctorShortProfileFragment, bundle);
    }
}
