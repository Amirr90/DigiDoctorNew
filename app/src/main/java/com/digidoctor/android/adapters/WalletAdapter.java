package com.digidoctor.android.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.WalletViewBinding;
import com.digidoctor.android.model.WalletModel;

public class WalletAdapter extends ListAdapter<WalletModel, WalletAdapter.ViewHolder> {
    public WalletAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WalletViewBinding walletViewBinding = WalletViewBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(walletViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        WalletViewBinding walletViewBinding;

        public ViewHolder(@NonNull WalletViewBinding walletViewBinding) {
            super(walletViewBinding.getRoot());
            this.walletViewBinding = walletViewBinding;
        }
    }

    public static DiffUtil.ItemCallback<WalletModel> itemCallback = new DiffUtil.ItemCallback<WalletModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull WalletModel oldItem, @NonNull WalletModel newItem) {
            return oldItem.getId().equalsIgnoreCase(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull WalletModel oldItem, @NonNull WalletModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
