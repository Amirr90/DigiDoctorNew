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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.adapters.pharmacy.ratingandreviewadapter;
import com.digidoctor.android.databinding.AllratingandreviewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.patientModel.User;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.digidoctor.android.model.pharmacyModel.ProductModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.getPrimaryUser;

public class AllRatingAndReviewFragment extends Fragment {

    AllratingandreviewBinding allRatingAndReviewFragment;
    NavController navController;
    ratingandreviewadapter ratingandreviewadapter;
    String ProductID;
    String SizeId = null;
    private List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> getallreview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        allRatingAndReviewFragment = AllratingandreviewBinding.inflate(getLayoutInflater());
        return allRatingAndReviewFragment.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        navController = Navigation.findNavController(view);

        getallreview = new ArrayList<>();

        if (getArguments() == null)
            return;

        ProductID = getArguments().getString("productId");

        ratingandreviewadapter = new ratingandreviewadapter(getallreview, requireActivity());


        allRatingAndReviewFragment.ratingreview.setAdapter(ratingandreviewadapter);

        gteproductdetailsrating();


    }

    private void gteproductdetailsrating() {

        ProductModel model = new ProductModel();

        User user = getPrimaryUser(requireActivity());
        model.setMemberId(String.valueOf(user.getId()));
        model.setProductId(Integer.parseInt(ProductID));

        try {
            ApiUtils.getProductdetailsbyProductID(model, requireActivity(), new ApiCallbackInterface() {
                @Override
                public void onSuccess(List<?> o) {
                    AppUtils.hideDialog();
                    List<ProductDetailModelResponse.ProductDetailsList> models = (List<ProductDetailModelResponse.ProductDetailsList>) o;
                    Log.d("TAG", "onSuccess: " + models.get(0).getReviewDetails());
                    List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> topSearchproductLists1 = models.get(0).getReviewDetails();

//                    Collections.reverse(topSearchproductLists1);
                    getallreview.addAll(topSearchproductLists1);
                    ratingandreviewadapter.notifyDataSetChanged();


                    //  allRatingAndReviewFragment.textView170.setText("All Review (" + ratingandreviewadapter.getItemCount() + ")");
                }

                @Override
                public void onError(String s) {
                    Toast.makeText(requireActivity(), "" + s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    Toast.makeText(requireActivity(), "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
