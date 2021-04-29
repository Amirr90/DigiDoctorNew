package com.digidoctor.android.model.patientModel;

import java.util.List;

public class HospitalAndPackageResponse {

    private List<HospitalModel> hospitalList;
    private List<PackageModel> packageList;

    public List<HospitalModel> getHospitalList() {
        return hospitalList;
    }

    public List<PackageModel> getPackageList() {
        return packageList;
    }

    public static class PackageModel {
        Integer id;
        String packageName;

        public Integer getId() {
            return id;
        }

        public String getPackageName() {
            return packageName;
        }
    }
}
