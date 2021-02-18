package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.ReceiverViewBinding;
import com.digidoctor.android.databinding.SenderViewBinding;
import com.digidoctor.android.interfaces.ChatInterface;
import com.digidoctor.android.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.createDate;

public class ChatAdapter extends RecyclerView.Adapter {
    public static final int VIEW_TYPE_SENDER = 0;
    public static final int VIEW_TYPE_RECEIVER = 1;
    ChatInterface chatInterface;
    List<ChatModel> chats;
    String uid;

    public ChatAdapter(ChatInterface chatInterface, List<ChatModel> chats, String uid) {
        this.chatInterface = chatInterface;
        this.chats = chats;
        this.uid = uid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_RECEIVER) {
            ReceiverViewBinding receiverViewBinding = ReceiverViewBinding.inflate(inflater, parent, false);
            receiverViewBinding.setChatInterface(chatInterface);
            return new ReceiverViewHolder(receiverViewBinding);
        } else {
            SenderViewBinding senderViewBinding = SenderViewBinding.inflate(inflater, parent, false);
            senderViewBinding.setChatInterface(chatInterface);
            return new SenderViewHolder(senderViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ChatModel snapshot = chats.get(position);
        try {
            if (getItemViewType(position) == VIEW_TYPE_SENDER) {
                SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
                senderViewHolder.senderViewBinding.setChats(snapshot);
                senderViewHolder.senderViewBinding.tvTimeStampRec.setText(createDate(snapshot.getTimestamp()));
            } else {
                ReceiverViewHolder receiverViewBinding = (ReceiverViewHolder) holder;
                receiverViewBinding.receiverViewBinding.setChats(snapshot);
                receiverViewBinding.receiverViewBinding.tvTimeStamp.setText(createDate(snapshot.getTimestamp()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatModel model = chats.get(position);
        if (uid.equalsIgnoreCase(model.getSenderId()))
            return VIEW_TYPE_RECEIVER;
        else return VIEW_TYPE_SENDER;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }


    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        SenderViewBinding senderViewBinding;
        public SenderViewHolder(@NonNull SenderViewBinding senderViewBinding) {
            super(senderViewBinding.getRoot());
            this.senderViewBinding = senderViewBinding;
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        ReceiverViewBinding receiverViewBinding;
        public ReceiverViewHolder(@NonNull ReceiverViewBinding receiverViewBinding) {
            super(receiverViewBinding.getRoot());
            this.receiverViewBinding = receiverViewBinding;
        }
    }


    public void addChatItems(ChatModel model) {
        if (null == chats)
            chats = new ArrayList<>();
        chats.add(0, model);
        notifyDataSetChanged();
    }

}
