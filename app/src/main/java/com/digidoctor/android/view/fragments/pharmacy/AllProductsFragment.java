package com.digidoctor.android.view.fragments.pharmacy;


import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import java.util.Comparator;
import java.util.List;

import static com.digidoctor.android.utility.utils.CATEGORY_ID;
import static com.digidoctor.android.utility.utils.getPrimaryUser;

public class AllProductsFragment extends Fragment {
    private static final String TAG = "AllProductsFragment";
    final List<GetAllProductResponse.GetProduct> AllProductModels = new ArrayList<>();
    FragmentAllProductlistBinding fragmentAllProductlistBinding;
    NavController navController;
    GetAllProductAdapter getAllProductAdapter;
    AlertDialog optionDialog;
    String CategoryID = null;
    int selectedItem = 0;
    private SearchView searchView;
    private List<GetAllProductResponse.GetProduct> contactListFiltered = new ArrayList<>();


    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

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

        fragmentAllProductlistBinding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogeSort();

            }
        });

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


        fragmentAllProductlistBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // navController.navigate(R.id.action_allProductsFragment_to_fillterActivity);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentAllProductlistBinding = FragmentAllProductlistBinding.inflate(getLayoutInflater());
        return fragmentAllProductlistBinding.getRoot();


    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
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
                            Collections.sort(AllProductModels, new Comparator<GetAllProductResponse.GetProduct>() {
                                @Override
                                public int compare(GetAllProductResponse.GetProduct getProduct, GetAllProductResponse.GetProduct t1) {
/*
                                    String p1 = String.valueOf(getProduct.getOfferedPrice());
                                    String p2 = String.valueOf(t1.getOfferedPrice());*/
                                    return String.valueOf(getProduct.getMrp()).compareTo(String.valueOf(t1.getMrp()));
                                    //  return p1.compareTo(p2);
                                }
                            });
                            Toast.makeText(requireActivity(), "Showing Product Price High to Low", Toast.LENGTH_SHORT).show();
                            getAllProductAdapter.notifyDataSetChanged();

                        }
                        break;
                        case 1: {
                            Collections.sort(AllProductModels, new Comparator<GetAllProductResponse.GetProduct>() {
                                @Override
                                public int compare(GetAllProductResponse.GetProduct getProduct, GetAllProductResponse.GetProduct t1) {
                                    return String.valueOf(getProduct.getMrp()).compareTo(String.valueOf(t1.getMrp()));
                                }
                            });
                            getAllProductAdapter.notifyDataSetChanged();
                            Toast.makeText(requireActivity(), "Showing Product Price Low to High", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 2: {
                            Collections.sort(AllProductModels, new Comparator<GetAllProductResponse.GetProduct>() {
                                @Override
                                public int compare(GetAllProductResponse.GetProduct getProduct, GetAllProductResponse.GetProduct t1) {
                                    return String.valueOf(getProduct.getMrp()).compareTo(String.valueOf(t1.getMrp()));
                                }
                            });
                            getAllProductAdapter.notifyDataSetChanged();
                            Toast.makeText(requireActivity(), "Showing Product Price Low to High ", Toast.LENGTH_SHORT).show();
                        }
                        break;
                        case 3: {
                            //Collections.sort(getAllProductModels, (o1, o2) -> o2.getUnitPrice().compareTo(o1.getUnitPrice()));
                            Collections.sort(AllProductModels, new Comparator<GetAllProductResponse.GetProduct>() {
                                @Override
                                public int compare(GetAllProductResponse.GetProduct getProduct, GetAllProductResponse.GetProduct t1) {
                                    return String.valueOf(getProduct.getStarRating()).compareTo(String.valueOf(t1.getStarRating()));
                                }
                            });

                            Toast.makeText(requireActivity(), "Showing Product by Popularity High", Toast.LENGTH_SHORT).show();
                            getAllProductAdapter.notifyDataSetChanged();
                        }
                        break;
                        case 4: {

                            Collections.sort(AllProductModels, new Comparator<GetAllProductResponse.GetProduct>() {
                                @Override
                                public int compare(GetAllProductResponse.GetProduct getProduct, GetAllProductResponse.GetProduct t1) {
                                    return String.valueOf(getProduct.getStarRating()).compareTo(String.valueOf(t1.getStarRating()));
                                }
                            });
                            //Collections.sort(getAllProductModels, (o1, o2) -> o1.getUnitPrice().compareTo(o2.getUnitPrice()));
                            Toast.makeText(requireActivity(), "Showing Product by Popularity Low", Toast.LENGTH_SHORT).show();
                            getAllProductAdapter.notifyDataSetChanged();
                        }
                        break;
                    }

                })

                .setNegativeButton(R.string.cancel, (dialog, id) -> dialog.dismiss())

                .show();

/*dialogSort = new Dialog(mActivity);

dialogSort.setTitle("");
dialogSort.setContentView(R.layout.alert_shorting);
//getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
dialogSort.getWindow().setBackgroundDrawable(new
ColorDrawable(Color.TRANSPARENT));
// builder1.getWindow().setBackgroundDrawableResource(R.drawable.dialouge_box_design);
dialogSort.setCancelable(true);
dialogSort.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

rvSort = dialogSort.findViewById(R.id.rvShort);
rvSort.setLayoutManager(new LinearLayoutManager(mActivity));
if (AppUtils.isNetworkConnected(mActivity)) {
hitSortList();
} else {
AppUtils.showToastSort(mActivity, getString(R.string.noInternetConnection));
}
dialogSort.show();*/

    }


}
