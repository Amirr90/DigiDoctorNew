package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.CalenderViewBinding;
import com.digidoctor.android.model.CalendarModel;
import com.digidoctor.android.view.activity.PatientDashboard;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalenderVH> {

    List<CalendarModel> calendarModelList;
    CalenderInterface calenderInterface;
    public static int selectedPosition;

    public CalendarAdapter(List<CalendarModel> calendarModelList, CalenderInterface calenderInterface) {
        this.calendarModelList = calendarModelList;
        this.calenderInterface = calenderInterface;
        selectedPosition = 0;
        for (int a = 0; a < calendarModelList.size(); a++) {
            if (calendarModelList.get(a).isAvailable()) {
                selectedPosition = a;
                return;
            }
        }

    }

    @NonNull
    @Override
    public CalenderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CalenderViewBinding calenderViewBinding = CalenderViewBinding.inflate(inflater, parent, false);
        calenderViewBinding.setCalenderInterface(calenderInterface);
        return new CalenderVH(calenderViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull final CalenderVH holder, final int position) {

        final CalendarModel calendarModel = calendarModelList.get(position);
        holder.calenderViewBinding.setCalender(calendarModel);

        holder.calenderViewBinding.rlCalenderRoot.setEnabled(calendarModel.isAvailable());
        holder.calenderViewBinding.rlCalenderRoot.setOnClickListener(v -> {
            calenderInterface.onItemClicked(calendarModel, position);
            holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color_yellow));
            selectedPosition = position;
            setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.white),
                    PatientDashboard.getInstance().getResources().getColor(R.color.white));
            notifyDataSetChanged();


        });


        if (calendarModel.isAvailable()) {
            if (selectedPosition == position) {
                setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.white),
                        PatientDashboard.getInstance().getResources().getColor(R.color.white));
                holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color_yellow));
            } else {
                setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.PrimaryColor),
                        PatientDashboard.getInstance().getResources().getColor(R.color.TextGrayColo));
                holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color));
            }

        } else {
            setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.TextGrayColo),
                    PatientDashboard.getInstance().getResources().getColor(R.color.TextGrayColo));
            holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.disable_rectangle_outline_new_ui_color));

        }


    }

    public CalendarModel getItem() {
        CalendarModel calendarModel;
        if (null == calendarModelList || calendarModelList.isEmpty()) {
        }
        else {
            for (CalendarModel calendarModel1 : calendarModelList) {
                if (calendarModel1.isAvailable()) {
                    calendarModel = calendarModel1;
                    return calendarModel;
                }
            }
        }
        return null;

    }

    private void setTextColor(CalenderVH holder, int color, int color2) {
        holder.calenderViewBinding.textView81.setTextColor(color2);
        holder.calenderViewBinding.textView85.setTextColor(color);
        holder.calenderViewBinding.textView86.setTextColor(color2);
    }

    @Override
    public int getItemCount() {
        return calendarModelList.size();
    }

    public static class CalenderVH extends RecyclerView.ViewHolder {
        CalenderViewBinding calenderViewBinding;

        public CalenderVH(CalenderViewBinding calenderViewBinding) {
            super(calenderViewBinding.getRoot());
            this.calenderViewBinding = calenderViewBinding;
        }
    }

    public interface CalenderInterface {
        void onItemClicked(CalendarModel calendarModel, int position);
    }

}
