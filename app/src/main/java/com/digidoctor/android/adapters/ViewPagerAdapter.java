package com.digidoctor.android.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.digidoctor.android.OneFragment;
import com.digidoctor.android.model.EraInvestigationData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.NewDashboardUtils.getJSONFromModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    List<EraInvestigationData.PatientTestResult> patientTestDate;

    public ViewPagerAdapter(FragmentManager manager, List<EraInvestigationData.PatientTestResult> patientTestDate) {
        super(manager);
        this.patientTestDate = patientTestDate;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putString("PatientTestResult", getJSONFromModel(patientTestDate.get(position)));
        Fragment frag = OneFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}