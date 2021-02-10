package com.digidoctor.android.model.patientModel;

import java.util.List;

public class DoctorModelRes {

    List<DoctorModel> recomendedDoctor;
    List<DoctorModel> popularDoctor;

    public List<DoctorModel> getRecomendedDoctor() {
        return recomendedDoctor;
    }

    public List<DoctorModel> getPopularDoctor() {
        return popularDoctor;
    }
}
