package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.StethoViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.utility.AppUtils;

import java.util.List;

public class LaryingoAdapter extends RecyclerView.Adapter<LaryingoAdapter.LaryingoVH> {
    List<FileModel> recAudioList;
    AdapterInterface adapterInterface;

    public LaryingoAdapter(List<FileModel> recAudioList, AdapterInterface adapterInterface) {
        this.recAudioList = recAudioList;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public LaryingoAdapter.LaryingoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StethoViewBinding viewBinding = StethoViewBinding.inflate(inflater, parent, false);
        return new LaryingoVH(viewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull LaryingoAdapter.LaryingoVH holder, int position) {


        holder.viewBinding.textView226.setText("Video File");
        holder.viewBinding.btnPlay.setOnClickListener(v -> {
            int code = (int) holder.viewBinding.btnPlay.getTag();
            if (code == AppUtils.Pause) {
                adapterInterface.onItemClicked(recAudioList.get(position).getFilePath());
            }


        });
    }

    @Override
    public int getItemCount() {
        return null == recAudioList ? 0 : recAudioList.size();
    }

    public static class LaryingoVH extends RecyclerView.ViewHolder {
        StethoViewBinding viewBinding;

        public LaryingoVH(@NonNull StethoViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}
