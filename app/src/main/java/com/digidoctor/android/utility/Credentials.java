package com.digidoctor.android.utility;

import com.digidoctor.android.interfaces.PayUHashGenerationListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class Credentials {
    private String LIVE_KEY = "rzp_live_BwhTaXRxeklaAI";
    private String TEST_KEY = "rzp_test_ErUo3tsXqnIjiP";

    public String getLIVE_KEY() {
        return LIVE_KEY;
    }

    public String getTEST_KEY() {
        return TEST_KEY;
    }

}
