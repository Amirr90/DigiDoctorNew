package com.digidoctor.android.view.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentShowFileOrPdfBinding;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.Step;


public class ShowFileOrPdfFragment extends Fragment {

    private static final String TAG = "ShowFileOrPdfFragment";

    FragmentShowFileOrPdfBinding showFileOrPdfBinding;

    String filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showFileOrPdfBinding = FragmentShowFileOrPdfBinding.inflate(getLayoutInflater());
        return showFileOrPdfBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        filePath = getArguments().getString("filePath");
        Log.d(TAG, "onViewCreated: filePath " + filePath);

        showFileOrPdfBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        String extension = filePath.substring(filePath.lastIndexOf("."));

        if (extension.equalsIgnoreCase(".png") ||
                extension.equalsIgnoreCase(".jpeg") ||
                extension.equalsIgnoreCase(".jpg")) {

            loadImage();
        } else if (extension.equalsIgnoreCase(".pdf")) {
            showPdf(filePath);
        } else
            PatientDashboard.getInstance().

                    onSupportNavigateUp();

        showFileOrPdfBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatientDashboard.getInstance().onSupportNavigateUp();
            }
        });
    }

    private void showPdf(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(filePath), "application/pdf");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "showPdf: " + e.getLocalizedMessage());
        }
    }

    private void loadImage() {
        try {
            Glide.with(PatientDashboard.getInstance())
                    .load(filePath)
                    .centerCrop()
                    .into(showFileOrPdfBinding.imageView11);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "loadSpeImage: ");
        }
    }
}