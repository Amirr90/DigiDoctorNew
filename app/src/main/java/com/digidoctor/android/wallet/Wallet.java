package com.digidoctor.android.wallet;

import android.app.Activity;
import android.widget.Toast;

import com.digidoctor.android.utility.ApiUtils;
import com.digidoctor.android.utility.App;

public class Wallet {

    Activity activity;
    WalletInterface walletInterface;
    Integer walletAmount = 0;

    public Wallet(Activity activity, WalletInterface walletInterface) {
        this.activity = activity;
        this.walletInterface = walletInterface;
    }

    public void fetchWalletAmount() {
        ApiUtils.loadWalletAmount(new ApiUtils.WalletInterface() {
            @Override
            public void onSuccess(Object obj) {
                walletInterface.onAmountFetched((String) obj);
                walletAmount = Integer.parseInt((String) obj);
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void updateWalletAmount(String amount) {
        String newAmount = String.valueOf(walletAmount - Integer.parseInt(amount));
        walletInterface.onAmountUpdate(newAmount);
        walletAmount = Integer.parseInt(newAmount);

    }

    public void addWalletAmountForLab(String amount) {
        updateWalletAmount(amount);
    }

    public void cancelToAddWalletAmount(String amount) {
        updateWalletAmount("-" + amount);
    }

}
