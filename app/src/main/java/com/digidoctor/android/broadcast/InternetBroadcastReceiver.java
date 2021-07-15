package com.digidoctor.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.Toast;

public class InternetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            //pass default value false in intent has null value
            if (noConnectivity) {
                Toast.makeText(context, "No internet", Toast.LENGTH_SHORT).show();
                Intent deepLinkIntent = new Intent(Intent.ACTION_VIEW);
                deepLinkIntent.setData(Uri.parse("https://www.noInternetLayout.com"));
                context.startActivity(deepLinkIntent);
            } /*else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
            }*/

        }
        //run the App
    }
}
