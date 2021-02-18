package com.digidoctor.android.view.fragments.pharmacy;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.digidoctor.android.adapters.pharmacy.AddressAdapter;
import com.digidoctor.android.adapters.pharmacy.CartDetailsAdapter;
import com.digidoctor.android.adapters.pharmacy.PriceDetailsAdapter;
import com.digidoctor.android.databinding.FragmentOrderSummaryBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ProductInterface;
import com.digidoctor.android.model.pharmacyModel.CartDetailsResponse;
import com.digidoctor.android.model.pharmacyModel.OrderPlaceModelResponse;
import com.digidoctor.android.model.pharmacyModel.getaddressModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.hideSoftKeyboard;

public class OrderSummaryFragment extends Fragment implements ProductInterface {
    NavController navController;
    FragmentOrderSummaryBinding fragmentOrderSummaryBinding;
    CartDetailsAdapter cartDetailsAdapter;
    PriceDetailsAdapter priceDetailsAdapter;
    AddressAdapter addressAdapter;

    String AddressId = null;
    List<getaddressModel.getaddressDetails> addAdressModels = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        final List<CartDetailsResponse.GetCartDetails> getCartDetails = new ArrayList<>();
        final List<CartDetailsResponse.GetCartDetails.GetPriceDetails> gp = new ArrayList<>();


        fragmentOrderSummaryBinding.cart.setVisibility(View.GONE);
        cartDetailsAdapter = new CartDetailsAdapter(getCartDetails, requireActivity(), this);

        priceDetailsAdapter = new PriceDetailsAdapter(gp, requireActivity());

        addressAdapter = new AddressAdapter(addAdressModels, requireActivity());

        fragmentOrderSummaryBinding.cart.setAdapter(cartDetailsAdapter);

        getaddress();
        addAdressModels.clear();

        ApiUtils.getCartDetails(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<CartDetailsResponse.GetCartDetails> models = (List<CartDetailsResponse.GetCartDetails>) o;
                if (null == models || models.isEmpty())
                    return;

                if (models.get(0).getProductDetails().isEmpty())
                    return;
                List<CartDetailsResponse.GetCartDetails> sp = models.get(0).getProductDetails();
                getCartDetails.clear();
                getCartDetails.addAll(sp);
                cartDetailsAdapter.notifyDataSetChanged();
                CartDetailsResponse.GetCartDetails.GetPriceDetails price = models.get(0).getPriceDetails().get(0);
                fragmentOrderSummaryBinding.setCartprice(price);
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        fragmentOrderSummaryBinding.button6.setOnClickListener(view1 -> navController.navigate(R.id.action_orderSummaryFragment_to_allAddressFragment));


        fragmentOrderSummaryBinding.button5.setOnClickListener(view12 -> {

            if (addAdressModels.isEmpty()) {
                Toast.makeText(requireActivity(), "Kindly add Address First!", Toast.LENGTH_SHORT).show();
            } else {
                hideSoftKeyboard(PatientDashboard.getInstance());
                new AlertDialog.Builder(requireActivity())
                        .setMessage("Product Price ₹")
                        .setPositiveButton("Cash On Delivery ",
                                (dialog, id) -> {
                                    dialog.cancel();
                                    Orderplaced();
                                })
                        .setNegativeButton("Pay Online", (dialog, id) -> dialog.cancel()).show();
            }


        });
    }


    public void getaddress() {
        ApiUtils.getadddetails(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<getaddressModel.getaddressDetails> models = (List<getaddressModel.getaddressDetails>) o;

                Log.d("TAG", "onSuccessAddressLoad: " + models);
                if (models.isEmpty())
                    return;

                addAdressModels.clear();
                addAdressModels.addAll(models);


                if (!models.isEmpty()) {
                    for (int a = 0; a < models.size(); a++) {
                        if (models.get(a).getIsDefault().equals("true")) {
                            fragmentOrderSummaryBinding.textView28.setText(models.get(a).getName());
                            fragmentOrderSummaryBinding.textView30.setText(models.get(a).getMobileno());
                            fragmentOrderSummaryBinding.textView29.setText(models.get(a).getHouseNo() + " " + models.get(a).getArea() + " " + models.get(a).getCity() + "\n" + models.get(a).getState() + " " + models.get(a).getPincode());
                            fragmentOrderSummaryBinding.textView132.setVisibility(View.VISIBLE);
//
                        }
                    }
                }


                addressAdapter.notifyDataSetChanged();

                if (models != null) {
                    fragmentOrderSummaryBinding.cardView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();

                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Orderplaced() {

        AddressId = addAdressModels.get(0).getAddressId();
        ApiUtils.orderPlaced(AddressId, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<OrderPlaceModelResponse.GetOrderPlaced> models = (List<OrderPlaceModelResponse.GetOrderPlaced>) o;
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", models.get(0).getOrderNo());
                Toast.makeText(requireActivity(), "Your Order Has Been Placed." + bundle.getString("orderNo"), Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_orderSummaryFragment_to_orderPLacedFRagment, bundle);


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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentOrderSummaryBinding = FragmentOrderSummaryBinding.inflate(getLayoutInflater());

        return fragmentOrderSummaryBinding.getRoot();


    }


    @Override
    public void onDeleteItemClick(Object obj) {


    }

    @Override
    public void onCartItemUpdate(Object obj, int value) {

    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        getaddress();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }


}
