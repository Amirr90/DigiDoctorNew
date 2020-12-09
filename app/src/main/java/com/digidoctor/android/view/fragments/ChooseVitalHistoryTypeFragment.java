package com.digidoctor.android.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.VitalTypeAdapter;
import com.digidoctor.android.databinding.FragmentChooseVitalHistoryTypeBinding;
import com.digidoctor.android.databinding.SelectVitalVategoryListBinding;
import com.digidoctor.android.databinding.SelectVitalVategoryListViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.SelectVitalCategoryModel;
import com.digidoctor.android.model.VitalTypeModel;
import com.digidoctor.android.view.activity.PatientDashboard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.BLOOD_SUGAR_ID;
import static com.digidoctor.android.utility.utils.BP_ID;
import static com.digidoctor.android.utility.utils.PULSE_RATE_ID;
import static com.digidoctor.android.utility.utils.RESPIRATORY_ID;
import static com.digidoctor.android.utility.utils.SPO2_ID;
import static com.digidoctor.android.utility.utils.TEMPERATURE_ID;
import static com.digidoctor.android.utility.utils.VITAL_ID;
import static com.digidoctor.android.utility.utils.VITAL_IMAGE;
import static com.digidoctor.android.utility.utils.VITAL_NAME;


public class ChooseVitalHistoryTypeFragment extends Fragment implements AdapterInterface {
    FragmentChooseVitalHistoryTypeBinding vitalHistoryTypeBinding;
    NavController navController;
    VitalTypeAdapter typeAdapter;
    List<VitalTypeModel> vitalTypeModelList;

    AlertDialog optionDialog;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vitalHistoryTypeBinding = FragmentChooseVitalHistoryTypeBinding.inflate(getLayoutInflater());
        return vitalHistoryTypeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);


        vitalTypeModelList = new ArrayList<>();
        typeAdapter = new VitalTypeAdapter(vitalTypeModelList, this);
        vitalHistoryTypeBinding.recVitalType.setAdapter(typeAdapter);
        loadData();

        vitalHistoryTypeBinding.imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PatientDashboard.getInstance().onSupportNavigateUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData() {
        vitalTypeModelList.add(new VitalTypeModel(
                getString(R.string.trends),
                getString(R.string.trends_description),
                R.drawable.trends_icon));

        vitalTypeModelList.add(new VitalTypeModel(
                getString(R.string.live),
                getString(R.string.live_description),
                R.drawable.live_icon));


        vitalTypeModelList.add(new VitalTypeModel(
                getString(R.string.customize),
                getString(R.string.customize_description),
                R.drawable.customize_icon));

        typeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void showSelectVitalCategoryList() {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.select_vital_vategory_list, null, false);

        final SelectVitalVategoryListBinding binding = SelectVitalVategoryListBinding.bind(formElementsView);


        List<SelectVitalCategoryModel> selectTypeList = getSelectTypData();
        binding.recSelectType.setAdapter(new SelectVitalCategoryListAdapter(selectTypeList));

        // the alert dialog
        optionDialog = new AlertDialog.Builder(requireActivity()).create();
        optionDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        optionDialog.getWindow().setWindowAnimations(R.style.DialogNoAnimation);

        optionDialog.setView(formElementsView);

        optionDialog.show();

    }

    private List<SelectVitalCategoryModel> getSelectTypData() {
        List<SelectVitalCategoryModel> selectTypeList = new ArrayList<>();
        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure),
                R.drawable.bp_chart_icon,
                false, "#FFEDD4", BP_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.pulse_rate),
                R.drawable.pulse_rate_icon,
                false, "#EAE2FF", PULSE_RATE_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.random_blood_sugar),
                R.drawable.blood_sugar_icon,
                false, "#E5FFEF", BLOOD_SUGAR_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.spo2),
                R.drawable.spo_icon,
                false, "#E2EEFF", SPO2_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.respiratory_rate),
                R.drawable.respiratory_icon,
                false, "#E3D9FF", RESPIRATORY_ID));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.temperature),
                R.drawable.thermometer,
                false, "#FFE4E8", TEMPERATURE_ID));


        return selectTypeList;
    }

    @Override
    public void onItemClicked(Object o) {
        VitalTypeModel model = (VitalTypeModel) o;
        if (model.getTitle().equalsIgnoreCase("Customize"))
            navController.navigate(R.id.action_chooseVitalHistoryTypeFragment_to_addVitalsFragment);

        else if (model.getTitle().equalsIgnoreCase("Live"))
            navController.navigate(R.id.action_chooseVitalHistoryTypeFragment_to_selectDeviceFragment);
        else showSelectVitalCategoryList();
    }

    private class SelectVitalCategoryListAdapter extends RecyclerView.Adapter<SelectVitalCategoryListAdapter.CategoryVH> {
        List<SelectVitalCategoryModel> selectTypeList;

        public SelectVitalCategoryListAdapter(List<SelectVitalCategoryModel> selectTypeList) {
            this.selectTypeList = selectTypeList;
        }

        @NonNull
        @Override
        public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            SelectVitalVategoryListViewBinding listViewBinding = SelectVitalVategoryListViewBinding.inflate(inflater, parent, false);
            return new CategoryVH(listViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryVH holder, final int position) {

            try {
                SelectVitalCategoryModel model = selectTypeList.get(position);
                holder.listViewBinding.setModel(model);
                holder.listViewBinding.view.setVisibility(selectTypeList.size() == (position + 1) ? View.GONE : View.VISIBLE);
                holder.listViewBinding.constraintsMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(VITAL_ID, selectTypeList.get(position).getVitalId());
                        bundle.putString(VITAL_NAME, selectTypeList.get(position).getTitle());
                        bundle.putInt(VITAL_IMAGE, selectTypeList.get(position).getImage());
                        optionDialog.dismiss();
                        navController.navigate(R.id.action_chooseVitalHistoryTypeFragment_to_vitalChartFragment, bundle);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return selectTypeList.size();
        }

        public class CategoryVH extends RecyclerView.ViewHolder {
            SelectVitalVategoryListViewBinding listViewBinding;

            public CategoryVH(SelectVitalVategoryListViewBinding listViewBinding) {
                super(listViewBinding.getRoot());
                this.listViewBinding = listViewBinding;
            }
        }
    }



}