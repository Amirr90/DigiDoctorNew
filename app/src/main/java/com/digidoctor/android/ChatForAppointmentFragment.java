package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.adapters.ChatAdapter;
import com.digidoctor.android.databinding.FragmentChatForAppointmentBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.ChatInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.digidoctor.android.utility.utils.*;
import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.MSG;
import static com.digidoctor.android.utility.utils.TIMESTAMP;
import static com.digidoctor.android.utility.utils.getPrimaryUser;


public class ChatForAppointmentFragment extends Fragment implements ChatInterface {
    private static final String TAG = "ChatForAppointmentFragm";

    FragmentChatForAppointmentBinding chat;
    NavController navController;
    ChatAdapter adapter;
    List<DocumentSnapshot> chats;
    String AppointmentId = null;
    String doId = null;

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

        AppointmentId = getArguments().getString(KEY_APPOINTMENT_ID);
        doId = getArguments().getString("docId");
        User user = getPrimaryUser(requireActivity());
        chats = new ArrayList<>();
        adapter = new ChatAdapter(this, chats, String.valueOf(user.getMemberId()));
        chat.chatRec.setAdapter(adapter);


        loadChatsData(AppointmentId);


        chat.btnSendMsg.setOnClickListener(view1 -> {
            if (chat.editTextTextPersonName4.getText().toString().isEmpty())
                return;
            sendMsg(chat.editTextTextPersonName4.getText().toString());
        });


    }

    private void updateVisibility() {
        chat.noChatsLay.setVisibility(adapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        chat.chatRec.setVisibility(adapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    }

    private void sendMsg(String msg) {
        HashMap<String, Object> chatMap = getChatMap(msg);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(APPOINTMENT_CHAT).add(chatMap);
        chat.editTextTextPersonName4.setText("");
        updateVisibility();
    }

    private HashMap<String, Object> getChatMap(String msg) {
        User user = getPrimaryUser(requireActivity());
        HashMap<String, Object> map = new HashMap<>();
        map.put(TIMESTAMP, System.currentTimeMillis());
        map.put(MSG, msg);
        map.put(SENDER_ID, String.valueOf(user.getMemberId()));
        map.put(RECEIVER_ID, doId);
        map.put(APPOINTMENT_ID, AppointmentId);
        map.put(SENDER_NAME, user.getName());
        map.put(TOKEN, "token");
        map.put(IS_SEEN, false);
        return map;
    }

    private void loadChatsData(String appointmentId) {
        ApiUtils.loadChats(appointmentId, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<DocumentSnapshot> snapshots = (List<DocumentSnapshot>) o;
                chats.clear();
                chats.addAll(snapshots);
                adapter.notifyDataSetChanged();
                updateVisibility();
            }

            @Override
            public void onError(String s) {
                Log.d(TAG, "onError: " + s);
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailed: " + throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onChatItemClicked(Object obj) {

    }
}