package com.digidoctor.android.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.VitalListAdapter;
import com.digidoctor.android.databinding.FragmentVitalChartBinding;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.VitalModel;
import com.digidoctor.android.model.VitalResponse;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILine;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.VITAL_ID;
import static com.digidoctor.android.utility.utils.VITAL_IMAGE;
import static com.digidoctor.android.utility.utils.VITAL_NAME;
import static com.digidoctor.android.utility.utils.getVitalMaxValue;
import static com.digidoctor.android.utility.utils.getVitalMinValue;


public class VitalChartFragment extends Fragment {
    private static final String TAG = "VitalChartFragment";

    FragmentVitalChartBinding chartBinding;
    NavController navController;
    PatientViewModel viewModel;

    String VitalId = null;
    String VitalName = null;
    VitalListAdapter adapter;
    int ImagePath;

    //graph Variables
    HIOptions options;
    HIPlotOptions plotOptions;
    HIExporting hiExporting;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        chartBinding = FragmentVitalChartBinding.inflate(getLayoutInflater());
        return chartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (getArguments() == null)
            return;

        VitalId = getArguments().getString(VITAL_ID);
        VitalName = getArguments().getString(VITAL_NAME);
        ImagePath = getArguments().getInt(VITAL_IMAGE);

        chartBinding.tvVitalName.setText(VitalName);
        chartBinding.ivVital.setImageResource(ImagePath);

        chartBinding.tvVitalMaxValue.setText(getVitalMaxValue(VitalId));
        chartBinding.tvVitalMinValue.setText(getVitalMinValue(VitalId));

        initGraph();
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        final VitalModel vitalModel = new VitalModel();
        User user = utils.getPrimaryUser(requireActivity());

        vitalModel.setMemberId(String.valueOf(user.getId()));
        vitalModel.setVitalId(VitalId);


        adapter = new VitalListAdapter();
        chartBinding.recVitalList.setAdapter(adapter);
        chartBinding.recVitalList.addItemDecoration(new
                DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));


        viewModel.getVitals(vitalModel,
                requireActivity()).
                observe(getViewLifecycleOwner(), vitalResponse -> {
                    Log.d(TAG, "onChanged: " + vitalResponse);
                    if (null == vitalResponse || vitalResponse.isEmpty()) {
                        Toast.makeText(requireActivity(), "No data found", Toast.LENGTH_SHORT).show();
                        PatientDashboard.getInstance().onSupportNavigateUp();
                        return;
                    }

                    adapter.submitList(vitalResponse);

                    setVitalChartData(vitalResponse);


                });


        chartBinding.menuItem1.setOnClickListener(view1 -> navController.navigate(R.id.action_vitalChartFragment_to_addVitalsFragment));
        chartBinding.menuItem2.setOnClickListener((View view12) -> {
                    // navController.navigate(R.id.action_vitalChartFragment_to_selectDeviceFragment);
                    Toast.makeText(requireActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
                }
        );
    }


    private void initGraph() {
        chartBinding.chartView.theme = "sand-signika";
        options = new HIOptions();
        final HIYAxis yAxis = new HIYAxis();
        yAxis.setTitle(new HITitle());
        yAxis.getTitle().setText(VitalName);
        options.setYAxis(new ArrayList<HIYAxis>() {{
            add(yAxis);
        }});

        HITitle hiTitle = new HITitle();
        hiTitle.setText("");
        options.setTitle(hiTitle);

        plotOptions = new HIPlotOptions();
        plotOptions.setLine(new HILine());


        plotOptions.getLine().setEnableMouseTracking(true);
        options.setPlotOptions(plotOptions);

        hiExporting = new HIExporting();
        hiExporting.setEnabled(false);
        options.setExporting(hiExporting);
        chartBinding.chartView.setOptions(options);
    }

    private void setVitalChartData(List<VitalResponse.VitalDateVise> vitalResponse) {
        final HIXAxis xAxis = new HIXAxis();
        String[] categoriesList = new String[vitalResponse.size()];
        for (int a = 0; a < vitalResponse.size(); a++) {
            categoriesList[a] = vitalResponse.get(a).getVitalDateForGraph();
        }
        xAxis.setCategories(new ArrayList<>(Arrays.asList(categoriesList)));
        options.setXAxis(new ArrayList<HIXAxis>() {{
            add(xAxis);
        }});

        if (VitalId.equalsIgnoreCase("-1")) {

            HISeries series1 = null, series2 = null;
            try {
                series1 = new HISeries();
                series1.setName(getString(R.string.systolic));
                Number[] sysData = new Number[vitalResponse.size()];

                for (int a = 0; a < vitalResponse.size(); a++) {
                    String sysValue = vitalResponse.get(a).getVitalDetails().get(0).getVitalValue();
                    sysData[a] = Double.parseDouble(sysValue);
                }
                series1.setData(new ArrayList<>(Arrays.asList(sysData)));

                series2 = new HISeries();
                series2.setName(getString(R.string.diastolic));

                Number[] diasData = new Number[vitalResponse.size()];
                for (int a = 0; a < vitalResponse.size(); a++) {
                    if (vitalResponse.get(a).getVitalDetails().size() > 1) {
                        String diasValue = vitalResponse.get(a).getVitalDetails().get(1).getVitalValue();
                        diasData[a] = Integer.parseInt(diasValue);
                    }

                }
                series2.setData(new ArrayList<>(Arrays.asList(diasData)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            options.setSeries(new ArrayList<>(Arrays.asList(series1, series2)));
        } else {
            HISeries series1 = new HISeries();
            series1.setName(VitalName);
            Number[] sysData = new Number[vitalResponse.size()];

            for (int a = 0; a < vitalResponse.size(); a++) {
                String sysValue = vitalResponse.get(a).getVitalDetails().get(0).getVitalValue();
                sysData[a] = Double.parseDouble(sysValue);
            }
            series1.setData(new ArrayList<>(Arrays.asList(sysData)));
            options.setSeries(new ArrayList<>(Arrays.asList(series1)));
        }
        chartBinding.chartView.redraw();
        chartBinding.progressBar4.setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}