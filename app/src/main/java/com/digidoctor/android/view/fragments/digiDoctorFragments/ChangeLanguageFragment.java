package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentChangeLanguageBinding;
import com.digidoctor.android.databinding.LanguageViewBinding;
import com.digidoctor.android.model.LanguageModel;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.AppUtils.getLanguageList;
import static com.digidoctor.android.utility.AppUtils.getLanguageModel;
import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.setAppLocale;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.fadeIn;
import static com.digidoctor.android.utility.utils.setString;


public class ChangeLanguageFragment extends Fragment {

    public static final String LANGUAGE = "language";
    FragmentChangeLanguageBinding changeLanguageBinding;
    LanguageAdapter adapter;
    NavController navController;


    int selectedLanguageId = -1;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        changeLanguageBinding = FragmentChangeLanguageBinding.inflate(getLayoutInflater());
        selectedLanguageId = Integer.parseInt(utils.getString(LANGUAGE, requireActivity()));
        return changeLanguageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        changeLanguageBinding.getRoot().setAnimation(fadeIn(requireActivity()));

        adapter = new LanguageAdapter(getLanguageList());

        changeLanguageBinding.languageRec.setAdapter(adapter);
        RecyclerView.ItemAnimator animator = changeLanguageBinding.languageRec.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        adapter.notifyDataSetChanged();

        changeLanguageBinding.btnDone.setOnClickListener(view1 -> updateLanguage());


    }


    private class LanguageAdapter extends RecyclerView.Adapter<LanguageVH> {
        List<LanguageModel> languageModels;

        public LanguageAdapter(List<LanguageModel> languageModels) {
            this.languageModels = languageModels;
        }

        @NonNull
        @Override
        public LanguageVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LanguageViewBinding languageViewBinding = LanguageViewBinding.inflate(inflater, parent, false);
            return new LanguageVH(languageViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull LanguageVH holder, int position) {
            LanguageModel languageModel = languageModels.get(position);
            holder.languageViewBinding.setLan(languageModel);


            holder.languageViewBinding.checkBox3.setEnabled(position == 1);
            holder.languageViewBinding.checkBox3.setOnClickListener(view -> {
                selectedLanguageId = position;
                notifyDataSetChanged();
                if (!holder.languageViewBinding.checkBox3.isEnabled())
                    Toast.makeText(requireActivity(), getString(R.string.not_available), Toast.LENGTH_SHORT).show();
            });

            holder.languageViewBinding.checkBox3.setChecked(selectedLanguageId == position);

        }

        @Override
        public int getItemCount() {
            return languageModels.size();
        }
    }

    private void updateLanguage() {
        setString(LANGUAGE, String.valueOf(selectedLanguageId), PatientDashboard.getInstance());
        showRequestDialog(requireActivity());
        new Handler().postDelayed(() -> {
            setAppLocale(getLanguageModel(selectedLanguageId).getLocaleCode(), PatientDashboard.getInstance());
            hideDialog();
            PatientDashboard.getInstance().onSupportNavigateUp();
        }, 500);
    }

    private static class LanguageVH extends RecyclerView.ViewHolder {
        LanguageViewBinding languageViewBinding;

        public LanguageVH(@NonNull LanguageViewBinding languageViewBinding) {
            super(languageViewBinding.getRoot());
            this.languageViewBinding = languageViewBinding;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();

    }
}