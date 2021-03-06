package com.digidoctor.android.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.DashBoardViewBinding;
import com.digidoctor.android.model.DashboardModel1;
import com.digidoctor.android.view.activity.PatientDashboard;


public class DashboardPatientAdapter1 extends ListAdapter<DashboardModel1, DashboardPatientAdapter1.DashboardModelVH> {

    Integer[] images = new Integer[]{
            R.drawable.specialities,
            R.drawable.symptom,
            R.drawable.lab_test,
            R.drawable.pharmacy};

    Integer[] imagesBackground = new Integer[]{
            R.drawable.doctor_dash,
            R.drawable.cough_dash,
            R.drawable.flask,
            R.drawable.medicine};

    Activity activity;


    public DashboardPatientAdapter1(Activity activity) {
        super(DashboardModel1.itemCallback);
        this.activity = activity;

    }

    @NonNull
    @Override
    public DashboardModelVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DashBoardViewBinding dashBoardViewBinding = DashBoardViewBinding.inflate(inflater, parent, false);
        return new DashboardModelVH(dashBoardViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardModelVH holder, final int position) {

        DashboardModel1 dashboardModel1 = getItem(position);
        holder.dashBoardViewBinding.setDashboard1(dashboardModel1);
        holder.dashBoardViewBinding.imageView21.setImageResource(images[position]);
        holder.dashBoardViewBinding.ivBackgroundImages.setImageResource(imagesBackground[position]);
        holder.dashBoardViewBinding.textView55.setText(dashboardModel1.getDescription());
        holder.dashBoardViewBinding.cv1.setOnClickListener(v -> {
            if (position == 0)
                PatientDashboard.getInstance().navigate(R.id.action_patientDashboardFragment_to_specialitiesFragment2);
            else if (position == 1)
                PatientDashboard.getInstance().navigate(R.id.action_patientDashboardFragment_to_symptomsFragment2);
            else if (position == 2)
                //Toast.makeText(activity, activity.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                PatientDashboard.getInstance().navigate(R.id.action_patientDashboardFragment_to_lab_Home_Fragment);
            else if (position == 3)
                // Toast.makeText(activity, activity.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                PatientDashboard.getInstance().navigate(R.id.action_patientDashboardFragment_to_onlinePharmacyFragment);

            else
                Toast.makeText(activity, activity.getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

        });


    }

    public static class DashboardModelVH extends RecyclerView.ViewHolder {
        DashBoardViewBinding dashBoardViewBinding;

        public DashboardModelVH(DashBoardViewBinding dashBoardViewBinding) {
            super(dashBoardViewBinding.getRoot());
            this.dashBoardViewBinding = dashBoardViewBinding;
        }
    }
}
