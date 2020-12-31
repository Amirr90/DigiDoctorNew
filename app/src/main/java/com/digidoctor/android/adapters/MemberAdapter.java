package com.digidoctor.android.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.MemberViewBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.fragments.ShowMemberListFragment;

import java.util.List;

public class MemberAdapter extends ListAdapter<User, MemberAdapter.MemberVH> {

    AdapterInterface adapterInterface;
    Activity activity;

    public MemberAdapter(AdapterInterface adapterInterface, Activity activity) {
        super(User.itemCallback);
        this.adapterInterface = adapterInterface;
        this.activity = activity;
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

            holder.memberViewBinding.ivDeleteMember.setOnClickListener(view -> {
                showDeleteMemberDialog(member, position);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDeleteMemberDialog(User member, int position) {
        AppUtils.showDeleteDialog(activity, (dialogInterface, i) -> deleteMember(member, position));
    }

    private void deleteMember(User member, int position) {
        AppUtils.showRequestDialog(activity);
        ApiUtils.deleteMember(member, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                ShowMemberListFragment.getInstance().getMembersList();
                Toast.makeText(activity, activity.getString(com.digidoctor.android.R.string.member_deleted), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(activity, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public class MemberVH extends RecyclerView.ViewHolder {
        MemberViewBinding memberViewBinding;

        public MemberVH(MemberViewBinding memberViewBinding) {
            super(memberViewBinding.getRoot());
            this.memberViewBinding = memberViewBinding;
        }
    }
}
