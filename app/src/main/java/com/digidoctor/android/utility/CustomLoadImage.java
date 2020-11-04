package com.digidoctor.android.utility;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.view.activity.PatientDashboard;


public class CustomLoadImage {

    private static final String TAG = "CustomLoadImage";

    @BindingAdapter("android:loadCustomDoctorImage")
    public static void loadImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.defualt_clinics_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadSpeImage: ");
            }
        }
        Log.d(TAG, "loadSpeImage: err");
    }

    @BindingAdapter("android:loadCustomUserImage")
    public static void loadUserImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.profile)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadSpeImage: ");
            }
        }
        Log.d(TAG, "loadSpeImage: err");
    }

    @BindingAdapter("android:loadNavImage")
    public static void loadNavImage(ImageView imageView, int imagePath) {
        imageView.setImageResource(imagePath);
        Log.d(TAG, "loadNavImage: " + imagePath);
    }
}
