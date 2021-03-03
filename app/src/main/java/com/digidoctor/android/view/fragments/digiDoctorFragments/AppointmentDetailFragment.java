package com.digidoctor.android.view.fragments.digiDoctorFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.FilesAdapter;
import com.digidoctor.android.adapters.OldAppointmentsAdapter;
import com.digidoctor.android.databinding.FragmentAppointmentDetailBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.AppointmentDetailsRes;
import com.digidoctor.android.model.AppointmentModel;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.getJSONFromModel;


public class AppointmentDetailFragment extends Fragment implements OnClickListener, AdapterInterface {
    private static final String TAG = "AppointmentDetailFragme";
    public static final String PRESCRIBE = "1";
    public static final String CONFIRMED = "2";


    NavController navController;
    OnlineAppointmentModel appointmentModel;

    FragmentAppointmentDetailBinding detailBinding;

    FilesAdapter adapter;

    List<FileModel> modelList;


    List<AppointmentModel> oldList;


    String appointmentId = null;

    AppointmentDetailFragmentArgs detailFragmentArgs;

    OldAppointmentsAdapter oldAppointmentAdapter;
    PatientViewModel viewModel;
    User user;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailBinding = FragmentAppointmentDetailBinding.inflate(getLayoutInflater());

        Log.d(TAG, "onCreateView: ");
        return detailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: ");
        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);

        if (getArguments() == null)
            PatientDashboard.getInstance().onSupportNavigateUp();

        detailFragmentArgs = AppointmentDetailFragmentArgs.fromBundle(getArguments());

        appointmentId = detailFragmentArgs.getAppointmentId();


        modelList = new ArrayList<>();
        oldList = new ArrayList<>();
        adapter = new FilesAdapter(modelList);
        detailBinding.recFiles.setAdapter(adapter);


        oldAppointmentAdapter = new OldAppointmentsAdapter(oldList, this);
        detailBinding.recOldAppointments.setAdapter(oldAppointmentAdapter);
        detailBinding.recOldAppointments.addItemDecoration(new
                DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));

        getAppointmentData(appointmentId);


        if (null != appointmentModel)
            Log.d(TAG, "onViewCreated: appointmentModel " + appointmentModel.toString());


        detailBinding.setClickListener(this);


        detailBinding.btnGoToChat.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_APPOINTMENT_ID, appointmentModel.getAppointmentId());
            bundle.putString("docId", String.valueOf(appointmentModel.getDoctorId()));
            bundle.putString("docName", String.valueOf(appointmentModel.getDoctorName()));
            bundle.putString("firstAppointmentId", String.valueOf(appointmentModel.getFirstAppointmentId()));

            navController.navigate(R.id.action_appointmentDetailFragment_to_chatForAppointmentFragment, bundle);

        });

        detailBinding.ivMap.setOnClickListener(view13 -> {
            String lat = String.valueOf(appointmentModel.getLatitude());
            String lng = String.valueOf(appointmentModel.getLongititude());
            if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
                Toast.makeText(requireActivity(), "location not found", Toast.LENGTH_SHORT).show();
                return;
            }
            openMap(lat, lng);
        });

        detailBinding.btnUpload.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("id", appointmentModel.getAppointmentId());
            navController.navigate(R.id.action_appointmentDetailFragment_to_uploadDocumentForAppointmentFragment, bundle);
        });

        detailBinding.btnReVisit.setOnClickListener(v -> {
            String model = getJSONFromModel(appointmentModel);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            bundle.putBoolean("reVisit", true);
            bundle.putInt("docFee", appointmentModel.getDoctorFees());
            bundle.putInt("docId", appointmentModel.getDoctorId());
            bundle.putInt("firstAppointmentId", appointmentModel.getFirstAppointmentId());
            Log.d(TAG, "onItemClick: " + model);

            navController.navigate(R.id.action_appointmentDetailFragment_to_reScheduleFragment, bundle);
        });


    }

    private void getAppointmentData(String appointmentId) {
        AppUtils.showRequestDialog(requireActivity());
        user = new User();
        user.setAppointmentId(appointmentId);
        viewModel.getAppointmentDetails(user).observe(getViewLifecycleOwner(), (List<AppointmentDetailsRes.Appointments> appointmentModels) -> {

            AppUtils.hideDialog();

            if (appointmentModels.isEmpty())
                return;

            String jsonString = appointmentModels.get(0).getAppointmentDetails().get(0).toString();
            Log.d(TAG, "onViewCreated: " + jsonString);
            appointmentModel = new OnlineAppointmentModel();

            Gson gson = new Gson();
            appointmentModel = gson.fromJson(jsonString, OnlineAppointmentModel.class);
            Log.d(TAG, "onViewCreated: appointmentModel " + appointmentModel.toString());
            detailBinding.setAppointmentModel(appointmentModel);

            addAppointmentRelatedData(appointmentModel.getAttachFile());

            detailBinding.uploadFilesView.setVisibility(appointmentModel.isPrescribed() ? View.GONE : appointmentModel.getExpiredStatus() == 1 ? View.GONE : View.VISIBLE);

            detailBinding.btnReVisit.setVisibility(appointmentModel.isPrescribed() ? appointmentModel.getIsVisit() == 1 ? View.VISIBLE : View.GONE : View.GONE);
            detailBinding.btnAction.setVisibility(View.VISIBLE);

            if (!appointmentModels.isEmpty()) {
                oldList.clear();
                oldList.addAll(appointmentModels.get(0).getOtherAppointments());
            }
            oldAppointmentAdapter.notifyDataSetChanged();
            AppUtils.hideDialog();

        });

    }

    public void addAppointmentRelatedData(String attachFile) {
        modelList.clear();
        Log.d(TAG, "addAppointmentRelatedData: " + attachFile);
        try {
            JSONArray jsonArray = new JSONArray(attachFile);
            for (int a = 0; a < jsonArray.length(); a++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(a);
                String filePath = jsonObject.getString("filePath");
                String fileType = jsonObject.getString("fileType");
                modelList.add(new FileModel(filePath, fileType));
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            adapter.notifyDataSetChanged();
        }
        updateUploadBtnTag();
    }

    public void updateUploadBtnTag() {
        if (null == adapter || adapter.getItemCount() == 0) {
            detailBinding.btnUpload.setText(getString(R.string.upload));
        } else if (adapter.getItemCount() > 0)
            detailBinding.btnUpload.setText(getString(R.string.add_more_files));
    }

    private void openMap(String lat, String lng) {

        Log.d(TAG, "openMap Lat: " + lat);
        Log.d(TAG, "openMap Long: " + lng);

        String url = "http://maps.google.com/maps?daddr=" + lat + "," + lng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onItemClick(Object object) {

        String tag = (String) detailBinding.btnAction.getTag();
        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) object;
        if (tag.equals(CONFIRMED)) {
            String model = getJSONFromModel(appointmentModel);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            bundle.putBoolean("reVisit", false);
            navController.navigate(R.id.action_appointmentDetailFragment_to_reScheduleFragment, bundle);
        } else if (tag.equals(PRESCRIBE)) {
            getMedicationData(appointmentModel.getAppointmentId());
        }

    }

    private void getMedicationData(String appointmentId) {
        ApiUtils.getPatientMedicationDetail(appointmentId, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<GetPatientMedicationMainModel> models = (List<GetPatientMedicationMainModel>) o;
                if (null == models || models.isEmpty())
                    return;

                GetPatientMedicationMainModel getPatientMedicationMainModels = models.get(0);
                Bundle bundle = new Bundle();
                bundle.putString("presModel", getJSONFromModel(getPatientMedicationMainModels));
                navController.navigate(R.id.action_appointmentDetailFragment_to_visitFragment, bundle);
            }

            @Override
            public void onError(String s) {
                AppUtils.hideDialog();
                Toast.makeText(requireActivity(), getString(R.string.retry), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                AppUtils.hideDialog();

            }
        });
    }

    @Override
    public void onItemClicked(Object o) {
        int pos = (int) o;
        detailBinding.recOldAppointments.scrollToPosition(pos);
    }
}