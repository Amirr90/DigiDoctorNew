package com.digidoctor.android.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.MemberViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.model.User;

public class MemberAdapter extends ListAdapter<User, MemberAdapter.MemberVH> {

    AdapterInterface adapterInterface;

    public MemberAdapter(AdapterInterface adapterInterface) {
        super(User.itemCallback);
        this.adapterInterface = adapterInterface;
    }

    @NonNull
    @Override
    public MemberVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MemberViewBinding memberViewBinding = MemberViewBinding.inflate(inflater, parent, false);
        memberViewBinding.setAdapterInterface(adapterInterface);
        return new MemberVH(memberViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull MemberVH holder, int position) {

        try {

            User member = getItem(position);
            holder.memberViewBinding.setMember(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MemberVH extends RecyclerView.ViewHolder {
        MemberViewBinding memberViewBinding;

        public MemberVH(MemberViewBinding memberViewBinding) {
            super(memberViewBinding.getRoot());
            this.memberViewBinding = memberViewBinding;
        }
    }
}
