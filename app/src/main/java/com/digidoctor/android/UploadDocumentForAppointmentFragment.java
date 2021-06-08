package com.digidoctor.android;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.digidoctor.android.adapters.AudioRecorderAdapter;
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


public class UploadDocumentForAppointmentFragment extends Fragment implements AdapterInterface, MediaPlayer.OnCompletionListener {
    private static final String TAG = "UploadDocumentForAppoin";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final String CAMERA_PREF = "cameraPref";
    private static final String ALLOW_KEY = "allow";
    private static final int REQ_CODE_SELECT_VIDEO = 12;
    private static final int REQ_CODE_SELECT_LARYNGO_VIDEO = 13;
    private static final int REQ_CODE_SELECT_STETHO_AUDIO = 100;
    private static final int REQ_CODE_SELECT_AUDIO = 101;

    FragmentUploadDocumentForAppointmentBinding uploadBinding;
    FilesAdapter adapter;
    List<FileModel> modelList;
    List<FileModel> recAudioList;

    AndroidAudioRecorder audioRecorder;
    String appointmentId = null;

    NavController navController;
    MediaRecorder myAudioRecorder;
    AudioRecorderAdapter audioRecorderAdapter;
    String recordingType = "";


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


        setUpAudioRecView();

        appointmentId = getArguments().getString("id");
        audioRecorder = GetAudioRecorder.getInstance(requireActivity());


        uploadBinding.laySelectImage.setOnClickListener(view1 -> selectImage());
        uploadBinding.laySelectAudio.setOnClickListener(view1 -> selectAudio());
        uploadBinding.laySelectVideo.setOnClickListener(view1 -> selectVideo(REQ_CODE_SELECT_VIDEO));
        uploadBinding.btnUploadFiles.setOnClickListener(view1 -> sendFiles());
        uploadBinding.recordAudio.setOnClickListener(view1 -> startRec());
        uploadBinding.layVideo.setOnClickListener(view1 -> selectVideo(REQ_CODE_SELECT_LARYNGO_VIDEO));

    }

    private void setUpAudioRecView() {
        recAudioList = new ArrayList<>();
        audioRecorderAdapter = new AudioRecorderAdapter(recAudioList, requireActivity(), position -> {
            if (recAudioList != null) {
                recAudioList.remove(position);
                audioRecorderAdapter.notifyDataSetChanged();
            }
        });
        uploadBinding.recSte.setAdapter(audioRecorderAdapter);
        uploadBinding.recSte.addItemDecoration(new
                DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));
    }

    private void startRec() {

        if (recordingType.isEmpty() || recordingType.equalsIgnoreCase("stetho")) {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            startActivityForResult(intent, REQ_CODE_SELECT_STETHO_AUDIO);
        } else
            Toast.makeText(requireActivity(), "can't select Stethoscope audio at this time !!", Toast.LENGTH_SHORT).show();


    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                (dialog, which) -> dialog.dismiss());

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                (dialog, which) -> {
                    dialog.dismiss();
                    startInstalledAppDetailsActivity(requireActivity());
                });

        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera(int reqCode) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, reqCode);
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                (dialog, which) -> dialog.dismiss());

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                (dialog, which) -> {
                    dialog.dismiss();
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                });
        alertDialog.show();
    }

    private void sendFiles() {
        if (modelList.isEmpty() && recAudioList.isEmpty()) {
            Toast.makeText(requireActivity(), "Add Some Image/Audio/Video/Stethoscope sound  ", Toast.LENGTH_SHORT).show();
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
        if (!modelList.isEmpty())
            for (FileModel fileModel : modelList)
                list.add(fileModel.getFilePath());

        if (!recAudioList.isEmpty())
            for (FileModel fileModel : recAudioList)
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
                uploadPresDataModel.setRecordingType(recordingType);

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
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, REQ_CODE_SELECT_AUDIO);
    }

    private void selectVideo(int reqCode) {

        if (recordingType.isEmpty() || recordingType.equalsIgnoreCase("laryngoscope")) {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (getFromPref(requireActivity(), ALLOW_KEY)) {
                    showSettingsAlert();
                } else if (ContextCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                            Manifest.permission.CAMERA)) {
                        showAlert();
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
            } else {
                openCamera(reqCode);
            }
        } else
            Toast.makeText(requireActivity(), "cant add laryngoscope at this time", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode != 100) {
            if (null != data) {
                try {
                    FileModel fileModel = new FileModel();
                    Uri uri = data.getData();
                    fileModel.setFilePath(uri.toString());
                    if (requestCode == REQ_CODE_SELECT_VIDEO) {
                        fileModel.setThumbnail(R.drawable.video_file);
                        fileModel.setFileType("mp4");
                    }
                    if (requestCode == REQ_CODE_SELECT_LARYNGO_VIDEO) {
                        recordingType = "laryngoscope";
                        fileModel.setThumbnail(R.drawable.laryngoscope);
                        fileModel.setFileType("mp4");
                    }

                    updateImageRecyclerView(fileModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_SELECT_STETHO_AUDIO) {
            Uri uri = data.getData();
            Log.d(TAG, "onActivityResult: Uri " + uri);
            FileModel fileModel = new FileModel();
            fileModel.setAudioUri(uri);
            recordingType = "stetho";
            try {
                FileModel audioList = new FileModel();
                Uri uri2 = data.getData();
                audioList.setFilePath(uri2.toString());
                audioList.setAudioUri(uri);
                recAudioList.add(audioList);

                audioRecorderAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public interface onRecDeleteButtonClick {
        void onClick(int position);
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


    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion: ");
    }
}