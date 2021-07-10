package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.digidoctor.android.R;


public class CallFragment extends Fragment {

    public static CallFragment newInstance() {
        return new CallFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_call, container, false);
    }
}