package com.digidoctor.android.view.fragments.lab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.digidoctor.android.adapters.labadapter.LabHomeGridAdapter;
import com.digidoctor.android.adapters.labadapter.LabSliderAdapter;
import com.digidoctor.android.adapters.labadapter.LabsAdapter;
import com.digidoctor.android.adapters.labadapter.PackagesAdapter;
import com.digidoctor.android.databinding.LabTestHomeBinding;
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.interfaces.PackagesInterface;
import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.model.labmodel.BannerText;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.model.labmodel.CategoryModel;
import com.digidoctor.android.model.labmodel.LabDashBoardmodel;
import com.digidoctor.android.utility.Cart;
import com.digidoctor.android.viewHolder.PatientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.fadeIn;

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
    public static Lab_Home_Fragment instance;

    public static Lab_Home_Fragment getInstance() {
        return instance;
    }


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
        labTestHomeBinding.recBannerSlider.setAdapter(labSliderAdapter);


        //Packages Adapter
        Cart cart = new Cart(requireActivity(), this);
        packagesAdapter = new PackagesAdapter(cart, this);
        labTestHomeBinding.healthPackageRecyclerview.setAdapter(packagesAdapter);


        //CategoryAdapter
        categoryAdapter = new CategoryAdapter();
        labTestHomeBinding.recCategory.setAdapter(categoryAdapter);

        //LabsAdapter
        labsAdapter = new LabsAdapter();
        labTestHomeBinding.recLab.setAdapter(labsAdapter);

        labTestHomeBinding.tvViewAllPackages.setOnClickListener(v -> navController.navigate(R.id.action_lab_Home_Fragment_to_test_Package_Fragment));
        viewModel.getLabDashboardModel("10", "20").observe(getViewLifecycleOwner(), labDashBoardmodel -> {

            labTestHomeBinding.contentconstrainet.setVisibility(null == labDashBoardmodel ? View.GONE : View.VISIBLE);
            labTestHomeBinding.loadingView.setVisibility(null == labDashBoardmodel ? View.VISIBLE : View.GONE);
            labTestHomeBinding.contentconstrainet.setAnimation(fadeIn(requireActivity()));
            if (null == labDashBoardmodel)
                return;

            updateCartBadge(labDashBoardmodel.getCartCount());
            //packages
            List<PackageModel> packageDetails = labDashBoardmodel.getPackageDetails();
            if (null != packageDetails && !packageDetails.isEmpty()) {
                packagesAdapter.submitList(packageDetails);
                labTestHomeBinding.tvViewAllPackages.setVisibility(packagesAdapter.getItemCount() > 2 ? View.VISIBLE : View.GONE);
            }


            //category
            List<CategoryModel> categoryModels = labDashBoardmodel.getCategoryDetails();
            if (null != categoryModels && !categoryModels.isEmpty()) {
                categoryAdapter.submitList(categoryModels);
                labTestHomeBinding.textView153.setVisibility(categoryAdapter.getItemCount() > 2 ? View.VISIBLE : View.GONE);
            }


            List<BannerText> bannerTextList = labDashBoardmodel.getBannerText();
            if (null != bannerTextList && !bannerTextList.isEmpty())
                setBannerData(bannerTextList);

            //Labs
            List<LabModel> labModels = labDashBoardmodel.getPathalogyDetails();
            if (null != labModels && !labModels.isEmpty()) {
                labsAdapter.submitList(labModels);
                labTestHomeBinding.textView158.setVisibility(labsAdapter.getItemCount() > 2 ? View.VISIBLE : View.GONE);
            }


            //Banner
            List<LabDashBoardmodel.SliderImage> imageList = labDashBoardmodel.getSliderImage();
            if (null != imageList && !imageList.isEmpty()) {
                imageUrls.clear();
                imageUrls.addAll(imageList);
                labSliderAdapter.notifyDataSetChanged();
            }

        });


        //setting HomeGrid Adapter
        labTestHomeBinding.recyclerView9.setAdapter(new LabHomeGridAdapter(o -> {
            int position = (int) o;
            if (position == 2) {
                navController.navigate(R.id.action_lab_Home_Fragment_to_investigationFragment);
            } else if (position == 3) {
                navController.navigate(R.id.action_lab_Home_Fragment_to_addInvestigationFragment2);
            }
        }));


        //setting Listener to tvViewAll Category
        labTestHomeBinding.textView153.setOnClickListener(v -> navController.navigate(R.id.action_lab_Home_Fragment_to_labHealthCategoryFragment));

        //setting Listener to ViewAll Labs
        labTestHomeBinding.textView158.setOnClickListener(v -> navController.navigate(R.id.action_lab_Home_Fragment_to_allLabsFragment));

        //setting Listener to search Labs
        labTestHomeBinding.ivSearchLabIcon.setOnClickListener(v -> navController.navigate(R.id.action_lab_Home_Fragment_to_searchLabsAndTestFragment));

        //on Cart Icon Click
        labTestHomeBinding.ivLabCart.setOnClickListener(v -> navController.navigate(R.id.action_lab_Home_Fragment_to_fragmentCartListLab2));

        //onBack Arrow Click
        labTestHomeBinding.ivBack.setOnClickListener(v -> navController.navigateUp());

    }


    private void setBannerData(List<BannerText> bannerTextList) {
        BannerText bannerText = bannerTextList.get(0);
        labTestHomeBinding.textView156.setText(bannerText.getBannerText());
        labTestHomeBinding.bannerCall.setOnClickListener(v -> startCalling(bannerText.getCallingNo()));
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


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();

    }


    @Override
    public void onItemClicked(Object obj) {
        String packageId = (String) obj;
        Bundle args = new Bundle();
        args.putString("packageID", packageId);
        navController.navigate(R.id.action_lab_Home_Fragment_to_testDetailsFRagment, args);
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(requireActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartItemAdded(Object obj) {
        Toast.makeText(requireActivity(), "Added successfully !!", Toast.LENGTH_SHORT).show();
        List<CartModel> cartModels = (List<CartModel>) obj;
        updateCartBadge(cartModels.size());
    }

    private void updateCartBadge(int badgeCount) {
        labTestHomeBinding.tvCartBadge.setText(String.valueOf(badgeCount));
    }

    @Override
    public void onCartItemDeleted(Object obj) {

        List<CartModel> cartModels = (List<CartModel>) obj;
        updateCartBadge(cartModels.size());
        Toast.makeText(requireActivity(), "Removed successfully !!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cartItem(Object obj) {
        List<CartModel> cartModels = (List<CartModel>) obj;
        updateCartBadge(cartModels.size());
        navController.navigate(R.id.action_lab_Home_Fragment_to_fragmentCartListLab);
    }

}
