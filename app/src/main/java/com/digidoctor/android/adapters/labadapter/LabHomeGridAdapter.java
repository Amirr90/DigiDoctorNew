package com.digidoctor.android.adapters.labadapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.LabHomeView2Binding;
import com.digidoctor.android.interfaces.AdapterInterface;

import java.util.ArrayList;
import java.util.List;

public class LabHomeGridAdapter extends RecyclerView.Adapter<LabHomeGridAdapter.HomeVH> {
    List<GridModel> homeGridList = getData();
    AdapterInterface adapterInterface;

    public LabHomeGridAdapter(AdapterInterface adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    private List<GridModel> getData() {
        List<GridModel> strings = new ArrayList<>();
        strings.add(new GridModel("Best Price", "Guaranteed", R.drawable.first));
        strings.add(new GridModel("Home", "Sample", R.drawable.ic_home_sample));
        strings.add(new GridModel("View", "Reports", R.drawable.ic_view_reports));
        strings.add(new GridModel("Add", "Reports", R.drawable.investigation));
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
        holder.binding.IVbestprice.setImageResource(homeGridList.get(position).getImage());
        holder.binding.first.setOnClickListener(v -> {
            adapterInterface.onItemClicked(position);
        });
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

    public static
    class GridModel {
        String title;
        String description;
        int image;

        public GridModel(String title, String description, int image) {
            this.title = title;
            this.description = description;
            this.image = image;
        }


        public int getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
