
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
import com.digidoctor.android.adapters.pharmacy.ProductColorAdapter;
import com.digidoctor.android.adapters.pharmacy.ProductDetaillAdapter;
import com.digidoctor.android.adapters.pharmacy.ProductFalvourAdapter;
import com.digidoctor.android.adapters.pharmacy.ProductMaterialAdapter;
import com.digidoctor.android.adapters.pharmacy.ProductSizeAdapter;
import com.digidoctor.android.adapters.pharmacy.ProductSliderviewAdapter;
import com.digidoctor.android.adapters.pharmacy.SimilarproductADapter;
import com.digidoctor.android.adapters.pharmacy.ratingandreviewadapter;
import com.digidoctor.android.databinding.FragmentProductDetailsBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.pharmacyModel.AddToCartModel;
import com.digidoctor.android.model.pharmacyModel.AddtoWishlist;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.digidoctor.android.model.pharmacyModel.ProductModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.getPrimaryUser;

public class ProductDetailsFragment extends Fragment {
    private static final String TAG = "ProductDetailsFragment";
    public static List<ProductDetailModelResponse.ProductDetailsList.ProductDetail> AllProductModels = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> productReviewLists = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.SimilarProduct> similarProducts = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider> productDetailsSliders = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.SizeDetails> sizeDetails = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.FlavourDetails> flavourDetails = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.ColorDetails> colorDetails = new ArrayList<>();
    final List<ProductDetailModelResponse.ProductDetailsList.MaterialDetails> materialdetails = new ArrayList<>();

    FragmentProductDetailsBinding fragmentProductDetailsBinding;
    NavController navController;
    ProductDetaillAdapter productDetaillAdapter;
    ratingandreviewadapter ratingandreviewadapter;
    SimilarproductADapter similarproductADapter;
    ProductSliderviewAdapter productSliderviewAdapter;
    ProductSizeAdapter productSizeAdapter;
    ProductMaterialAdapter productMaterialAdapter;
    ProductFalvourAdapter productFalvourAdapter;
    ProductColorAdapter productColorAdapter;
    public String productId = null;
    String SizeId = null;
    String memberId = null;

    static String MedicineID = null, MedicineName = null;

    public static ProductDetailsFragment instance;

    public static ProductDetailsFragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProductDetailsBinding = FragmentProductDetailsBinding.inflate(getLayoutInflater());
        instance = this;
        return fragmentProductDetailsBinding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        if (getArguments() == null)
            return;

        productId = String.valueOf(getArguments().getInt("productID"));
        memberId = String.valueOf(getArguments().getInt("member"));

        SizeId = String.valueOf(getArguments().getInt("Sizeid"));


        productDetaillAdapter = new ProductDetaillAdapter(requireActivity());
        ratingandreviewadapter = new ratingandreviewadapter(productReviewLists, requireActivity());
        similarproductADapter = new SimilarproductADapter(similarProducts, requireActivity());
        productSliderviewAdapter = new ProductSliderviewAdapter(productDetailsSliders, requireActivity());
        productSizeAdapter = new ProductSizeAdapter(sizeDetails, requireActivity());
        productFalvourAdapter = new ProductFalvourAdapter(flavourDetails, requireActivity());
        productColorAdapter = new ProductColorAdapter(colorDetails, requireActivity());
        productMaterialAdapter = new ProductMaterialAdapter(materialdetails, requireActivity());


        fragmentProductDetailsBinding.recyclerView2.setAdapter(ratingandreviewadapter);
        fragmentProductDetailsBinding.similarrecy.setAdapter(similarproductADapter);
        if (productSliderviewAdapter != null)
            fragmentProductDetailsBinding.productDetailsSliderView.setSliderAdapter(productSliderviewAdapter);

        fragmentProductDetailsBinding.recyclerView3.setAdapter(productSizeAdapter);
        fragmentProductDetailsBinding.recyclerView4.setAdapter(productFalvourAdapter);
        fragmentProductDetailsBinding.recyclerViewcolor.setAdapter(productColorAdapter);
        fragmentProductDetailsBinding.materialrecycler.setAdapter(productMaterialAdapter);

        AllProductModels.clear();
        getproductdetails();

