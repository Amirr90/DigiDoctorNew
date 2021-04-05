package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.FragmentSelectDeviceBinding;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.view.activity.DeviceScanActivity;
import com.digidoctor.android.view.activity.MedCheckDeviceGetData;
import com.digidoctor.android.view.activity.ViaOximeterScanActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.digidoctor.android.utility.utils.BLE_DEVICE_TYPE;

public class SelectDeviceFragment extends Fragment implements OnClickListener {

    FragmentSelectDeviceBinding deviceBinding;
    NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        deviceBinding = FragmentSelectDeviceBinding.inflate(getLayoutInflater());
        return deviceBinding.getRoot();
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        // deviceBinding.setListener(this);

        deviceBinding.linearLayout8.setOnClickListener(v -> startActivity(new Intent(requireActivity(), MedCheckDeviceGetData.class)));
        deviceBinding.linearLayout20.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(), ViaOximeterScanActivity.class).putExtra("show", "0")));
        deviceBinding.linearLayout9.setOnClickListener(v ->
                startActivity(new Intent(requireActivity(), DeviceScanActivity.class)
                        .putExtra("show", "0")));
        deviceBinding.linearLayout10.setOnClickListener(controlD-> startActivity(new Intent(requireActivity(), DeviceScanActivity.class).putExtra("show", "0")));

    }

    @Override
    public void onItemClick(Object object) {
        Bundle bundle = new Bundle();
        bundle.putString(BLE_DEVICE_TYPE, (String) object);
        // navController.navigate(R.id.action_selectDeviceFragment_to_searchBluetoothDeviceFragment, bundle);
    }
}