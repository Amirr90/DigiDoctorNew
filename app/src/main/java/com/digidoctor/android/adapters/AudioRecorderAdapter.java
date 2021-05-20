package com.digidoctor.android.adapters;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.UploadDocumentForAppointmentFragment;
import com.digidoctor.android.databinding.AudioVideoViewBinding;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AudioRecorderAdapter extends RecyclerView.Adapter<AudioRecorderAdapter.AudioVH> {
    private static final String TAG = "AudioRecorderAdapter";
    List<FileModel> audioUri;
    MediaPlayer mediaPlayer;
    FragmentActivity fragmentActivity;
    UploadDocumentForAppointmentFragment.onRecDeleteButtonClick onRecDeleteButtonClick;
    int currentPosition = -1;

    public AudioRecorderAdapter(List<FileModel> audioUri, FragmentActivity fragmentActivity, UploadDocumentForAppointmentFragment.onRecDeleteButtonClick onRecDeleteButtonClick) {
        this.audioUri = audioUri;
        this.fragmentActivity = fragmentActivity;
        this.onRecDeleteButtonClick = onRecDeleteButtonClick;
    }

    @NonNull
    @Override
    public AudioVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AudioVideoViewBinding videoViewBinding = AudioVideoViewBinding.inflate(inflater, parent, false);
        return new AudioVH(videoViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioVH holder, int position) {

        MediaPlayer mediaPlayer = MediaPlayer.create(fragmentActivity, audioUri.get(position).getAudioUri());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration());
        long hours = TimeUnit.MILLISECONDS.toHours(mediaPlayer.getDuration());

        String duration = hours + ":" + minutes + ":" + seconds;
        holder.videoViewBinding.tvDuration.setText(duration);

        holder.videoViewBinding.btnPlay.setOnClickListener(v -> {
            int code = (int) holder.videoViewBinding.btnPlay.getTag();
            Log.d(TAG, "TAG: " + code);
            Uri uri = audioUri.get(position).getAudioUri();
            playPauseStop(code, uri, holder);
        });
        holder.videoViewBinding.btnDelete.setOnClickListener(v -> {
            if (null != mediaPlayer) {
                mediaPlayer.stop();
                mediaPlayer.release();

                currentPosition = position;
            }
            onRecDeleteButtonClick.onClick(position);
        });
    }

    private void playPauseStop(int code, Uri uri, AudioVH holder) {
        switch (code) {
            case AppUtils.Pause:
                playRec(uri, holder);
                break;
            case AppUtils.Playing:
                stopRec(uri, holder);
                break;
        }
    }

    private void stopRec(Uri uri, AudioVH holder) {
        if (null != mediaPlayer)
            mediaPlayer.pause();
        holder.videoViewBinding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        holder.videoViewBinding.btnPlay.setTag(AppUtils.Pause);
        Toast.makeText(App.context, "Pause", Toast.LENGTH_SHORT).show();

        int code = (int) holder.videoViewBinding.btnPlay.getTag();
        Log.d(TAG, "stopRec: " + code);

    }

    private void playRec(Uri uri, AudioVH holder) {
        if (null == mediaPlayer)
            mediaPlayer = MediaPlayer.create(fragmentActivity, uri);
        mediaPlayer.start();
        holder.videoViewBinding.btnPlay.setTag(AppUtils.Playing);
        holder.videoViewBinding.btnPlay.setImageResource(R.drawable.ic_baseline_pause_24);
        Toast.makeText(App.context, "Playing", Toast.LENGTH_SHORT).show();
        holder.videoViewBinding.btnStatus.setText(fragmentActivity.getString(R.string.playing));

        long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration());
        String duration = minutes + ":" + seconds;
        Log.d(TAG, "duration: " + duration);
        holder.videoViewBinding.tvDuration.setText(duration);

        mediaPlayer.setOnCompletionListener(mp -> {
            holder.videoViewBinding.btnPlay.setTag(AppUtils.Pause);
            holder.videoViewBinding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            int code = (int) holder.videoViewBinding.btnPlay.getTag();
            Log.d(TAG, "onCompletion: " + code);
            holder.videoViewBinding.btnStatus.setText(fragmentActivity.getString(R.string.played));
        });


    }

    @Override
    public int getItemCount() {
        return null == audioUri ? 0 : audioUri.size();
    }

    public class AudioVH extends RecyclerView.ViewHolder {
        AudioVideoViewBinding videoViewBinding;

        public AudioVH(@NonNull AudioVideoViewBinding videoViewBinding) {
            super(videoViewBinding.getRoot());
            this.videoViewBinding = videoViewBinding;
        }
    }
}
