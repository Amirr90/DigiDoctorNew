package com.digidoctor.android.view.fragments.digiDoctorFragments;

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

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentSelectDeviceBinding;
import com.digidoctor.android.interfaces.OnClickListener;

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

        deviceBinding.setListener(this);
    }

    @Override
    public void onItemClick(Object object) {
        Bundle bundle = new Bundle();
        bundle.putString(BLE_DEVICE_TYPE, (String) object);
        navController.navigate(R.id.action_selectDeviceFragment_to_searchBluetoothDeviceFragment, bundle);
    }
}