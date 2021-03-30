package com.digidoctor.android.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;

import java.util.List;

public class LabRepo {
    public MutableLiveData<List<PackageDetail>> mutablePackagesLiveData;

    public LiveData<List<PackageDetail>> getPackageLiveData() {
        if (mutablePackagesLiveData == null)
            mutablePackagesLiveData = new MutableLiveData<>();
        loadPackageData();
        return mutablePackagesLiveData;
    }

    private void loadPackageData() {
        ApiUtils.getPackageData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<PackageDetail> packageDetails = (List<PackageDetail>) o;
                mutablePackagesLiveData.setValue(packageDetails);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(App.context, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Throwable throwable) {
                Toast.makeText(App.context, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
