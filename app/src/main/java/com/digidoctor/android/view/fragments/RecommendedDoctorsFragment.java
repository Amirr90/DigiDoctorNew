package com.digidoctor.android.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.PopularDoctorsAdapter;
import com.digidoctor.android.adapters.RecommendedDoctorsAdapter;
import com.digidoctor.android.databinding.FragmentRecommendedDoctorsBinding;
import com.digidoctor.android.databinding.GenderAgeViewBinding;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.DoctorModelRes;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AdapterInterface;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

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

    AlertDialog optionDialog;
    String GENDER, AGE;


    boolean isDialogShow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        recommendedDoctorsBinding.editTextTextSearchRecDoc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    RecommendedDoctorsFragment.this.performSearch(v.getText().toString());
                    hideSoftKeyboard(PatientDashboard.getInstance());
                    return true;
                }
                return false;
            }
        });


    }

    private void showSelectGenderAgeDialog() {
        isDialogShow = true;
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.gender_age_view, null, false);

        final GenderAgeViewBinding genderViewBinding = GenderAgeViewBinding.bind(formElementsView);


        genderViewBinding.llMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GENDER = "1";
                setBackgroundSelected(1, genderViewBinding);
            }
        });

        genderViewBinding.llFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GENDER = "2";
                setBackgroundSelected(2, genderViewBinding);
            }
        });
        genderViewBinding.llChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GENDER = "3";
                setBackgroundSelected(3, genderViewBinding);
            }
        });
        genderViewBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (radioGroup.getCheckedRadioButtonId() == R.id.below15) {
                    AGE = "5";
                } else {
                    AGE = "16";
                }
            }
        });

        genderViewBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GENDER == null)
                    Toast.makeText(requireContext(), R.string.select_gender, Toast.LENGTH_SHORT).show();
                else if (AGE == null)
                    Toast.makeText(requireContext(), R.string.select_age, Toast.LENGTH_SHORT).show();
                else {
                    optionDialog.dismiss();
                    Toast.makeText(requireActivity(), "Age= " + AGE + " Gender= " + GENDER, Toast.LENGTH_SHORT).show();
                    getData(map);
                }
            }
        });
        // the alert dialog
        optionDialog = new AlertDialog.Builder(requireActivity()).create();
        optionDialog.setView(formElementsView);
        optionDialog.show();
    }

    private void getData(HashMap<String, String> map) {
        map.put(KEY_GENDER, GENDER);
        map.put(KEY_AGE, AGE);

        viewModel.getRecommendedDoctorsData(map).observe(getViewLifecycleOwner(), new Observer<List<DoctorModelRes>>() {
            @Override
            public void onChanged(List<DoctorModelRes> doctorModelRes) {
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

            }
        });
    }


    private void setBackgroundSelected(int i, GenderAgeViewBinding genderViewBinding) {
        switch (i) {
            case 1:
                genderViewBinding.llMale.setBackgroundColor(getResources().getColor(R.color.YellowColo));
                genderViewBinding.llFemale.setBackgroundColor(getResources().getColor(R.color.white));
                genderViewBinding.llChild.setBackgroundColor(getResources().getColor(R.color.white));

                //Changing TextColor
                genderViewBinding.tvMale.setTextColor(getResources().getColor(R.color.white));
                genderViewBinding.tvFemale.setTextColor(getResources().getColor(R.color.textColorBlack));
                genderViewBinding.tvChild.setTextColor(getResources().getColor(R.color.textColorBlack));


                break;
            case 2:
                genderViewBinding.llMale.setBackgroundColor(getResources().getColor(R.color.white));
                genderViewBinding.llFemale.setBackgroundColor(getResources().getColor(R.color.YellowColo));
                genderViewBinding.llChild.setBackgroundColor(getResources().getColor(R.color.white));

                //Changing TextColor
                genderViewBinding.tvMale.setTextColor(getResources().getColor(R.color.textColorBlack));
                genderViewBinding.tvFemale.setTextColor(getResources().getColor(R.color.white));
                genderViewBinding.tvChild.setTextColor(getResources().getColor(R.color.textColorBlack));
                break;
            case 3:
                genderViewBinding.llMale.setBackgroundColor(getResources().getColor(R.color.white));
                genderViewBinding.llFemale.setBackgroundColor(getResources().getColor(R.color.white));
                genderViewBinding.llChild.setBackgroundColor(getResources().getColor(R.color.YellowColo));

                //Changing TextColor
                genderViewBinding.tvMale.setTextColor(getResources().getColor(R.color.textColorBlack));
                genderViewBinding.tvFemale.setTextColor(getResources().getColor(R.color.textColorBlack));
                genderViewBinding.tvChild.setTextColor(getResources().getColor(R.color.white));
                break;

        }
    }

    @Override

    public void onItemClicked(Object o) {
        DoctorModel doctorModel = (DoctorModel) o;
        Bundle bundle = new Bundle();
        bundle.putString("docModel", getJSONFromModel(doctorModel));
        Log.d(TAG, "onItemClicked: " + doctorModel.toString());
        Log.d(TAG, "onItemClicked: " + getJSONFromModel(doctorModel));
        navController.navigate(R.id.action_recommendedDoctorsFragment_to_doctorShortProfileFragment, bundle);
    }


    private void performSearch(String docName) {
        map.put(KEY_DOC_NAME, docName);
        map.put(KEY_GENDER, GENDER);
        map.put(KEY_AGE, AGE);

        recommendedDoctorsBinding.tvPopularDoc.setVisibility(View.GONE);
        viewModel.getRecommendedDoctorsData(map).observe(getViewLifecycleOwner(), new Observer<List<DoctorModelRes>>() {
            @Override
            public void onChanged(List<DoctorModelRes> doctorModelRes) {
                List<DoctorModel> recDocList = doctorModelRes.get(0).getRecomendedDoctor();
                List<DoctorModel> popDocList = doctorModelRes.get(0).getPopularDoctor();
                recommendedDoctorsBinding.tvRecommendedDoc.setVisibility(recDocList.isEmpty() ? View.GONE : View.VISIBLE);
                doctorsAdapter.submitList(recDocList);
                popularDoctorsAdapter.submitList(popDocList);

            }
        });
    }


}