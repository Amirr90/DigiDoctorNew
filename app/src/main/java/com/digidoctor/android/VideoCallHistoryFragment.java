package com.digidoctor.android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import com.digidoctor.android.adapters.CategoryViewHolder;
import com.digidoctor.android.databinding.CallHistoryViewBinding;
import com.digidoctor.android.databinding.FragmentVideoCallHistoryBinding;
import com.digidoctor.android.model.CallModel;
import com.digidoctor.android.utility.AppUtils;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.utils.fadeIn;

public class VideoCallHistoryFragment extends Fragment {


    FragmentVideoCallHistoryBinding binding;
    private static final String TAG = "VideoCallHistoryFragmen";

    PatientViewModel viewModel;
    FirestorePagingAdapter adapter;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVideoCallHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.getRoot().setAnimation(fadeIn(requireActivity()));

        viewModel = new ViewModelProvider(requireActivity()).get(PatientViewModel.class);


        binding.btnAllCall.setOnClickListener(v -> {
            updateColorState(binding.btnAllCall);
            getCallData(AppUtils.CALL_ALL);
        });
        binding.btnMissedCall.setOnClickListener(v -> {
            updateColorState(binding.btnMissedCall);
            getCallData(AppUtils.CALL_MISSED);
        });
    }

    private void updateColorState(Button btnId) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getCallData(AppUtils.CALL_ALL);

        AppUtils.showToolbar(requireActivity());
    }

    private void getCallData(String callType) {
        AppUtils.showRequestDialog(requireActivity());
        Query query;

        if (callType.equalsIgnoreCase(AppUtils.CALL_MISSED)) {
            query = AppUtils.getFirestoreReference().collection(AppUtils.VIDEO_CALLS_DEMO)
                    .whereEqualTo(AppUtils.UID, "" + utils.getUserForBooking(requireActivity()).getId())
                    .whereEqualTo(AppUtils.CALL_STATUS, AppUtils.CALL_MISSED)
                    .orderBy(AppUtils.callInitiatedTimestamp, Query.Direction.DESCENDING);

        } else {
            query = AppUtils.getFirestoreReference().collection(AppUtils.VIDEO_CALLS_DEMO)
                    .whereEqualTo(AppUtils.UID, "" + utils.getUserForBooking(requireActivity()).getId())
                    .orderBy(AppUtils.callInitiatedTimestamp, Query.Direction.DESCENDING);
        }

        query.get().addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getLocalizedMessage()));

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(5)
                .build();

        FirestorePagingOptions<CallModel> options1 = new FirestorePagingOptions.Builder<CallModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, CallModel.class).build();

        adapter = new FirestorePagingAdapter<CallModel, CategoryViewHolder>(options1) {
            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                CallHistoryViewBinding binding = CallHistoryViewBinding.inflate(inflater, parent, false);
                return new CategoryViewHolder(binding);
            }


            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull final CallModel model) {
                holder.binding.setCall(model);
                int color = getResources().getColor(R.color.text_color);
                if (model.getCallStatus().equalsIgnoreCase(AppUtils.CALL_MISSED)) {
                    holder.binding.textView245.setTextColor(getResources().getColor(R.color.red));
                } else holder.binding.textView245.setTextColor(color);
            }


            @Override
            protected void onError(@NonNull Exception e) {
                super.onError(e);
                hideDialog();
                Timber.d("onError: %s", e.getLocalizedMessage());
            }

            @Override
            protected void onLoadingStateChanged(@NonNull LoadingState state) {
                super.onLoadingStateChanged(state);
                switch (state) {
                    case ERROR: {
                        hideDialog();
                        Timber.d("onLoadingStateChanged: error ");
                        Toast.makeText(requireActivity(), "failed to get Data !!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case FINISHED: {
                        hideDialog();
                        Timber.d("onLoadingStateChanged: FINISHED");
                    }
                    break;
                    case LOADED: {
                        hideDialog();
                        Timber.d("onLoadingStateChanged: LOADED %s", getItemCount());
                    }
                    case LOADING_MORE: {
                        Timber.d("onLoadingStateChanged: LOADING_MORE");
                    }
                    case LOADING_INITIAL: {
                        hideDialog();
                        Timber.d("onLoadingStateChanged: LOADING_INITIAL");

                    }
                    break;
                }
            }
        };

        binding.recCallHistory.setHasFixedSize(true);
        binding.recCallHistory.setAdapter(adapter);
    }

}