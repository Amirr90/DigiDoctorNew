package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.adapters.ChatAdapter;
import com.digidoctor.android.databinding.FragmentChatForAppointmentBinding;
import com.digidoctor.android.interfaces.ChatInterface;
import com.digidoctor.android.interfaces.NewApiInterface;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.getPrimaryUser;


public class ChatForAppointmentFragment extends Fragment implements ChatInterface {
    private static final String TAG = "ChatForAppointmentFragm";
    FragmentChatForAppointmentBinding chat;
    NavController navController;
    ChatAdapter adapter;
    List<ChatModel> chats;
    String AppointmentId = null;
    String doId = null;

    String uid;

    PatientViewModel viewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chat = FragmentChatForAppointmentBinding.inflate(getLayoutInflater());

        return chat.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (null == getArguments())
            return;

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        uid = String.valueOf(getPrimaryUser(requireActivity()).getMemberId());

        AppointmentId = getArguments().getString(KEY_APPOINTMENT_ID);
        doId = getArguments().getString("docId");

        //Showing the title
        Objects.requireNonNull(Navigation.findNavController(view).getCurrentDestination()).setLabel(getArguments().getString("docName"));

        chats = new ArrayList<>();
        adapter = new ChatAdapter(this, chats, uid);
        chat.chatRec.setAdapter(adapter);


        loadLiveData();


        chat.btnSendMsg.setOnClickListener(view1 -> {
            if (chat.editTextTextPersonName4.getText().toString().isEmpty())
                return;
            sendMsg();
        });

    }

    private void loadLiveData() {
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setAppointmentId(AppointmentId);

        viewModel.getChatData(appointmentModel).observe(getViewLifecycleOwner(), (List<ChatModel> chatModelList) -> {
            if (null != chatModelList) {
                chats.clear();

                chats.addAll(chatModelList);
                // Collections.sort(chats, Collections.reverseOrder());


            }
            adapter.notifyDataSetChanged();
            updateVisibility();
        });

    }

    private void updateVisibility() {
        chat.noChatsLay.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        chat.chatRec.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }


    public ChatModel getChat() {
        ChatModel chatModel = new ChatModel();
        chatModel.setAppointmentId(AppointmentId);
        chatModel.setMessage(chat.editTextTextPersonName4.getText().toString());
        chatModel.setSenderId(String.valueOf(getPrimaryUser(requireActivity()).getMemberId()));
        chatModel.setReceiverId(doId);
        chatModel.setSeen(false);
        chatModel.setServiceProviderTypeId("6");//6 for patient
        chatModel.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return chatModel;
    }

    private void sendMsg() {
        adapter.notifyDataSetChanged();
        adapter.addChatItems(getChat());
        ApiUtils.getChatResponse(ApiUtils.sendMsg(getChat()), new NewApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<ChatModel> chatModels = (List<ChatModel>) obj;
                Log.d(TAG, "onSuccess: msg send " + chatModels.toString());
                chats.clear();
                chats.addAll(chatModels);
                adapter.notifyDataSetChanged();
                updateVisibility();
            }

            @Override
            public void onFailed(String msg) {

            }
        });
        chat.editTextTextPersonName4.setText("");
        updateVisibility();
    }


    @Override
    public void onChatItemClicked(Object obj) {

    }

}