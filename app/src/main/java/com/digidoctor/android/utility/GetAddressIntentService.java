package com.digidoctor.android.utility;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GetAddressIntentService extends IntentService {
    private static final String IDENTIFIER = "GetAddressIntentService";
    private ResultReceiver addressResultReceiver;

    public GetAddressIntentService() {
        super(IDENTIFIER);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg;
        addressResultReceiver = Objects.requireNonNull(intent).getParcelableExtra("add_receiver");
        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService", "No receiver, not processing the request further");
            return;
        }
        Location location = intent.getParcelableExtra("add_location");
        if (location == null) {
            msg = "No location, can't go further without location";
            sendResultsToReceiver(0, msg, "0", "0");
            return;
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }
        if (addresses == null || addresses.size() == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(1, msg, "0", "0");
        } else {
            Address address = addresses.get(0);
        /*    String addressDetails = address.getFeatureName() + "\n" + address.getThoroughfare() + "\n" +
                    "Locality: " + address.getLocality() + "\n" + "County: " + address.getSubAdminArea() + "\n" +
                    "State: " + address.getAdminArea() + "\n" + "Country: " + address.getCountryName() + "\n" +
                    "Postal Code: " + address.getPostalCode() + "\n";*/


            String addressDetails = address.getLocality() + "," + address.getSubLocality();
            sendResultsToReceiver(2, addressDetails,
                    String.valueOf(location.getLatitude()),
                    String.valueOf(location.getLongitude()));
        }
    }

    private void sendResultsToReceiver(int resultCode, String message, String lat, String lng) {
        Bundle bundle = new Bundle();
        bundle.putString("address_result", message);
        bundle.putString("lat", lat);
        bundle.putString("lng", lng);
        addressResultReceiver.send(resultCode, bundle);
    }
}
