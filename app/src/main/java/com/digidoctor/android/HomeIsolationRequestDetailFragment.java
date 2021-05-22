package com.digidoctor.android;

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

import com.digidoctor.android.databinding.FragmentHomeIsolationRequestDetailBinding;
import com.digidoctor.android.databinding.VitalsViewBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.User;
import com.digidoctor.android.model.patientModel.HomeIsolationReqModel;
import com.digidoctor.android.model.patientModel.IsolationVitalModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeIsolationRequestDetailFragment extends Fragment {

    private static final String TAG = "HomeIsolationRequestDet";

    FragmentHomeIsolationRequestDetailBinding binding;
    NavController navController;
    String id;

    HomeIsolationReqModel homeIsolationReqModel;
    int color;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeIsolationRequestDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null == getArguments()) {
            Toast.makeText(App.context, "invalid isolation id", Toast.LENGTH_SHORT).show();
            return;
        }
        navController = Navigation.findNavController(view);
        id = HomeIsolationRequestDetailFragmentArgs.fromBundle(getArguments()).getId();


        getIsolationData();
    }

    private void getIsolationData() {

        AppUtils.showRequestDialog(requireActivity());
        User user = new User();
        user.setId(Integer.valueOf(id));
        ApiUtils.isolationData(user, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<HomeIsolationReqModel> homeIsolationReqModels = (List<HomeIsolationReqModel>) o;
                if (null != homeIsolationReqModels && homeIsolationReqModels.size() > 0) {
                    homeIsolationReqModel = homeIsolationReqModels.get(0);
                    binding.setIsolation(homeIsolationReqModel);
                    updateRequestStatus(homeIsolationReqModel);
                    binding.tvPackagePriceD.setText(AppUtils.getCurrencyFormat(homeIsolationReqModel.getPackagePrice()));


                    if (null != homeIsolationReqModels.get(0).getVitalDetails()) {
                        try {
                            binding.recVital.setVisibility(View.VISIBLE);
                            JSONArray jsonarray = new JSONArray(homeIsolationReqModels.get(0).getVitalDetails());
                            List<IsolationVitalModel> isolationVitalModels = new ArrayList<>();
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String name = jsonobject.getString("vitalName");
                                String value = jsonobject.getString("vitalValue");
                                Log.d(TAG, "onSuccess: " + name + " value " + value);
                                isolationVitalModels.add(new IsolationVitalModel(name, value));
                            }
                            binding.recVital.setAdapter(new VitalsAdapter(isolationVitalModels));
                        } catch (JSONException e) {
                            binding.recVital.setVisibility(View.GONE);
                            e.printStackTrace();
                            Log.d(TAG, "error: " + e.getLocalizedMessage());
                        }
                    } else {
                        Log.d(TAG, "onSuccess: no vital value");
                        binding.recVital.setVisibility(View.GONE);
                    }
                }
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
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }

    private void updateRequestStatus(HomeIsolationReqModel tuitionModel) {

        if (tuitionModel.getHomeIsolationStatus().equalsIgnoreCase("Pending")) {
            binding.btLoading.setAnimation(R.raw.waiting);
            color = getResources().getColor(R.color.yellow700);
        } else if (tuitionModel.getHomeIsolationStatus().equalsIgnoreCase("Approved")) {
            binding.btLoading.setAnimation(R.raw.accepted);
            color = getResources().getColor(R.color.green700);
        } else if (tuitionModel.getHomeIsolationStatus().equalsIgnoreCase("Declined")) {
            binding.btLoading.setAnimation(R.raw.rejected);
            color = getResources().getColor(R.color.red);
        }

        binding.tvStatus2.setTextColor(color);
        binding.btLoading.playAnimation();

    }

    private class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.VitalVH> {

        public VitalsAdapter(List<IsolationVitalModel> models) {
            this.models = models;
        }

        List<IsolationVitalModel> models;

        @NonNull
        @Override
        public VitalVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VitalsViewBinding viewBinding = VitalsViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new VitalVH(viewBinding);

        }

        @Override
        public void onBindViewHolder(@NonNull VitalVH holder, int position) {
            holder.viewBinding.setVitals(models.get(position));
        }

        @Override
        public int getItemCount() {
            return null == models ? 0 : models.size();
        }

        public class VitalVH extends RecyclerView.ViewHolder {
            VitalsViewBinding viewBinding;

            public VitalVH(@NonNull VitalsViewBinding viewBinding) {
                super(viewBinding.getRoot());
                this.viewBinding = viewBinding;
            }
        }
    }
}