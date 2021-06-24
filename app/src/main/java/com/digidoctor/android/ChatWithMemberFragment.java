package com.digidoctor.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.FragmentChatWithMemberBinding;
import com.digidoctor.android.interfaces.EmergencyBookingListener;
import com.digidoctor.android.interfaces.OnSearchChange;
import com.digidoctor.android.model.SymptomModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.BookAppointment;
import com.digidoctor.android.utility.EmergencyBooking;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ChatWithMemberFragment extends Fragment implements OnSearchChange {
    private static final String TAG = "ChatWithMemberFragment";


    NavController navController;
    User user;
    FragmentChatWithMemberBinding binding;
    Chip chip;

    PatientViewModel viewModel;
    List<SymptomModel> selectedSymptoms = new ArrayList<>();
    List<SymptomModel> listSymptoms = new ArrayList<>();
    int amount = 1;
    EmergencyBooking emergencyBooking;
    String problemId;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatWithMemberBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        if (null == getArguments()) {
            Toast.makeText(requireContext(), getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        }

        amount = Integer.parseInt(getArguments().getString("fee"));
        emergencyBooking = new EmergencyBooking(requireActivity());

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        Gson gson = new Gson();
        user = gson.fromJson(getArguments().getString("user"), User.class);

        user.setAmount(amount);
        user.setSpecialityId(2);

        binding.setUser(user);


        binding.ivBack.setOnClickListener(v -> new AlertDialog.Builder(requireActivity()).setMessage("Want To go back ??")
                .setPositiveButton(R.string.yes, (dialog, which) -> navController.navigateUp()).setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss()).show());


        loadAllDepartmentData("");

        binding.chpDepartment.setOnClickListener(view1 -> {
            binding.chpDepartment.removeView(view1.getRootView());
            binding.chpDepartment.invalidate();
        });

        binding.chpDepartment.setOnCheckedChangeListener((ChipGroup.OnCheckedChangeListener) (group, checkedId) -> {
            Chip chip = (Chip) group.findViewById(group.getCheckedChipId());
            if (chip != null) {
                for (int a = 0; a < listSymptoms.size(); a++) {
                    if (listSymptoms.get(a).getProblemName().equalsIgnoreCase(chip.getText().toString()))
                        if (!selectedSymptoms.contains(listSymptoms.get(a))) {
                            addSymptoms(chip.getText().toString());
                            selectedSymptoms.add(listSymptoms.get(a));
                            Log.d(TAG, "added : " + listSymptoms.get(a).toString());
                        } else {
                            Toast.makeText(requireActivity(), "Symptom already Added !!", Toast.LENGTH_SHORT).show();
                        }

                }


            }

        });

        binding.tvChooseSymptoms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence != null && charSequence.length() > 3) {
                    loadAllDepartmentData(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.button16.setOnClickListener(v -> {
            if (selectedSymptoms.isEmpty())
                Toast.makeText(requireActivity(), "Add Some symptoms to submit !!", Toast.LENGTH_SHORT).show();

            else {
                StringBuilder problemIds = new StringBuilder();
                for (SymptomModel model : selectedSymptoms)
                    problemIds.append(String.valueOf(model.getProblemId())).append(",");
                AppUtils.showRequestDialog(requireActivity());
                Log.d(TAG, "submit request: ");
                emergencyBooking.setUser(user);
                emergencyBooking.setAmount(amount);
                emergencyBooking.setProblemName(problemIds.toString());
                emergencyBooking.setBookAppointment(initBooking());
                emergencyBooking.initEmergencyBooking(new EmergencyBookingListener() {
                    @Override
                    public void onEmergencyBookingSuccess(Object obj) {

                        Toasty.success(requireActivity(), "Booked successfully !!", Toast.LENGTH_SHORT, true).show();
                        navController.navigateUp();
                        AppUtils.hideDialog();

                    }

                    @Override
                    public void onEmergencyBookingFailed(String msg) {
                        AppUtils.hideDialog();
                        Toasty.error(requireActivity(), msg, Toast.LENGTH_SHORT, true).show();

                    }
                });
            }
        });


    }

    private BookAppointment initBooking() {
        BookAppointment appointment = new BookAppointment(requireActivity());
        appointment.setAmount(String.valueOf(amount));
        return appointment;
    }

    private void addSymptoms(String symptoms) {
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Chip chip = (Chip) inflater.inflate(R.layout.item_chip_symptoms, binding.chipGroupSymptoms, false);
        chip.setCheckable(true);
        chip.setText(symptoms);

        binding.chipGroupSymptoms.addView(chip);
        chip.setOnCloseIconClickListener(v -> {
            try {
                binding.chipGroupSymptoms.removeView(v);
                for (int a = 0; a < selectedSymptoms.size(); a++) {
                    if (selectedSymptoms.get(a).getProblemName().equalsIgnoreCase(symptoms)) {
                        Log.d(TAG, "removed !! " + selectedSymptoms.get(a));
                        selectedSymptoms.remove(selectedSymptoms.get(a));

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loadAllDepartmentData(String symptomName) {
        viewModel.getAppDepartmentData(symptomName).observe(getViewLifecycleOwner(), symptomModels -> {
            binding.chpDepartment.removeAllViews();
            listSymptoms.clear();
            for (SymptomModel model : symptomModels) {
                listSymptoms.add(model);
                LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                chip = (Chip) inflater.inflate(R.layout.item_chip, binding.chpDepartment, false);
                chip.setCheckable(true);
                chip.setText(model.getProblemName());
                binding.chpDepartment.addView(chip);
                chip.setOnCloseIconClickListener(v -> {
                    try {
                        binding.chpDepartment.removeView(v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }


    @Override
    public void onSearchChange(String text) {
        Log.d(TAG, "onSearchChange: " + text);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + data.getData());
    }
}