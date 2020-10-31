package com.digidoctor.android.utility;

import java.util.List;

public interface ApiCallbackInterface {
    void onSuccess(List<?> o);

    void onError(String s);

    void onFailed(Throwable throwable);
}
