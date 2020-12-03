package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.View;
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

    int selectedPosition = 0;

    public CalendarAdapter(List<CalendarModel> calendarModelList, CalenderInterface calenderInterface) {
        this.calendarModelList = calendarModelList;
        this.calenderInterface = calenderInterface;

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
        holder.calenderViewBinding.rlCalenderRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderInterface.onItemClicked(calendarModel,position);
                holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color_yellow));
                selectedPosition = position;
                setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.white),
                        PatientDashboard.getInstance().getResources().getColor(R.color.white));
                notifyDataSetChanged();
            }
        });

        if (selectedPosition == position) {
            setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.white),
                    PatientDashboard.getInstance().getResources().getColor(R.color.white));
            holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color_yellow));

        } else {
            setTextColor(holder, PatientDashboard.getInstance().getResources().getColor(R.color.PrimaryColor),
                    PatientDashboard.getInstance().getResources().getColor(R.color.TextGrayColo));
            holder.calenderViewBinding.getRoot().setBackground(PatientDashboard.getInstance().getResources().getDrawable(R.drawable.rectangle_outline_new_ui_color));
        }
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

    public class CalenderVH extends RecyclerView.ViewHolder {
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
