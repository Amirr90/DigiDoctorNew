package com.digidoctor.android.view.fragments.Patient;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.patient.ClinicAdapter;
import com.digidoctor.android.adapters.patient.DashboardPatientAdapter1;
import com.digidoctor.android.adapters.patient.HealthProductAdapter;
import com.digidoctor.android.adapters.patient.MainSliderAdapter;
import com.digidoctor.android.databinding.FragmentPatientDashboardBinding;
import com.digidoctor.android.model.patientModel.BannerModel;
import com.digidoctor.android.model.patientModel.DashboardModel1;
import com.digidoctor.android.model.patientModel.PatientDashboardModel;
import com.digidoctor.android.model.patientModel.User;
import com.digidoctor.android.utility.PicassoImageLoadingService;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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


        adapter1 = new DashboardPatientAdapter1();
        adapter2 = new HealthProductAdapter();
        adapter3 = new ClinicAdapter();

        dashboard2Binding.rec1.setAdapter(adapter1);
        dashboard2Binding.rec2.setAdapter(adapter2);
        dashboard2Binding.rec3.setAdapter(adapter3);


        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        List<DashboardModel1> dashboardModel1s = new ArrayList<>();
        dashboardModel1s.add(new DashboardModel1("Specialities"));
        dashboardModel1s.add(new DashboardModel1("Symptoms"));
        dashboardModel1s.add(new DashboardModel1("Tests"));
        dashboardModel1s.add(new DashboardModel1("Pharmacy"));


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

        dashboard2Binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_patientDashboardFragment_to_profileFragment);
            }
        });

    }

    public void loadData(String lat, String lng) {
        Log.d(TAG, "loadData: lat,lng :" + lat + "," + lng);
        viewModel.getDashboardData(lat, lng).observe(getViewLifecycleOwner(), new Observer<PatientDashboardModel>() {
            @Override
            public void onChanged(PatientDashboardModel patientDashboardModel) {

                dashboard2Binding.getRoot().setVisibility(View.VISIBLE);

                if (patientDashboardModel != null) {
                    String image = patientDashboardModel.getTopImage().get(0).getTopImage();
                    if (null != image && !image.isEmpty())
                        Picasso.get().load(image).placeholder(R.drawable.banner_one).into(dashboard2Binding.dashboardHomeImage);

                    adapter2.submitList(patientDashboardModel.getHealthProductDetails());
                    adapter3.submitList(patientDashboardModel.getTopClinics());
                    setSlider(patientDashboardModel.getBannerDetails());


                }
            }
        });

    }


    private void setSlider(List<BannerModel> bannerDetails) {

        ArrayList<String> images = new ArrayList<>();
        for (BannerModel bannerModel : bannerDetails)
            images.add(bannerModel.getSliderImages());
        dashboard2Binding.bannerSlider1.setAdapter(new MainSliderAdapter(images));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}