package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.InvestigationViewBinding;
import com.digidoctor.android.databinding.InvestigationViewTwoBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.ImageModel;
import com.digidoctor.android.model.InvestigationModel;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class InvestigationAdapter extends RecyclerView.Adapter {

    OnClickListener onClickListener;
    List<InvestigationModel> investigationModels;

    public InvestigationAdapter(OnClickListener onClickListener, List<InvestigationModel> investigationModels) {
        this.onClickListener = onClickListener;
        this.investigationModels = investigationModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == 0) {
            InvestigationViewBinding binding = InvestigationViewBinding.inflate(inflater, parent, false);
            binding.setClickListener(onClickListener);
            return new InvestigationVHOne(binding);
        } else {
            InvestigationViewTwoBinding binding = InvestigationViewTwoBinding.inflate(inflater, parent, false);
            binding.setClickListener(onClickListener);
            return new InvestigationVHTwo(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        InvestigationModel investigationModel = investigationModels.get(position);
        if (null == investigationModel.getFilePath() || investigationModel.getFilePath().isEmpty()) {
            InvestigationVHOne investigationVHOne = (InvestigationVHOne) holder;
            investigationVHOne.binding.setInvestigationModel(investigationModel);
            if (!investigationModel.getInvestigation().isEmpty()) {
                String testName = investigationModel.getInvestigation().get(0).getTestName();
                if (investigationModel.getInvestigation().size() > 1) {
                    investigationVHOne.binding.textView96.setText(testName + "   (+" + (investigationModel.getInvestigation().size() - 1) + ")");
                } else
                    investigationVHOne.binding.textView96.setText(testName);
            }
        } else {
            InvestigationVHTwo investigationVHTwo = (InvestigationVHTwo) holder;
            investigationVHTwo.binding.setInvestigationModel(investigationModel);
            setImagePath(investigationVHTwo, investigationModel);
            investigationVHTwo.binding.setImagePath(investigationModel.getFilePath());
            if (!investigationModel.getInvestigation().isEmpty()) {
                String testName = investigationModel.getInvestigation().get(0).getTestName();
                if (investigationModel.getInvestigation().size() > 1) {
                    investigationVHTwo.binding.textView96.setText(testName + "   (+" + (investigationModel.getInvestigation().size() - 1) + ")");
                } else
                    investigationVHTwo.binding.textView96.setText(testName);
            }
        }
    }

    private void setImagePath(InvestigationVHTwo investigationVHTwo, InvestigationModel investigationModel) {
        ImageModel imageModel;
        String imageList = investigationModel.getFilePath();
        String newStr = imageList.replace("\\", "");
        List<ImageModel> imageLists = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(newStr);
            Gson gson = new Gson();

            for (int a = 0; a < jsonArray.length(); a++) {
                imageModel = gson.fromJson((jsonArray.get(a).toString()), ImageModel.class);
                if (!imageModel.getFileType().equalsIgnoreCase("pdf"))
                    imageLists.add(imageModel);
                if (a == 0) {
                    try {
                        Glide.with(PatientDashboard.getInstance())
                                .load(imageLists.get(0).getFilePath())
                                .centerCrop()
                                .placeholder(R.drawable.diagnosis_demo_image)
                                .into(investigationVHTwo.binding.imageView44);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("TAG", "LoadInvestigationImageError: " + e.getLocalizedMessage());
                    }
                }
            }


            investigationVHTwo.binding.textView146.setVisibility(jsonArray.length() > 1 ? View.VISIBLE : View.GONE);
            if (jsonArray.length() > 1)
                investigationVHTwo.binding.textView146.setText("+" + (jsonArray.length() - 1) + " more");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return investigationModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        InvestigationModel model = investigationModels.get(position);
        if (null == model.getFilePath() || model.getFilePath().isEmpty())
            return 0;
        else return 1;
    }

    public static class InvestigationVHOne extends RecyclerView.ViewHolder {
        InvestigationViewBinding binding;

        public InvestigationVHOne(@NonNull InvestigationViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class InvestigationVHTwo extends RecyclerView.ViewHolder {

        InvestigationViewTwoBinding binding;

        public InvestigationVHTwo(@NonNull InvestigationViewTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
