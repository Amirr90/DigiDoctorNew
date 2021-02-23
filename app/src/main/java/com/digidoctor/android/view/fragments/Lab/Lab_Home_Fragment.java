package com.digidoctor.android.view.fragments.Lab;

import android.content.Intent;
import android.net.Uri;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.labadapter.HealthPackageAdapter;
import com.digidoctor.android.adapters.labadapter.sliderimageadapter;
import com.digidoctor.android.databinding.LabTestHomeBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ApiInterface;
import com.digidoctor.android.model.labmodel.BannerText;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.model.labmodel.PathalogyDetail;
import com.digidoctor.android.model.labmodel.labModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.ArrayUtil;
import com.digidoctor.android.utility.Response;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.ArrayUtil.objectToJSONArray;

public class Lab_Home_Fragment extends Fragment {

    private static final String TAG = "Lab_Home_Fragment";
    LabTestHomeBinding labTestHomeBinding;
    NavController navController;

    PatientViewModel viewModel;


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
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        viewModel.getLabDashboardModel("10", "20").observe(getViewLifecycleOwner(), labDashBoardmodel -> {

            List<LabDashBoardmodel.SliderImage> sliderImages = labDashBoardmodel.getSliderImage();
            if (null != sliderImages && !sliderImages.isEmpty())
                setSliderData(sliderImages);

            List<PackageDetail> packageDetails = labDashBoardmodel.getPackageDetails();
            if (null != packageDetails && !packageDetails.isEmpty())
                setPackagesData(packageDetails);


            List<BannerText> bannerTextList = labDashBoardmodel.getBannerTextList();
            if (null != bannerTextList && !bannerTextList.isEmpty())
                setBannerData(bannerTextList);

            List<PathalogyDetail> pathalogyDetailList = labDashBoardmodel.getPathalogyDetailList();
            if (null != pathalogyDetailList && !pathalogyDetailList.isEmpty())
                setPathalogyDetailData(pathalogyDetailList);
        });

    }

    private void setPathalogyDetailData(List<PathalogyDetail> pathalogyDetailList) {
    }

    private void setBannerData(List<BannerText> bannerTextList) {
        BannerText bannerText = bannerTextList.get(0);
        labTestHomeBinding.textView156.setText(bannerText.getBannerText());
        labTestHomeBinding.bannerCall.setOnClickListener(v -> {
            startCalling(bannerText.getCallingNo());
        });
    }

    private void startCalling(String callingNo) {
        Uri u = Uri.parse("tel:" + callingNo);
        Intent i = new Intent(Intent.ACTION_DIAL, u);
        try {
            startActivity(i);
        } catch (SecurityException s) {
            Toast.makeText(requireActivity(), "An error occurred", Toast.LENGTH_LONG).show();
        }
    }

    private void setPackagesData(List<PackageDetail> packageDetails) {
    }

    private void setSliderData(List<LabDashBoardmodel.SliderImage> sliderImages) {
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

    }


}
