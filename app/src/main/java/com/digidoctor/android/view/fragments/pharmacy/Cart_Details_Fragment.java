package com.digidoctor.android.view.fragments.pharmacy;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.digidoctor.android.adapters.pharmacy.CartDetailsAdapter;
import com.digidoctor.android.adapters.pharmacy.PriceDetailsAdapter;
import com.digidoctor.android.databinding.FragmentCartListBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ProductInterface;
import com.digidoctor.android.model.pharmacyModel.AddToCartModel;
import com.digidoctor.android.model.pharmacyModel.CartDetailsResponse;
import com.digidoctor.android.model.pharmacyModel.CouponModel;
import com.digidoctor.android.model.pharmacyModel.DeleteItems;
import com.digidoctor.android.model.pharmacyModel.PriceDetail;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart_Details_Fragment extends Fragment implements ProductInterface {
    public static final String COUPON_CODE = "couponCode";
    NavController navController;

    FragmentCartListBinding fragmentCartListBinding;
    CartDetailsAdapter cartDetailsAdapter;
    PriceDetailsAdapter priceDetailsAdapter;
    List<CartDetailsResponse.GetCartDetails> getCartDetails = new ArrayList<>();
    List<CartDetailsResponse.GetCartDetails.GetPriceDetails> gp = new ArrayList<>();


    List<CartDetailsResponse.GetCartDetails> sp = new ArrayList<>();

    String couponCode = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentCartListBinding = FragmentCartListBinding.inflate(getLayoutInflater());
        return fragmentCartListBinding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        if (null != getArguments()) {
            couponCode = getArguments().getString(COUPON_CODE);
            setCoupon(couponCode);
        }


        cartDetailsAdapter = new CartDetailsAdapter(getCartDetails, requireActivity(), this);

        priceDetailsAdapter = new PriceDetailsAdapter(gp, requireActivity());

        fragmentCartListBinding.cartrecyc.setAdapter(cartDetailsAdapter);
        getCartDetails.clear();
        gp.clear();
        updateCartData();

        fragmentCartListBinding.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_cart_Details_Fragment_to_orderSummaryFragment);
            }
        });

        fragmentCartListBinding.textView106.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_cart_Details_Fragment_to_allCoupneFragment);
            }
        });

        fragmentCartListBinding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_cart_Details_Fragment_to_allProductsFragment);
            }
        });
        fragmentCartListBinding.btnValidateCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String code = fragmentCartListBinding.editTextTextPersonName2.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(requireActivity(), "Select Coupon First", Toast.LENGTH_SHORT).show();

                } else {
                    AppUtils.hideDialog();
                    validateCoupon(code);
                }

            }
        });

    }

    private void setCoupon(String couponCode) {
        fragmentCartListBinding.editTextTextPersonName2.setText(couponCode);
    }

    private void validateCoupon(String code) {
        AppUtils.showRequestDialog(requireActivity());
        CouponModel couponModel = new CouponModel(
                String.valueOf(utils.getPrimaryUser(requireActivity()).getMemberId()),
                code,
                String.valueOf(gp.get(0).getTotalAmount()));

        ApiUtils.validateCoupon(couponModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                updateCartData();
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
    }

    private void updateCartData() {

        AppUtils.showRequestDialog(requireActivity());

        ApiUtils.getCartDetails(requireActivity(), new ApiCallbackInterface() {

            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<CartDetailsResponse.GetCartDetails> models = (List<CartDetailsResponse.GetCartDetails>) o;

                if (models.isEmpty())
                    return;

                if (null == models || getCartDetails.isEmpty() || models.get(0).getProductDetails().isEmpty())
                    //Show No product in cart layout

                    fragmentCartListBinding.cartLay.setVisibility(View.GONE);
                sp = models.get(0).getProductDetails();
                gp.clear();
                gp.addAll(models.get(0).getPriceDetails());


                getCartDetails.clear();
                getCartDetails.addAll(models.get(0).getProductDetails());
                cartDetailsAdapter.notifyDataSetChanged();
                priceDetailsAdapter.notifyDataSetChanged();
                if (!models.get(0).getPriceDetails().isEmpty()) {
                    CartDetailsResponse.GetCartDetails.GetPriceDetails price = models.get(0).getPriceDetails().get(0);
                    updatePriceDetails(price);

                }


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
    }

    @Override
    public void onDeleteItemClick(Object obj) {

        final CartDetailsResponse.GetCartDetails gc = (CartDetailsResponse.GetCartDetails) obj;
        AppUtils.showRequestDialog(requireActivity());
        final DeleteItems items = new DeleteItems(gc.getCartId());
        getCartDetails.clear();
        ApiUtils.deleteItems(items, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                updateCartData();
                Toast.makeText(requireActivity(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                List<CartDetailsResponse.GetCartDetails> cartDetails = (List<CartDetailsResponse.GetCartDetails>) o;


                if (null == cartDetails || cartDetails.isEmpty() || cartDetails.get(0).getProductDetails().isEmpty()) {
                    //Show No product in cart layout
                    Toast.makeText(requireActivity(), "No product in cart", Toast.LENGTH_SHORT).show();
                    fragmentCartListBinding.cartLay.setVisibility(View.GONE);
                } else {
                    fragmentCartListBinding.cartLay.setVisibility(View.VISIBLE);
                    List<CartDetailsResponse.GetCartDetails> getProductDetails = cartDetails.get(0).getProductDetails();
                    for (CartDetailsResponse.GetCartDetails items : getProductDetails) {
                        getCartDetails.clear();
                        getCartDetails.add(items);

                    }
                    cartDetailsAdapter.notifyDataSetChanged();


                    if (cartDetails.get(0).getPriceDetails() != null)
                        gp.clear();
                    gp.addAll(cartDetails.get(0).getPriceDetails());


                    if (!cartDetails.get(0).getProductDetails().isEmpty())
                        updatePriceDetails(cartDetails.get(0).getPriceDetails().get(0));
                }

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
    }

    public void updatePriceDetails(CartDetailsResponse.GetCartDetails.GetPriceDetails
                                           getPriceDetails) {

        PriceDetail priceDetail = new PriceDetail();
        priceDetail.setDelievryCharge(Double.valueOf(getPriceDetails.getDelievryCharge()));
        priceDetail.setSaveAmount(Double.valueOf(getPriceDetails.getSaveAmount()));
        priceDetail.setTotalAmount(Double.valueOf(getPriceDetails.getTotalAmount()));
        priceDetail.setTotalMrp(Double.valueOf(getPriceDetails.getTotalMrp()));
        priceDetail.setTotalProducts(Integer.parseInt(getPriceDetails.getTotalProducts()));
        priceDetail.setCouponAmount(Double.valueOf(getPriceDetails.getCouponAmount()));
        priceDetail.setCouponCode(getPriceDetails.getCouponCode());

        Log.d("TAG", "updatePriceDetails: " + priceDetail);
        fragmentCartListBinding.setPriceDetails(priceDetail);


        if (getPriceDetails.getTotalProducts().equals("0"))
            return;


        fragmentCartListBinding.cartLay.setVisibility(getPriceDetails.getTotalProducts().equals("0.") || getPriceDetails.getTotalProducts().equals("0") ? View.GONE : View.VISIBLE);


    }

    @Override
    public void onCartItemUpdate(Object obj, int value) {
        CartDetailsResponse.GetCartDetails getcart = (CartDetailsResponse.GetCartDetails) obj;

        AppUtils.showRequestDialog(requireActivity());
        AddToCartModel addToCartModel = new AddToCartModel();


        addToCartModel.setQuantity(String.valueOf(value));
        addToCartModel.setMemberId(String.valueOf(utils.getPrimaryUser(requireActivity()).getMemberId()));
        addToCartModel.setProductInfoCode(getcart.getProductInfoCode());

        getCartDetails.clear();
        ApiUtils.addtocart(addToCartModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                getCartDetails.clear();
                updateCartData();


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
                AppUtils.hideDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        updateCartData();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}
