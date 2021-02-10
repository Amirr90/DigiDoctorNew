package com.digidoctor.android.adapters.pharmacy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.RatingAndReviewViewBinding;
import com.digidoctor.android.model.pharmacyModel.ProductDetailModelResponse;

import java.util.ArrayList;
import java.util.List;

public class ratingandreviewadapter extends RecyclerView.Adapter<ratingandreviewadapter.ratingandreviewVH> {
    private static final String TAG = "ratingandreviewadapter";
    Context ctx;
    private List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> getallreview;

    public ratingandreviewadapter(List<ProductDetailModelResponse.ProductDetailsList.ReviewDetails> getallreview, Context ctx) {
        this.getallreview = getallreview;
        this.ctx = ctx;

    }


    @NonNull
    @Override
    public ratingandreviewadapter.ratingandreviewVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RatingAndReviewViewBinding ratingAndReviewViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rating_and_review_view, parent, false);
        return new ratingandreviewadapter.ratingandreviewVH(ratingAndReviewViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ratingandreviewadapter.ratingandreviewVH holder, int position) {
        ProductDetailModelResponse.ProductDetailsList.ReviewDetails reviewList = getallreview.get(position);
        holder.ratingAndReviewViewBinding.textView100.setText(reviewList.getReviewBy());
        holder.ratingAndReviewViewBinding.textView102.setText(reviewList.getStarRating());
        holder.ratingAndReviewViewBinding.textView101.setText(reviewList.getReviewDate());
        holder.ratingAndReviewViewBinding.textView103.setText(reviewList.getReview());


    }


    @Override
    public int getItemCount() {

        return getallreview.size();
    }



    public class ratingandreviewVH extends RecyclerView.ViewHolder {

        RatingAndReviewViewBinding ratingAndReviewViewBinding;

        public ratingandreviewVH(@NonNull RatingAndReviewViewBinding itemView) {
            super(itemView.getRoot());
            ratingAndReviewViewBinding = itemView;
        }
    }

}
