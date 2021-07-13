package com.digidoctor.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentLabHealthCategoryBinding;
import com.digidoctor.android.databinding.HealthCheckupCategoryLabLayoutLinearBinding;
import com.digidoctor.android.model.labmodel.CategoryModel;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public class LabHealthCategoryFragment extends Fragment {
    FragmentLabHealthCategoryBinding binding;
    NavController navController;
    PatientViewModel labViewModel;
    CategoryAdapterAll categoryAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLabHealthCategoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //CategoryAdapter
        categoryAdapter = new CategoryAdapterAll();
        binding.recCategory.setAdapter(categoryAdapter);

        labViewModel = new ViewModelProvider(this).get(PatientViewModel.class);


        labViewModel.getLabDashboardModel("10", "20").observe(getViewLifecycleOwner(), labDashBoardmodel -> {
            //category
            List<CategoryModel> categoryModels = labDashBoardmodel.getCategoryDetails();
            if (null != categoryModels && !categoryModels.isEmpty())
                categoryAdapter.submitList(categoryModels);


        });
    }

    private static class CategoryAdapterAll extends ListAdapter<CategoryModel, CategoryAdapterAll.CategoryVH> {

        public CategoryAdapterAll() {
            super(CategoryModel.itemCallback);
        }

        @NonNull
        @Override
        public CategoryAdapterAll.CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            HealthCheckupCategoryLabLayoutLinearBinding binding = HealthCheckupCategoryLabLayoutLinearBinding.inflate(layoutInflater, parent, false);
            return new CategoryVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryAdapterAll.CategoryVH holder, int position) {
            CategoryModel categoryModel = getItem(position);
            holder.binding.setCategoryModel(categoryModel);
        }

        public static class CategoryVH extends RecyclerView.ViewHolder {
            HealthCheckupCategoryLabLayoutLinearBinding binding;

            public CategoryVH(@NonNull HealthCheckupCategoryLabLayoutLinearBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(App.context, "Coming Soon", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

    }

}