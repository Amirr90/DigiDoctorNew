package com.digidoctor.android.view.fragments;

import android.app.Activity;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chooseTimeBinding = FragmentChooseTimeBinding.inflate(inflater, container, false);
        if (PatientDashboard.getInstance() != null)
            activity = PatientDashboard.getInstance();
        return chooseTimeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        date = getDateToSend(0);

        if (PatientDashboard.getInstance() != null)
            AppUtils.showRequestDialog(PatientDashboard.getInstance());

        if (null == getArguments())
            return;

        String jsonString = getArguments().getString("docModel");
        doctorModel = new DoctorModel();
        Gson gson = new Gson();
        doctorModel = gson.fromJson(jsonString, DoctorModel.class);

        chooseTimeBinding.setDocModel(doctorModel);


        chooseTimeBinding.tvCurrentDate.setText(getCurrentDateInWeekMonthDayFormat());


        calendarAdapter = new CalendarAdapter(getNextWeekDays(), (calendarModel, pos) -> {
            date = getDateToSend(pos);
            getDocTimerSlot(date);

        });

        chooseTimeBinding.calRec.setAdapter(calendarAdapter);
        getDocTimerSlot(date);


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
                                    bundle.putString("date", ChooseTimeFragment.this.date);
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
            calendarModelList.add(new CalendarModel(
                    getNextWeekDays.get(a).get("date"),
                    getNextWeekDays.get(a).get("day"),
                    getNextWeekDays.get(a).get("dateSend")));
        }

        return calendarModelList;
    }

    private String getDateToSend(int position) {
        List<CalendarModel> calendarModelList = new ArrayList<>();
        ArrayList<HashMap<String, String>> getNextWeekDays = NewDashboardUtils.getNextWeekDays();
        for (int a = 0; a < getNextWeekDays.size(); a++) {
            calendarModelList.add(new CalendarModel(
                    getNextWeekDays.get(a).get("date"),
                    getNextWeekDays.get(a).get("day"),
                    getNextWeekDays.get(a).get("dateSend")));
        }

        return calendarModelList.get(position).getDateSend();
    }


}