package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.AppointmentAdapter;
import com.digidoctor.android.databinding.FragmentAppointmentBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.digidoctor.android.utility.utils.getJSONFromModel;


public class AppointmentsFragment extends Fragment implements OnClickListener {

    FragmentAppointmentBinding appointmentBinding;
    NavController navController;
    AppointmentAdapter adapter;
    PatientViewModel viewModel;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appointmentBinding = FragmentAppointmentBinding.inflate(getLayoutInflater());
        return appointmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        adapter = new AppointmentAdapter(this);

        appointmentBinding.recAppointment.setAdapter(adapter);

        AppUtils.showRequestDialog(requireActivity());
        User user = new User();
        user.setMemberId(utils.getPrimaryUser(requireActivity()).getId());
        viewModel.getAppointmentList(user).observe(getViewLifecycleOwner(), appointmentModels -> {
            adapter.submitList(appointmentModels);
            AppUtils.hideDialog();
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onItemClick(Object object) {

        AppointmentModel appointmentModel = (AppointmentModel) object;
        String model = getJSONFromModel(appointmentModel);
        Bundle bundle = new Bundle();
        bundle.putString("model", model);
        navController.navigate(R.id.action_appointmentsFragment_to_appointmentDetailFragment, bundle);
    }
}