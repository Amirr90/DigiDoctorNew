package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.Resources;
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
import com.digidoctor.android.databinding.FragmentBookAppointmentBinding;
import com.digidoctor.android.databinding.LogoutOptionViewBinding;
import com.digidoctor.android.databinding.PaymentViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.BookAppointmentInterface;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.PayModeModel;
import com.digidoctor.android.model.PaymentMode;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.BookAppointment;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.digidoctor.android.utility.AppUtils.PAY_MODE_PAY_ON_VISIT;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_PAY_U_MONEY;
import static com.digidoctor.android.utility.AppUtils.PAY_MODE_RAZOR_PAYY;
import static com.digidoctor.android.utility.AppUtils.getDayOfWeekDayFromDate;
import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.parseDateToFormatDMY;
import static com.digidoctor.android.utility.AppUtils.parseUserDate;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_CASH;
import static com.digidoctor.android.utility.NewDashboardUtils.PAY_MODE_RAZOR_PAY;
import static com.digidoctor.android.utility.utils.TOKEN;
import static com.digidoctor.android.utility.utils.getJSONFromModel;
import static com.digidoctor.android.utility.utils.getMainUser;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.hideSoftKeyboard;


public class BookAppointmentFragment extends Fragment {


    private static final String TAG = "BookAppointmentFragment";
    FragmentBookAppointmentBinding appointmentBinding;

    NavController navController;

    String date, time;

    DoctorModel doctorModel;

    User user, bookingUser;

    public static BookAppointment bookAppointment;
    int selectedPaymentId = -1;
    int selectedItem = -1;
    String payModeTitle = null;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appointmentBinding = FragmentBookAppointmentBinding.inflate(inflater, container, false);
        return appointmentBinding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        if (null == getArguments()) {
            Toast.makeText(requireActivity(), R.string.date_taime_not_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        date = getArguments().getString("date");
        time = getArguments().getString("time");
        Log.d(TAG, "onViewCreated: Date " + date);

        //getting  user
        user = getPrimaryUser(requireActivity());
        bookingUser = getUserForBooking(requireActivity());

        if (user == null)
            user = new User();


        //checking user's profile is completed or not
        if (user.getIsExists() == 0) {
            showCompleteProfileDialog();
            return;
        }


        String docString = getArguments().getString("docModel");
        Gson gson = new Gson();
        doctorModel = gson.fromJson(docString, DoctorModel.class);

        appointmentBinding.setDoModel(doctorModel);


        Log.d(TAG, "onViewCreated: Booking User " + bookingUser.toString());


        appointmentBinding.setBookingUser(bookingUser);

        appointmentBinding.tvBookAppointmentTimeDate.setText(getDayOfWeekDayFromDate(date) + "  " + time);

        changeButtonBackground();


        //init  booking process
        appointmentBinding.btnConfirm.setOnClickListener(v -> {

            hideSoftKeyboard(PatientDashboard.getInstance());

            if (doctorModel.getDrFee() == 0) {
                bookAppointment(PAY_MODE_CASH);
            } else
                getPayMode();


        });


        appointmentBinding.btnSelectOtherMember.setOnClickListener(view1 -> {
            Bundle mBundle = new Bundle();
            mBundle.putString("FROM", "BookAppointmentFragment");
            navController.navigate(R.id.action_bookAppointmentFragment_to_showMemberListFragment, mBundle);
        });
        appointmentBinding.btnSelf.setOnClickListener(view12 -> {

            bookingUser = getMainUser(requireActivity());

            appointmentBinding.setBookingUser(bookingUser);

            changeButtonBackground();
        });


       /* User user = new User();
        getResponse(DemoApi(user), new DemoAoiInterface() {
            @Override
            public void onSuccess(Object obj) {

            }

            @Override
            public void onFailed(String msg) {

            }
        });*/

    }

    private void getPayMode() {
        AppUtils.showRequestDialog(requireActivity());
        PayModeModel payModeModel = new PayModeModel(String.valueOf(doctorModel.getId()));
        ApiUtils.getPayMode(payModeModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                hideDialog();
                hideSoftKeyboard(requireActivity());
                List<PaymentMode> paymentModes = (List<PaymentMode>) o;
                if (null != paymentModes) {
                    for (PaymentMode mode : paymentModes)
                        Log.d(TAG, "onSuccess PayMode: " + mode.getPaymentMode());

                    showDialog(paymentModes);
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

    private void showDialog(List<PaymentMode> paymentModes) {


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
                    bookAppointment(PAY_MODE_CASH);
                } else if (selectedPaymentId == PAY_MODE_RAZOR_PAYY) {
                    bookAppointment(PAY_MODE_RAZOR_PAY);
                } else {
                    bookAppointment(PAY_MODE_PAY_U_MONEY);
                }
            }

        });

    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void changeButtonBackground() {
        try {
            appointmentBinding.btnSelf.setTextColor(bookingUser.getPrimaryStatus() == 1 ? getResources().getColorStateList(R.color.white) : getResources().getColorStateList(R.color.textColor));
            appointmentBinding.btnSelectOtherMember.setTextColor(bookingUser.getPrimaryStatus() == 1 ? getResources().getColorStateList(R.color.textColor) : getResources().getColorStateList(R.color.white));


            appointmentBinding.btnSelf.setBackgroundTintList(bookingUser.getPrimaryStatus() == 1 ? getResources().getColorStateList(R.color.YellowColo) : getResources().getColorStateList(R.color.greyLight));
            appointmentBinding.btnSelectOtherMember.setBackgroundTintList(bookingUser.getPrimaryStatus() == 1 ? getResources().getColorStateList(R.color.greyLight) : getResources().getColorStateList(R.color.YellowColo));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void bookAppointment(int payMode) {
        bookAppointment = new BookAppointment(requireActivity());


        //Setting Parameters
        bookAppointment.setUserMobileNo(user.getMobileNo());
        bookAppointment.setMemberId(bookingUser.getPrimaryStatus() == 1 ? String.valueOf(bookingUser.getId()) : String.valueOf(bookingUser.getMemberId()));
        bookAppointment.setPatientName(bookingUser.getName());
        bookAppointment.setServiceProviderDetailsId(String.valueOf(doctorModel.getId()));
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
        bookAppointment.setIsEraUser(String.valueOf(doctorModel.getIsEraUser()));
        bookAppointment.setDrFee(String.valueOf(doctorModel.getDrFee()));
        bookAppointment.setPaymentMode(payModeTitle);


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


    private void showCompleteProfileDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Incomplete Profile")
                .setCancelable(false)
                .setMessage("Your profile is incomplete,\ncomplete  profile before booking appointment")
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.cancel();
                    navController.navigate(R.id.action_bookAppointmentFragment_to_profileFragment);
                })

                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                    PatientDashboard.getInstance().onSupportNavigateUp();
                })
                .show();

    }

    public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentVH> {
        List<PaymentMode> items;

        public PaymentAdapter(List<PaymentMode> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public PaymentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LogoutOptionViewBinding paymentOption = LogoutOptionViewBinding.inflate(inflater, parent, false);
            return new PaymentVH(paymentOption);
        }

        @Override
        public void onBindViewHolder(@NonNull PaymentVH holder, int position) {
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