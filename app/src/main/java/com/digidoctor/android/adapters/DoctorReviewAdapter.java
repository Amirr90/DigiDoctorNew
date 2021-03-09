package com.digidoctor.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.RatingAndReviewViewBinding;
import com.digidoctor.android.model.GetDocRevModelRes;

import java.util.ArrayList;
import java.util.List;

public class DoctorReviewAdapter extends RecyclerView.Adapter<DoctorReviewAdapter.ViewHolderVH> {
    private List<GetDocRevModelRes.GetDoctorReviewList> getDoctorReviewLists;


    public DoctorReviewAdapter(List<GetDocRevModelRes.GetDoctorReviewList> getDoctorReviewLists) {
        this.getDoctorReviewLists = getDoctorReviewLists;
    }

    @NonNull
    @Override
    public ViewHolderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RatingAndReviewViewBinding ratingAndReviewViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rating_and_review_view, parent, false);
        return new ViewHolderVH(ratingAndReviewViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVH holder, int position) {
        GetDocRevModelRes.GetDoctorReviewList getDoctorReviewList = getDoctorReviewLists.get(position);


        holder.ratingAndReviewViewBinding.textView100.setText(getDoctorReviewList.getName());
        holder.ratingAndReviewViewBinding.textView102.setText(String.valueOf(getDoctorReviewList.getStarRating()));
        holder.ratingAndReviewViewBinding.textView101.setText(getDoctorReviewList.getReviewDate());
        holder.ratingAndReviewViewBinding.textView103.setText(getDoctorReviewList.getReview());


    }

    @Override
    public int getItemCount() {
        if (null == getDoctorReviewLists)
            getDoctorReviewLists = new ArrayList<>();
        return getDoctorReviewLists.size();
    }

    public static class ViewHolderVH extends RecyclerView.ViewHolder {

        RatingAndReviewViewBinding ratingAndReviewViewBinding;

        public ViewHolderVH(@NonNull RatingAndReviewViewBinding itemView) {
            super(itemView.getRoot());
            ratingAndReviewViewBinding = itemView;
        }
    }
}
