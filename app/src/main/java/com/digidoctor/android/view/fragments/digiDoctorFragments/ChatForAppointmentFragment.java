package com.digidoctor.android.view.fragments.digiDoctorFragments;

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
import com.digidoctor.android.adapters.SmartSuggestionAdapter;
import com.digidoctor.android.databinding.FragmentChatForAppointmentBinding;
import com.digidoctor.android.interfaces.ChatInterface;
import com.digidoctor.android.interfaces.NewApiInterface;
import com.digidoctor.android.interfaces.SuggestionInterface;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.mlkit.nl.smartreply.SmartReply;
import com.google.mlkit.nl.smartreply.SmartReplyGenerator;
import com.google.mlkit.nl.smartreply.SmartReplySuggestion;
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult;
import com.google.mlkit.nl.smartreply.TextMessage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.getPrimaryUser;


public class ChatForAppointmentFragment extends Fragment implements ChatInterface, SuggestionInterface {
    private static final String TAG = "ChatForAppointmentFragm";
    FragmentChatForAppointmentBinding chat;
    NavController navController;
    ChatAdapter adapter;
    List<ChatModel> chats;
    String AppointmentId = null;
    String doId = null;

    String uid;

    PatientViewModel viewModel;
    SmartSuggestionAdapter suggestionAdapter;
    List<String> suggestionMsg;


    ArrayList<TextMessage> conversation = new ArrayList<TextMessage>();

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


        loadData();


        chat.btnSendMsg.setOnClickListener(view1 -> {
            if (chat.editTextTextPersonName4.getText().toString().isEmpty())
                return;
            sendMsg(chat.editTextTextPersonName4.getText().toString());
        });

        suggestionMsg = new ArrayList<>();
        suggestionAdapter = new SmartSuggestionAdapter(suggestionMsg, this::onSuggestionItemClicked);
        chat.recSmartSuggestion.setAdapter(suggestionAdapter);

    }

    private void loadData() {
        AppointmentModel appointmentModel = new AppointmentModel();
        appointmentModel.setAppointmentId(AppointmentId);

        viewModel.getChatData(appointmentModel).observe(getViewLifecycleOwner(), (List<ChatModel> chatModelList) -> {
            if (null != chatModelList) {
                chats.clear();

                chats.addAll(chatModelList);
                Log.d(TAG, "loadData: " + chats.size());
                //
                // Collections.sort(chats, Collections.reverseOrder());

                if (chatModelList.size() > 1) {
                    addSenderMsg(chatModelList.get(0).getMessage());
                    addReceiverMsg(chatModelList.get(1).getMessage());
                    Log.d(TAG, "loadLiveData: 0 " + chatModelList.get(0).getMessage());
                    Log.d(TAG, "loadLiveData: 1 " + chatModelList.get(1).getMessage());
                    getSmartReply();
                }


            }
            if (chats.size() > 0)
                chat.chatRec.scrollToPosition(0);
            adapter.notifyDataSetChanged();
            updateVisibility();
        });

    }

    private void updateVisibility() {
        chat.noChatsLay.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        chat.chatRec.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }


    public ChatModel getChat(String msg) {
        ChatModel chatModel = new ChatModel();
        chatModel.setAppointmentId(AppointmentId);
        chatModel.setMessage(msg);
        chatModel.setSenderId(String.valueOf(getPrimaryUser(requireActivity()).getMemberId()));
        chatModel.setReceiverId(doId);
        chatModel.setSeen(false);
        chatModel.setServiceProviderTypeId("6");//6 for patient
        chatModel.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return chatModel;
    }

    private void sendMsg(String msg) {
        adapter.notifyDataSetChanged();
        adapter.addChatItems(getChat(msg));
        addSenderMsg(msg);
        ApiUtils.getChatResponse(ApiUtils.sendMsg(getChat(msg)), new NewApiInterface() {
            @Override
            public void onSuccess(Object obj) {
                List<ChatModel> chatModels = (List<ChatModel>) obj;
                Log.d(TAG, "onSuccess: msg send " + chatModels.toString());
                chats.clear();
                chats.addAll(chatModels);
                adapter.notifyDataSetChanged();
                chat.chatRec.scrollToPosition(adapter.getItemCount() - 1);
                updateVisibility();

                if (chatModels.size() > 1) {
                    for (ChatModel chatModel : chatModels) {
                        if (chatModel.getSenderId().equals(uid)) {
                            addSenderMsg(chatModel.getMessage());
                            Log.d(TAG, "loadLiveData: 0 " + chatModel.getMessage());


                        } else {
                            addReceiverMsg(chatModel.getMessage());
                            Log.d(TAG, "loadLiveData: 1 " + chatModel.getMessage());
                        }
                    }
                  /*  addSenderMsg(chatModels.get(1).getMessage());
                    addReceiverMsg(chatModels.get(0).getMessage());
                    Log.d(TAG, "loadLiveData: 0 " + chatModels.get(0).getMessage());
                    Log.d(TAG, "loadLiveData: 1 " + chatModels.get(1).getMessage());*/
                    getSmartReply();
                }
                if (chats.size() > 0)
                    chat.chatRec.scrollToPosition(0);
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


    private void addSenderMsg(String msg) {
        conversation.add(TextMessage.createForLocalUser(
                msg, System.currentTimeMillis()));
    }

    private void addReceiverMsg(String msg) {
        conversation.add(TextMessage.createForRemoteUser(
                msg, System.currentTimeMillis(), doId));
    }

    private void getSmartReply() {
        SmartReplyGenerator smartReply = SmartReply.getClient();
        smartReply.suggestReplies(conversation)
                .addOnSuccessListener(result -> {
                    if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                        Log.d(TAG, "onSuccess: language not supported !!");
                    } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
                        suggestionMsg.clear();
                        for (SmartReplySuggestion suggestionMsg1 : result.getSuggestions()) {
                            Log.d(TAG, "smartReply: " + suggestionMsg1.getText());
                            suggestionMsg.add(suggestionMsg1.getText());
                        }
                        suggestionAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
                });
    }

    @Override
    public void onSuggestionItemClicked(String msg) {
        sendMsg(msg);
    }
}