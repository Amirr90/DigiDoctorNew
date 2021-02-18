package com.digidoctor.android.view.fragments.Lab;

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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.labadapter.HealthPackageAdapter;
import com.digidoctor.android.adapters.labadapter.sliderimageadapter;
import com.digidoctor.android.databinding.LabTestHomeBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class Lab_Home_Fragment extends Fragment {

    private static final String TAG = "Lab_Home_Fragment";
    LabTestHomeBinding labTestHomeBinding;
    sliderimageadapter sliderimageadapter;
    HealthPackageAdapter healthPackageAdapter;
    NavController navController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        labTestHomeBinding = LabTestHomeBinding.inflate(getLayoutInflater());
        return labTestHomeBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        List<LabDashBoardmodel.SliderImage> sliderimageoflab = new ArrayList<>();

        List<PackageDetail> packageDetails = new ArrayList<>();


        sliderimageadapter = new sliderimageadapter(sliderimageoflab, requireActivity());

        healthPackageAdapter = new HealthPackageAdapter(packageDetails);


        labTestHomeBinding.bannerSlider1.setAdapter(sliderimageadapter);


        labTestHomeBinding.healthpackagerecyclerview.setAdapter(healthPackageAdapter);


        ApiUtils.getlabdash( new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<LabDashBoardmodel> models = (List<LabDashBoardmodel>) o;
                List<LabDashBoardmodel.SliderImage> sliderImages = models.get(0).getSliderImage();

                Log.d(TAG, "onSuccess: " + sliderimageoflab);

                sliderimageoflab.addAll(sliderImages);
                sliderimageadapter.notifyDataSetChanged();


//                List<PackageDetail> packageDetails1 = models.get(0).getResponseValue().get(0).getPackageDetails();
//                Log.d(TAG, "onSuccess: " + packageDetails1);
//                packageDetails.addAll(packageDetails1);
//                healthPackageAdapter.notifyDataSetChanged();


            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        labTestHomeBinding.textView158.setOnClickListener(view1 -> navController.navigate(R.id.action_lab_Home_Fragment_to_health_Checkup_Categories_Fragment));

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }


}
