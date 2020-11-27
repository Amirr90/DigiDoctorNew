package com.digidoctor.android.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.VitalTypeAdapter;
import com.digidoctor.android.databinding.FragmentChooseVitalHistoryTypeBinding;
import com.digidoctor.android.databinding.SelectVitalVategoryListBinding;
import com.digidoctor.android.databinding.SelectVitalVategoryListViewBinding;
import com.digidoctor.android.databinding.VitalHistoryTypeViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.SelectVitalCategoryModel;
import com.digidoctor.android.model.VitalTypeModel;

import java.util.ArrayList;
import java.util.List;


public class ChooseVitalHistoryTypeFragment extends Fragment implements AdapterInterface {
    FragmentChooseVitalHistoryTypeBinding vitalHistoryTypeBinding;
    NavController navController;
    VitalTypeAdapter typeAdapter;
    List<VitalTypeModel> vitalTypeModelList;

    AlertDialog optionDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
    }

    private void loadData() {
        vitalTypeModelList.add(new VitalTypeModel(
                getString(R.string.trends),
                getString(R.string.trends_description),
                R.drawable.trends_icon));


        vitalTypeModelList.add(new VitalTypeModel(
                getString(R.string.graph),
                getString(R.string.graph_description),
                R.drawable.graphs_icon));


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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
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

        binding.btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionDialog.dismiss();
            }
        });
        optionDialog.show();

    }

    private List<SelectVitalCategoryModel> getSelectTypData() {
        List<SelectVitalCategoryModel> selectTypeList = new ArrayList<>();
        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure_chart),
                R.drawable.bp_chart_icon,
                false, "#ffffff"));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure_chart),
                R.drawable.bp_chart_icon,
                false, "#ffffff"));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure_chart),
                R.drawable.bp_chart_icon,
                false, "#ffffff"));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure_chart),
                R.drawable.bp_chart_icon,
                false, "#ffffff"));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure_chart),
                R.drawable.bp_chart_icon,
                false, "#ffffff"));


        selectTypeList.add(new SelectVitalCategoryModel(
                getString(R.string.blood_pressure_chart),
                R.drawable.bp_chart_icon,
                false, "#ffffff"));


        return selectTypeList;
    }

    @Override
    public void onItemClicked(Object o) {
        showSelectVitalCategoryList();
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
        public void onBindViewHolder(@NonNull CategoryVH holder, int position) {

            try {
                SelectVitalCategoryModel model = selectTypeList.get(position);
                holder.listViewBinding.setModel(model);

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