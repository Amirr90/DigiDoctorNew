package com.digidoctor.android.utility;

import com.digidoctor.android.interfaces.Api;

public class URLUtils {
    public static final String BASE_URL_NEW_API = "http://52.172.134.222:205/api/v1.0/Patient/";
    public static final String BASE_URL_NEW_API_DOCTOR = "http://52.172.134.222:205/api/v1.0/Doctor/";
    public static final String BASE_URL_NEW_API_PHARMACY = "http://52.172.134.222:205/api/v1.0/Pharmacy/";
    final static String BASE_URL_LAB_API = "http://52.172.134.222:205/api/v1.0/Lab/";
    final static String MedicineReportAPI = "http://182.156.200.179:330/";



  /*  //Test
    public static final String BASE_URL_NEW_API = "http://52.172.134.222:207/api/v1.0/Patient/";
    public static final String BASE_URL_NEW_API_DOCTOR = "http://52.172.134.222:207/api/v1.0/Doctor/";
    public static final String BASE_URL_NEW_API_PHARMACY = "http://52.172.134.222:207/api/v1.0/Pharmacy/";
    final static String BASE_URL_LAB_API = "http://52.172.134.222:207/api/v1.0/Lab/";
    final static String MedicineReportAPI = "http://182.156.200.179:330/";*/




    public static Api getAPIServiceForPatient() {
        return RetrofitClient.getClient(BASE_URL_NEW_API).create(Api.class);
    }

    public static Api getAPIServiceNewAPIForDoctor() {
        return RetrofitClient.getClient(BASE_URL_NEW_API_DOCTOR).create(Api.class);
    }

    public static Api getAPIServiceNewAPIForPharmacy() {
        return RetrofitClient.getClient(BASE_URL_NEW_API_PHARMACY).create(Api.class);
    }

    public static Api getLabApisRef() {
        return RetrofitClient.getClient(BASE_URL_LAB_API).create(Api.class);
    }

    public static Api getPharmacyApisRef() {

        return RetrofitClient.getClient(BASE_URL_NEW_API_PHARMACY).create(Api.class);

    }

    public static Api getMedicineDetails() {

        return RetrofitClient.getClient(MedicineReportAPI).create(Api.class);

    }
}
