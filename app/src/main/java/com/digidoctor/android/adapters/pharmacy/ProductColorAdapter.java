package com.digidoctor.android.adapters.pharmacy;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

public class ProductColorAdapter extends RecyclerView.Adapter<ProductColorAdapter.ViewHolderVH> {

    List<ProductDetailModelResponse.ProductDetailsList.ColorDetails> getproductcolorlist;
    Activity activity;
    int selectedPosition = -1;


    public ProductColorAdapter(List<ProductDetailModelResponse.ProductDetailsList.ColorDetails> getproductcolorlist, Activity activity) {
        this.getproductcolorlist = getproductcolorlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductColorAdapter.ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductcolorlayoutBinding productcolorlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.productcolorlayout, parent, false);
        return new ProductColorAdapter.ViewHolderVH(productcolorlayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductColorAdapter.ViewHolderVH holder, int position) {
        final ProductDetailModelResponse.ProductDetailsList.ColorDetails colorDetails = getproductcolorlist.get(position);
        holder.productcolorlayoutBinding.cardviewColor.setText(colorDetails.getColor());
        holder.productcolorlayoutBinding.cardviewColor.setText(colorDetails.getColor());

        String colorcode = colorDetails.getColorCode();
        holder.productcolorlayoutBinding.cardviewColor.setBackground(Drawable.createFromPath(colorcode));

        if (colorDetails.getIsSelected() == 1) {
            selectedPosition = position;
        }

      /*  if (colorDetails.getIsSelected() == 1) {
            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.flavourgreen);
            holder.productcolorlayoutBinding.cardviewColor.setTextColor(Color.parseColor("#ffffff"));
        }
*/
        if (selectedPosition == position) {
            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.size_flavour_text);
            holder.productcolorlayoutBinding.cardviewColor.setTextColor(Color.BLACK);
        }
        if (colorDetails.getIsSelected() == 0) {
            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.size_flavour_text);
            holder.productcolorlayoutBinding.cardviewColor.setTextColor(Color.BLACK);

        }


        holder.productcolorlayoutBinding.cardviewColor.setOnClickListener(v -> {

            selectedPosition = position;
            ProductModel model = new ProductModel();
            model.setSizeId(String.valueOf(colorDetails.getColorId()));
            notifyDataSetChanged();
            updateProduct(model);

            holder.productcolorlayoutBinding.cardviewColor.setBackgroundResource(R.drawable.flavourgreen);


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

                    if(models.get(0).getProductDetails().size()>0){



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
        return getproductcolorlist.size();
    }

    public class ViewHolderVH extends RecyclerView.ViewHolder {

        ProductcolorlayoutBinding productcolorlayoutBinding;

        public ViewHolderVH(@NonNull ProductcolorlayoutBinding itemView) {

            super(itemView.getRoot());

            productcolorlayoutBinding = itemView;
        }
    }
}