        fragmentProductDetailsBinding.textView200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_productDetailsFragment_to_medicineDetailsFragment);
            }
        });


        fragmentProductDetailsBinding.textView141.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("productId", productId);
            navController.navigate(R.id.action_productDetailsFragment_to_allRatingAndReviewFragment, bundle);
        });

        fragmentProductDetailsBinding.imageView18.setOnClickListener(view12 -> {
            if (AllProductModels.isEmpty())
                return;

            if (AllProductModels.get(0).getWishlistStatus().equals("0")) {
                AddtoWishlist addtoWishList = new AddtoWishlist();
                addtoWishList.setProductInfoCode(AllProductModels.get(0).getProductInfoCode());
                addtoWishList.setMemberId(String.valueOf(utils.getPrimaryUser(requireActivity()).getMemberId()));
                String wishlistStatus = "1";
                addToWishList(addtoWishList, wishlistStatus);
                AllProductModels.clear();
                clearAll();
                getproductdetails();
                Toast.makeText(requireActivity(), "Added in WishList", Toast.LENGTH_SHORT).show();
                fragmentProductDetailsBinding.imageView18.setChecked(true);
            } else if (AllProductModels.get(0).getWishlistStatus().equals("1")) {
                AddtoWishlist addtoWishList = new AddtoWishlist();
                addtoWishList.setProductInfoCode(AllProductModels.get(0).getProductInfoCode());
                addtoWishList.setMemberId(String.valueOf(utils.getPrimaryUser(requireActivity()).getMemberId()));
                String wishlistStatus = "0";
                addToWishList(addtoWishList, wishlistStatus);
                AllProductModels.clear();
                clearAll();
                getproductdetails();
                Toast.makeText(requireActivity(), "Removed From Your WishList!", Toast.LENGTH_SHORT).show();
            }


        });


        fragmentProductDetailsBinding.btnAddToCartFromProductPage.setOnClickListener(view13 -> {
            String tag = (String) fragmentProductDetailsBinding.btnAddToCartFromProductPage.getTag();
            if (tag.equalsIgnoreCase("1")) {
                AppUtils.showRequestDialog(requireActivity());
                AddToCartModel addToCartModel = new AddToCartModel();
                addToCartModel.setQuantity("1");
                addToCartModel.setMemberId(String.valueOf(utils.getPrimaryUser(requireActivity()).getMemberId()));
                addToCartModel.setProductInfoCode(AllProductModels.get(0).getProductInfoCode());
                ApiUtils.addtocart(addToCartModel, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(List<?> o) {
                        AppUtils.hideDialog();
                        Toast.makeText(requireActivity(), AllProductModels.get(0).getBrandName() + " Added to cart ", Toast.LENGTH_SHORT).show();
                        fragmentProductDetailsBinding.btnAddToCartFromProductPage.setText("Go To Cart");
                        fragmentProductDetailsBinding.btnAddToCartFromProductPage.setTag("0");
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
            } else
                navController.navigate(R.id.action_productDetailsFragment_to_cart_Details_Fragment);
        });


    }

    public void getproductdetails() {


        ProductModel model = new ProductModel();

        User user = getPrimaryUser(requireActivity());
        model.setMemberId(String.valueOf(user.getId()));
        model.setProductId(Integer.parseInt(productId));

        try {
            ApiUtils.getProductdetailsbyProductID(model, requireActivity(), new ApiCallbackInterface() {


                @Override
                public void onSuccess(List<?> o) {
                    List<ProductDetailModelResponse.ProductDetailsList> models = (List<ProductDetailModelResponse.ProductDetailsList>) o;
                    Log.d(TAG, "onSuccess: " + models.get(0).getProductDetails());
                    AllProductModels.addAll(models.get(0).getProductDetails());

                    if (!AllProductModels.isEmpty() && !models.isEmpty()) {
                        setProduct(AllProductModels.get(0));
                        updateProduct(models);
                    }


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
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(requireActivity(), "try again", Toast.LENGTH_SHORT).show();
        }

    }

    public void setProduct(ProductDetailModelResponse.ProductDetailsList.ProductDetail productDetail) {
        fragmentProductDetailsBinding.setProduct(productDetail);
    }

    public void updateProduct(List<ProductDetailModelResponse.ProductDetailsList> models) {

        clearAll();
        if (models.get(0).getMaterialDetails().isEmpty()) {
            fragmentProductDetailsBinding.textView199.setVisibility(View.GONE);
            fragmentProductDetailsBinding.materialrecycler.setVisibility(View.GONE);
        }
        if (models.get(0).getFlavourDetails().isEmpty()) {
            fragmentProductDetailsBinding.textView144.setVisibility(View.GONE);
            fragmentProductDetailsBinding.recyclerView4.setVisibility(View.GONE);
        }
        if (models.get(0).getColorDetails().isEmpty()) {
            fragmentProductDetailsBinding.textView145.setVisibility(View.GONE);
            fragmentProductDetailsBinding.recyclerViewcolor.setVisibility(View.GONE);
        }
        if (models.get(0).getSizeDetails().isEmpty()) {
            fragmentProductDetailsBinding.textView60.setVisibility(View.GONE);
            fragmentProductDetailsBinding.recyclerView3.setVisibility(View.GONE);
        }

        if (models.get(0).getProductDetails().get(0).getIsMedicine() == 1) {
            fragmentProductDetailsBinding.textView200.setVisibility(View.VISIBLE);
        }

        MedicineID = String.valueOf(models.get(0).getProductDetails().get(0).getKnowmedMedicineId());
        MedicineName = models.get(0).getProductDetails().get(0).getProductName();

        if (!AllProductModels.isEmpty())
            if (AllProductModels.get(0).getWishlistStatus().equals("1")) {
                fragmentProductDetailsBinding.imageView18.setChecked(true);
            }

        Log.d(TAG, "onSuccess: " + models.get(0).getReviewDetails());
        List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> topSearchproductLists1 = models.get(0).getReviewDetails();

        //  Collections.reverse(topSearchproductLists1);
        productReviewLists.clear();
        productReviewLists.addAll(topSearchproductLists1);

        ratingandreviewadapter.notifyDataSetChanged();

        if (productReviewLists.isEmpty()) {
            fragmentProductDetailsBinding.recyclerView2.setVisibility(View.GONE);
            fragmentProductDetailsBinding.textView141.setVisibility(View.GONE);
        }


        Log.d(TAG, "onSuccess: " + models.get(0).getSimilarProduct());
        List<ProductDetailModelResponse.ProductDetailsList.SimilarProduct> similarProducts1 = models.get(0).getSimilarProduct();
        similarProducts.addAll(similarProducts1);
        similarproductADapter.notifyDataSetChanged();


        Log.d(TAG, "onSuccessImage: " + models.get(0).getImagePath());
        List<ProductDetailModelResponse.ProductDetailsList.ProductDetailsSlider> productDetailsSliders1 = models.get(0).getImagePath();
        productDetailsSliders.clear();
        productDetailsSliders.addAll(productDetailsSliders1);
        productSliderviewAdapter.notifyDataSetChanged();


        Log.d(TAG, "onSuccessProductSize: " + models.get(0).getSizeDetails());
        List<ProductDetailModelResponse.ProductDetailsList.SizeDetails> sizeDetails1 = models.get(0).getSizeDetails();
        sizeDetails.clear();
        sizeDetails.addAll(sizeDetails1);
        productSizeAdapter.notifyDataSetChanged();

        Log.d(TAG, "onSuccess: " + models.get(0).getFlavourDetails());
        List<ProductDetailModelResponse.ProductDetailsList.FlavourDetails> flavourDetails1 = models.get(0).getFlavourDetails();
        flavourDetails.clear();
        flavourDetails.addAll(flavourDetails1);
        productFalvourAdapter.notifyDataSetChanged();

        Log.d(TAG, "onSuccess: " + models.get(0).getColorDetails());
        List<ProductDetailModelResponse.ProductDetailsList.ColorDetails> colorDetails1 = models.get(0).getColorDetails();
        colorDetails.clear();
        colorDetails.addAll(colorDetails1);
        productColorAdapter.notifyDataSetChanged();

        Log.d(TAG, "onSuccess: " + models.get(0).getMaterialDetails());
        List<ProductDetailModelResponse.ProductDetailsList.MaterialDetails> materialDetails1 = models.get(0).getMaterialDetails();
        materialdetails.clear();
        materialdetails.addAll(materialDetails1);
        productMaterialAdapter.notifyDataSetChanged();
    }

    public void clearAll() {

        productReviewLists.clear();
        similarProducts.clear();
        // productDetailsSliders.clear();
        sizeDetails.clear();
        flavourDetails.clear();
        colorDetails.clear();
    }

    public void addToWishList(AddtoWishlist addtoWishlist, String wishlistStatus) {

        addtoWishlist.setIsWhislist(wishlistStatus);


        ApiUtils.addtowishlist(addtoWishlist, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();

            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: Add To WishList " + throwable.getLocalizedMessage());

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

}
