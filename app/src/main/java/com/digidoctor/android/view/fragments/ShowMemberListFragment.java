package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.MemberAdapter;
import com.digidoctor.android.databinding.FragmentShowMemberListBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import static com.digidoctor.android.utility.utils.BOOKING_USER;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.USER;
import static com.digidoctor.android.utility.utils.fadeIn;
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static com.digidoctor.android.utility.utils.setUserForBooking;


public class ShowMemberListFragment extends Fragment implements AdapterInterface {
    private static final String TAG = "ShowMemberListFragment";

    FragmentShowMemberListBinding showMemberListBinding;
    NavController navController;
    MemberAdapter memberAdapter;
    PatientViewModel viewModel;

    String FROM;


    public static ShowMemberListFragment instance;

    public static ShowMemberListFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showMemberListBinding = FragmentShowMemberListBinding.inflate(getLayoutInflater());
        instance = this;
        return showMemberListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (null != getArguments()) {
            FROM = getArguments().getString("FROM");
        }
        navController = Navigation.findNavController(view);

        showMemberListBinding.getRoot().setAnimation(fadeIn(requireActivity()));

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        memberAdapter = new MemberAdapter(this, requireActivity());

        showMemberListBinding.recMemberList.setAdapter(memberAdapter);

        getMembersList();
        showMemberListBinding.btnAddOtherPatients.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("from", "ShowMemberListFragment");
            navController.navigate(R.id.action_showMemberListFragment_to_addMemberFragment, bundle);
        });


    }

    @Override
    public void onItemClicked(Object o) {
        try {
            User user = (User) o;
            if (null != FROM && FROM.equalsIgnoreCase("DashboardFragment")) {

                utils.savePrimaryUserData(USER, requireActivity(), user);

                utils.setUserForBooking(BOOKING_USER, requireActivity(), user);

                utils.setString(MOBILE_NUMBER, user.getMobileNo(), requireActivity());

                PatientDashboard.getInstance().updateUser();
                PatientDashboard.getInstance().onSupportNavigateUp();

            } else {
                setUserForBooking(BOOKING_USER, requireActivity(), user);
                PatientDashboard.getInstance().onSupportNavigateUp();
                Log.d(TAG, "userForBooking: " + getUserForBooking(requireActivity()).toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMembersList() {
        viewModel.getMemberList(requireActivity()).observe(getViewLifecycleOwner(), users -> {
            if (null != users) {
                memberAdapter.submitList(users);
            }
        });
    }

    public void removeMemberItem(int position) {
        memberAdapter.notifyItemRemoved(position);
    }
}