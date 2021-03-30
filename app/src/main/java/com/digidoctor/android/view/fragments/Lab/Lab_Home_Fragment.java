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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.labadapter.CategoryAdapter;
import com.digidoctor.android.adapters.labadapter.LabSliderAdapter;
import com.digidoctor.android.adapters.labadapter.LabsAdapter;
import com.digidoctor.android.adapters.labadapter.PackagesAdapter;
import com.digidoctor.android.databinding.LabTestHomeBinding;
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.interfaces.PackagesInterface;
import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.model.labmodel.BannerText;
import com.digidoctor.android.model.labmodel.CategoryModel;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;
import com.digidoctor.android.utility.Cart;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lab_Home_Fragment extends Fragment implements PackagesInterface, CartInterface {

    private static final String TAG = "Lab_Home_Fragment";
    LabTestHomeBinding labTestHomeBinding;
    NavController navController;
    PatientViewModel viewModel;
    PackagesAdapter packagesAdapter;
    CategoryAdapter categoryAdapter;
    LabSliderAdapter labSliderAdapter;
    LabsAdapter labsAdapter;
    List<LabDashBoardmodel.SliderImage> imageUrls;

    Cart cart;


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
        cart = new Cart(requireActivity(), this);
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        //Slider Adapter
        imageUrls = new ArrayList<>();
        labSliderAdapter = new LabSliderAdapter(imageUrls);
        labTestHomeBinding.recBannerSlider.setAdapter(labsAdapter);

        //Packages Adapter
        Cart cart = new Cart(requireActivity(), this);
        packagesAdapter = new PackagesAdapter(cart);
        labTestHomeBinding.healthPackageRecyclerview.setAdapter(packagesAdapter);


        //CategoryAdapter
        categoryAdapter = new CategoryAdapter();
        labTestHomeBinding.recCategory.setAdapter(categoryAdapter);

        //LabsAdapter
        labsAdapter = new LabsAdapter();
        labTestHomeBinding.recLab.setAdapter(labsAdapter);

        labTestHomeBinding.tvViewAllPackages.setOnClickListener(v -> navController.navigate(R.id.action_lab_Home_Fragment_to_test_Package_Fragment));

        viewModel.getLabDashboardModel("10", "20").observe(getViewLifecycleOwner(), labDashBoardmodel -> {

            List<LabDashBoardmodel.SliderImage> sliderImages = labDashBoardmodel.getSliderImage();
            if (null != sliderImages && !sliderImages.isEmpty())
                setSliderData(sliderImages);

            //packages
            List<PackageModel> packageDetails = labDashBoardmodel.getPackageDetails();
            if (null != packageDetails && !packageDetails.isEmpty())
                packagesAdapter.submitList(packageDetails);


            //category
            List<CategoryModel> categoryModels = labDashBoardmodel.getCategoryDetails();
            if (null != categoryModels && !categoryModels.isEmpty())
                categoryAdapter.submitList(categoryModels);


            List<BannerText> bannerTextList = labDashBoardmodel.getBannerText();
            if (null != bannerTextList && !bannerTextList.isEmpty())
                setBannerData(bannerTextList);

            //Labs
            List<LabModel> labModels = labDashBoardmodel.getPathalogyDetails();
            if (null != labModels && !labModels.isEmpty())
                labsAdapter.submitList(labModels);


            //Banner
            List<LabDashBoardmodel.SliderImage> imageList = labDashBoardmodel.getSliderImage();
            if (null != imageList && !imageList.isEmpty()) {
                imageUrls.clear();
                imageUrls.addAll(imageList);
                labSliderAdapter.notifyDataSetChanged();
            }


            //getting Test details
            Log.d(TAG, "onViewCreated: name " + packageDetails.get(0).getGroupDetails().get(0).getGroupName());
            Log.d(TAG, "onViewCreated: testSize " + packageDetails.get(0).getGroupDetails().get(0).getTestDetails().size());

        });

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


    private void setSliderData(List<LabDashBoardmodel.SliderImage> sliderImages) {

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

    }


    @Override
    public void onItemClicked(Object obj) {
        String packageId = (String) obj;
        cart.addItemToCart("", packageId);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(requireActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartItemAdded(Object obj) {
        Toast.makeText(requireActivity(), "Added successfully !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartItemDeleted(Object obj) {
        Toast.makeText(requireActivity(), "removed successfully !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cartItem(Object obj) {
        navController.navigate(R.id.action_lab_Home_Fragment_to_fragmentCartListLab);
    }

    public CartInterface getCartInterface() {
        return this;
    }
}
