package com.digidoctor.android.utility;

import com.digidoctor.android.interfaces.Api;

public class URLUtils {

    public static final String BASE_URL = "http://52.172.134.222:204/";

    final static String BASE_URL_TEST_CORE = "http://192.168.7.13:8095/api/Home/";
    //public static final String BASE_URL_TEST = "http://52.172.134.222:204/";


    private URLUtils() {
    }

    public static Api getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(Api.class);

    }

/*    public static Api getAPIServiceTest() {
        return RetrofitClientTest.getClient("BASE_URL_TEST").create(Api.class);

    }*/

    public static Api getAPIServiceCore() {

        return RetrofitClientDemo.getClient(BASE_URL_TEST_CORE).create(Api.class);

    }
}