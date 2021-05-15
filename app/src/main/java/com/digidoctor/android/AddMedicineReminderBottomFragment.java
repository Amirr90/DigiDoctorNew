package com.digidoctor.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.digidoctor.android.databinding.FragmentAddMedicineReminderBottomBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class AddMedicineReminderBottomFragment extends BottomSheetDialogFragment {

    FragmentAddMedicineReminderBottomBinding binding;
    private static final String TAG = "AddMedicineReminderBott";
    int days;
    String medName, frequency, medId;
    private String format;
    Boolean isAlarmSet = false;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMedicineReminderBottomBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null)
            dismiss();

        days = getArguments().getInt("duration");
        medId = getArguments().getString("id");
        medName = getArguments().getString("medName");
        frequency = getArguments().getString("frequency");

        binding.tvMedicineName.setText(medName);
        binding.tvBD.setVisibility(frequency.equalsIgnoreCase("OD") ? View.GONE : View.VISIBLE);
        binding.tvTD.setVisibility(frequency.equalsIgnoreCase("OD") || frequency.equalsIgnoreCase("BD") ? View.GONE : View.VISIBLE);
        binding.tvOD.setOnClickListener(v -> alertTimePicker(binding.tvOD, 100));
        binding.tvBD.setOnClickListener(v -> alertTimePicker(binding.tvBD, 101));
        binding.tvTD.setOnClickListener(v -> alertTimePicker(binding.tvTD, 102));
        binding.btnAddAlarm.setOnClickListener(v -> {
            if (isAlarmSet)
                updateAlarm();
            else Toast.makeText(requireActivity(), "Add alarm first !!", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateAlarm() {
        AppUtils.showRequestDialog(requireActivity());
        UpdateAlarmModel alarmModel = new UpdateAlarmModel(utils.getUserForBooking(requireActivity()).getMemberId(), Integer.parseInt(medId), 1);
        ApiUtils.updateAlarm(alarmModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                MedicineReminderListFragment.instance.getData();
                Toasty.success(requireActivity(), "Success!", Toast.LENGTH_SHORT, true).show();
                AppUtils.hideDialog();
                dismiss();
            }

            @Override
            public void onError(String s) {
                Toasty.error(requireActivity(), s, Toast.LENGTH_SHORT, true).show();
                dismiss();
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    public void alertTimePicker(TextView tvTime, int reqCode) {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker, null, false);
        final TimePicker myTimePicker = (TimePicker) view
                .findViewById(R.id.myTimePicker);
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle("Set Alarm Time")
                .setPositiveButton("Go", (dialog, id) -> {
                    int currentHourText = myTimePicker.getCurrentHour();
                    int currentMinuteText = myTimePicker.getCurrentMinute();

                    dialog.cancel();
                    showTime(currentHourText, currentMinuteText, tvTime);
                    Log.d(TAG, "alertTimePicker: " + myTimePicker.getDrawingTime());
                    startAlert(currentHourText, currentMinuteText, reqCode);

                }).show();
    }

    public void startAlert(int hour, int minute, int reqCode) {
        ArrayList<Integer> days = getNextWeekDays();
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
        intent.putExtra(AlarmClock.EXTRA_DAYS, days);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Pills Reminder for " + medName);
        //startActivity(intent);
        startActivityForResult(intent, reqCode);
    }

    private ArrayList<Integer> getNextWeekDays() {
        ArrayList<Integer> calendarModelList = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            SimpleDateFormat sdfDay = new SimpleDateFormat("u", Locale.getDefault());
            String day = sdfDay.format(calendar.getTime());
            calendarModelList.add(getDays(day));
        }
        return calendarModelList;
    }

    private Integer getDays(String day) {
        if (day.equalsIgnoreCase("1"))
            return Calendar.MONDAY;
        else if
        (day.equalsIgnoreCase("2"))
            return Calendar.TUESDAY;
        else if
        (day.equalsIgnoreCase("3"))
            return Calendar.WEDNESDAY;
        else if
        (day.equalsIgnoreCase("4"))
            return Calendar.THURSDAY;
        else if
        (day.equalsIgnoreCase("5"))
            return Calendar.FRIDAY;
        else if
        (day.equalsIgnoreCase("6"))
            return Calendar.SATURDAY;
        else
            return Calendar.SUNDAY;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            isAlarmSet = true;

            binding.tvOD.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_alarm_on_24, 0);
        } else if (requestCode == 101) {
            isAlarmSet = true;
            binding.tvBD.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_alarm_on_24, 0);
        } else if (requestCode == 102) {
            isAlarmSet = true;
            binding.tvTD.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_alarm_on_24, 0);
        } else
            Toasty.warning(requireActivity(), "Alarm not set !!", Toast.LENGTH_SHORT, true).show();

    }

    public void showTime(int hour, int min, TextView textView) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        textView.setText(String.format("Time    %s", new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format)));
    }

    public static class UpdateAlarmModel {
        private Integer memberId;
        private Integer id;
        private Integer isReminder;

        public UpdateAlarmModel(Integer memberId, Integer id, Integer isReminder) {
            this.memberId = memberId;
            this.id = id;
            this.isReminder = isReminder;
        }

        public Integer getMemberId() {
            return memberId;
        }

        public void setMemberId(Integer memberId) {
            this.memberId = memberId;
        }

        public Integer getMedicineId() {
            return id;
        }

        public void setMedicineId(Integer medicineId) {
            this.id = medicineId;
        }

        public Integer getIsReminder() {
            return isReminder;
        }

        public void setIsReminder(Integer isReminder) {
            this.isReminder = isReminder;
        }
    }
}
