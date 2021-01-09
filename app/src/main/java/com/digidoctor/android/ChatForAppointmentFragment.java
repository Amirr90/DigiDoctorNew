package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.adapters.ChatAdapter;
import com.digidoctor.android.databinding.FragmentChatForAppointmentBinding;
import com.digidoctor.android.interfaces.ChatInterface;
import com.digidoctor.android.model.ChatModel;
import com.digidoctor.android.model.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.APPOINTMENT_CHAT;
import static com.digidoctor.android.utility.utils.APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.TIMESTAMP;
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
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection(APPOINTMENT_CHAT)
                .whereEqualTo(APPOINTMENT_ID, AppointmentId)
                .orderBy(TIMESTAMP, Query.Direction.DESCENDING)
                .addSnapshotListener(requireActivity(), (value, e) -> {
                    if (e != null) {
                        System.err.println("Listen failed: " + e);
                        return;
                    }

                    chats.clear();
                    List<DocumentSnapshot> documentChanges = value.getDocuments();
                    for (DocumentSnapshot snapshot : documentChanges) {
                        ChatModel chatModel = snapshot.toObject(ChatModel.class);
                        chats.add(chatModel);
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
        User user = getPrimaryUser(requireActivity());
        ChatModel chatModel = new ChatModel();
        chatModel.setAppointment_id(AppointmentId);
        chatModel.setMsg(chat.editTextTextPersonName4.getText().toString());
        chatModel.setSender_id(String.valueOf(getPrimaryUser(requireActivity()).getMemberId()));
        chatModel.setReceiver_id(doId);
        chatModel.setSeen(false);
        chatModel.setSender_name(user.getName());
        chatModel.setToken("TOKEN");
        chatModel.setTimestamp(System.currentTimeMillis());


        return chatModel;
    }

    private void sendMsg() {
        adapter.notifyDataSetChanged();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(APPOINTMENT_CHAT).add(getChat());
        chat.editTextTextPersonName4.setText("");
        updateVisibility();
    }


    @Override
    public void onChatItemClicked(Object obj) {

    }

}