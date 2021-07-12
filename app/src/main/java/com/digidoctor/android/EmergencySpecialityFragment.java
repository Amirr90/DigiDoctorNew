package com.digidoctor.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.EmergencySpecialityViewBinding;
import com.digidoctor.android.databinding.FragmentEmergencySpecialityBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.SpecialityModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import static com.digidoctor.android.utility.NewDashboardUtils.getJSONFromModel;


public class EmergencySpecialityFragment extends Fragment implements AdapterInterface {

    private static final String TAG = "EmergencySpecialityFrag";
    FragmentEmergencySpecialityBinding binding;
    PatientViewModel viewModel;
    EmcDepartmentAdapter emcDepartmentadapter;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmergencySpecialityBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);
        initRec();

    }

    private void initRec() {
        emcDepartmentadapter = new EmcDepartmentAdapter(this);
        binding.recEmcDep.setAdapter(emcDepartmentadapter);

        loadDepartmentData();

    }

    private void loadDepartmentData() {
        viewModel.getEmcDepartmentData().observe(getViewLifecycleOwner(), specialityModels -> {
            binding.progressBar8.setVisibility(View.GONE);
            emcDepartmentadapter.submitList(specialityModels);
        });
    }

    @Override
    public void onItemClicked(Object o) {
        SpecialityModel specialityModel = (SpecialityModel) o;
        Bundle bundle = new Bundle();
        bundle.putString("emcModel", getJSONFromModel(specialityModel));
        navController.navigate(R.id.action_emergencySpecialityFragment_to_botChatFragment, bundle);
    }

    private class EmcDepartmentAdapter extends ListAdapter<SpecialityModel, EmcDepartmentAdapter.AppointmentVH> {
        AdapterInterface adapterInterface;

        protected EmcDepartmentAdapter(AdapterInterface adapterInterface) {
            super(SpecialityModel.itemCallback);
            this.adapterInterface = adapterInterface;
        }

        @NonNull
        @Override
        public AppointmentVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            EmergencySpecialityViewBinding specialityViewBinding = EmergencySpecialityViewBinding.inflate(layoutInflater, parent, false);
            specialityViewBinding.setAdapterInterface(adapterInterface);
            return new AppointmentVH(specialityViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull AppointmentVH holder, int position) {
            holder.specialityViewBinding.setDepartment(getItem(position));

        }

        public class AppointmentVH extends RecyclerView.ViewHolder {
            EmergencySpecialityViewBinding specialityViewBinding;

            public AppointmentVH(@NonNull EmergencySpecialityViewBinding specialityViewBinding) {
                super(specialityViewBinding.getRoot());
                this.specialityViewBinding = specialityViewBinding;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        AppUtils.showToolbar(requireActivity());
    }
}