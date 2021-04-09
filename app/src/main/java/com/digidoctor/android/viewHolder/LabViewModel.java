package com.digidoctor.android.viewHolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.digidoctor.android.model.LabModel;
import com.digidoctor.android.model.PackageModel;
import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.model.labmodel.SearchRes;
import com.digidoctor.android.repositories.LabRepo;

import java.util.List;

public class LabViewModel extends ViewModel {
    LabRepo labRepo = new LabRepo();

    public LiveData<List<PackageDetail>> packageLiveData() {
        return labRepo.getPackageLiveData();
    }

    public LiveData<PackageModel> getPackageData(String packageID) {
        return labRepo.getPackageData(packageID);
    }

    public LiveData<List<LabModel>> getAllLabs() {
        return labRepo.getAllLabs();
    }

    public LiveData<List<SearchRes.SearchModel>> getTestAndPackageData() {
        return labRepo.getTestAndPackageData();
    }

    /*public LiveData<List<PackageDetail>> getAllCategoryData() {
    }*/
}
