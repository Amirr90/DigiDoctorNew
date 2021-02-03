package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.app.AlertDialog;
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
import com.digidoctor.android.databinding.FragmentReScheduleBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.BookAppointmentInterface;
import com.digidoctor.android.model.CalendarModel;
import com.digidoctor.android.model.CheckSlotAvailabilityDataRes;
import com.digidoctor.android.model.GetAppointmentSlotsDataRes;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.BookAppointment2;
import com.digidoctor.android.utility.NewDashboardUtils;
import com.digidoctor.android.utility.URLUtils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.ApiUtils.getDoctorsTimeSlots;
import static com.digidoctor.android.utility.AppUtils.getCurrentDateInWeekMonthDayFormat;
import static com.digidoctor.android.utility.AppUtils.parseDateToFormatDMY;
import static com.digidoctor.android.utility.utils.APPOINTMENT_DATE;
import static com.digidoctor.android.utility.utils.APPOINTMENT_TIME;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.KEY_DOC_ID;
import static com.digidoctor.android.utility.utils.KEY_IS_ERA_USER;
import static com.digidoctor.android.utility.utils.MEMBER_ID;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.RE_SCHEDULE;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.logout;


public class ReScheduleFragment extends Fragment {
    private static final String TAG = "ReScheduleFragment";

    FragmentReScheduleBinding reScheduleBinding;
    NavController navController;
    OnlineAppointmentModel appointmentModel;

    String date = null;

    CalendarAdapter calendarAdapter;

    TimeSlotsAdapter slotsAdapter;

    List<GetAppointmentSlotsDataRes> slotsDataRes = new ArrayList<>();

    User user;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reScheduleBinding = FragmentReScheduleBinding.inflate(inflater, container, false);
        return reScheduleBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        date = getDateToSend(0);

        //getting Model
        if (null == getArguments())
            return;


        user = getPrimaryUser(requireActivity());

        String jsonString = getArguments().getString("model");
        appointmentModel = new OnlineAppointmentModel();
        Gson gson = new Gson();
        appointmentModel = gson.fromJson(jsonString, OnlineAppointmentModel.class);

        Log.d(TAG, "onViewCreated: model=> " + appointmentModel.toString());

        reScheduleBinding.setOnlineModel(appointmentModel);
        reScheduleBinding.tvCurrentDate.setText(getCurrentDateInWeekMonthDayFormat());


        calendarAdapter = new CalendarAdapter(getNextWeekDays(), (calendarModel, pos) -> {
            date = getDateToSend(pos);
            getDocTimeSlot(date);
        });


