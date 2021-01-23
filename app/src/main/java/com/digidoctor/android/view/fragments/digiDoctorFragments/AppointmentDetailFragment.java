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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.FilesAdapter;
import com.digidoctor.android.databinding.FragmentAppointmentDetailBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.getJSONFromModel;


public class AppointmentDetailFragment extends Fragment implements OnClickListener {
    private static final String TAG = "AppointmentDetailFragme";


    NavController navController;
    OnlineAppointmentModel appointmentModel;

    FragmentAppointmentDetailBinding detailBinding;

    FilesAdapter adapter;
    List<FileModel> modelList;


    public static AppointmentDetailFragment instance;

    public static AppointmentDetailFragment getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailBinding = FragmentAppointmentDetailBinding.inflate(getLayoutInflater());
        instance = this;
        return detailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        //getting Model
        if (null == getArguments())
            return;


        String jsonString = getArguments().getString("model");
        appointmentModel = new OnlineAppointmentModel();
        Gson gson = new Gson();
        appointmentModel = gson.fromJson(jsonString, OnlineAppointmentModel.class);

        Log.d(TAG, "onViewCreated: appointmentModel " + appointmentModel.toString());

        detailBinding.setAppointmentModel(appointmentModel);
        detailBinding.setClickListener(this);

        detailBinding.uploadFilesView.setVisibility(appointmentModel.isPrescribed() ? View.GONE : appointmentModel.getExpiredStatus() == 1 ? View.GONE : View.VISIBLE);

        detailBinding.btnGoToChat.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_APPOINTMENT_ID, appointmentModel.getAppointmentId());
            bundle.putString("docId", String.valueOf(appointmentModel.getDoctorId()));
            bundle.putString("docName", String.valueOf(appointmentModel.getDoctorName()));
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


        modelList = new ArrayList<>();
        adapter = new FilesAdapter(modelList);
        detailBinding.recFiles.setAdapter(adapter);

        addAppointmentRelatedData(appointmentModel.getAttachFile());


    }

    public void addAppointmentRelatedData(String attachFile) {
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


        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) object;

        if (detailBinding.btnAction.getText().toString().equalsIgnoreCase("Reschedule Appointment")) {
            String model = getJSONFromModel(appointmentModel);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            Log.d(TAG, "onItemClick: " + model);
            navController.navigate(R.id.action_appointmentDetailFragment_to_reScheduleFragment, bundle);
        } else if (detailBinding.btnAction.getText().toString().equalsIgnoreCase("View Prescription")) {

            getMedicationData(appointmentModel.getAppointmentId());
        }
    }

    private void getMedicationData(String appointmentId) {
        AppUtils.showRequestDialog(requireActivity());
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
                Log.d("TAG", "onItemClickedString: " + getJSONFromModel(getPatientMedicationMainModels));
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
}