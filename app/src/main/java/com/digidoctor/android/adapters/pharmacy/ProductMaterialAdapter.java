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
import com.digidoctor.android.databinding.ProductcolorlayoutBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;
import com.digidoctor.android.model.pharmacyModel.ProductModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.fragments.pharmacy.ProductDetailsFragment;

import org.json.JSONException;

import java.util.List;

public class ProductMaterialAdapter extends RecyclerView.Adapter<ProductMaterialAdapter.ViewHolderVH> {
    List<ProductDetailModelResponse.ProductDetailsList.MaterialDetails> getproductmaterials;
    Activity activity;
    int selectedPosition = -1;


    public ProductMaterialAdapter(List<ProductDetailModelResponse.ProductDetailsList.MaterialDetails> getproductmaterials, Activity activity) {
        this.getproductmaterials = getproductmaterials;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductcolorlayoutBinding productcolorlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.productcolorlayout, parent, false);
        return new ProductMaterialAdapter.ViewHolderVH(productcolorlayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVH holder, int position) {
        final ProductDetailModelResponse.ProductDetailsList.MaterialDetails colorDetails = getproductmaterials.get(position);
        holder.productcolorlayoutBinding.cardviewColor.setText(colorDetails.getMaterial());

        if (colorDetails.getIsSelected() == 1) {
            selectedPosition = position;
        }

      /*  if (colorDetails.getIsSelected() == 1) {
            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.flavourgreen);
            holder.productcolorlayoutBinding.cardviewColor.setTextColor(Color.parseColor("#ffffff"));
        }
*/
        if (selectedPosition == position) {
            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.flavourgreen);
            holder.productcolorlayoutBinding.cardviewColor.setTextColor(Color.WHITE);
        }
        if (colorDetails.getIsSelected() == 0) {
            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.size_flavour_text);
            holder.productcolorlayoutBinding.cardviewColor.setTextColor(Color.BLACK);

        }


        holder.productcolorlayoutBinding.cardviewColor.setOnClickListener(v -> {
            selectedPosition = position;
            ProductModel model = new ProductModel();
            model.setSizeId(String.valueOf(colorDetails.getMatreialId()));
            updateProduct(model);
        });


    }

    public void updateProduct(ProductModel model) {


        User user = utils.getPrimaryUser(activity);
        model.setMemberId(String.valueOf(user.getMemberId()));
        model.setProductId(Integer.parseInt(ProductDetailsFragment.getInstance().productId));

        try {
            ApiUtils.getProductdetailsbyProductID(model, activity, new ApiCallbackInterface() {

                @Override
                public void onSuccess(List<?> o) {
                    List<ProductDetailModelResponse.ProductDetailsList> models = (List<ProductDetailModelResponse.ProductDetailsList>) o;
                    Log.d("TAG", "onSuccess: " + models.get(0).getProductDetails());
                   /* AllProductModels.addAll(models.get(0).getProductDetails());

                    if (!AllProductModels.isEmpty() && models.isEmpty()) {
                        fragmentProductDetailsBinding.setProduct(AllProductModels.get(0));

                    }*/
                    if (models.isEmpty())
                        return;


                    if (models.get(0).getProductDetails().size() > 0) {

                        ProductDetailsFragment.getInstance().updateProduct(models);
                        ProductDetailsFragment.getInstance().setProduct(models.get(0).getProductDetails().get(0));
                    } else {
                        Toast.makeText(activity, "Not Available ", Toast.LENGTH_SHORT).show();
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
        return getproductmaterials.size();


    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        ProductcolorlayoutBinding productcolorlayoutBinding;

        public ViewHolderVH(@NonNull ProductcolorlayoutBinding itemView) {
            super(itemView.getRoot());


            productcolorlayoutBinding = itemView;
        }
    }
}
