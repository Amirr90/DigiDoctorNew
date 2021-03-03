package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.ClinicAdapter;
import com.digidoctor.android.adapters.DashboardPatientAdapter1;
import com.digidoctor.android.adapters.HealthProductAdapter;
import com.digidoctor.android.adapters.MainSliderAdapter;
import com.digidoctor.android.databinding.FragmentPatientDashboardBinding;
import com.digidoctor.android.model.BannerModel;
import com.digidoctor.android.model.DashboardModel1;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.PicassoImageLoadingService;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;

import static com.digidoctor.android.utility.AppUtils.getDashboardList;
import static com.digidoctor.android.utility.utils.getPrimaryUser;


public class PatientDashboardFragment extends Fragment {

    public static FragmentPatientDashboardBinding dashboard2Binding;
    DashboardPatientAdapter1 adapter1;
    HealthProductAdapter adapter2;
    ClinicAdapter adapter3;

    PatientViewModel viewModel;

    NavController navController;

    ImageLoadingService imageLoadingService;

    User user;

    String lat, lng;

    private static final String TAG = "PatientDashboardFragmen";
    //Last Update at 12/02/2021 from Animesh


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboard2Binding = FragmentPatientDashboardBinding.inflate(inflater, container, false);
        return dashboard2Binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        user = getPrimaryUser(requireActivity());
        if (null != user) {
            dashboard2Binding.setUser(user);
            Log.d(TAG, "PrimaryUser: " + user.toString());
        }

        imageLoadingService = new PicassoImageLoadingService(PatientDashboard.getInstance());
        Slider.init(imageLoadingService);


        adapter1 = new DashboardPatientAdapter1(requireActivity());
        adapter2 = new HealthProductAdapter();
        adapter3 = new ClinicAdapter();

        dashboard2Binding.rec1.setAdapter(adapter1);
        dashboard2Binding.rec2.setAdapter(adapter2);
        dashboard2Binding.rec3.setAdapter(adapter3);


        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        List<DashboardModel1> dashboardModel1s = getDashboardList(requireActivity());

        adapter1.submitList(dashboardModel1s);


        try {
            dashboard2Binding.tvLocation.setText(PatientDashboard.getInstance().getAreaName());
            dashboard2Binding.tvCity.setText(PatientDashboard.getInstance().getCityName());
            lat = PatientDashboard.getInstance().getLat();
            lng = PatientDashboard.getInstance().getLng();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadData(lat, lng);

        dashboard2Binding.profileImage.setOnClickListener(view1 ->
                PatientDashboard.getInstance().openDrawer()
        );
        dashboard2Binding.ivMenu.setOnClickListener(view1 ->
                PatientDashboard.getInstance().openDrawer()
        );


        dashboard2Binding.ivSearchIcon.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", "0");
            PatientDashboard.getInstance().navigate(R.id.action_patientDashboardFragment_to_subSpecialistFragment, bundle);
        });

        dashboard2Binding.tvLocation.setOnClickListener(view13 -> Log.d(TAG, "onViewCreated: Clicked"));
    }


    public void loadData(String lat, String lng) {
        viewModel.getDashboardData(lat, lng).observe(getViewLifecycleOwner(), patientDashboardModel -> {
            dashboard2Binding.getRoot().setVisibility(View.VISIBLE);
            if (patientDashboardModel != null) {
                String image = patientDashboardModel.getTopImage().get(0).getTopImage();
                if (null != image && !image.isEmpty())
                    Picasso.get().load(image).placeholder(R.drawable.banner_one).into(dashboard2Binding.dashboardHomeImage);

                adapter2.submitList(patientDashboardModel.getHealthProductDetails());
                adapter3.submitList(patientDashboardModel.getTopClinics());
                setSlider(patientDashboardModel.getBannerDetails());


            }
        });

    }


    private void setSlider(List<BannerModel> bannerDetails) {

        ArrayList<String> images = new ArrayList<>();
        for (BannerModel bannerModel : bannerDetails)
            images.add(bannerModel.getSliderImages());
        dashboard2Binding.recBannerSlider.setAdapter(new MainSliderAdapter(images));
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

    }

}