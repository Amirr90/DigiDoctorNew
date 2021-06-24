package com.digidoctor.android.utility;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.digidoctor.android.R;
import com.digidoctor.android.adapters.ShimmerAdapter;
import com.digidoctor.android.view.activity.PatientDashboard;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


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
                Log.d(TAG, "loadImage: " + e.getLocalizedMessage());
            }
        }

    }

    @BindingAdapter("android:loadCustomLabImage")
    public static void loadLabImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.lab_icon)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadImage: " + e.getLocalizedMessage());
            }
        }

    }

    @BindingAdapter("android:loadCustomBannerImage")
    public static void loadCustomBannerImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.banner_one)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadImage: " + e.getLocalizedMessage());
            }
        }

    }

    @BindingAdapter("android:loadCustomShimmerAdapter")
    public static void loadCustomShimmerAdapter(RecyclerView recView, int layout) {
        recView.setAdapter(new ShimmerAdapter(layout));
    }

    @BindingAdapter("android:loadCustomProductImage")
    public static void loadCustomProductImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.defualt_clinics_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadCustomProductImage: " + e.getLocalizedMessage());
            }
        }

    }


    @BindingAdapter("android:loadCustomDoctorWithDummyImage")
    public static void loadDummyImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.doctor_dummy_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadDummyImage: " + e.getLocalizedMessage());
            }
        }

    }


    @BindingAdapter("android:loadCustomUserImage")
    public static void loadUserImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.profile_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.profile_demo_image);
                Log.d(TAG, "loadUserImage: " + e.getLocalizedMessage());
            }
        } else {
            imageView.setImageResource(R.drawable.profile_demo_image);
        }

    }

    @BindingAdapter("android:loadPaymentOptionImage")
    public static void loadPaymentOptionImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.profile_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                imageView.setImageResource(R.drawable.profile_demo_image);
                Log.d(TAG, "loadUserImage: " + e.getLocalizedMessage());
            }
        } else {
            imageView.setImageResource(R.drawable.profile_demo_image);
        }

    }

    @BindingAdapter("android:loadCustomPrescriptionImage")
    public static void loadPrescriptionImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.diagnosis_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadPrescriptionImage: " + e.getLocalizedMessage());
            }
        }

    }

    @BindingAdapter("android:LoadInvestigationImage")
    public static void LoadInvestigationImage(ImageView imageView, String imagePath) {
        if (null != imagePath && !imagePath.isEmpty()) {
            try {
                Glide.with(PatientDashboard.getInstance())
                        .load(imagePath)
                        .centerCrop()
                        .placeholder(R.drawable.diagnosis_demo_image)
                        .into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "loadPrescriptionImage: " + e.getLocalizedMessage());
            }
        }

    }

    @BindingAdapter("android:loadNavImage")
    public static void loadNavImage(ImageView imageView, int imagePath) {
        imageView.setImageResource(imagePath);
        Log.d(TAG, "loadNavImage: " + imagePath);
    }

    @BindingAdapter("android:setCustomVisibility")
    public static void setCustomVisibility(View view, String text) {
        if (null == text || text.isEmpty())
            view.setVisibility(GONE);
        else view.setVisibility(VISIBLE);
    }

    @BindingAdapter("android:setCustomVisibility")
    public static void setCustomVisibility(View view, Boolean value) {
        view.setVisibility(value ? View.VISIBLE : View.GONE);
    }
}
