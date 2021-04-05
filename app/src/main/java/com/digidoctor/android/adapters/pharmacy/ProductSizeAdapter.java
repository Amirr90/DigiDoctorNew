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

import java.util.List;

import static com.digidoctor.android.view.fragments.pharmacy.ProductDetailsFragment.AllProductModels;


public class ProductSizeAdapter extends RecyclerView.Adapter<ProductSizeAdapter.ProductSizeAdapterVH> {
    private static final String TAG = "ProductSizeAdapter";
    List<ProductDetailModelResponse.ProductDetailsList.SizeDetails> getproductsize;
    Activity activity;
    int selectedPosition = -1;


    public ProductSizeAdapter(List<ProductDetailModelResponse.ProductDetailsList.SizeDetails> getproductsize, Activity activity) {
        this.getproductsize = getproductsize;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductSizeAdapter.ProductSizeAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductsizelayoutBinding productsizelayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.productsizelayout, parent, false);
        return new ProductSizeAdapterVH(productsizelayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSizeAdapter.ProductSizeAdapterVH holder, int position) {
        final ProductDetailModelResponse.ProductDetailsList.SizeDetails sizeDetails = getproductsize.get(position);
        holder.productsizelayoutBinding.textView135.setText(sizeDetails.getSize());

        if (sizeDetails.getIsSelected() == 1) {
            selectedPosition = position;
        }


        if (selectedPosition == position) {
            holder.productsizelayoutBinding.textView135.setBackgroundResource(R.drawable.flavourgreen);
            holder.productsizelayoutBinding.textView135.setTextColor(Color.WHITE);
        }
        if (sizeDetails.getIsSelected() == 0) {
            holder.productsizelayoutBinding.textView135.setBackgroundResource(R.drawable.size_flavour_text);
            holder.productsizelayoutBinding.textView135.setTextColor(Color.BLACK);

        }
        holder.productsizelayoutBinding.textView135.setOnClickListener(v -> {
            selectedPosition = position;
            ProductModel model = new ProductModel();
            model.setSizeId(String.valueOf(sizeDetails.getSizeid()));
            updateProduct(model);
            notifyDataSetChanged();


        });


    }


    private void updateProduct(ProductModel model) {
        User user = utils.getPrimaryUser(activity);
        model.setMemberId(String.valueOf(user.getMemberId()));
        model.setProductId(Integer.parseInt(ProductDetailsFragment.getInstance().productId));

        ApiUtils.getProductdetailsbyProductID(model, activity, new ApiCallbackInterface() {


            @Override
            public void onSuccess(List<?> o) {
                List<ProductDetailModelResponse.ProductDetailsList> models = (List<ProductDetailModelResponse.ProductDetailsList>) o;
                Log.d(TAG, "onSuccess: " + models.get(0).getProductDetails());
                AllProductModels.clear();
                AllProductModels.addAll(models.get(0).getProductDetails());
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
    }


    @Override
    public int getItemCount() {
        return getproductsize.size();
    }

    public static class ProductSizeAdapterVH extends RecyclerView.ViewHolder {


        ProductsizelayoutBinding productsizelayoutBinding;

        public ProductSizeAdapterVH(@NonNull ProductsizelayoutBinding itemView) {
            super(itemView.getRoot());
            productsizelayoutBinding = itemView;
        }
    }
}
