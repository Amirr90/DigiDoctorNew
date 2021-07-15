package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.digidoctor.android.adapters.CalendarAdapter;
import com.digidoctor.android.adapters.labadapter.Address;
import com.digidoctor.android.adapters.labadapter.LabAddressAdapter;
import com.digidoctor.android.adapters.labadapter.LabTimeSlotAdapter;
import com.digidoctor.android.databinding.FragmentTimeSlotForLabBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.CalendarModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.labmodel.LabSlotModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.NewDashboardUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.getUserForBooking;


public class TimeSlotForLabFragment extends Fragment {
    private static final String TAG = "TimeSlotForLabFragment";

    FragmentTimeSlotForLabBinding binding;
    NavController navController;
    List<Address> addressList = new ArrayList<>();
    LabAddressAdapter adapter;
    PatientViewModel viewModel;
    List<User> userList = new ArrayList<>();
    String ADD_MEMBER = "Add New Member";
    String memberId;

    CalendarAdapter calendarAdapter;
    List<LabSlotModel> timeSlotsModelList = new ArrayList<>();
    String date, pathologyId;

    Address address;
    LabTimeSlotAdapter slotAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimeSlotForLabBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        memberId = String.valueOf(getUserForBooking(requireActivity()).getMemberId());
        pathologyId = "1";

        addressList = new ArrayList<>();
        adapter = new LabAddressAdapter(addressList);
        binding.pagerAddress.setAdapter(adapter);


        binding.pagerAddress.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (null != addressList)
                    address = addressList.get(position);
            }
        });


        //navigate to add address screen
        binding.cardView7.setOnClickListener(v -> navController.navigate(R.id.action_timeSlotForLabFragment_to_addressFragment));

    }

    private void setCalenderRec() {

        User user = getUserForBooking(requireActivity());
        slotAdapter = new LabTimeSlotAdapter(timeSlotsModelList, o -> {
            Log.d(TAG, "onItemClicked: " + o);
            Bundle bundle = new Bundle();
            bundle.putString("date", date);
            bundle.putString("memberId", memberId);
            bundle.putString("time", (String) o);
            bundle.putString("addressId", null == address ? "0" : address.getAddressId());
            bundle.putString("name", null == address ? user.getName() : address.getName());
            navController.navigate(R.id.action_timeSlotForLabFragment_to_fragmentReviewOrderLab, bundle);
        });
        binding.timingRec.setAdapter(slotAdapter);


        calendarAdapter = new CalendarAdapter(getNextWeekDays(), (CalendarModel calendarModel, int pos) -> {
            getLabTimeSlots(calendarModel.getDateSend());
            date = calendarModel.getDateSend();
        });
        binding.calRec.setAdapter(calendarAdapter);

        if (calendarAdapter.getItem() != null) {
            date = calendarAdapter.getItem().getDateSend();
            Log.d(TAG, "setCalenderRec: " + date);
            setAllMembersToSpinner();

            getAllAddress(date);


        }
    }

    private void getLabTimeSlots(String dateSend) {
        AppUtils.showRequestDialog(requireActivity());
        if (null != address && null != address.getPincode()) {


            String pinCode = address.getPincode();
            if (pinCode.length() < 6) {
                Toast.makeText(App.context, "There is no slot as per your pincode.Kindly check your pincode", Toast.LENGTH_SHORT).show();
                return;
            } else {
                String date = AppUtils.parseDate(dateSend, "yyyy-MM-dd", "yyyy/MM/dd");
                LabSlotModel labSlotModel = new LabSlotModel(pinCode, date, pathologyId);
                ApiUtils.getLabTimeSlots(labSlotModel, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(List<?> o) {
                        AppUtils.hideDialog();
                        timeSlotsModelList.clear();
                        List<LabSlotModel> labSlotModels = (List<LabSlotModel>) o;
                        if (null != labSlotModels && !labSlotModels.isEmpty()) {
                            timeSlotsModelList.addAll(labSlotModels);

                        } else {
                            // Toasty.error(App.context, getString(R.string.slot_not_available) + " for pinCode " + pinCode, Toast.LENGTH_LONG, true).show();
                        }

                        slotAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(String s) {
                        AppUtils.hideDialog();
                        timeSlotsModelList.clear();
                        AppUtils.hideDialog();
                        slotAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        AppUtils.hideDialog();
                        timeSlotsModelList.clear();
                        AppUtils.hideDialog();
                        Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());
                        Toast.makeText(App.context, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        slotAdapter.notifyDataSetChanged();


                    }
                });
            }
        } else {
            navController.navigate(R.id.addressFragment);

        }

    }


    private void setAllMembersToSpinner() {
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);
        viewModel.getMemberList(requireActivity()).observe(getViewLifecycleOwner(), users -> {
            if (null != users) {

                userList.clear();
                userList = users;
                List<String> dataset = new ArrayList<>();

                //Adding The name of Members to spinner
                for (User user : userList)
                    dataset.add(user.getName());

                dataset.add(ADD_MEMBER);
                binding.spinnerSelectMember.attachDataSource(dataset);
            }
        });

        binding.spinnerSelectMember.setOnSpinnerItemSelectedListener((parent, view, position, id) -> {
            String item = (String) parent.getItemAtPosition(position);
            if (item.equalsIgnoreCase(ADD_MEMBER)) {
                //navigate to addNew member!!
                navController.navigate(R.id.action_timeSlotForLabFragment_to_addMemberFragment);
            } else {
                memberId = String.valueOf(userList.get(position).getMemberId());
                Log.d(TAG, "setAllMembersToSpinner: " + memberId);
            }
        });
    }

    public void getAllAddress(String date) {
        ApiUtils.getAllAddress(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                binding.progressBarViewPager.setVisibility(View.GONE);
                AppUtils.hideDialog();
                List<Address> models = (List<Address>) o;


                if (null != models) {
                    for (Address add : models)
                        if (add.getIsDefault())
                            address = add;

                    binding.clNoAddress.setVisibility(models.isEmpty() ? View.VISIBLE : View.GONE);
                    binding.pagerAddress.setVisibility(models.isEmpty() ? View.GONE : View.VISIBLE);

                    if (models.isEmpty()) {
                        Toast.makeText(requireActivity(), "Address not added !!", Toast.LENGTH_SHORT).show();
                    } else {
                        addressList.clear();
                        addressList.addAll(models);
                        adapter.notifyDataSetChanged();

                        //get lab Slots
                        getLabTimeSlots(date);
                    }
                } else {
                    binding.clNoAddress.setVisibility(models == null ? View.VISIBLE : View.GONE);
                    binding.pagerAddress.setVisibility(View.GONE);
                    Toast.makeText(requireActivity(), "Address not found, try again !!", Toast.LENGTH_SHORT).show();
                }


            }


            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), "Something Wrong", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        setCalenderRec();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    private List<CalendarModel> getNextWeekDays() {
        List<CalendarModel> calendarModelList = new ArrayList<>();
        ArrayList<HashMap<String, String>> getNextWeekDays = NewDashboardUtils.getNextWeekDays();
        for (int a = 0; a < getNextWeekDays.size(); a++) {

            calendarModelList.add(new CalendarModel(
                    getNextWeekDays.get(a).get("date"),
                    getNextWeekDays.get(a).get("day"),
                    getNextWeekDays.get(a).get("dateSend"),
                    true));

        }

        return calendarModelList;
    }
}