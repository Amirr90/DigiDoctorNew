package com.digidoctor.android.adapters;

import android.app.AlertDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.StethoViewBinding;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.utility.App;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;

import java.io.IOException;
import java.util.List;

public class StethoAdapter extends RecyclerView.Adapter<StethoAdapter.StethoVH> {
    private static final String TAG = "StethoAdapter";
    List<FileModel> fileModels;
    FragmentActivity fragmentActivity;
    MediaPlayer mediaPlayer;

    int currentPosition = -1;

    public StethoAdapter(List<FileModel> fileModels, FragmentActivity fragmentActivity) {
        this.fileModels = fileModels;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public StethoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StethoViewBinding viewBinding = StethoViewBinding.inflate(inflater, parent, false);
        return new StethoVH(viewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull StethoVH holder, int position) {

        holder.viewBinding.setFileModel(fileModels.get(position));
        holder.viewBinding.btnPlay.setOnClickListener(v -> {
            String uri = fileModels.get(position).getFilePath();
            int code = (int) holder.viewBinding.btnPlay.getTag();
            if (utils.isNetworkConnected(App.context)) {
                if (position != currentPosition) {
                    if (null != mediaPlayer)
                        try {
                            mediaPlayer.stop();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                }

                currentPosition = position;


                if (code == AppUtils.Pause) {
                    holder.viewBinding.btnPlay.setTag(AppUtils.Playing);
                    holder.viewBinding.btnPlay.setImageResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer = new MediaPlayer();
                    playAudio(uri, holder, mediaPlayer);
                } else if (code == AppUtils.Playing) {
                    holder.viewBinding.btnPlay.setTag(AppUtils.Pause);
                    holder.viewBinding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    if (null != mediaPlayer) {
                        try {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else
                Toast.makeText(fragmentActivity, "No internet", Toast.LENGTH_SHORT).show();
        });
    }



    @Override
    public int getItemCount() {
        return null == fileModels ? 0 : fileModels.size();
    }

    public static class StethoVH extends RecyclerView.ViewHolder {
        StethoViewBinding viewBinding;

        public StethoVH(@NonNull StethoViewBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }

    private void playAudio(String audioUrl, StethoVH holder, MediaPlayer mediaPlayer) {

        AppUtils.showRequestDialog(fragmentActivity);

        try {
            Uri uri = Uri.parse(audioUrl);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(fragmentActivity, uri);
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.setOnPreparedListener(mp -> {
                AppUtils.hideDialog();
            });

            mediaPlayer.setOnCompletionListener(mp -> {
                holder.viewBinding.btnPlay.setTag(AppUtils.Pause);
                holder.viewBinding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                holder.viewBinding.btnStatus.setText(fragmentActivity.getString(R.string.played));
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
