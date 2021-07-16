package com.digidoctor.android.repositories;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digidoctor.android.interfaces.ApiCallbackInterface;
import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.model.labmodel.PackageRes;
import com.digidoctor.android.model.labmodel.PackagesRes;
import com.digidoctor.android.model.labmodel.SearchRes;
import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;

import java.util.List;

public class LabRepo {


    public MutableLiveData<List<PackageDetail>> mutablePackagesLiveData;
    public MutableLiveData<PackageModel> mutablePackagesData;
    public MutableLiveData<List<LabModel>> mutableLabsData;
    public MutableLiveData<List<SearchRes.SearchModel>> mutableSearchData;

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

    public LiveData<List<LabModel>> getAllLabs() {
        if (mutableLabsData == null)
            mutableLabsData = new MutableLiveData<>();
        loadLabsDataData();
        return mutableLabsData;
    }

    private void loadLabsDataData() {
        ApiUtils.getLabData(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<LabModel> models = (List<LabModel>) o;
                if (null != models)
                    mutableLabsData.setValue(models);
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

    public LiveData<List<SearchRes.SearchModel>> getTestAndPackageData() {
        if (mutableSearchData == null) {
            mutableSearchData = new MutableLiveData<>();
            loadLabsSearchData();
        }
        return mutableSearchData;
    }

    private void loadLabsSearchData() {

        ApiUtils.searchLabsANdPackages(new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<SearchRes.SearchModel> models = (List<SearchRes.SearchModel>) o;
                if (null != models)
                    mutableSearchData.setValue(models);
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

    public MutableLiveData<String> walletMutable;

    public MutableLiveData<String> getWalletAmount() {
        if (null == walletMutable)
            walletMutable = new MutableLiveData<>();
        loadWalletAmount();
        return walletMutable;
    }

    private void loadWalletAmount() {
        ApiUtils.loadWalletAmount(new ApiUtils.WalletInterface() {
            @Override
            public void onSuccess(Object obj) {
                walletMutable.setValue((String) obj);
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
            }

        });
    }
}
