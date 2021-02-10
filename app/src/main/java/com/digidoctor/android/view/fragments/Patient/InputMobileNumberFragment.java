package com.digidoctor.android.view.fragments.Patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentInputMobileNumberBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.patientModel.GenerateOtpModel;

import java.util.List;

import static com.digidoctor.android.utility.ApiUtils.getOTP;

public class InputMobileNumberFragment extends Fragment {


    FragmentInputMobileNumberBinding numberBinding;
    NavController navController;
    GenerateOtpModel generateOtpModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        numberBinding = FragmentInputMobileNumberBinding.inflate(inflater, container, false);
        return numberBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        generateOtpModel = new GenerateOtpModel();
        numberBinding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_inputMobileNumberFragment_to_patientDashboard);
                requireActivity().finish();
            }
        });

        numberBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = numberBinding.editTextGetMobileNumber.getText().toString().trim();
                if (TextUtils.isEmpty(number))
                    Toast.makeText(requireActivity(), R.string.mobile_number, Toast.LENGTH_SHORT).show();
                else {
                    generateOtpModel.setMobileNo(number);
                    sendOTP(number);
                }
            }
        });

    }

    private void sendOTP(final String number) {
        getOTP(generateOtpModel, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                Bundle bundle = new Bundle();
                bundle.putString("number", number);
                navController.navigate(R.id.action_inputMobileNumberFragment_to_inputOtpFragment, bundle);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
            }
        });


    }
}