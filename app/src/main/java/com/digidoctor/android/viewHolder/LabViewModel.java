package com.digidoctor.android.viewHolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.digidoctor.android.model.labmodel.PackageDetail;
import com.digidoctor.android.repositories.LabRepo;

import java.util.List;

public class LabViewModel extends ViewModel {
    LabRepo labRepo = new LabRepo();

    public LiveData<List<PackageDetail>> packageLiveData() {
        return labRepo.getPackageLiveData();
    }
}