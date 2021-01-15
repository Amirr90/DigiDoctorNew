package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.digidoctor.android.databinding.FragmentInputMobileNumberBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.GenerateOtpModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.getOTP;

public class InputMobileNumberFragment extends Fragment {


    FragmentInputMobileNumberBinding numberBinding;
    NavController navController;
    GenerateOtpModel generateOtpModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        numberBinding = FragmentInputMobileNumberBinding.inflate(inflater, container, false);
        return numberBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        generateOtpModel = new GenerateOtpModel();
        numberBinding.tvSkip.setOnClickListener(view12 -> {
            navController.navigate(R.id.action_inputMobileNumberFragment_to_patientDashboard);
            requireActivity().finish();
        });

        numberBinding.btnNext.setOnClickListener(view1 -> {
            String number = numberBinding.editTextGetMobileNumber.getText().toString().trim();
            if (TextUtils.isEmpty(number))
                Toast.makeText(requireActivity(), R.string.mobile_number, Toast.LENGTH_SHORT).show();
            else if (number.length() < 10) {
                Toast.makeText(requireActivity(), "please enter valid mobile number", Toast.LENGTH_SHORT).show();
            } else {
                generateOtpModel.setMobileNo(number);
                sendOTP(number);
            }
        });

    }

    private void sendOTP(final String number) {
        utils.hideSoftKeyboard(requireActivity());
        AppUtils.showRequestDialog(requireActivity());
        getOTP(generateOtpModel, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                Bundle bundle = new Bundle();
                bundle.putString("number", number);
                navController.navigate(R.id.action_inputMobileNumberFragment_to_inputOtpFragment, bundle);
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
            }
        });


    }
}