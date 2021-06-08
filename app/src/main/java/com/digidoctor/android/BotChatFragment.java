package com.digidoctor.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.adapters.BootAdapter;
import com.digidoctor.android.databinding.EnterMobileNumberBinding;
import com.digidoctor.android.databinding.EnterNameBinding;
import com.digidoctor.android.databinding.FragmentBotChatBinding;
import com.digidoctor.android.model.BotModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import org.jetbrains.annotations.NotNull;

import static com.digidoctor.android.utility.AppUtils.StringToDate;

public class BotChatFragment extends Fragment {

    FragmentBotChatBinding binding;
    NavController navController;
    BootAdapter adapter;
    User user;
    int threadTime = 800;
    User userAddedByBot = new User();

    Handler handler1, handler2, handler3;

    private static final String TAG = "BotChatFragment";

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBotChatBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.ivBAck.setOnClickListener(v -> navController.navigateUp());


        startBotChat();

    }

    private void startBotChat() {
        adapter = new BootAdapter();
        binding.recBootChat.setAdapter(adapter);

        addData();


    }

    private void addData() {
        user = utils.getUserForBooking(requireActivity());
        binding.setUser(user);
        String msg = "Hi " + user.getName();
        adapter.addItem(new BotModel(msg, AppUtils.RECEIVER, System.currentTimeMillis()));

        handler1 = new Handler();
        handler2 = new Handler();

        handler1.postDelayed(() -> {
            String msg2 = getString(R.string.thank_you_for_choosing);
            adapter.addItem(new BotModel(msg2, AppUtils.RECEIVER, System.currentTimeMillis()));

            handler2.postDelayed(() -> {
                String msg3 = getString(R.string.please_let_us_know);
                adapter.addItem(new BotModel(msg3, AppUtils.RECEIVER, System.currentTimeMillis()));
                showMembers();
            }, threadTime);

        }, threadTime);


    }


    @Override
    public void onStop() {
        super.onStop();


    }

    private void showMembers() {
        binding.selectMemberLay.setVisibility(View.VISIBLE);
        binding.selectMemberLay.setAnimation(utils.fadeIn(requireActivity()));

        binding.tvSomeoneElse.setOnClickListener(v -> {
            if (binding.tvSomeoneElse.getText().toString().equals(getString(R.string.someone_else))) {
                adapter.addItem(new BotModel(getString(R.string.someone_else), AppUtils.SENDER, System.currentTimeMillis()));
                binding.selectMemberLay.setVisibility(View.GONE);

                new Handler().postDelayed(() -> {
                    adapter.addItem(new BotModel(getString(R.string.what_is_his_name), AppUtils.RECEIVER, System.currentTimeMillis()));
                    binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                    new Handler().postDelayed(this::showBottomDialogToEnterName, threadTime);

                }, threadTime);
            } else if (binding.tvSomeoneElse.getText().toString().equals(getString(R.string.female))) {
                adapter.addItem(new BotModel(getString(R.string.female), AppUtils.SENDER, System.currentTimeMillis()));
                userAddedByBot.setGender(2);
                binding.selectMemberLay.setVisibility(View.GONE);

                new Handler().postDelayed(() -> {
                    adapter.addItem(new BotModel(getString(R.string.select_date_of_birth), AppUtils.RECEIVER, System.currentTimeMillis()));
                    binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                    new Handler().postDelayed(this::showEnterDateOfBirth, threadTime);
                }, threadTime);

            }


        });
        binding.tvMemberName.setOnClickListener(v -> {
            if (binding.tvMemberName.getText().toString().equals(getString(R.string.male))) {
                adapter.addItem(new BotModel(getString(R.string.male), AppUtils.SENDER, System.currentTimeMillis()));
                userAddedByBot.setGender(1);
                new Handler().postDelayed(() -> {
                    adapter.addItem(new BotModel(getString(R.string.select_date_of_birth), AppUtils.RECEIVER, System.currentTimeMillis()));
                    binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                    new Handler().postDelayed(this::showEnterDateOfBirth, threadTime);
                }, threadTime);
            } else {
                adapter.addItem(new BotModel(user.getName(), AppUtils.SENDER, System.currentTimeMillis()));

                initToShowSpeciality(user);
            }

            binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
            binding.selectMemberLay.setVisibility(View.GONE);

        });
    }

    private void showEnterDateOfBirth() {
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_picker, null, false);
        final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);
        myDatePicker.setMaxDate(System.currentTimeMillis());
        myDatePicker.setCalendarViewShown(false);
        new AlertDialog.Builder(requireActivity()).setView(view)
                .setTitle(R.string.select_date)
                .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        int month = myDatePicker.getMonth() + 1;
                        int day = myDatePicker.getDayOfMonth();
                        int year = myDatePicker.getYear();
                        String date = year + "-" + month + "-" + day;
                        userAddedByBot.setDob(date);
                        AppUtils.showToast(date);

                        dialog.cancel();

                        new Handler().postDelayed(() -> {

                            adapter.addItem(new BotModel(StringToDate(date), AppUtils.SENDER, System.currentTimeMillis()));
                            binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                            new Handler().postDelayed(() -> {
                                adapter.addItem(new BotModel(getString(R.string.enter_your_mobile_number), AppUtils.RECEIVER, System.currentTimeMillis()));
                                binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                                enterMobileNumber();
                            }, threadTime);
                        }, threadTime);
                    }


                }).show();
    }


    private void AddMember() {

    }

    private void enterMobileNumber() {
        new Handler().postDelayed(() -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            EnterMobileNumberBinding mobileNumberBinding = EnterMobileNumberBinding.inflate(inflater);
            builder.setView(mobileNumberBinding.getRoot())
                    .setMessage(R.string.enter_your_mobile_number)
                    .setCancelable(false)
                    .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // your sign in code here
                            String number = mobileNumberBinding.number.getText().toString();
                            if (!TextUtils.isEmpty(number)) {
                                adapter.addItem(new BotModel(number, AppUtils.SENDER, System.currentTimeMillis()));
                                userAddedByBot.setMobileNo(number);
                                binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                                initToShowSpeciality(userAddedByBot);
                            } else
                                Toast.makeText(requireActivity(), getString(R.string.mobile_required), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // remove the dialog from the screen
                        }
                    })
                    .show();
        }, threadTime);

    }

    private void initToShowSpeciality(User user) {
        String userInString = user.toString();
        Bundle bundle = new Bundle();
        bundle.putString("user", userInString);
        Log.d(TAG, "initToShowSpeciality: " + userInString);
        navController.navigate(R.id.action_botChatFragment_to_chatWithMemberFragment, bundle);
    }

    private void showBottomDialogToEnterName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        EnterNameBinding nameBinding = EnterNameBinding.inflate(inflater);
        builder.setView(nameBinding.getRoot())
                .setMessage(R.string.enter_patient_name)
                .setPositiveButton(R.string.submit, (dialog, which) -> {
                    String name = nameBinding.username.getText().toString();
                    if (!TextUtils.isEmpty(name)) {
                        adapter.addItem(new BotModel(name, AppUtils.SENDER, System.currentTimeMillis()));
                        userAddedByBot.setName(name);
                        binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                        new Handler().postDelayed(() -> {
                            adapter.addItem(new BotModel(getString(R.string.select__your_gender), AppUtils.RECEIVER, System.currentTimeMillis()));
                            binding.recBootChat.scrollToPosition(adapter.getItemCount() - 1);
                            new Handler().postDelayed(this::selectGender, threadTime);
                        }, threadTime);

                    } else {
                        Toast.makeText(requireActivity(), "Patient name could't be empty !!", Toast.LENGTH_SHORT).show();
                        binding.selectMemberLay.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(dialog -> {
                    String name1 = nameBinding.username.getText().toString();
                    if (TextUtils.isEmpty(name1))
                        binding.selectMemberLay.setVisibility(View.VISIBLE);
                })
                .setCancelable(false)
                .show();


    }

    private void selectGender() {
        binding.selectMemberLay.setVisibility(View.VISIBLE);
        binding.selectMemberLay.setAnimation(utils.slideUp(requireActivity()));
        binding.tvMemberName.setText(R.string.male);
        binding.tvSomeoneElse.setText(R.string.female);

    }

}
