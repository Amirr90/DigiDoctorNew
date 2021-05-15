package com.digidoctor.android;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentMedicineReminderListBinding;
import com.digidoctor.android.databinding.MedicineReminderViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.patientModel.MedicineReminderModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


public class MedicineReminderListFragment extends Fragment {

    FragmentMedicineReminderListBinding binding;
    NavController navController;

    MedicineReminderAdapter reminderAdapter;
    List<MedicineReminderModel> reminderModels;
    public static MedicineReminderListFragment instance;

    public static MedicineReminderListFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        instance = this;
        binding = FragmentMedicineReminderListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        reminderModels = new ArrayList<>();
        reminderAdapter = new MedicineReminderAdapter(reminderModels);
        binding.recMedicineReminder.setAdapter(reminderAdapter);

        getData();

    }

    public void getData() {
        ApiUtils.MedicineReminderList(requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<MedicineReminderModel> medicineReminderModels = (List<MedicineReminderModel>) o;
                if (null != medicineReminderModels) {
                    reminderModels.clear();
                    reminderModels.addAll(medicineReminderModels);
                    binding.recMedicineReminder.setVisibility(View.VISIBLE);
                    binding.textView219.setVisibility(View.GONE);
                } else {
                    binding.recMedicineReminder.setVisibility(View.GONE);
                    binding.textView219.setVisibility(View.VISIBLE);
                }
                reminderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {
                binding.recMedicineReminder.setVisibility(View.GONE);
                binding.textView219.setVisibility(View.VISIBLE);
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private class MedicineReminderAdapter extends RecyclerView.Adapter<MedicineReminderAdapter.MedicineVH> {
        List<MedicineReminderModel> medicineReminderModels;

        public MedicineReminderAdapter(List<MedicineReminderModel> medicineReminderModels) {
            this.medicineReminderModels = medicineReminderModels;
        }

        @NonNull
        @Override
        public MedicineReminderAdapter.MedicineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            MedicineReminderViewBinding binding = MedicineReminderViewBinding.inflate(inflater, parent, false);
            return new MedicineVH(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull MedicineReminderAdapter.MedicineVH holder, int position) {
            holder.binding.setMedicineReminder(medicineReminderModels.get(position));

            holder.binding.btnSetReminder.setOnClickListener(v -> setReminder(medicineReminderModels.get(position), 1));
            holder.binding.btnCancelReminder.setOnClickListener(v -> setReminder(medicineReminderModels.get(position), 0));
        }

        @Override
        public int getItemCount() {
            return null == medicineReminderModels ? 0 : medicineReminderModels.size();
        }

        public class MedicineVH extends RecyclerView.ViewHolder {
            MedicineReminderViewBinding binding;

            public MedicineVH(@NonNull MedicineReminderViewBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    private void setReminder(MedicineReminderModel medicineReminderModel, int reminder) {
        if (reminder == 0) {
            new AlertDialog.Builder(requireActivity()).setMessage("Remove Medicine reminder ??")
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                        dialog.dismiss();
                        updateAlarm(medicineReminderModel.getId().toString());
                    }).setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss()).show();

        } else {
            Bundle bundle = new Bundle();
            bundle.putString("id", medicineReminderModel.getId().toString());
            bundle.putString("medName", medicineReminderModel.getMedicineName() + "  " + medicineReminderModel.getStrength() + medicineReminderModel.getUnitName());
            bundle.putInt("duration", medicineReminderModel.getDurationInDays());
            bundle.putString("frequency", medicineReminderModel.getFrequencyName());
            navController.navigate(R.id.action_medicineReminderListFragment_to_addMedicineReminderBottomFragment, bundle);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

    }

    private void updateAlarm(String medID) {
        AppUtils.showRequestDialog(requireActivity());
        AddMedicineReminderBottomFragment.UpdateAlarmModel alarmModel = new AddMedicineReminderBottomFragment.UpdateAlarmModel(utils.getUserForBooking(requireActivity()).getMemberId(), Integer.parseInt(medID), 0);
        ApiUtils.updateAlarm(alarmModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                getData();
                Toasty.success(requireActivity(), "Success!", Toast.LENGTH_SHORT, true).show();
                AppUtils.hideDialog();
            }

            @Override
            public void onError(String s) {
                Toasty.error(requireActivity(), s, Toast.LENGTH_SHORT, true).show();
                AppUtils.hideDialog();
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }
}