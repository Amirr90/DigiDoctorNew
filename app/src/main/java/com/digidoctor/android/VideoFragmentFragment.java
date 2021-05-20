package com.digidoctor.android;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.digidoctor.android.databinding.FragmentVideoFragmentBinding;

import org.jetbrains.annotations.NotNull;


public class VideoFragmentFragment extends Fragment {


    FragmentVideoFragmentBinding binding;
    NavController navController;
    String url;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        if (getArguments() == null)
            navController.navigateUp();

        url = getArguments().getString("url");


        setUpVideoView();
    }

    private void setUpVideoView() {
        binding.videoView.setVideoPath(url);

        binding.videoView.setOnPreparedListener(mp -> {
            binding.progressBar.setVisibility(View.GONE);
            mp.start();

            float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
            float screenRatio = binding.videoView.getWidth() / (float) binding.videoView.getHeight();
            float scale = videoRatio / screenRatio;
            if (scale >= 1f) {
                binding.videoView.setScaleX(scale);
            } else {
                binding.videoView.setScaleY(1f / scale);
            }


        });

        binding.videoView.setOnCompletionListener(MediaPlayer::start);
    }
}