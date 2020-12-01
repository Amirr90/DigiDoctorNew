package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentInputOtpBinding;
import com.digidoctor.android.model.User;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.utility.utils;

import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.checkLogin;
import static com.digidoctor.android.utility.ApiUtils.getOTP;
import static com.digidoctor.android.utility.utils.BOOKING_USER;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.USER;


public class InputOtpFragment extends Fragment {

    private static final String TAG = "InputOtpFragment";


    FragmentInputOtpBinding inputOtpBinding;
    NavController navController;
    String number;
    CountDownTimer countDownTimer;
    public int counter = 30;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inputOtpBinding = FragmentInputOtpBinding.inflate(inflater, container, false);
        return inputOtpBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        number = getArguments().getString("number");


        startTimer();

        inputOtpBinding.tvOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    inputOtpBinding.tvOTP1.setBackground(charSequence.toString().isEmpty() ?
                            getResources().getDrawable(R.drawable.color_primary_edittext) :
                            getResources().getDrawable(R.drawable.gray_edittext));

                    if (charSequence.toString().length() == 1)
                        inputOtpBinding.tvOTP2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpBinding.tvOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    inputOtpBinding.tvOTP2.setBackground(charSequence.toString().isEmpty() ?
                            getResources().getDrawable(R.drawable.color_primary_edittext) :
                            getResources().getDrawable(R.drawable.gray_edittext));

                    if (charSequence.toString().length() == 1)
                        inputOtpBinding.tvOTP3.requestFocus();
                    else if (charSequence.toString().length() == 0)
                        inputOtpBinding.tvOTP1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpBinding.tvOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    inputOtpBinding.tvOTP3.setBackground(charSequence.toString().isEmpty() ?
                            getResources().getDrawable(R.drawable.color_primary_edittext) :
                            getResources().getDrawable(R.drawable.gray_edittext));

                    if (charSequence.toString().length() == 1)
                        inputOtpBinding.tvOTP4.requestFocus();
                    else if (charSequence.toString().length() == 0)
                        inputOtpBinding.tvOTP2.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputOtpBinding.tvOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    inputOtpBinding.tvOTP4.setBackground(charSequence.toString().isEmpty() ?
                            getResources().getDrawable(R.drawable.color_primary_edittext) :
                            getResources().getDrawable(R.drawable.gray_edittext));

                    if (charSequence.toString().length() == 1)
                        checkOTP();
                    else if (charSequence.toString().length() == 0)
                        inputOtpBinding.tvOTP3.requestFocus();


                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inputOtpBinding.tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSendCode();
            }
        });
        inputOtpBinding.tvSlogen.setText("An OTP has been sent to mobile\n +91" + number + "");
    }

    private void reSendCode() {


        getOTP(number, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                counter = 30;
                countDownTimer.cancel();
                inputOtpBinding.tvResendCode.setVisibility(View.GONE);
                startTimer();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOtp Resend: " + s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOtp Resend: " + throwable.getLocalizedMessage());
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(32000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                inputOtpBinding.tvTimer.setVisibility(View.VISIBLE);
                inputOtpBinding.tvTimer.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                inputOtpBinding.tvTimer.setVisibility(View.GONE);
                inputOtpBinding.tvResendCode.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void checkOTP() {
        String otp1, otp2, otp3, otp4;
        otp1 = inputOtpBinding.tvOTP1.getText().toString().trim();
        otp2 = inputOtpBinding.tvOTP2.getText().toString().trim();
        otp3 = inputOtpBinding.tvOTP3.getText().toString().trim();
        otp4 = inputOtpBinding.tvOTP4.getText().toString().trim();
        utils.hideSoftKeyboard(requireActivity());


        String otp = otp1 + otp2 + otp3 + otp4;


        checkLogin(number, otp, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<User> loginModelList = (List<User>) o;
                User user = loginModelList.get(0);


                utils.setBoolean(utils.IS_LOGIN, true, requireActivity());

                utils.savePrimaryUserData(USER, requireActivity(), user);

                utils.setUserForBooking(BOOKING_USER, requireActivity(), user);


                utils.setString(MOBILE_NUMBER, number, requireActivity());


                navController.navigate(R.id.action_inputOtpFragment_to_patientDashboard);
                countDownTimer.cancel();
                requireActivity().finish();
            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });

    }
}