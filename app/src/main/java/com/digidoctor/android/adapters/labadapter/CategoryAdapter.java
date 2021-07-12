package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.HealthCheckupCategoryLabLayoutBinding;
import com.digidoctor.android.model.labmodel.CategoryModel;

import es.dmoral.toasty.Toasty;

public class CategoryAdapter extends ListAdapter<CategoryModel, CategoryAdapter.CategoryVH> {
    public CategoryAdapter() {
        super(CategoryModel.itemCallback);
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HealthCheckupCategoryLabLayoutBinding binding = HealthCheckupCategoryLabLayoutBinding.inflate(layoutInflater, parent, false);
        return new CategoryVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, int position) {
        CategoryModel categoryModel = getItem(position);
        holder.binding.setCategoryModel(categoryModel);
    }

    public static class CategoryVH extends RecyclerView.ViewHolder {
        HealthCheckupCategoryLabLayoutBinding binding;

        public CategoryVH(@NonNull HealthCheckupCategoryLabLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.layout.setOnClickListener(v -> Toasty.warning(v.getContext(), "Coming Soon", Toasty.LENGTH_LONG).show());
        }
    }
}
