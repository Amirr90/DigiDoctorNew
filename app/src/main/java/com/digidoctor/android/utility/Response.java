package com.digidoctor.android.utility;

import androidx.annotation.NonNull;

import com.digidoctor.android.interfaces.ApiInterface;
import com.digidoctor.android.model.labmodel.ApiLabResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;

public class Response {
    public static void getResponse(Call<ApiResponse> call, ApiInterface apiCallbackInterface) {
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NotNull retrofit2.Response<ApiResponse> response) {
                if (response.code() == 200 && null != response.body()) {
                    if (response.body().getResponseCode() == 0) {
                        apiCallbackInterface.onFailed(response.body().getResponseMessage());
                    } else apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else apiCallbackInterface.onFailed("Error " + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
                apiCallbackInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

    public static void getDashboardResponse(Call<ApiLabResponse> call, ApiInterface apiCallbackInterface) {
        call.enqueue(new Callback<ApiLabResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiLabResponse> call, @NotNull retrofit2.Response<ApiLabResponse> response) {
                if (response.code() == 200 && null != response.body()) {
                    if (response.body().getResponseCode() == 0) {
                        apiCallbackInterface.onFailed(response.body().getResponseMessage());
                    } else apiCallbackInterface.onSuccess(response.body().getResponseValue());
                } else apiCallbackInterface.onFailed("Error " + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ApiLabResponse> call, @NonNull Throwable t) {
                apiCallbackInterface.onFailed(t.getLocalizedMessage());
            }
        });
    }

}
