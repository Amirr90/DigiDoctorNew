package com.digidoctor.android.utility;

import android.os.Environment;

import androidx.fragment.app.Fragment;

import com.digidoctor.android.R;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

public class GetAudioRecorder {
    private static final String TAG = "GetAudioRecorder";

    public static AndroidAudioRecorder getInstance(Fragment fragment) {
        String filePath = Environment.getExternalStorageDirectory() + "/recorded_audio.wav";
        int color = fragment.getResources().getColor(R.color.colorPrimaryDark);
        int requestCode = 0;

        return AndroidAudioRecorder.with(fragment)
                .setFilePath(filePath)
                .setColor(color)
                .setRequestCode(requestCode)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(true)
                .setKeepDisplayOn(true);
    }
}
