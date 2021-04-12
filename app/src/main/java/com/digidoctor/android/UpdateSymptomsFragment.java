package com.digidoctor.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.digidoctor.android.databinding.FragmentUpdateSymptomsBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.patientModel.SymptomsNotificationModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.fadeIn;
import static com.digidoctor.android.utility.utils.getUserForBooking;
import static tvi.webrtc.ContextUtils.getApplicationContext;


public class UpdateSymptomsFragment extends Fragment {


    FragmentUpdateSymptomsBinding notificationBinding;

    NavController navController;

    List<SymptomsNotificationModel> symptomsNotificationModelList;
    int ProblemPosition;
    int counter = 1;
    List<String> problemsContain;
    boolean submitStatus = false;


    String memberId;
    PatientViewModel viewModel;

    public static UpdateSymptomsFragment instance;

    public UpdateSymptomsFragment() {
        ProblemPosition = 0;
    }

    public static UpdateSymptomsFragment getInstance() {
        return instance;
    }

    List<String> memberName = new ArrayList<>();
    List<String> memberIds = new ArrayList<>();

    ProgressDialog dialog;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notificationBinding = FragmentUpdateSymptomsBinding.inflate(getLayoutInflater());
        return notificationBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        dialog = new ProgressDialog(requireActivity());
        memberId = String.valueOf(getUserForBooking(requireActivity()).getMemberId());

        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        getNotificationData(memberId);


        setSpinnerData();

        notificationBinding.btnNext.setOnClickListener(view12 -> {
            if (submitStatus) {
                // TODO: 12-10-2020 upload Problem feedback data
                try {
                    if (symptomsNotificationModelList.isEmpty()) {
                        Toast.makeText(requireActivity(), R.string.problem_not_selected, Toast.LENGTH_SHORT).show();

                    } else {
                        StringBuilder builder = new StringBuilder();
                        for (int a = 0; a < problemsContain.size(); a++) {
                            builder.append(problemsContain.get(a) + ",");
                        }

                        AppUtils.showRequestDialog(requireActivity());
                        ApiUtils.submitSymptomsRes(builder.toString(), memberId, new ApiCallbackInterface() {
                            @Override
                            public void onSuccess(List<?> o) {

                                hideDialog();
                                Toast.makeText(requireActivity(), "Submitted successfully !!", Toast.LENGTH_SHORT).show();
                                navController.navigateUp();
                            }

                            @Override
                            public void onError(String s) {
                                hideDialog();
                                Toast.makeText(requireActivity(), "our servers are busy, try again !!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(Throwable throwable) {
                                hideDialog();
                                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    counter = counter + 1;
                    notificationBinding.setProblem(String.valueOf(counter));
                    ProblemPosition = ProblemPosition + 1;
                    setProblem(ProblemPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        notificationBinding.btnPrevious.setOnClickListener(view13 -> {
            try {
                ProblemPosition = ProblemPosition - 1;
                counter = counter - 1;
                notificationBinding.setProblem(String.valueOf(counter));
                setProblem(ProblemPosition);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        notificationBinding.cvNo.setOnClickListener(view14 -> {
            notificationBinding.cvNo.setChecked(!notificationBinding.cvNo.isChecked());
            notificationBinding.cvYes.setChecked(false);

            if (problemsContain == null)
                problemsContain = new ArrayList<>();

            if (problemsContain.contains("" + symptomsNotificationModelList.get(ProblemPosition).getProblemId()))
                problemsContain.remove("" + symptomsNotificationModelList.get(ProblemPosition).getProblemId());
            else
                problemsContain.add("" + symptomsNotificationModelList.get(ProblemPosition).getProblemId());

        });

        notificationBinding.cvYes.setOnClickListener(view15 -> {
            notificationBinding.cvYes.setChecked(!notificationBinding.cvYes.isChecked());
            notificationBinding.cvNo.setChecked(false);


        });


        notificationBinding.btnGotoDashboard.setOnClickListener(view1 -> navController.navigateUp());
    }

    private void getNotificationData(String memberId) {
        viewModel.getSymptomsNotificationData(memberId).observe(getViewLifecycleOwner(), symptomsNotificationModels -> {
            notificationBinding.root1.setVisibility(symptomsNotificationModels.isEmpty() ? View.GONE : View.VISIBLE);
            notificationBinding.root2.setVisibility(symptomsNotificationModels.isEmpty() ? View.VISIBLE : View.GONE);

            symptomsNotificationModelList = symptomsNotificationModels;
            problemsContain = new ArrayList<>();
            if (symptomsNotificationModelList != null && !symptomsNotificationModelList.isEmpty()) {
                setProblem(ProblemPosition);
            }
        });
    }

    private void setSpinnerData() {

        viewModel.getMemberList(requireActivity()).observe(getViewLifecycleOwner(), memberModelList -> {
            if (null != memberModelList) {

                memberName.clear();
                for (int a = 0; a < memberModelList.size(); a++) {
                    memberName.add(memberModelList.get(a).getName());
                    memberIds.add(String.valueOf(memberModelList.get(a).getMemberId()));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, memberName);
                notificationBinding.spinner.setAdapter(adapter);


                for (int a = 0; a < adapter.getCount(); a++) {
                    if (memberIds.get(a).equals(memberId)) {
                        notificationBinding.spinner.setSelection(a);
                        return;
                    }
                }
            }
        });

        notificationBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                memberId = memberIds.get(i);
                getNotificationData(memberIds.get(i));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void setProblem(int problemPosition) {
        notificationBinding.cvYes.setChecked(false);
        notificationBinding.cvNo.setChecked(false);
        notificationBinding.rlQuestion.setAnimation(fadeIn(requireActivity()));

        if (problemPosition == 0)
            notificationBinding.btnPrevious.setVisibility(View.GONE);
        else notificationBinding.btnPrevious.setVisibility(View.VISIBLE);

        if (problemPosition == (symptomsNotificationModelList.size() - 1)) {
            notificationBinding.btnNext.setText(getString(R.string.submit));
            submitStatus = true;
        } else {
            submitStatus = false;
            notificationBinding.btnNext.setText(getString(R.string.next));
        }

        if (symptomsNotificationModelList != null && !symptomsNotificationModelList.isEmpty() && ProblemPosition <= symptomsNotificationModelList.size() - 1) {
            notificationBinding.tvProblemName.setText(symptomsNotificationModelList.get(problemPosition).getProblemName());
        }

    }

    public void showDialog(String msg) {
        dialog.setMessage(msg);
        dialog.show();
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.dismiss();
    }
}