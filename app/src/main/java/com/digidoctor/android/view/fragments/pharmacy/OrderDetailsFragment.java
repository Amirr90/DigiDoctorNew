package com.digidoctor.android.view.fragments.pharmacy;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.adapters.pharmacy.OrderDetailsAdapter;
import com.digidoctor.android.adapters.pharmacy.OrderDetailsRelatedProductAdapter;
import com.digidoctor.android.databinding.FragmentOrderDetailsBinding;
import com.digidoctor.android.databinding.WriteareviveBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.pharmacyModel.OrderDetailModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailsFragment extends Fragment {
    NavController navController;
    FragmentOrderDetailsBinding fragmentOrderDetailsBinding;
    OrderDetailsAdapter orderDetailsAdapter;
    OrderDetailsRelatedProductAdapter orderDetailsRelatedProductAdapter;

    WriteareviveBinding writeareviveBinding;
    String orderDetailsId = null;
    String MemberID = null;
    String orderstatus;

    String Brand = null, productinfocode = null;
    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentOrderDetailsBinding = FragmentOrderDetailsBinding.inflate(getLayoutInflater());
        return fragmentOrderDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if (getArguments() == null)
            return;

        orderDetailsId = getArguments().getString("OrderDetailsID");


        final List<OrderDetailModel.Orderdetaillist.ProductDetails> orderdetails = new ArrayList<>();
        final List<OrderDetailModel.Orderdetaillist.relatedProducts> relatedProducts1 = new ArrayList<>();
        orderDetailsAdapter = new OrderDetailsAdapter(orderdetails, requireActivity());
        orderDetailsRelatedProductAdapter = new OrderDetailsRelatedProductAdapter(requireActivity(), relatedProducts1);

        fragmentOrderDetailsBinding.relatedrecycler.setAdapter(orderDetailsRelatedProductAdapter);


        ApiUtils.getorderdetails(orderDetailsId, requireActivity(), new ApiCallbackInterface() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onSuccess(List<?> o) {
                List<OrderDetailModel.Orderdetaillist> models = (List<OrderDetailModel.Orderdetaillist>) o;

                if (models.isEmpty())
                    return;

                //    Log.d("TAG", "onSuccess: " + models.get(0).getProductDetails().get(0).getBrandName());
                OrderDetailModel.Orderdetaillist.ProductDetails productDetail = models.get(0).getProductDetails().get(0);
                OrderDetailModel.Orderdetaillist.GetOrderStatus getOrderStatus = models.get(0).getOrderStatus().get(0);
                OrderDetailModel.Orderdetaillist.PriceDetails priceDetails = models.get(0).getPriceDetails().get(0);

                Brand = productDetail.getBrandName();
                productinfocode = productDetail.getProductInfoCode();
                relatedProducts1.clear();
                relatedProducts1.addAll(models.get(0).getRelatedProducts());
                orderDetailsRelatedProductAdapter.notifyDataSetChanged();

                if (relatedProducts1.isEmpty()) {
                    fragmentOrderDetailsBinding.textView128.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.textView129.setVisibility(View.GONE);
                }
                fragmentOrderDetailsBinding.textView129.setText(orderDetailsRelatedProductAdapter.getItemCount() + " more orders");

                fragmentOrderDetailsBinding.textView113.setText(productDetail.getProductName());
                fragmentOrderDetailsBinding.textView114.setText(String.valueOf(productDetail.getSubTotal()));
                fragmentOrderDetailsBinding.textView115.setText("Quantity: " + productDetail.getQuantity());
                fragmentOrderDetailsBinding.textView112.setText("Order ID: " + productDetail.getOrderNo());
                Glide.with(requireActivity()).load(productDetail.getImagePath()).placeholder(R.drawable.box_two)
                        .into(fragmentOrderDetailsBinding.imageView21);
                fragmentOrderDetailsBinding.shippingname.setText(productDetail.getCustomerName());
                fragmentOrderDetailsBinding.shippingaddress.setText(productDetail.getAddress());
                fragmentOrderDetailsBinding.ivMobile.setText(productDetail.getCustomerMobile());

                fragmentOrderDetailsBinding.textView117.setText(getOrderStatus.getOrderDate());
                fragmentOrderDetailsBinding.textView119.setText(getOrderStatus.getPackingDate());
                fragmentOrderDetailsBinding.textView121.setText(getOrderStatus.getShippiingDate());
                fragmentOrderDetailsBinding.textView123.setText(getOrderStatus.getDeliveryDate());
                fragmentOrderDetailsBinding.textView124.setText("Order Status: " + getOrderStatus.getOrderStatus() + " " + getOrderStatus.getCancelledDate());
                fragmentOrderDetailsBinding.paymentmode.setText(priceDetails.getPaymentMode());
                orderstatus = getOrderStatus.getOrderStatus();
                fragmentOrderDetailsBinding.textView142.setText(String.valueOf(priceDetails.getFinalAmount()));

                fragmentOrderDetailsBinding.setPrice(priceDetails);
                fragmentOrderDetailsBinding.textView20.setText(priceDetails.getTotalProducts() + " ( Qty: " + priceDetails.getTotalQuantity() + ")");


                if (getOrderStatus.getOrderStatus().equals("Delivered")) {
                    fragmentOrderDetailsBinding.ratingBar2.setVisibility(View.VISIBLE);
                    fragmentOrderDetailsBinding.textView125.setVisibility(View.VISIBLE);
                    fragmentOrderDetailsBinding.cancel.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.textView122.setText("Delivered");
                }

                if (getOrderStatus.getOrderStatus().equals("Cancelled")) {
                    fragmentOrderDetailsBinding.textView118.setText("Cancelled");
                    fragmentOrderDetailsBinding.textView118.setTextColor(Color.RED);
                    fragmentOrderDetailsBinding.view.setBackgroundColor(Color.RED);
                    fragmentOrderDetailsBinding.imageView48.setBackgroundResource(R.drawable.ic_baseline_check_circle_24);
                    fragmentOrderDetailsBinding.textView119.setText(getOrderStatus.getCancelledDate());
                    fragmentOrderDetailsBinding.textView120.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.textView122.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.view7.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.imageView49.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.view6.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.imageView50.setVisibility(View.GONE);
                    fragmentOrderDetailsBinding.cardView4.setVisibility(View.GONE);

                }

                if (getOrderStatus.getOrderDate().isEmpty()) {
                    fragmentOrderDetailsBinding.imageView47.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                }
                if (getOrderStatus.getPackingDate().isEmpty()) {
                    fragmentOrderDetailsBinding.imageView48.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                }
                if (getOrderStatus.getShippiingDate().isEmpty()) {
                    fragmentOrderDetailsBinding.imageView49.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
                }
                if (getOrderStatus.getDeliveryDate().isEmpty()) {
                    fragmentOrderDetailsBinding.imageView50.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
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

        fragmentOrderDetailsBinding.CancelorderTextview.setOnClickListener(view1 -> {
            if (orderstatus.equals("Cancelled") || orderstatus.equals("Delivered")) {
                fragmentOrderDetailsBinding.cancel.setVisibility(View.GONE);
            } else {
                new AlertDialog.Builder(requireActivity()).setTitle("Cancel Order")
                        .setMessage("Do you really want to Cancel this Order?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> ApiUtils.CancelOrder(orderDetailsId, requireActivity(), new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(List<?> o) {
                                Toast.makeText(requireActivity(), "Your Order Has Been Cancelled!", Toast.LENGTH_SHORT).show();
                                navController.navigateUp();
                            }

                            @Override
                            public void onError(String s) {
                                Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(Throwable throwable) {
                                Toast.makeText(requireActivity(), "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })).setNegativeButton("No", (dialogInterface, i) -> {

                        }).show();

            }

        });


        fragmentOrderDetailsBinding.textView125.setOnClickListener(view12 -> {
            dialog = new Dialog(requireActivity());

            dialog.setTitle("Write a Review");
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.writearevive);
            RatingBar star = dialog.findViewById(R.id.ratingBar3);
            EditText review = dialog.findViewById(R.id.editTextTextPersonName5);
            Button save = dialog.findViewById(R.id.button17);


            dialog.show();

            save.setOnClickListener(view121 -> {
                String rating = String.valueOf(star.getRating());
                String re = review.getText().toString().trim();
                final Map<String, String> map = new HashMap<>();

                map.put("pinfo", productinfocode);
                map.put("rating", rating);
                map.put("review", re);

                dialog.dismiss();
                ApiUtils.add_rating(requireActivity(), map, new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(List<?> o) {
                                AppUtils.hideDialog();
                                Toast.makeText(requireActivity(), "Thanks for Rate!", Toast.LENGTH_SHORT).show();

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
                        }
                );
            });


        });


    }
}
