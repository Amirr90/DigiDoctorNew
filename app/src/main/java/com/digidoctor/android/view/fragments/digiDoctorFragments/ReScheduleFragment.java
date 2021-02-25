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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.CalendarAdapter;
import com.digidoctor.android.adapters.TimeSlotsAdapter;
import com.digidoctor.android.databinding.FragmentReScheduleBinding;
import com.digidoctor.android.databinding.LogoutOptionViewBinding;
import com.digidoctor.android.databinding.PaymentViewBinding;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.BookAppointmentInterface;
import com.digidoctor.android.model.CalendarModel;
import com.digidoctor.android.model.CheckSlotAvailabilityDataRes;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.GetAppointmentSlotsDataRes;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.OnlineAppointmentRes;
import com.digidoctor.android.model.PayModeModel;
import com.digidoctor.android.model.PaymentMode;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.BookAppointment;
import com.digidoctor.android.utility.BookAppointment2;
import com.digidoctor.android.utility.NewDashboardUtils;
import com.digidoctor.android.utility.URLUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digidoctor.android.utility.ApiUtils.getDoctorsTimeSlots;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_PAY_ON_VISIT;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_PAY_U_MONEY;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_RAZOR_PAYY;
import static com.digidoctor.android.utility.AppUtils.getCurrentDateInWeekMonthDayFormat;
import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.parseDateToFormatDMY;
import static com.digidoctor.android.utility.AppUtils.parseUserDate;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_CASH;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_RAZOR_PAY;
import static com.digidoctor.android.utility.utils.APPOINTMENT_DATE;
import static com.digidoctor.android.utility.utils.APPOINTMENT_TIME;
import static com.digidoctor.android.utility.utils.IS_REVISIT;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.KEY_DOC_ID;
import static com.digidoctor.android.utility.utils.KEY_IS_ERA_USER;
import static com.digidoctor.android.utility.utils.MEMBER_ID;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.RE_SCHEDULE;
import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;
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

    List<String> workingDays;

    boolean isRevisit = false;


    Integer drFee = 0;
    Integer docId = 0;
    Integer firstAppointmentId = 0;

    int selectedPaymentId = -1;
    public static ReScheduleFragment instance;

    public static ReScheduleFragment getInstance() {
        return instance;
    }

    int selectedItem = -1;
    String payModeTitle = null;
    public static BookAppointment bookAppointment;
    String time;

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


        //getting Model
        if (null == getArguments())
            return;


        user = getPrimaryUser(requireActivity());

        String jsonString = getArguments().getString("model");

        //Getting Values from AppointmentDetailFragment
        isRevisit = getArguments().getBoolean("reVisit", false);
        drFee = getArguments().getInt("docFee", 0);
        docId = getArguments().getInt("docId", 0);
        firstAppointmentId = getArguments().getInt("firstAppointmentId", 0);

        appointmentModel = new OnlineAppointmentModel();
        Gson gson = new Gson();
        appointmentModel = gson.fromJson(jsonString, OnlineAppointmentModel.class);

        Log.d(TAG, "onViewCreated: model=> " + appointmentModel.toString());

        reScheduleBinding.setOnlineModel(appointmentModel);
        reScheduleBinding.tvCurrentDate.setText(getCurrentDateInWeekMonthDayFormat());

        try {
            workingDays = getWorkingDays(appointmentModel);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onViewCreated: working Time null");

        }

        calendarAdapter = new CalendarAdapter(getNextWeekDays(), (calendarModel, pos) -> {
            date = calendarModel.getDateSend();
            getDocTimeSlot(date);
        });


        reScheduleBinding.calRec.setAdapter(calendarAdapter);


        if (calendarAdapter.getItem() != null) {
            date = calendarAdapter.getItem().getDateSend();
            getDocTimeSlot(date);
        }
    }

    public void scrollToPosition(int position) {
        Log.d(TAG, "scrollToPosition: " + position);
        reScheduleBinding.calRec.scrollToPosition(position);
    }

    private List<String> getWorkingDays(OnlineAppointmentModel doctorModel) throws JSONException {
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
                        slotsAdapter = new TimeSlotsAdapter(slotsDataRes, obj -> {
                            if (date != null) {
                                showRescheduleDialog(obj);
                            } else
                                Toast.makeText(PatientDashboard.getInstance(), R.string.select_date, Toast.LENGTH_SHORT).show();

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
        String msg;
        String title;
        if (isRevisit) {
            msg = "Re-Visit Appointment on " + time + "  " + date;
            title = getString(R.string.re_visit_appointment);

        } else {
            title = getString(R.string.re__schedule_appointment);
            msg = "Re-Scheduling Appointment on " + time + "  " + date;
        }

        new AlertDialog.Builder(requireActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.yes,
                        (dialog, id) -> {
                            dialog.cancel();
                            if (isRevisit && drFee > 0) {
                                // getPayMode(time, date);
                                Bundle bundle = new Bundle();
                                bundle.putString("date", date);
                                bundle.putString("time", time);
                                bundle.putBoolean("isRevisit", true);
                                bundle.putInt("firstAppointmentId", getArguments().getInt("firstAppointmentId", 0));

                                DoctorModel doctorModel = new DoctorModel();
                                doctorModel.setDrFee(drFee);
                                doctorModel.setId(docId);
                                bundle.putString("docModel", doctorModel.toString());
                                navController.navigate(R.id.action_reScheduleFragment_to_bookAppointmentFragment, bundle);
                            } else
                                checkTimeSlot(date, time);

                        })
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel()).show();
    }

    private void checkTimeSlot(String date, String time) {


        Map<String, Object> map = new HashMap<>();
        map.put(MOBILE_NUMBER, user.getMobileNo());
        map.put(MEMBER_ID, String.valueOf(user.getMemberId()));
        map.put(KEY_DOC_ID, String.valueOf(appointmentModel.getDoctorId()));
        map.put(APPOINTMENT_DATE, date);
        map.put(APPOINTMENT_TIME, time);
        map.put(IS_REVISIT, isRevisit);
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


    private void getPayMode(String time, String date) {

        AppUtils.showRequestDialog(requireActivity());
        PayModeModel payModeModel = new PayModeModel(String.valueOf(docId));
        ApiUtils.getPayMode(payModeModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                hideDialog();
                hideSoftKeyboard(requireActivity());
                List<PaymentMode> paymentModes = (List<PaymentMode>) o;
                if (null != paymentModes) {
                    for (PaymentMode mode : paymentModes)
                        Log.d(TAG, "onSuccess PayMode: " + mode.getPaymentMode());

                    showDialog(paymentModes, time, date);
                }


            }

            @Override
            public void onError(String s) {
                hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                hideDialog();
                Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());
                Toast.makeText(requireActivity(), "try again", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void showDialog(List<PaymentMode> paymentModes, String time, String date) {


        PaymentViewBinding paymentViewBinding = PaymentViewBinding.inflate(requireActivity().getLayoutInflater());
        paymentViewBinding.recyclerView5.setAdapter(new PaymentAdapter(paymentModes));
        paymentViewBinding.recyclerView5.addItemDecoration(new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL));


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        AlertDialog dialog = builder.create();
        dialog.setView(paymentViewBinding.getRoot());
        dialog.show();


        paymentViewBinding.btnSelf.setOnClickListener(view -> {
            if (selectedPaymentId < 0)
                Toast.makeText(requireActivity(), R.string.select_payment_option, Toast.LENGTH_SHORT).show();
            else {
                dialog.dismiss();
                if (selectedPaymentId == PAY_MODE_PAY_ON_VISIT) {
                    bookAppointment(PAY_MODE_CASH, time, date);
                } else if (selectedPaymentId == PAY_MODE_RAZOR_PAYY) {
                    bookAppointment(PAY_MODE_RAZOR_PAY, time, date);
                } else {
                    bookAppointment(PAY_MODE_PAY_U_MONEY, time, date);
                }
            }

        });

    }


    private void bookAppointment(int payMode, String time, String date) {
        User bookingUser = getUserForBooking(requireActivity());
        bookAppointment = new BookAppointment(requireActivity());


        //Setting Parameters
        bookAppointment.setUserMobileNo(user.getMobileNo());
        bookAppointment.setMemberId(bookingUser.getPrimaryStatus() == 1 ? String.valueOf(bookingUser.getId()) : String.valueOf(bookingUser.getMemberId()));
        bookAppointment.setPatientName(bookingUser.getName());
        bookAppointment.setServiceProviderDetailsId(String.valueOf(docId));
        bookAppointment.setAppointDate(parseDateToFormatDMY(date));
        bookAppointment.setAppointTime(time);
        bookAppointment.setAppointmentId("0");
        bookAppointment.setGuardianTypeId("0");
        bookAppointment.setDtPaymentTable("");
        bookAppointment.setTrxId("");
        bookAppointment.setMemberId(String.valueOf(bookingUser.getId()));
        bookAppointment.setDob(parseUserDate(bookingUser.getDob()));
        bookAppointment.setMobileNo(bookingUser.getMobileNo());
        bookAppointment.setEmail(bookingUser.getEmailId());
        bookAppointment.setToken(utils.getString(TOKEN, requireActivity()));
        bookAppointment.setGender(String.valueOf(bookingUser.getGender()));
        bookAppointment.setIsEraUser("0");
        bookAppointment.setDrFee(String.valueOf(drFee));
        bookAppointment.setPaymentMode(payModeTitle);
        bookAppointment.setRevisit(isRevisit);


        Log.d(TAG, "onClick: " + bookAppointment.toString());
        bookAppointment.initBooking(payMode, new BookAppointmentInterface() {
            @Override
            public void onAppointmentBooked(OnlineAppointmentModel appointmentModel) {
                Bundle bundle = new Bundle();
                bundle.putString("appointmentModel", getJSONFromModel(appointmentModel));
                Toast.makeText(PatientDashboard.getInstance(), "Appointment Booked " + appointmentModel.getAppointmentId(), Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_bookAppointmentFragment_to_appointmentDoneFragment, bundle);
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(PatientDashboard.getInstance(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(PatientDashboard.getInstance(), errorMsg, Toast.LENGTH_SHORT).show();

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
        appointment2.setAppointmentId(appointmentModel.getAppointmentId());
        appointment2.setFirstAppointmentId(String.valueOf(firstAppointmentId));
        AppUtils.showRequestDialog(requireActivity());
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<OnlineAppointmentRes> call;

        if (!isRevisit) {
            call = iRestInterfaces.onlineAppointment2(appointment2);
        } else call = iRestInterfaces.revisitAppointment(appointment2);

        if (null != call)
            call.enqueue(new Callback<OnlineAppointmentRes>() {
                @Override
                public void onResponse(@NotNull Call<OnlineAppointmentRes> call, @NotNull Response<OnlineAppointmentRes> response) {
                    AppUtils.hideDialog();
                    if (response.code() == 200 && response.body() != null) {
                        if (response.body().getResponseCode() == 1) {
                            bookAppointmentInterface.onAppointmentBooked(response.body().getResponseValue().get(0));
                        } else
                            bookAppointmentInterface.onError(response.body().getResponseMessage());
                    } else bookAppointmentInterface.onError(response.message());
                }

                @Override
                public void onFailure(@NotNull Call<OnlineAppointmentRes> call, @NotNull Throwable t) {
                    AppUtils.hideDialog();
                    bookAppointmentInterface.onFailed(t.getLocalizedMessage());
                }
            });
        else
            Toast.makeText(requireActivity(), getString(R.string.retry), Toast.LENGTH_SHORT).show();
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

    public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentVH> {
        List<PaymentMode> items;

        public PaymentAdapter(List<PaymentMode> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public PaymentAdapter.PaymentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LogoutOptionViewBinding paymentOption = LogoutOptionViewBinding.inflate(inflater, parent, false);
            return new PaymentAdapter.PaymentVH(paymentOption);
        }

        @Override
        public void onBindViewHolder(@NonNull PaymentAdapter.PaymentVH holder, int position) {
            PaymentMode paymentOptionModel = items.get(position);
            holder.paymentOption.setPaymentOption(paymentOptionModel);

            holder.paymentOption.checkBox.setChecked(selectedItem == position);

            holder.paymentOption.checkBox.setOnClickListener(view -> {
                payModeTitle = paymentOptionModel.getPaymentMode();
                selectedItem = position;
                selectedPaymentId = paymentOptionModel.getId();
                notifyDataSetChanged();
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class PaymentVH extends RecyclerView.ViewHolder {
            LogoutOptionViewBinding paymentOption;

            public PaymentVH(@NonNull LogoutOptionViewBinding paymentOption) {
                super(paymentOption.getRoot());
                this.paymentOption = paymentOption;
            }
        }
    }

}