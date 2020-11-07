package com.digidoctor.android.view.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.digidoctor.android.databinding.FragmentBookAppointmentBinding;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiCallbackInterface;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.BookAppointment;
import com.digidoctor.android.utility.BookAppointmentInterface;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import java.util.List;

import static com.digidoctor.android.utility.AppUtils.getDayOfWeekDayFromDate;
import static com.digidoctor.android.utility.AppUtils.parseDateToFormatDMY;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_CASH;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_RAZOR_PAY;
import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;


public class BookAppointmentFragment extends Fragment {


    private static final String TAG = "BookAppointmentFragment";
    FragmentBookAppointmentBinding appointmentBinding;

    NavController navController;

    String date, time;

    DoctorModel doctorModel;

    User user;

    public static BookAppointment bookAppointment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appointmentBinding = FragmentBookAppointmentBinding.inflate(inflater, container, false);
        return appointmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        //getting current user
        user = getPrimaryUser(requireActivity());
        if (user == null)
            user = new User();


        //checking user's profile is completed or not
        if (user.getIsExists() == 0) {
            showCompleteProfileDialog();
            return;
        }


        date = getArguments().getString("date");
        time = getArguments().getString("time");


        String docString = getArguments().getString("docModel");
        Gson gson = new Gson();
        doctorModel = gson.fromJson(docString, DoctorModel.class);

        appointmentBinding.setDoModel(doctorModel);


        appointmentBinding.tvPatientName.setText(user.getName());
        appointmentBinding.tvBookAppointmentTimeDate.setText(getDayOfWeekDayFromDate(date) + "  " + time);
        appointmentBinding.editTextTextPersonName2.setText(user.getName());
        appointmentBinding.editTextMobile.setText(user.getMobileNo());
        appointmentBinding.editTextTexEmail.setText(user.getEmailId());


        //init  booking process
        appointmentBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(PatientDashboard.getInstance());
                new AlertDialog.Builder(requireActivity())
                        .setMessage("Consultation Fee ₹" + doctorModel.getDrFee())
                        .setPositiveButton("Pay Now",
                                new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        bookAppointment(PAY_MODE_RAZOR_PAY);

                                    }
                                })
                        .setNegativeButton("Pay on Visit", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                bookAppointment(PAY_MODE_CASH);
                            }
                        }).show();


            }
        });


        appointmentBinding.btnAddOtherMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMemberList();
            }
        });

    }

    private void getMemberList() {

        ApiUtils.GetMembersRes(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<User> userList = (List<User>) o;
                if (null != userList && !userList.isEmpty()) {
                    showMemberDialog(userList);
                    Toast.makeText(requireActivity(), "" + userList.size(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private void showMemberDialog(List<User> userList) {
    }

    private void bookAppointment(int payMode) {
        bookAppointment = new BookAppointment(requireActivity());

        //Setting Parameters
        bookAppointment.setUserMobileNo(user.getMobileNo());
        bookAppointment.setMemberId(String.valueOf(user.getId()));
        bookAppointment.setPatientName(user.getName());
        bookAppointment.setServiceProviderDetailsId(String.valueOf(doctorModel.getId()));
        bookAppointment.setAppointDate(parseDateToFormatDMY(date));
        bookAppointment.setAppointTime(time);
        bookAppointment.setAppointmentId("0");
        bookAppointment.setMobileNo(user.getMobileNo());
        bookAppointment.setEmail(user.getEmailId());
        bookAppointment.setToken(utils.getString(TOKEN, requireActivity()));
        bookAppointment.setGender(String.valueOf(user.getGender()));
        bookAppointment.setIsEraUser(String.valueOf(doctorModel.getIsEraUser()));
        bookAppointment.setDrFee(String.valueOf(doctorModel.getDrFee()));


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
                Toast.makeText(PatientDashboard.getInstance(), "Failed to book Appointment\n" + msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(PatientDashboard.getInstance(), "Error While Booking Appointment\n" + errorMsg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showCompleteProfileDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Incomplete Profile")
                .setCancelable(false)
                .setMessage("Your profile is incomplete,\ncomplete  profile before booking appointment")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        navController.navigate(R.id.action_bookAppointmentFragment_to_profileFragment);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        PatientDashboard.getInstance().onSupportNavigateUp();
                    }
                })
                .show();

    }

}