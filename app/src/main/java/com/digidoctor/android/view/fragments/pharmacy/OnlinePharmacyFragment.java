package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
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
import com.digidoctor.android.adapters.pharmacy.PopularProductAdapter;
import com.digidoctor.android.adapters.pharmacy.ShopByCategoryAdapter;
import com.digidoctor.android.adapters.pharmacy.SliderAdapterforPharmacy;
import com.digidoctor.android.adapters.pharmacy.TopSearchProductListAdapter;
import com.digidoctor.android.databinding.CategoryViewBinding;
import com.digidoctor.android.databinding.FragmentOnlinePharmacyBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.CartCount;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlinePharmacyFragment extends Fragment {
    private static final String TAG = "OnlinePharmacyFragment";
    private static final int SELECT_PICTURE = 1;
    FragmentOnlinePharmacyBinding onlinePharmacyBinding;
    ShopByCategoryAdapter adapter;
    PopularProductAdapter adapter1;
    SliderAdapterforPharmacy sliderAdapterforPharmacy;
    TopSearchProductListAdapter adapter2;
    CategoryViewBinding categoryViewBinding;
    NavController navController;
    private String selectedImagePath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onlinePharmacyBinding = FragmentOnlinePharmacyBinding.inflate(getLayoutInflater());
        return onlinePharmacyBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        navController = Navigation.findNavController(view);

        final List<ShopBycategoryModel.CategoryModel> categoryModels = new ArrayList<>();
        final List<ShopBycategoryModel.PopularProductList> popoularmodel = new ArrayList<>();
        final List<ShopBycategoryModel.TopSearchproductList> topSearchproductLists = new ArrayList<>();
        final List<ShopBycategoryModel.SliderImage> sliderImages = new ArrayList<>();

        final List<CartCount.CartcountList> cartcountLists = new ArrayList<>();

        adapter2 = new TopSearchProductListAdapter(topSearchproductLists, requireActivity(), navController);
        adapter1 = new PopularProductAdapter(popoularmodel, requireActivity(), navController);
        adapter = new ShopByCategoryAdapter(categoryModels, requireActivity());
        sliderAdapterforPharmacy = new SliderAdapterforPharmacy(sliderImages, requireActivity());


        onlinePharmacyBinding.popularRec.setAdapter(adapter1);
        onlinePharmacyBinding.SBC.setAdapter(adapter);
        onlinePharmacyBinding.topsearchproductrec.setAdapter(adapter2);
        onlinePharmacyBinding.bannerview.setAdapter(sliderAdapterforPharmacy);


        if (!utils.isNetworkConnected(requireActivity())) {
            Toast.makeText(requireActivity(), getString(R.string.not_connected_to_internet), Toast.LENGTH_SHORT).show();
            return;
        }
        AppUtils.showRequestDialog(requireActivity());

        ApiUtils.getShopByCategory(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<ShopBycategoryModel> models = (List<ShopBycategoryModel>) o;
                List<ShopBycategoryModel.CategoryModel> categoryModel = models.get(0).getCategoryList();
                categoryModels.addAll(categoryModel);
                adapter.notifyDataSetChanged();


                Log.d(TAG, "popularProductsList: " + models.get(0).getPopularProductsList().toString());
                List<ShopBycategoryModel.PopularProductList> popularProductLists = models.get(0).getPopularProductsList();
                popoularmodel.addAll(popularProductLists);
                adapter1.notifyDataSetChanged();


                Log.d(TAG, "searchProductList: " + models.get(0).getTopSearchproductLists().toString());
                List<ShopBycategoryModel.TopSearchproductList> topSearchproductLists1 = models.get(0).getTopSearchproductLists();
                topSearchproductLists.addAll(topSearchproductLists1);
                adapter2.notifyDataSetChanged();


                Log.d(TAG, "SliderImages: " + models.get(0).getBannerList().toString());
                List<ShopBycategoryModel.SliderImage> sliderImages1 = models.get(0).getBannerList();
                sliderImages.addAll(sliderImages1);
                sliderAdapterforPharmacy.notifyDataSetChanged();
                AppUtils.hideDialog();
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });


//        ApiUtils.getcartcountutils(requireActivity(), new ApiCallbackInterface() {
//            @Override
//            public void onSuccess(List<?> o) {
//                List<CartCount> models = (List<CartCount>) o;
//                //    List<CartCount.CartcountList> categoryModel = models.get(0).getCartcountList();
//                onlinePharmacyBinding.actionbarNotifcationTextview.setText(models.get(0).getCartcountList().get(1).getCartCount());
//                AppUtils.hideDialog();
//            }
//
//            @Override
//            public void onError(String s) {
//                AppUtils.hideDialog();
//            }
//
//            @Override
//            public void onFailed(Throwable throwable) {
//                AppUtils.hideDialog();
//            }
//        });
//

        onlinePharmacyBinding.textView58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("catData", "" + adapter.getShopCateData());
                navController.navigate(R.id.action_onlinePharmacyFragment_to_allCategoryFragment, bundle);
            }
        });


        onlinePharmacyBinding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_onlinePharmacyFragment_to_allProductsFragment);


            }
        });
        onlinePharmacyBinding.textView111.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_onlinePharmacyFragment_to_allProductsFragment);
            }
        });


        onlinePharmacyBinding.wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_onlinePharmacyFragment_to_fragmentAllWishLIstProduct);
                onlinePharmacyBinding.wish.setImageResource(R.drawable.heart);

            }
        });

        onlinePharmacyBinding.cartimge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_onlinePharmacyFragment_to_cart_Details_Fragment);
            }
        });

        onlinePharmacyBinding.Borderwithprescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(requireActivity(), "Coming Soon!", Toast.LENGTH_SHORT).show();

            }


        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }
}
