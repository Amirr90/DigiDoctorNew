package com.digidoctor.android.utility;

import com.digidoctor.android.interfaces.Api;

public class URLUtils {

    public static final String BASE_URL_NEW_API = "http://52.172.134.222:205/api/v1.0/Patient/";

    public static Api getAPIServiceNewAPI() {

        return RetrofitClient.getClient(BASE_URL_NEW_API).create(Api.class);

    }
}
