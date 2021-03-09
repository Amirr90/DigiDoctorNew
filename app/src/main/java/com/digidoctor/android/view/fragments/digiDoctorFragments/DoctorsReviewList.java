package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.DoctorReviewAdapter;
import com.digidoctor.android.adapters.pharmacy.RatingAndReviewAdapter;
import com.digidoctor.android.databinding.DoctorsReviewListFragmentBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.LabOrderInterface;
import com.digidoctor.android.model.DocModel;
import com.digidoctor.android.model.GetDocRevModelRes;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class DoctorsReviewList extends Fragment {
    private static final String TAG = "DoctorsReviewList";

    NavController navController;
    List<GetDocRevModelRes.GetDoctorReviewList> getreview = new ArrayList<>();
    DoctorsReviewListFragmentBinding doctorsReviewListFragmentBinding;
    DoctorReviewAdapter doctorReviewAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        doctorsReviewListFragmentBinding = DoctorsReviewListFragmentBinding.inflate(getLayoutInflater());
        return doctorsReviewListFragmentBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        doctorReviewAdapter = new DoctorReviewAdapter(getreview);
        doctorsReviewListFragmentBinding.reviewRecyclerview.setAdapter(doctorReviewAdapter);

        GetDoctorReview();


    }

    private void GetDoctorReview() {
        AppUtils.showRequestDialog(requireActivity());
        DocModel docModel = new DocModel();
        docModel.setServiceProviderDetailsId(2015);
        ApiUtils.getDoctorsReviews(docModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<GetDocRevModelRes.GetDoctorReviewList> models = (List<GetDocRevModelRes.GetDoctorReviewList>) o;
                if (null != models && !models.isEmpty()) {
                    getreview.clear();
                    getreview.addAll(models);

                    Log.d(TAG, "onSuccessModel: " + models);
                    doctorReviewAdapter.notifyDataSetChanged();
                } else
                    Log.d(TAG, "onSuccess: No Dat Found !!");

            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "try again !!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: " + s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "try again !!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());
            }
        });

    }
}