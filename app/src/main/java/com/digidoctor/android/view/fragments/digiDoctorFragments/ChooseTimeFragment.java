package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.CalendarAdapter;
import com.digidoctor.android.adapters.TimeSlotsAdapter;
import com.digidoctor.android.databinding.FragmentChooseTimeBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.CalendarModel;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.GetAppointmentSlotsDataRes;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.NewDashboardUtils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.digidoctor.android.adapters.CalendarAdapter.selectedPosition;
import static com.digidoctor.android.utility.ApiUtils.getDoctorsTimeSlots;
import static com.digidoctor.android.utility.AppUtils.getCurrentDateInWeekMonthDayFormat;
import static com.digidoctor.android.utility.utils.logout;

public class ChooseTimeFragment extends Fragment {

    DoctorModel doctorModel;

    FragmentChooseTimeBinding chooseTimeBinding;

    NavController navController;

    CalendarAdapter calendarAdapter;

    TimeSlotsAdapter slotsAdapter;

    String date = null;

    Activity activity;

    List<GetAppointmentSlotsDataRes> slotsDataRes = new ArrayList<>();

    List<String> workingDays;

    private static final String TAG = "ChooseTimeFragment";

    public static ChooseTimeFragment instance;

    public static ChooseTimeFragment getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chooseTimeBinding = FragmentChooseTimeBinding.inflate(inflater, container, false);
        if (PatientDashboard.getInstance() != null)
            activity = PatientDashboard.getInstance();
        instance = this;
        return chooseTimeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        if (null == getArguments())
            return;

        String jsonString = getArguments().getString("docModel");
        doctorModel = new DoctorModel();
        Gson gson = new Gson();
        doctorModel = gson.fromJson(jsonString, DoctorModel.class);

        chooseTimeBinding.setDocModel(doctorModel);


        try {
            workingDays = getWorkingDays(doctorModel);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onViewCreated: working Time null");

        }

        chooseTimeBinding.tvCurrentDate.setText(getCurrentDateInWeekMonthDayFormat());


        calendarAdapter = new CalendarAdapter(getNextWeekDays(), (CalendarModel calendarModel, int pos) -> {

            getDocTimerSlot(calendarModel.getDateSend());

            Log.d(TAG, "SelectedDate: " + calendarModel.getDateSend());

        });

        chooseTimeBinding.calRec.setAdapter(calendarAdapter);


        if (calendarAdapter.getItem() != null) {
            date = calendarAdapter.getItem().getDateSend();
            getDocTimerSlot(date);
        }
        scrollToPosition(selectedPosition);
    }

    public void scrollToPosition(int position) {
        Log.d(TAG, "scrollToPosition: " + position);
        chooseTimeBinding.calRec.scrollToPosition(position);
    }

    private List<String> getWorkingDays(DoctorModel doctorModel) throws JSONException {
        List<String> list = new ArrayList<>();
        if (null != doctorModel.getWorkingHours()) {
            JSONArray jsonArray = new JSONArray(doctorModel.getWorkingHours());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String day = jsonObject.getString("dayName");
                list.add(day.substring(0, 3));
            }
        } else
            Toast.makeText(requireActivity(), "Slots not available !!", Toast.LENGTH_SHORT).show();

        return list;
    }


    private void getDocTimerSlot(String date) {
        AppUtils.showRequestDialog(requireActivity());
        getDoctorsTimeSlots(String.valueOf(doctorModel.getId()),
                date,
                String.valueOf(doctorModel.getIsEraUser()),
                new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(List<?> o) {
                        AppUtils.hideDialog();
                        List<GetAppointmentSlotsDataRes> slots = (List<GetAppointmentSlotsDataRes>) o;
                        slotsDataRes.clear();
                        slotsDataRes.addAll(slots);
                        slotsAdapter = new TimeSlotsAdapter(slotsDataRes, o1 -> {
                            if (ChooseTimeFragment.this.date != null) {
                                Gson gson = new Gson();
                                String jsonString = gson.toJson(doctorModel);
                                try {
                                    JSONObject request = new JSONObject(jsonString);
                                    String time = (String) o1;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("date", date);
                                    bundle.putString("time", time);
                                    bundle.putString("docModel", request.toString());
                                    navController.navigate(R.id.action_chooseTimeFragment2_to_bookAppointmentFragment2, bundle);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else
                                Toast.makeText(PatientDashboard.getInstance(), R.string.select_date, Toast.LENGTH_SHORT).show();

                        });
                        chooseTimeBinding.timingRec.setAdapter(slotsAdapter);
                        slotsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String s) {
                        AppUtils.hideDialog();
                        if (null != slotsDataRes)
                            slotsDataRes.clear();
                        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                        if (null != slotsAdapter)
                            slotsAdapter.notifyDataSetChanged();

                        try {
                            if (s.equalsIgnoreCase("Failed to authenticate token !!")) {
                                logout(PatientDashboard.getInstance(), true);
                                Toast.makeText(PatientDashboard.getInstance(), s, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        AppUtils.hideDialog();
                        if (null != slotsDataRes)
                            slotsDataRes.clear();
                        Toast.makeText(activity, R.string.retry, Toast.LENGTH_SHORT).show();
                        if (null != slotsAdapter)
                            slotsAdapter.notifyDataSetChanged();

                    }
                });
    }

    private List<CalendarModel> getNextWeekDays() {
        List<CalendarModel> calendarModelList = new ArrayList<>();
        ArrayList<HashMap<String, String>> getNextWeekDays = NewDashboardUtils.getNextWeekDays();
        for (int a = 0; a < getNextWeekDays.size(); a++) {
            Log.d(TAG, "getNextWeekDays: " + workingDays.contains(getNextWeekDays.get(a).get("day")));
            if (workingDays.contains(getNextWeekDays.get(a).get("day"))) {
                calendarModelList.add(new CalendarModel(
                        getNextWeekDays.get(a).get("date"),
                        getNextWeekDays.get(a).get("day"),
                        getNextWeekDays.get(a).get("dateSend"),
                        true));
            } else {
                calendarModelList.add(new CalendarModel(
                        getNextWeekDays.get(a).get("date"),
                        getNextWeekDays.get(a).get("day"),
                        getNextWeekDays.get(a).get("dateSend"),
                        false));
            }


        }

        return calendarModelList;
    }


}