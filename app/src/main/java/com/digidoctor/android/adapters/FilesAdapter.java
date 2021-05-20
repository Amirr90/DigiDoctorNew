package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.SymptomsAttachmentViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.FilesVH> {
    List<FileModel> modelList;
    AdapterInterface adapterInterface;

    public FilesAdapter(List<FileModel> modelList, AdapterInterface adapterInterface) {
        this.modelList = modelList;
        this.adapterInterface = adapterInterface;
    }

    public FilesAdapter(List<FileModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public FilesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SymptomsAttachmentViewBinding binding = SymptomsAttachmentViewBinding.inflate(inflater, parent, false);
        return new FilesVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesVH holder, int position) {
        FileModel fileModel = modelList.get(position);
        holder.binding.setFile(fileModel);

        String fileType = fileModel.getFileType();

        if (fileModel.getThumbnail() != null) {
            holder.binding.imageView55.setImageResource(fileModel.getThumbnail());
        } else {
            if (null != fileType)
                if (fileType.equalsIgnoreCase("aac")) {
                    holder.binding.imageView55.setImageResource(R.drawable.audio_file);
                } else if (fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("png") || fileType.equalsIgnoreCase("jpg")) {
                    Glide.with(PatientDashboard.getInstance())
                            .load(fileModel.getFilePath())
                            .centerCrop()
                            .placeholder(R.drawable.profile)
                            .into(holder.binding.imageView55);
                }
        }


        if (null != adapterInterface)
            holder.binding.ivDeleteFiles.setOnClickListener(view -> adapterInterface.onItemClicked(position));

        holder.binding.ivDeleteFiles.setVisibility(null == adapterInterface ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (null == modelList)
            modelList = new ArrayList<>();
        return modelList.size();
    }

    public boolean addItem(FileModel model) {
        if (null == modelList)
            modelList = new ArrayList<>();
        modelList.add(model);
        notifyDataSetChanged();
        return true;
    }

    public boolean removeItem(int position) {
        if (null == modelList)
            return false;
        else {
            if (modelList.size() > position) {
                modelList.remove(position);
                notifyDataSetChanged();
                return true;
            } else return false;
        }
    }

    public static class FilesVH extends RecyclerView.ViewHolder {
        SymptomsAttachmentViewBinding binding;

        public FilesVH(@NonNull SymptomsAttachmentViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}