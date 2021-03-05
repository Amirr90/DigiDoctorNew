
package com.digidoctor.android.adapters;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.OldAppointmentsViewsBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class OldAppointmentsAdapter extends RecyclerView.Adapter<OldAppointmentsAdapter.AppointmentsVH> {


    List<AppointmentModel> appointmentModels;
    AdapterInterface adapterInterface;
    private int originalHeight = 0;
    private boolean mIsViewExpanded = false;


    public OldAppointmentsAdapter(List<AppointmentModel> appointmentModels, AdapterInterface adapterInterface) {
        this.appointmentModels = appointmentModels;
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public OldAppointmentsAdapter.AppointmentsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        OldAppointmentsViewsBinding binding = OldAppointmentsViewsBinding.inflate(layoutInflater, parent, false);
        return new AppointmentsVH(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull OldAppointmentsAdapter.AppointmentsVH holder, int position) {


        AppointmentModel appointmentModel = appointmentModels.get(position);
        holder.binding.setAppointment(appointmentModel);
        holder.binding.textView201.setText(AppUtils.parseDate(appointmentModel.getAppointDate(), "E, dd MMM yyyy"));
       /* holder.binding.oldAppointmentView.setOnClickListener(v -> {

        });*/

        holder.binding.btnShowPrescription.setOnClickListener(view -> {
            float originalRotation = view.getRotation();

            if (originalRotation != 180) {
                view.setRotation(180);
            } else view.setRotation(0);


            if (originalHeight == 0) {
                originalHeight = view.getHeight();
            }

            // Declare a ValueAnimator object
            ValueAnimator valueAnimator;
            if (!mIsViewExpanded) {
                holder.binding.otherAppointmentLay.setVisibility(View.VISIBLE);
                holder.binding.otherAppointmentLay.setEnabled(true);
                mIsViewExpanded = true;

                valueAnimator = ValueAnimator.ofInt(originalHeight, originalHeight + (int) (originalHeight * 2.0)); // These values in this method can be changed to expand however much you like
                Animation a = new AlphaAnimation(0.00f, 2.00f); // Fade in

                a.setDuration(400);

                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.binding.otherAppointmentLay.setVisibility(View.VISIBLE);
                        holder.binding.otherAppointmentLay.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // Set the animation on the custom view
                holder.binding.otherAppointmentLay.startAnimation(a);

            } else {
                mIsViewExpanded = false;
                valueAnimator = ValueAnimator.ofInt(originalHeight + (int) (originalHeight * 2.0), originalHeight);

                Animation a = new AlphaAnimation(2.00f, 0.00f); // Fade out

                a.setDuration(400);
                // Set a listener to the animation and configure onAnimationEnd
                a.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.binding.otherAppointmentLay.setVisibility(View.GONE);
                        holder.binding.otherAppointmentLay.setEnabled(false);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // Set the animation on the custom view
                holder.binding.otherAppointmentLay.startAnimation(a);
            }
            valueAnimator.setDuration(400);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(animation -> {
                Integer value = (Integer) animation.getAnimatedValue();
                view.getLayoutParams().height = value.intValue();
                view.requestLayout();
            });

            adapterInterface.onItemClicked(position);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        if (null == appointmentModels)
            appointmentModels = new ArrayList<>();
        return appointmentModels.size();
    }

    public static class AppointmentsVH extends RecyclerView.ViewHolder {
        OldAppointmentsViewsBinding binding;

        public AppointmentsVH(@NonNull OldAppointmentsViewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
