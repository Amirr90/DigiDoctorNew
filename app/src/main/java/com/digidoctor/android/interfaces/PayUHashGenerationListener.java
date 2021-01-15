package com.digidoctor.android.interfaces;

import java.util.HashMap;

public interface PayUHashGenerationListener {
    void onHashGenerated(HashMap<String, String> map);
}
