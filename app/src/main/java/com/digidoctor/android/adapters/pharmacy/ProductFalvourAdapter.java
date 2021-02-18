package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ProductsizelayoutBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.digidoctor.android.model.pharmacyModel.ProductModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.fragments.pharmacy.ProductDetailsFragment;

import org.json.JSONException;

import java.util.List;

public class ProductFalvourAdapter extends RecyclerView.Adapter<ProductFalvourAdapter.ViewHolderVH> {
    List<ProductDetailModelResponse.ProductDetailsList.FlavourDetails> getfalvour;
    Activity activity;

    int selectedPosition = -1;


    public ProductFalvourAdapter(List<ProductDetailModelResponse.ProductDetailsList.FlavourDetails> getfalvour, Activity activity) {
        this.getfalvour = getfalvour;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductsizelayoutBinding productsizelayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.productsizelayout, parent, false);
        return new ProductFalvourAdapter.ViewHolderVH(productsizelayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVH holder, int position) {
        final ProductDetailModelResponse.ProductDetailsList.FlavourDetails flavourDetails = getfalvour.get(position);
        holder.productsizelayoutBinding.textView135.setText(flavourDetails.getFlavour());

        if (flavourDetails.getIsSelected() == 1) {
            selectedPosition = position;
        }

        if (selectedPosition == position) {
            holder.productsizelayoutBinding.textView135.setBackgroundResource(R.drawable.flavourgreen);
            holder.productsizelayoutBinding.textView135.setTextColor(Color.WHITE);
        }
        if (flavourDetails.getIsSelected() == 0) {
            holder.productsizelayoutBinding.textView135.setBackgroundResource(R.drawable.size_flavour_text);
            holder.productsizelayoutBinding.textView135.setTextColor(Color.BLACK);

        }


        holder.productsizelayoutBinding.textView135.setOnClickListener(view -> {
            selectedPosition = position;
            ProductModel model = new ProductModel();
            model.setSizeId(String.valueOf(flavourDetails.getFlavourId()));
            notifyDataSetChanged();
            updateProduct(model);


        });


    }

    private void updateProduct(ProductModel model) {

        User user = utils.getPrimaryUser(activity);
        model.setMemberId(String.valueOf(user.getMemberId()));
        model.setProductId(Integer.parseInt(ProductDetailsFragment.getInstance().productId));

        try {
            ApiUtils.getProductdetailsbyProductID(model, activity, new ApiCallbackInterface() {

                @Override
                public void onSuccess(List<?> o) {
                    List<ProductDetailModelResponse.ProductDetailsList> models = (List<ProductDetailModelResponse.ProductDetailsList>) o;
                    Log.d("TAG", "onSuccess: " + models.get(0).getProductDetails());

                    if (models.isEmpty())
                        return;

                    if (models.get(0).getProductDetails().size() > 0) {


                        ProductDetailsFragment.getInstance().updateProduct(models);
                        ProductDetailsFragment.getInstance().setProduct(models.get(0).getProductDetails().get(0));
                    }

                }

                @Override
                public void onError(String s) {
                    Toast.makeText(activity, "" + s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(Throwable throwable) {
                    Toast.makeText(activity, "" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity, "try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return getfalvour.size();
    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        ProductsizelayoutBinding productsizelayoutBinding;

        public ViewHolderVH(@NonNull ProductsizelayoutBinding itemView) {
            super(itemView.getRoot());
            productsizelayoutBinding = itemView;

        }
    }
}
