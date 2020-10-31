package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.DashBoardViewHorizontal1Binding;
import com.digidoctor.android.model.HealthProductDetailsModel;

import java.util.Random;

public class HealthProductAdapter extends ListAdapter<HealthProductDetailsModel, HealthProductAdapter.DashboadVH2> {
    Integer[] cards = new Integer[]{R.drawable.box_one,
            R.drawable.box_two,
            R.drawable.box_three,
            R.drawable.box_four,
            R.drawable.box_five,
            R.drawable.box_six};

    public HealthProductAdapter() {
        super(HealthProductDetailsModel.itemCallback);
    }

    @NonNull
    @Override
    public DashboadVH2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DashBoardViewHorizontal1Binding dashBoardViewBinding = DashBoardViewHorizontal1Binding.inflate(inflater, parent, false);
        return new DashboadVH2(dashBoardViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboadVH2 holder, int position) {

        HealthProductDetailsModel healthProductDetailsModel = getItem(position);
        holder.dashBoardViewBinding.setDashboard2(healthProductDetailsModel);
        // holder.dashBoardViewBinding.imageView22.setImageResource(images[position]);

        Random rand = new Random();
        int pos = rand.nextInt(5);
        holder.dashBoardViewBinding.imageView22.setBackgroundResource(cards[pos]);

    }

    public class DashboadVH2 extends RecyclerView.ViewHolder {
        DashBoardViewHorizontal1Binding dashBoardViewBinding;

        public DashboadVH2(DashBoardViewHorizontal1Binding dashBoardViewBinding) {
            super(dashBoardViewBinding.getRoot());
            this.dashBoardViewBinding = dashBoardViewBinding;
        }
    }
}
