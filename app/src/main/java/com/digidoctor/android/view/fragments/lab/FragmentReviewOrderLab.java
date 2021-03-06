package com.digidoctor.android.view.fragments.lab;

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
import com.digidoctor.android.adapters.labadapter.CartItemViewAdapter;
import com.digidoctor.android.databinding.FragmentReviewOrderLabBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.CartInterface;
import com.digidoctor.android.interfaces.LabOrderInterface;
import com.digidoctor.android.model.ResponseModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.labmodel.CartModel;
import com.digidoctor.android.model.labmodel.LabOrderModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.Cart;
import com.digidoctor.android.utility.Payment;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.wallet.Wallet;
import com.digidoctor.android.wallet.WalletInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.digidoctor.android.utility.Payment.PAY_MODE_RAZOR_PAY;
import static com.digidoctor.android.utility.utils.APPOINTMENT_DATE;
import static com.digidoctor.android.utility.utils.APPOINTMENT_TIME;
import static com.digidoctor.android.utility.utils.KEY_AMOUNT;
import static com.digidoctor.android.utility.utils.KEY_DOC_ID;
import static com.digidoctor.android.utility.utils.KEY_IS_ERA_USER;
import static com.digidoctor.android.utility.utils.KEY_PATIENT_NAME;
import static com.digidoctor.android.utility.utils.MEMBER_ID;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.getUserForBooking;


public class FragmentReviewOrderLab extends Fragment implements LabOrderInterface, CartInterface, WalletInterface {
    private static final String TAG = "FragmentReviewOrderLab";


    String amountToPay;
    FragmentReviewOrderLabBinding binding;
    NavController navController;
    public static Payment labPayment;
    LabOrderModel labOrderModel;
    Cart cart;

    List<CartModel> cartModelList;

    String addressID = "", pathologyID = "", name = "Aamirr khan";

    CartItemViewAdapter adapter;

    String date, time, memberId;

    String amountToAddForLabFromWallet = "100";


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReviewOrderLabBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (getArguments() == null)
            return;

        labPayment = new Payment(requireActivity());
        labOrderModel = new LabOrderModel();

        date = getArguments().getString("date");
        time = getArguments().getString("time");
        memberId = getArguments().getString("memberId");
        addressID = getArguments().getString("addressId");
        name = getArguments().getString("name");


        binding.btnContinue.setOnClickListener(v -> {
            AppUtils.showRequestDialog(requireActivity());
            setLabOrderModel();
            if (!TextUtils.isEmpty(amountToPay)) {
                getTrxNumber();

            }
        });

        Wallet wallet = new Wallet(requireActivity(), this);
        wallet.fetchWalletAmount();


