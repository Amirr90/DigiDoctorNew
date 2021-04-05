package com.digidoctor.android.adapters.pharmacy;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.RatingAndReviewViewBinding;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;

import java.util.List;

public class RatingAndReviewAdapter extends RecyclerView.Adapter<RatingAndReviewAdapter.ratingandreviewVH> {


    private final List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> getallreview;

    public RatingAndReviewAdapter( List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> getallreview) {

        this.getallreview = getallreview;
    }


    @NonNull
    @Override
    public RatingAndReviewAdapter.ratingandreviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RatingAndReviewViewBinding ratingAndReviewViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rating_and_review_view, parent, false);
        return new ratingandreviewVH(ratingAndReviewViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull RatingAndReviewAdapter.ratingandreviewVH holder, int position) {
        ProductDetailModelResponse.ProductDetailsList.ReviewDetails reviewList = getallreview.get(position);
        holder.ratingAndReviewViewBinding.textView100.setText(reviewList.getReviewBy());
        holder.ratingAndReviewViewBinding.textView206.setText(reviewList.getStarRating());
        holder.ratingAndReviewViewBinding.textView101.setText(reviewList.getReviewDate());
        holder.ratingAndReviewViewBinding.textView103.setText(reviewList.getReview());


    }


    @Override
    public int getItemCount() {

        return getallreview.size();
    }


    public static class ratingandreviewVH extends RecyclerView.ViewHolder {

        RatingAndReviewViewBinding ratingAndReviewViewBinding;

        public ratingandreviewVH(@NonNull RatingAndReviewViewBinding itemView) {
            super(itemView.getRoot());
            ratingAndReviewViewBinding = itemView;
        }
    }

}