        reScheduleBinding.calRec.setAdapter(calendarAdapter);
        getDocTimeSlot(date);
    }

    private void getDocTimeSlot(final String date) {
        getDoctorsTimeSlots(String.valueOf(appointmentModel.getDoctorId()),
                date,
                String.valueOf(appointmentModel.getIsEraUser()),
                new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(List<?> o) {
                        AppUtils.hideDialog();
                        List<GetAppointmentSlotsDataRes> slots = (List<GetAppointmentSlotsDataRes>) o;
                        slotsDataRes.clear();
                        slotsDataRes.addAll(slots);
                        slotsAdapter = new TimeSlotsAdapter(slotsDataRes, new AdapterInterface() {
                            @Override
                            public void onItemClicked(Object obj) {
                                if (date != null) {
                                    showRescheduleDialog(obj);
                                } else
                                    Toast.makeText(PatientDashboard.getInstance(), R.string.select_date, Toast.LENGTH_SHORT).show();

                            }
                        });

                        reScheduleBinding.timingRec.setAdapter(slotsAdapter);
                        slotsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String s) {
                        AppUtils.hideDialog();
                        if (null != slotsDataRes)
                            slotsDataRes.clear();
                        Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(requireActivity(), R.string.retry, Toast.LENGTH_SHORT).show();
                        if (null != slotsAdapter)
                            slotsAdapter.notifyDataSetChanged();

                    }
                });
    }

    private void showRescheduleDialog(final Object obj) {

        final String time = (String) obj;
        new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.re__schedule_appointment)
                .setMessage("Re-Scheduling Appointment on " + time + "  " + date)
                .setPositiveButton(R.string.yes,
                        (dialog, id) -> {
                            dialog.cancel();
                            checkTimeSlot(date, time);

                        })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel()).show();
    }

    private void checkTimeSlot(String date, String time) {


        Map<String, String> map = new HashMap<>();

        map.put(MOBILE_NUMBER, user.getMobileNo());
        map.put(MEMBER_ID, String.valueOf(user.getMemberId()));
        map.put(KEY_DOC_ID, String.valueOf(appointmentModel.getDoctorId()));
        map.put(APPOINTMENT_DATE, date);
        map.put(APPOINTMENT_TIME, time);
        map.put(KEY_IS_ERA_USER, String.valueOf(appointmentModel.getIsEraUser()));
        map.put(KEY_APPOINTMENT_ID, appointmentModel.getAppointmentId());


        Log.d(TAG, "checkTimeSlot: MAP:=> " + map.toString());

        ApiUtils.checkTimeSlotAvailability(map, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> obj) {

                Log.d(TAG, "onSuccess: " + obj);

                List<CheckSlotAvailabilityDataRes> response = (List<CheckSlotAvailabilityDataRes>) obj;
                if (response != null) {

                    if (response.get(0).getIsAvailable() == 1) {
                        reScheduleAppointment(time, new BookAppointmentInterface() {
                            @Override
                            public void onAppointmentBooked(OnlineAppointmentModel appointmentModel) {
                                Toast.makeText(requireContext(), R.string.appointment_re_scheduled_successfully, Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putString("key", RE_SCHEDULE);
                                navController.navigate(R.id.action_reScheduleFragment_to_cancelAppointmentFragment2, bundle);
                            }

                            @Override
                            public void onFailed(String msg) {
                                Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String errorMsg) {
                                Toast.makeText(requireActivity(), errorMsg, Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        Toast.makeText(requireActivity(), getString(R.string.slot_not_available), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reScheduleAppointment(String time, final BookAppointmentInterface bookAppointmentInterface) {
        User bookingUser = getUserForBooking(requireActivity());


        BookAppointment2 appointment2 = new BookAppointment2();

        appointment2.setMemberId((bookingUser.getPrimaryStatus() == 1 ? String.valueOf(bookingUser.getId()) : String.valueOf(bookingUser.getMemberId())));
        appointment2.setMobileNo(bookingUser.getMobileNo());
        appointment2.setServiceProviderDetailsId(String.valueOf(appointmentModel.getDoctorId()));
        appointment2.setAppointDate(parseDateToFormatDMY(date));
        appointment2.setAppointTime(time);
        appointment2.setIsEraUser(String.valueOf(appointmentModel.getIsEraUser()));
        appointment2.setAppointmentId(appointmentModel.getAppointmentId());


        AppUtils.showRequestDialog(requireActivity());
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<OnlineAppointmentRes> call = iRestInterfaces.onlineAppointment2(appointment2);
        call.enqueue(new Callback<OnlineAppointmentRes>() {
            @Override
            public void onResponse(@NotNull Call<OnlineAppointmentRes> call, @NotNull Response<OnlineAppointmentRes> response) {
                AppUtils.hideDialog();
                if (response.code() == 200 && response.body() != null) {
                    if (response.body().getResponseCode() == 1) {
                        bookAppointmentInterface.onAppointmentBooked(response.body().getResponseValue().get(0));
                    } else bookAppointmentInterface.onError(response.body().getResponseMessage());
                } else bookAppointmentInterface.onError(response.message());
            }

            @Override
            public void onFailure(@NotNull Call<OnlineAppointmentRes> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                bookAppointmentInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }


    private String getDateToSend(int position) {
        List<CalendarModel> calendarModelList = new ArrayList<>();
        ArrayList<HashMap<String, String>> getNextWeekDays = NewDashboardUtils.getNextWeekDays();
        for (int a = 0; a < getNextWeekDays.size(); a++) {
            calendarModelList.add(new CalendarModel(
                    getNextWeekDays.get(a).get("date"),
                    getNextWeekDays.get(a).get("day"),
                    getNextWeekDays.get(a).get("dateSend"), true));
        }

        return calendarModelList.get(position).getDateSend();
    }

    private List<CalendarModel> getNextWeekDays() {
        List<CalendarModel> calendarModelList = new ArrayList<>();
        ArrayList<HashMap<String, String>> getNextWeekDays = NewDashboardUtils.getNextWeekDays();
        for (int a = 0; a < getNextWeekDays.size(); a++) {
            calendarModelList.add(new CalendarModel(
                    getNextWeekDays.get(a).get("date"),
                    getNextWeekDays.get(a).get("day"),
                    getNextWeekDays.get(a).get("dateSend"), true));
        }

        return calendarModelList;
    }
}