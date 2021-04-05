package com.digidoctor.android.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.SelectedImageViewBinding;
import com.digidoctor.android.interfaces.ImageClickListener;

import java.util.ArrayList;
import java.util.List;

public class SelectedImageAdapter extends RecyclerView.Adapter<SelectedImageAdapter.ImageVH> {

    List<String> uriList;
    ImageClickListener imageClickListener;

    public SelectedImageAdapter(List<String> uriList, ImageClickListener imageClickListener) {
        this.uriList = uriList;
        this.imageClickListener = imageClickListener;
    }

    @NonNull
    @Override
    public ImageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SelectedImageViewBinding selectedImageViewBinding = SelectedImageViewBinding.inflate(inflater, parent, false);
        selectedImageViewBinding.setImageClickListener(imageClickListener);
        return new ImageVH(selectedImageViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageVH holder, int position) {

        try {
            holder.selectedImageViewBinding.setUri(Uri.parse(uriList.get(position)));
            holder.selectedImageViewBinding.imageView47.setImageURI(Uri.parse(uriList.get(position)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.selectedImageViewBinding.imageView48.setOnClickListener(view -> imageClickListener.onDeleteButtonClick(position));
    }

    @Override
    public int getItemCount() {
        if (null == uriList)
            uriList = new ArrayList<>();
        return uriList.size();
    }

    public static class ImageVH extends RecyclerView.ViewHolder {
        SelectedImageViewBinding selectedImageViewBinding;

        public ImageVH(@NonNull SelectedImageViewBinding selectedImageViewBinding) {
            super(selectedImageViewBinding.getRoot());
            this.selectedImageViewBinding = selectedImageViewBinding;
        }
    }


    public Boolean removeImage(int position) {
        if (null != uriList && uriList.size() >= position) {
            uriList.remove(position);
            notifyDataSetChanged();
            return true;
        } else return false;

    }

    public Boolean addMoreImage(List<String> uri) {
        if (null == uriList)
            uriList = new ArrayList<>();

        for (String uri1 : uri) {
            if (!uriList.contains(uri1)) {
                uriList.add(uri1);
            }
        }
        notifyDataSetChanged();
        return true;


      /*  uriList.clear();
        uriList.add(uri.get(0));
        notifyDataSetChanged();
        return true;*/
    }

}
