package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.BlogViewBinding;
import com.digidoctor.android.model.patientModel.BlogModel;

public class BlogAdapter extends ListAdapter<BlogModel, BlogAdapter.BlogVH> {
    public BlogAdapter() {
        super(BlogModel.itemCallback);
    }

    @NonNull
    @Override
    public BlogVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BlogViewBinding binding = BlogViewBinding.inflate(inflater, parent, false);
        return new BlogVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogVH holder, int position) {
        holder.binding.setBlog(getItem(position));
    }


    @Override
    public int getItemCount() {
        if (super.getItemCount() < 6)
            return super.getItemCount();
        else return 5;
    }

    public class BlogVH extends RecyclerView.ViewHolder {
        BlogViewBinding binding;

        public BlogVH(@NonNull BlogViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
