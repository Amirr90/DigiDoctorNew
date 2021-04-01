package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.LabHomeView2Binding;

import java.util.ArrayList;
import java.util.List;

public class LabHomeGridAdapter extends RecyclerView.Adapter<LabHomeGridAdapter.HomeVH> {
    List<GridModel> homeGridList = getData();

    private List<GridModel> getData() {
        List<GridModel> strings = new ArrayList<>();
        strings.add(new GridModel("Best Price", "Best Price Description"));
        strings.add(new GridModel("Best Price", "Best Price Description"));
        strings.add(new GridModel("Best Price", "Best Price Description"));
        strings.add(new GridModel("Best Price", "Best Price Description"));
        return strings;
    }

    @NonNull
    @Override
    public LabHomeGridAdapter.HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LabHomeView2Binding binding = LabHomeView2Binding.inflate(layoutInflater, parent, false);
        return new HomeVH(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull LabHomeGridAdapter.HomeVH holder, int position) {
        holder.binding.setHomeModel(homeGridList.get(position));
    }

    @Override
    public int getItemCount() {
        return homeGridList.size();
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        LabHomeView2Binding binding;

        public HomeVH(@NonNull LabHomeView2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public
    class GridModel {
        String title;
        String description;

        public GridModel(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
