package com.digidoctor.android.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.BotReceiverViewBinding;
import com.digidoctor.android.databinding.BotSenderViewBinding;
import com.digidoctor.android.model.BotModel;
import com.digidoctor.android.utility.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class BootAdapter extends RecyclerView.Adapter {
    private static final String TAG = "BootAdapter";

    List<BotModel> list;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        BotSenderViewBinding senderViewBinding = BotSenderViewBinding.inflate(layoutInflater, parent, false);
        BotReceiverViewBinding botReceiverViewBinding = BotReceiverViewBinding.inflate(layoutInflater, parent, false);
        return viewType == AppUtils.VIEW_TYPE_SENDER ? new SenderVH(senderViewBinding) : new ReceiverVH(botReceiverViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BotModel snapshot = list.get(position);
        try {
            if (getItemViewType(position) == AppUtils.VIEW_TYPE_SENDER) {
                SenderVH senderViewHolder = (SenderVH) holder;
                senderViewHolder.senderViewBinding.setBotModel(snapshot);
                // senderViewHolder.senderViewBinding.tvTimeStampRec.setText(createDate(snapshot.getTimestamp()));
            } else {
                ReceiverVH receiverVH = (ReceiverVH) holder;
                receiverVH.botReceiverViewBinding.setBotModel(snapshot);
                // receiverViewBinding.receiverViewBinding.tvTimeStamp.setText(createDate(snapshot.getTimestamp()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onBindViewHolder: " + e.getLocalizedMessage());
            // Log.d(TAG, "Timestamp: " + snapshot.getTimestamp());
        }
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType().equalsIgnoreCase(AppUtils.SENDER) ? AppUtils.VIEW_TYPE_SENDER : AppUtils.VIEW_TYPE_RECEIVER;
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public void addItem(BotModel model) {
        if (null == list)
            list = new ArrayList<>();
        list.add(model);
        notifyItemInserted(getItemCount() - 1);
    }

    public class SenderVH extends RecyclerView.ViewHolder {
        BotSenderViewBinding senderViewBinding;

        public SenderVH(@NonNull BotSenderViewBinding senderViewBinding) {
            super(senderViewBinding.getRoot());
            this.senderViewBinding = senderViewBinding;
        }
    }

    public class ReceiverVH extends RecyclerView.ViewHolder {
        BotReceiverViewBinding botReceiverViewBinding;

        public ReceiverVH(@NonNull BotReceiverViewBinding botReceiverViewBinding) {
            super(botReceiverViewBinding.getRoot());
            this.botReceiverViewBinding = botReceiverViewBinding;
        }
    }
}
