package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.NavViewRecItemBinding;
import com.digidoctor.android.interfaces.NavigationInterface;
import com.digidoctor.android.model.NavModel;

import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavVH> {
    List<NavModel> navModelList;
    NavigationInterface adapterInterface;


    public NavAdapter(List<NavModel> navModelList, NavigationInterface adapterInterface) {
        this.navModelList = navModelList;
        this.adapterInterface = adapterInterface;
    }


    @NonNull
    @Override
    public NavVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NavViewRecItemBinding navViewRecItemBinding = NavViewRecItemBinding.inflate(inflater, parent, false);
        return new NavVH(navViewRecItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NavVH holder, final int position) {

        NavModel navModel = navModelList.get(position);
        holder.navViewRecItemBinding.setNavModel(navModel);

        holder.navViewRecItemBinding.rlNavRoot.setOnClickListener(view -> adapterInterface.onNavigationItemClicked(position));

    }

    @Override
    public int getItemCount() {
        return navModelList.size();
    }

    public class NavVH extends RecyclerView.ViewHolder {
        NavViewRecItemBinding navViewRecItemBinding;

        public NavVH(NavViewRecItemBinding navViewRecItemBinding) {
            super(navViewRecItemBinding.getRoot());
            this.navViewRecItemBinding = navViewRecItemBinding;
        }
    }
}
