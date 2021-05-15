package com.digidoctor.android;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.databinding.FragmentHomeIsolationRequestListBinding;
import com.digidoctor.android.databinding.HomeIsolationViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.patientModel.HomeIsolationReqModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.utils.getUserForBooking;


public class HomeIsolationRequestListFragment extends Fragment {
    private static final String TAG = "HomeIsolationRequestLis";


    FragmentHomeIsolationRequestListBinding binding;
    NavController navController;
    List<HomeIsolationReqModel> reqModels;
    HomeIsolationAdapter homeIsolationAdapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeIsolationRequestListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        reqModels = new ArrayList<>();
        homeIsolationAdapter = new HomeIsolationAdapter(reqModels);
        binding.isolationRec.setAdapter(homeIsolationAdapter);
        getIsolationData();
    }

    private void getIsolationData() {

        AppUtils.showRequestDialog(requireActivity());
        User user = new User();
        user.setMemberId(getUserForBooking(requireActivity()).getMemberId());
        ApiUtils.isolationData(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                reqModels.clear();
                reqModels.addAll((List<HomeIsolationReqModel>) o);
                homeIsolationAdapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: " + homeIsolationAdapter.getItemCount());
                if (homeIsolationAdapter.getItemCount() == 0)
                    Toast.makeText(requireActivity(), "No request found !!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.hideDialog();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    private class HomeIsolationAdapter extends RecyclerView.Adapter<HomeIsolationAdapter.IsolationVH> {
        List<HomeIsolationReqModel> isolationReqModels;

        public HomeIsolationAdapter(List<HomeIsolationReqModel> reqModels) {
            this.isolationReqModels = reqModels;
        }

        @NonNull
        @Override
        public IsolationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            HomeIsolationViewBinding isolationViewBinding = HomeIsolationViewBinding.inflate(inflater, parent, false);
            return new IsolationVH(isolationViewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull IsolationVH holder, int position) {
            HomeIsolationReqModel homeIsolationReqModel = isolationReqModels.get(position);
            holder.isolationViewBinding.setIsolation(homeIsolationReqModel);

            String status = homeIsolationReqModel.getHomeIsolationStatus();


            if (null != isolationReqModels.get(position) && null != isolationReqModels.get(position).getHomeIsolationStatus()) {
                if (status.equalsIgnoreCase("Pending")) {
                    holder.isolationViewBinding.llImage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow700)));
                } else if (status.equalsIgnoreCase("Approved")) {
                    holder.isolationViewBinding.llImage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green700)));
                } else if (status.equalsIgnoreCase("Declined")) {
                    holder.isolationViewBinding.llImage.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
            }

            holder.isolationViewBinding.getRoot().setOnClickListener(v -> {
                HomeIsolationRequestListFragmentDirections.ActionHomeIsolationRequestListFragmentToHomeIsolationRequestDetailFragment action = HomeIsolationRequestListFragmentDirections.actionHomeIsolationRequestListFragmentToHomeIsolationRequestDetailFragment();
                action.setId(String.valueOf(homeIsolationReqModel.getId()));
                navController.navigate(action);
            });
        }

        @Override
        public int getItemCount() {
            return null == isolationReqModels ? 0 : isolationReqModels.size();
        }

        public class IsolationVH extends RecyclerView.ViewHolder {
            HomeIsolationViewBinding isolationViewBinding;

            public IsolationVH(@NonNull HomeIsolationViewBinding isolationViewBinding) {
                super(isolationViewBinding.getRoot());
                this.isolationViewBinding = isolationViewBinding;
            }
        }
    }
}