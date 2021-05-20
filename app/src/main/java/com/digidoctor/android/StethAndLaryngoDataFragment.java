package com.digidoctor.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.digidoctor.android.adapters.LaryingoAdapter;
import com.digidoctor.android.adapters.StethoAdapter;
import com.digidoctor.android.databinding.FragmentStethAndLaryngoDataBinding;
import com.digidoctor.android.interfaces.AdapterInterface;
import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.FileModel;
import com.digidoctor.android.utility.ApiRequestModel;
import com.digidoctor.android.utility.ApiUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.getUserForBooking;


public class StethAndLaryngoDataFragment extends Fragment {


    FragmentStethAndLaryngoDataBinding binding;
    NavController navController;
    List<FileModel> recAudioList;
    StethoAdapter audioRecorderAdapter;
    LaryingoAdapter laryingoAdapter;
    String type;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStethAndLaryngoDataBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        if (null == getArguments())
            return;

        type = getArguments().getString("type");
        if (type.equalsIgnoreCase("stetho")) {
            setUpAudioRecView();
        } else {
            // TODO: 19-05-2021
            setUpVideoRecView();
        }

        loadData();
    }

    private void setUpVideoRecView() {
        recAudioList = new ArrayList<>();
        laryingoAdapter = new LaryingoAdapter(recAudioList, adapterInterface);
        binding.recSte.setAdapter(laryingoAdapter);
        binding.recSte.addItemDecoration(new
                DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));
    }

    private void loadData() {
        ApiRequestModel requestModel = new ApiRequestModel();
        requestModel.setRecordingType(type);
        requestModel.setMemberId(getUserForBooking(requireActivity()).getMemberId());
        ApiUtils.getData(requestModel, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<FileModel> recordingModelList = (List<FileModel>) o;
                if (null == recordingModelList) {
                    Toast.makeText(requireActivity(), "something went wrong !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (recordingModelList.isEmpty()) {
                    Toast.makeText(requireActivity(), "No Data !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                recAudioList.addAll(recordingModelList);

                if (null != audioRecorderAdapter)
                    audioRecorderAdapter.notifyDataSetChanged();
                if (null != laryingoAdapter)
                    laryingoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(requireActivity(), "s", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private void setUpAudioRecView() {
        recAudioList = new ArrayList<>();
        audioRecorderAdapter = new StethoAdapter(recAudioList, requireActivity());
        binding.recSte.setAdapter(audioRecorderAdapter);
        binding.recSte.addItemDecoration(new
                DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));
    }


    AdapterInterface adapterInterface = new AdapterInterface() {
        @Override
        public void onItemClicked(Object o) {
            String url = (String) o;
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            navController.navigate(R.id.action_stethAndLaryngoDataFragment_to_videoFragmentFragment, bundle);
        }
    };
}