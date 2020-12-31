package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.PopularDoctorsAdapter;
import com.digidoctor.android.adapters.RecommendedDoctorsAdapter;
import com.digidoctor.android.databinding.FragmentRecommendedDoctorsBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import static com.digidoctor.android.utility.NewDashboardUtils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.KEY_AGE;
import static com.digidoctor.android.utility.utils.KEY_DOC_NAME;
import static com.digidoctor.android.utility.utils.KEY_GENDER;
import static com.digidoctor.android.utility.utils.KEY_SYMPTOM_ID;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;


public class RecommendedDoctorsFragment extends Fragment implements AdapterInterface {

    private static final String TAG = "RecommendedDoctorsFragm";
    PatientViewModel viewModel;
    NavController navController;
    RecommendedDoctorsAdapter doctorsAdapter;
    PopularDoctorsAdapter popularDoctorsAdapter;

    FragmentRecommendedDoctorsBinding recommendedDoctorsBinding;

    String symptomID;

    User user;


    HashMap<String, String> map;
    String GENDER, AGE;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recommendedDoctorsBinding = FragmentRecommendedDoctorsBinding.inflate(inflater, container, false);
        return recommendedDoctorsBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            symptomID = getArguments().getString("id");
        } else symptomID = "";
        map = new HashMap<>();

        user = getPrimaryUser(requireActivity());


        navController = Navigation.findNavController(view);


        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        doctorsAdapter = new RecommendedDoctorsAdapter(this);
        popularDoctorsAdapter = new PopularDoctorsAdapter(this);
        recommendedDoctorsBinding.recommendedRec.setAdapter(doctorsAdapter);
        recommendedDoctorsBinding.popularRec.setAdapter(popularDoctorsAdapter);

        map.put(KEY_SYMPTOM_ID, symptomID);
        map.put(KEY_DOC_NAME, null);


        getData(map);

        recommendedDoctorsBinding.editTextTextSearchRecDoc.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                RecommendedDoctorsFragment.this.performSearch(v.getText().toString());
                hideSoftKeyboard(PatientDashboard.getInstance());
                return true;
            }
            return false;
        });


    }


    private void getData(HashMap<String, String> map) {
        map.put(KEY_GENDER, GENDER);
        map.put(KEY_AGE, AGE);

        viewModel.getRecommendedDoctorsData(map).observe(getViewLifecycleOwner(), doctorModelRes -> {
            List<DoctorModel> recDocList = doctorModelRes.get(0).getRecomendedDoctor();
            List<DoctorModel> popDocList = doctorModelRes.get(0).getPopularDoctor();

            recommendedDoctorsBinding.tvRecommendedDoc.setVisibility(recDocList.isEmpty() ? View.GONE : View.VISIBLE);
            recommendedDoctorsBinding.tvPopularDoc.setVisibility(popDocList.isEmpty() ? View.GONE : View.VISIBLE);


            recommendedDoctorsBinding.rlNoDocFound2.setVisibility(recDocList.isEmpty() && popDocList.isEmpty() ? View.VISIBLE : View.GONE);
            recommendedDoctorsBinding.llViewHolder.setVisibility(recDocList.isEmpty() && popDocList.isEmpty() ? View.GONE : View.VISIBLE);

            doctorsAdapter.submitList(recDocList);
            popularDoctorsAdapter.submitList(popDocList);

           /* if (user.getIsExists() == 1 && !isDialogShow)
                showSelectGenderAgeDialog();*/

        });
    }


    @Override

    public void onItemClicked(Object o) {
        DoctorModel doctorModel = (DoctorModel) o;
        Bundle bundle = new Bundle();
        bundle.putString("docModel", getJSONFromModel(doctorModel));
        Log.d(TAG, "onItemClicked: " + doctorModel.toString());
        try {
            Log.d(TAG, "onItemClicked: " + getJSONFromModel(doctorModel));
        } catch (Exception e) {
            e.printStackTrace();
        }
        navController.navigate(R.id.action_recommendedDoctorsFragment_to_doctorShortProfileFragment, bundle);
    }


    private void performSearch(String docName) {
        map.put(KEY_DOC_NAME, docName);
        map.put(KEY_GENDER, GENDER);
        map.put(KEY_AGE, AGE);

        recommendedDoctorsBinding.tvPopularDoc.setVisibility(View.GONE);
        viewModel.getRecommendedDoctorsData(map).observe(getViewLifecycleOwner(), doctorModelRes -> {
            List<DoctorModel> recDocList = doctorModelRes.get(0).getRecomendedDoctor();
            List<DoctorModel> popDocList = doctorModelRes.get(0).getPopularDoctor();
            recommendedDoctorsBinding.tvRecommendedDoc.setVisibility(recDocList.isEmpty() ? View.GONE : View.VISIBLE);
            doctorsAdapter.submitList(recDocList);
            popularDoctorsAdapter.submitList(popDocList);
        });
    }


}