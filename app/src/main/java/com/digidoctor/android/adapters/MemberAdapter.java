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
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.view.fragments.digiDoctorFragments.ShowMemberListFragment;

import java.util.List;

import static com.digidoctor.android.utility.utils.BOOKING_USER;
import static com.digidoctor.android.utility.utils.MOBILE_NUMBER;
import static com.digidoctor.android.utility.utils.USER;
import static com.digidoctor.android.utility.utils.getMainUser;
import static com.digidoctor.android.utility.utils.getPrimaryUser;

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

            holder.memberViewBinding.ivDeleteMember.setOnClickListener(view -> showDeleteMemberDialog(member));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDeleteMemberDialog(User member) {
        AppUtils.showDeleteDialog(activity, (dialogInterface, i) -> deleteMember(member));
    }

    private void deleteMember(User member) {
        AppUtils.showRequestDialog(activity);
        ApiUtils.deleteMember(member, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                User user = getPrimaryUser(activity);
                User mainUser = getMainUser(activity);

                if (member.getMemberId() == user.getMemberId())
                    updateUser(mainUser);

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

    private void updateUser(User user) {

        utils.savePrimaryUserData(USER, activity, user);

        utils.setUserForBooking(BOOKING_USER, activity, user);

        utils.setString(MOBILE_NUMBER, user.getMobileNo(), activity);

        PatientDashboard.getInstance().updateUser();
        PatientDashboard.getInstance().onSupportNavigateUp();
    }

    public class MemberVH extends RecyclerView.ViewHolder {
        MemberViewBinding memberViewBinding;

        public MemberVH(MemberViewBinding memberViewBinding) {
            super(memberViewBinding.getRoot());
            this.memberViewBinding = memberViewBinding;
        }
    }
}
