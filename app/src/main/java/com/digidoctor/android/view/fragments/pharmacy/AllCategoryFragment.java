package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import com.digidoctor.android.adapters.pharmacy.AllCategoryAdapter;
import com.digidoctor.android.databinding.FragmentAllCategoryBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.ShopBycategoryModel;
import com.digidoctor.android.utility.ApiUtils;

import java.util.ArrayList;
import java.util.List;

public class AllCategoryFragment extends Fragment {
    private static final String TAG = "AllCategoryFragment";

    AllCategoryAdapter adapter;

    FragmentAllCategoryBinding allCategoryBinding;
    NavController navController;

    String CategoryData;
    ShopBycategoryModel.CategoryModel shopBycategoryModel;

    private List<ShopBycategoryModel.CategoryModel> sbc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        allCategoryBinding = FragmentAllCategoryBinding.inflate(getLayoutInflater());
        return allCategoryBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        navController = Navigation.findNavController(view);
        sbc = new ArrayList<>();

        adapter = new AllCategoryAdapter(sbc, requireActivity(), navController);
        navController = Navigation.findNavController(view);

        if (getArguments() == null)
            return;

        CategoryData = getArguments().getString("catData");

        ApiUtils.getShopByCategory(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<ShopBycategoryModel> models = (List<ShopBycategoryModel>) o;
                List<ShopBycategoryModel.CategoryModel> categoryModel = models.get(0).getCategoryList();
                sbc.addAll(categoryModel);

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onError(String s) {

                Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        allCategoryBinding.specRec.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
