package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.MemberAdapter;
import com.digidoctor.android.databinding.FragmentShowMemberListBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.digidoctor.android.utility.utils.BOOKING_USER;
import static com.digidoctor.android.utility.utils.setUserForBooking;


public class ShowMemberListFragment extends Fragment implements AdapterInterface {

    FragmentShowMemberListBinding showMemberListBinding;
    NavController navController;
    MemberAdapter memberAdapter;
    PatientViewModel viewModel;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showMemberListBinding = FragmentShowMemberListBinding.inflate(getLayoutInflater());
        return showMemberListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        memberAdapter = new MemberAdapter(this);

        showMemberListBinding.recMemberList.setAdapter(memberAdapter);

        viewModel.getMemberList(requireActivity()).observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (null != users) {
                    memberAdapter.submitList(users);
                }
            }
        });
        showMemberListBinding.btnAddOtherPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "ShowMemberListFragment");
                navController.navigate(R.id.action_showMemberListFragment_to_addMemberFragment, bundle);
            }
        });

    }

    @Override
    public void onItemClicked(Object o) {
        try {
            User user = (User) o;
            setUserForBooking(BOOKING_USER, requireActivity(), user);
            PatientDashboard.getInstance().onSupportNavigateUp();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}