        binding.checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                wallet.addWalletAmountForLab(amountToAddForLabFromWallet);
                binding.tvWalletAppliedAmount.setText(AppUtils.getCurrencyFormat(amountToAddForLabFromWallet));
                amountToPay = String.valueOf(Integer.parseInt(amountToPay) - Integer.parseInt(amountToAddForLabFromWallet));
            } else {
                wallet.cancelToAddWalletAmount(amountToAddForLabFromWallet);
                amountToPay = String.valueOf(Integer.parseInt(amountToPay) + Integer.parseInt(amountToAddForLabFromWallet));
            }
            updateCartAmount(amountToPay);
            binding.walletAmountLay.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

    }

    private void updateCartAmount(String amountToPay) {
        binding.tvTotalCartPrice.setText(AppUtils.getCurrencyFormat(amountToPay));
        binding.btnContinue.setText(String.format("Pay %s", AppUtils.getCurrencyFormat(amountToPay)));
        labPayment.setAmount(amountToPay);
    }

    private void getTrxNumber() {

        Map<String, Object> map = new HashMap<>();
        User user = getUserForBooking(requireActivity());
        map.put(KEY_PATIENT_NAME, user.getName());
        map.put(KEY_AMOUNT, amountToPay);

        map.put(MOBILE_NUMBER, user.getMobileNo());
        map.put(MEMBER_ID, user.getMemberId().toString());
        map.put(KEY_DOC_ID, "0");
        map.put(APPOINTMENT_DATE, date);
        map.put(APPOINTMENT_TIME, time);

        map.put(KEY_IS_ERA_USER, null == user.getIsEraUser() ? "0" : user.getIsEraUser().toString());
        map.put("transactionType", "lab");

        ApiUtils.getTransactionNo(map, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<ResponseModel.HashModel> models = (List<ResponseModel.HashModel>) o;
                if (null != models) {
                    String tId = models.get(0).getTaxId();
                    utils.setString("txid", tId, requireActivity());
                    labOrderModel.setTrancationNo(tId);
                    labPayment.startPayment(labOrderModel.getPaymentMode());
                }


            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "try again !!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "try again !!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void setLabOrderModel() {

        User user = getUserForBooking(requireActivity());

        labPayment.setEmail(user.getEmailId());
        labPayment.setMobileNumber(user.getMobileNo());
        labPayment.setTxId(String.valueOf(System.currentTimeMillis()));
        labPayment.setName(getArguments() == null ? user.getName() : name);

        if (null != getArguments()) {

            labOrderModel.setAddressId(addressID);
            String date = getArguments().getString("date");
            labOrderModel.setAppointmentDate(AppUtils.parseDate(date, "yyyy-MM-dd", "yyyy/MM/dd"));
            labOrderModel.setAppointmentTime(getArguments().getString("time"));
            labOrderModel.setPathalogyId("1");
            labOrderModel.setMemberId(getArguments().getString("memberId"));
            labOrderModel.setPaymentMode(PAY_MODE_RAZOR_PAY);
            labOrderModel.setCouponCode("");
            labOrderModel.setUniqueNo("0");
            labOrderModel.setDtPaymentTable("");
            labPayment.setMemberId(labOrderModel.getMemberId());
            labPayment.setLabModel(labOrderModel);
            labPayment.setLabInterface(this);
        }

    }

    @Override
    public void onOrderPlaced(Object obj) {
        AppUtils.hideDialog();
        Toast.makeText(requireActivity(), "Order Placed Successfully !!", Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.action_fragmentReviewOrderLab_to_labOrdersFragment);
    }

    @Override
    public void onFailed(Object obj) {
        Toast.makeText(requireActivity(), "Failed to place Order !! " + (String) obj, Toast.LENGTH_SHORT).show();
        navController.navigateUp();
    }

    @Override
    public void orders(Object obj) {

    }

    @Override
    public void onCancelOrder(Object obj) {

    }


    @Override
    public void onResume() {
        super.onResume();

        getOrderDetailFromCart();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    private void getOrderDetailFromCart() {
        //init Cart Interface & Class
        cart = new Cart(requireActivity(), this);
        cart.getCart();


        //init Adapter
        cartModelList = new ArrayList<>();

    }

    @Override
    public void onFailed(String msg) {

    }

    @Override
    public void onCartItemAdded(Object obj) {

    }

    @Override
    public void onCartItemDeleted(Object obj) {

    }

    @Override
    public void cartItem(Object obj) {

        cartModelList = new ArrayList<>();
        adapter = new CartItemViewAdapter(cartModelList);
        binding.recCartItem.setAdapter(adapter);
        this.cartModelList.clear();
        List<CartModel> cartModelList = (List<CartModel>) obj;
        if (null != cartModelList && !cartModelList.isEmpty()) {
            this.cartModelList.addAll(cartModelList);
        } else Toast.makeText(App.context, "Cart Is empty !!", Toast.LENGTH_SHORT).show();

        adapter.notifyDataSetChanged();

        if (cartModelList.size() > 0) {
            CartModel cartModel = cartModelList.get(0);
            if (null != cartModel.getCartAmount() && !cartModel.getCartAmount().isEmpty()) {
                binding.tvTotalCartPrice.setText(AppUtils.getCurrencyFormat(cartModel.getCartAmount().get(0).getTotalAmount().toString()));
                binding.btnContinue.setText(String.format("Pay %s", AppUtils.getCurrencyFormat(cartModel.getCartAmount().get(0).getTotalAmount().toString())));
            }
            binding.tvAppointmentDateTime.setText(String.format("%s, %s", AppUtils.parseDate(date, "EEE, MMM d", "yyyy/MM/dd"), time));
            binding.setCartModel(cartModel);
            amountToPay = String.valueOf((int) Float.parseFloat(cartModel.getPrice()));
            pathologyID = cartModel.getPathalogyId();
            labPayment.setAmount(amountToPay);

        }

    }

    @Override
    public void onAmountFetched(Object obj) {
        binding.tvWalletText.setText(String.format("Use Wallet Amount %s", obj));

    }

    @Override
    public void onAmountUpdate(Object msg) {
        binding.tvWalletText.setText(String.format("Use Wallet Amount %s", msg));
        binding.tvWalletAppliedAmount.setText(amountToAddForLabFromWallet);
    }
}