package com.digidoctor.android.interfaces;

import java.util.List;

public interface ApiCallbackInterface {
    void onSuccess(List<?> o);

    void onError(String s);

    void onFailed(Throwable throwable);
}
