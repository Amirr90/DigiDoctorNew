package com.digidoctor.android.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.model.labmodel.PackageRes;
import com.digidoctor.android.model.labmodel.PackagesRes;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;

import java.util.List;

public class LabRepo {
    public MutableLiveData<List<PackageDetail>> mutablePackagesLiveData;
    public MutableLiveData<PackageModel> mutablePackagesData;

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
                List<PackagesRes.Packages> packageDetails = (List<PackagesRes.Packages>) o;
                if (!packageDetails.isEmpty()) {

                    mutablePackagesLiveData.setValue(packageDetails.get(0).getPackageDetails());
                }
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

    public LiveData<PackageModel> getPackageData(String packageID) {
        if (mutablePackagesData == null)
            mutablePackagesData = new MutableLiveData<>();
        loadPackageDataById(packageID);
        return mutablePackagesData;
    }

    private void loadPackageDataById(String packageID) {
        ApiUtils.loadPackageDataById(packageID, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {

                List<PackageRes.Packages> packages = (List<PackageRes.Packages>) o;
                if (packages.isEmpty() || packages.get(0).getPackageDetails().isEmpty())
                    return;

                PackageModel packageModel = packages.get(0).getPackageDetails().get(0);
                if (null != packageModel)
                    mutablePackagesData.setValue(packageModel);
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
