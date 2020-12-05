package com.digidoctor.android.utility;

import com.digidoctor.android.interfaces.Api;

public class URLUtils {

    public static final String BASE_URL = "http://52.172.134.222:204/";
    public static final String BASE_URL_NEW_API = "http://52.172.134.222:205/api/v1.0/Patient/";

    final static String BASE_URL_TEST_CORE = "http://192.168.7.13:8095/api/Home/";
    //public static final String BASE_URL_TEST = "http://52.172.134.222:204/";


    private URLUtils() {
    }

    public static Api getAPIService() {

        return RetrofitClientDemo.getClient(BASE_URL).create(Api.class);

    }

    public static Api getAPIServiceCore() {

        return RetrofitClientDemo.getClient(BASE_URL_TEST_CORE).create(Api.class);

    }

    public static Api getAPIServiceNewAPI() {

        return RetrofitClient.getClient(BASE_URL_NEW_API).create(Api.class);

    }
}
