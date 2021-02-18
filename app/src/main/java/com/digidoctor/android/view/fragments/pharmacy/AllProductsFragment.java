package com.digidoctor.android.view.fragments.pharmacy;


import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.pharmacy.GetAllProductAdapter;
import com.digidoctor.android.databinding.FragmentAllProductlistBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.GetAllProductResponse;
import com.digidoctor.android.model.pharmacyModel.PharmacyModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.CATEGORY_ID;
import static com.digidoctor.android.utility.utils.getPrimaryUser;

public class AllProductsFragment extends Fragment {
    final List<GetAllProductResponse.GetProduct> AllProductModels = new ArrayList<>();
    FragmentAllProductlistBinding fragmentAllProductlistBinding;
    NavController navController;
    GetAllProductAdapter getAllProductAdapter;
    String CategoryID = null;
    int selectedItem = 0;
    private final List<GetAllProductResponse.GetProduct> contactListFiltered = new ArrayList<>();


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        getAllProductAdapter = new GetAllProductAdapter(AllProductModels, contactListFiltered, requireActivity());

        fragmentAllProductlistBinding.allproductrec.setAdapter(getAllProductAdapter);


        PharmacyModel pharmacyModel = new PharmacyModel();
        if (!utils.isNetworkConnected(requireActivity())) {
            Toast.makeText(requireActivity(), getString(R.string.not_connected_to_internet), Toast.LENGTH_SHORT).show();
            return;
        }


        if (null != getArguments()) {
            CategoryID = getArguments().getString(CATEGORY_ID);
            pharmacyModel.setCategoryId(CategoryID);
        }
        pharmacyModel.setMemberId(String.valueOf(getPrimaryUser(requireActivity()).getMemberId()));
        AllProductModels.clear();
        ApiUtils.getallpro(requireActivity(), pharmacyModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<GetAllProductResponse.GetProduct> models = (List<GetAllProductResponse.GetProduct>) o;
                if (null == models || models.isEmpty()) {
                    fragmentAllProductlistBinding.noproductfound.setVisibility(View.VISIBLE);
                    fragmentAllProductlistBinding.scrollview.setVisibility(View.GONE);

                    return;
                }
                AllProductModels.addAll(models);
                getAllProductAdapter.notifyDataSetChanged();

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

        fragmentAllProductlistBinding.button2.setOnClickListener(view1 -> dialogeSort());

        fragmentAllProductlistBinding.editTextTextSearchSpeciality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 1) {
                    fragmentAllProductlistBinding.progressBar3.setVisibility(View.VISIBLE);
                    getAllProductAdapter.getFilter().filter(charSequence);
                    getAllProductAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                getAllProductAdapter.getFilter().filter(editable);
                getAllProductAdapter.notifyDataSetChanged();
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentAllProductlistBinding = FragmentAllProductlistBinding.inflate(getLayoutInflater());
        return fragmentAllProductlistBinding.getRoot();


    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }


    private void dialogeSort() {


        String[] choices = {"Price High to Low", "Price Low to High", "Popularity High", "Popularity Low"};
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.choose_one)

                .setSingleChoiceItems(choices, selectedItem, (arg0, arg1) -> {
                })
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    selectedItem = selectedPosition;
                    switch (selectedPosition) {
                        case 0: {
                            Collections.sort(AllProductModels, (getProduct, t1) -> String.valueOf(getProduct.getMrp()).compareTo(String.valueOf(t1.getMrp())));
                            Toast.makeText(requireActivity(), "Showing Product Price High to Low", Toast.LENGTH_SHORT).show();
                            getAllProductAdapter.notifyDataSetChanged();

                        }
                        break;
                        case 1: {
                            Collections.sort(AllProductModels, (getProduct, t1) -> String.valueOf(getProduct.getMrp()).compareTo(String.valueOf(t1.getMrp())));
                            getAllProductAdapter.notifyDataSetChanged();
                            Toast.makeText(requireActivity(), "Showing Product Price Low to High", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 2: {
                            Collections.sort(AllProductModels, (getProduct, t1) -> String.valueOf(getProduct.getMrp()).compareTo(String.valueOf(t1.getMrp())));
                            getAllProductAdapter.notifyDataSetChanged();
                            Toast.makeText(requireActivity(), "Showing Product Price Low to High ", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 3: {
                            Collections.sort(AllProductModels, (getProduct, t1) -> String.valueOf(getProduct.getStarRating()).compareTo(String.valueOf(t1.getStarRating())));

                            Toast.makeText(requireActivity(), "Showing Product by Popularity High", Toast.LENGTH_SHORT).show();
                            getAllProductAdapter.notifyDataSetChanged();
                        }
                        break;
                        case 4: {

                            Collections.sort(AllProductModels, (getProduct, t1) -> String.valueOf(getProduct.getStarRating()).compareTo(String.valueOf(t1.getStarRating())));
                            Toast.makeText(requireActivity(), "Showing Product by Popularity Low", Toast.LENGTH_SHORT).show();
                            getAllProductAdapter.notifyDataSetChanged();
                        }
                        break;
                    }

                })

                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss())

                .show();


    }


}
