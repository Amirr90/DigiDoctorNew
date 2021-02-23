package com.digidoctor.android;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
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

import com.digidoctor.android.adapters.FilesAdapter;
import com.digidoctor.android.databinding.FragmentUploadDocumentForAppointmentBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.model.UploadPresDataModel;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.GetAudioRecorder;
import com.digidoctor.android.view.activity.PatientDashboard;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;


public class UploadDocumentForAppointmentFragment extends Fragment implements AdapterInterface {
    private static final String TAG = "UploadDocumentForAppoin";

    FragmentUploadDocumentForAppointmentBinding uploadBinding;
    FilesAdapter adapter;
    List<FileModel> modelList;

    AndroidAudioRecorder audioRecorder;
    String appointmentId = null;

    NavController navController;


    MediaRecorder myAudioRecorder;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uploadBinding = FragmentUploadDocumentForAppointmentBinding.inflate(getLayoutInflater());
        return uploadBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myAudioRecorder = new MediaRecorder();

        navController = Navigation.findNavController(view);

        modelList = new ArrayList<>();
        adapter = new FilesAdapter(modelList, this);
        uploadBinding.recFiles.setAdapter(adapter);

        if (getArguments() == null)
            PatientDashboard.getInstance().onSupportNavigateUp();

        appointmentId = getArguments().getString("id");
        audioRecorder = GetAudioRecorder.getInstance(requireActivity());


        uploadBinding.laySelectImage.setOnClickListener(view1 -> selectImage());
        uploadBinding.laySelectAudio.setOnClickListener(view1 -> selectAudio());
        uploadBinding.laySelectVideo.setOnClickListener(view1 -> selectVideo());
        uploadBinding.btnUploadFiles.setOnClickListener(view1 -> sendFiles());

    }

    private void sendFiles() {
        if (modelList.isEmpty()) {
            Toast.makeText(requireActivity(), "Add Some Image/Audio/Video ", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            uploadFiles();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "sendFiles: " + e.getLocalizedMessage());
            Toast.makeText(requireActivity(), getString(R.string.retry), Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadFiles() throws IOException {
        List<String> list = new ArrayList<>();
        for (FileModel fileModel : modelList)
            list.add(fileModel.getFilePath());

        ApiUtils.uploadMultipleFile(list, requireActivity(), new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                AppUtils.hideDialog();
                List<String> filePaths = (List<String>) o;
                if (null == filePaths)
                    return;
                UploadPresDataModel uploadPresDataModel = new UploadPresDataModel();
                uploadPresDataModel.setAppointmentId(appointmentId);
                uploadPresDataModel.setDtDataTable(filePaths.get(0));
                AppUtils.showRequestDialog(requireActivity());
                ApiUtils.saveAttachmentAfterBooking(uploadPresDataModel, new ApiCallbackInterface() {
                    @Override
                    public void onSuccess(List<?> o) {
                        AppUtils.hideDialog();
                        Toast.makeText(requireActivity(), "Document send successfully", Toast.LENGTH_SHORT).show();
                        PatientDashboard.getInstance().onSupportNavigateUp();

                    }

                    @Override
                    public void onError(String s) {
                        Toast.makeText(requireActivity(), s, Toast.LENGTH_SHORT).show();
                        AppUtils.hideDialog();

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        Toast.makeText(requireActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        AppUtils.hideDialog();

                    }
                });
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


    private void selectImage() {
        ImagePicker.Companion.with(this)
                .crop(8f, 12f)
                .compress(512)
                .maxResultSize(1080, 1080)
                .start();
    }

    private void selectAudio() {
      //  audioRecorder.record();
         Toast.makeText(requireActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
    }

    private void selectVideo() {
        Toast.makeText(requireActivity(), getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (null != data) {
                try {
                    FileModel fileModel = new FileModel();
                    Uri uri = data.getData();
                    fileModel.setFilePath(uri.toString());
                    updateImageRecyclerView(fileModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void updateImageRecyclerView(FileModel fileModel) {

        if (!adapter.addItem(fileModel)) {
            Toast.makeText(requireActivity(), "File Already Added", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClicked(Object o) {
        adapter.removeItem((int) o);
    }


}