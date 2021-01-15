package com.digidoctor.android.view.fragments.digiDoctorFragments;

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

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.FragmentAppointmentDetailBinding;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.interfaces.OnClickListener;
import com.digidoctor.android.model.GetPatientMedicationMainModel;
import com.digidoctor.android.model.OnlineAppointmentModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.digidoctor.android.utility.utils.KEY_APPOINTMENT_ID;
import static com.digidoctor.android.utility.utils.getJSONFromModel;


public class AppointmentDetailFragment extends Fragment implements OnClickListener {
    private static final String TAG = "AppointmentDetailFragme";


    NavController navController;
    OnlineAppointmentModel appointmentModel;

    FragmentAppointmentDetailBinding detailBinding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        detailBinding = FragmentAppointmentDetailBinding.inflate(getLayoutInflater());
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

        detailBinding.setAppointmentModel(appointmentModel);
        detailBinding.setClickListener(this);

        detailBinding.btnGoToChat.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString(KEY_APPOINTMENT_ID, appointmentModel.getAppointmentId());
            bundle.putString("docId", String.valueOf(appointmentModel.getDoctorId()));
            bundle.putString("docName", String.valueOf(appointmentModel.getDoctorName()));
            navController.navigate(R.id.action_appointmentDetailFragment_to_chatForAppointmentFragment, bundle);

        });
        detailBinding.btnUpload.setOnClickListener(view12 -> navController.navigate(R.id.action_appointmentDetailFragment_to_uploadDocumentForAppointmentFragment));
    }

    @Override
    public void onItemClick(Object object) {


        OnlineAppointmentModel appointmentModel = (OnlineAppointmentModel) object;

        if (detailBinding.btnAction.getText().toString().equalsIgnoreCase("Reschedule Appointment")) {
            String model = getJSONFromModel(appointmentModel);
            Bundle bundle = new Bundle();
            bundle.putString("model", model);
            Log.d(TAG, "onItemClick: " + model.toString());
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