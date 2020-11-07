package com.digidoctor.android.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.NavAdapter;
import com.digidoctor.android.databinding.ActivityDashBoardBinding;
import com.digidoctor.android.interfaces.NavigationInterface;
import com.digidoctor.android.model.NavModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.AdapterInterface;
import com.digidoctor.android.utility.GetAddressIntentService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.view.fragments.BookAppointmentFragment.bookAppointment;
import static com.digidoctor.android.view.fragments.PatientDashboardFragment.dashboard2Binding;

public class PatientDashboard extends AppCompatActivity implements PaymentResultWithDataListener, NavigationInterface {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    ActivityDashBoardBinding mainBinding;

    NavController navController;

    User user;

    String lat, lng;


    private static final String TAG = "PatientDashboard";


    public static PatientDashboard instance;


    public static PatientDashboard getInstance() {
        return instance;
    }

    public String cityName;
    public String areaName;

    NavAdapter navAdapter;
    List<NavModel> navModels;

    public String getCityName() {
        if (cityName == null)
            return "";
        else return cityName;

    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        if (areaName == null)
            return "";
        else return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    private FusedLocationProviderClient fusedLocationClient;
    private LocationAddressResultReceiver addressResultReceiver;
    private Location currentLocation;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);

        instance = this;

        user = getPrimaryUser(getInstance());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);


        getSupportActionBar().hide();

        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                currentLocation = locationResult.getLocations().get(0);
                getAddress();
            }
        };
        startLocationUpdates();

        setNavRec();
    }

    private void setNavRec() {
        //mainBinding.navRec.setVisibility(user.getIsExists() == 0 ? View.GONE : View.VISIBLE);
        mainBinding.imageView6.setVisibility(user.getIsExists() == 0 ? View.GONE : View.VISIBLE);

        mainBinding.setUser(getPrimaryUser(this));
        navModels = new ArrayList<>();
        navAdapter = new NavAdapter(navModels, PatientDashboard.this);
        mainBinding.navRec.setAdapter(navAdapter);
        loadNavData();
    }

    private void loadNavData() {
        navModels.add(new NavModel(getString(R.string.appointment), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.lab_tests), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.orders), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.prescription_history), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.investigation_history), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.notifications), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.settings), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.about_us), R.drawable.appointments));
        navAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    public View getView() {
        return mainBinding.getRoot();
    }


    public void navigate(int id) {
        navController.navigate(id);
    }

    public void navigate(int id, Bundle bundle) {
        navController.navigate(id, bundle);
    }


    public User getUser() {
        if (user == null)
            user = new User();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onPaymentSuccess(String status, PaymentData paymentData) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("transactionNo", bookAppointment.getTrxId());
            jsonObject.put("paymentAmount", bookAppointment.getDrFee());
            jsonObject.put("paymentStatus", "success");
            jsonObject.put("bankRefNo", status);
            jsonObject.put("isErauser", bookAppointment.getIsEraUser());
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d(TAG, "dtDataTable: " + jsonArray.toString());

        Log.d(TAG, "onPaymentSuccess: " + status);

        Toast.makeText(this, this.getString(R.string.transaction_successful), Toast.LENGTH_LONG).show();

        bookAppointment.setDtDataTable(jsonArray.toString());

        bookAppointment.startBookingAppointment();

        Log.d(TAG, "onPaymentSuccessData: " + paymentData.getData());
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.d(TAG, "onPaymentError: " + s);
        Toast.makeText(instance, R.string.failed_to_book, Toast.LENGTH_SHORT).show();
    }


    @SuppressWarnings("MissingPermission")
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new
                            String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    public String getLat() {
        if (lat == null) return "";
        else
            return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        if (lng == null) return "";
        else
            return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {
        if (!Geocoder.isPresent()) {
            Toast.makeText(PatientDashboard.this, "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", currentLocation);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
            int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission not granted, " + "restart the app if you want the feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNavigationItemClicked(int pos) {
        mainBinding.drawerLayout.close();
        switch (pos) {
            case 0:
                navController.navigate(R.id.appointmentsFragment);
                break;
            case 3:
                if (user.getIsExists() == 1)
                    navController.navigate(R.id.prescriptionHistoryFragment);
                else navController.navigate(R.id.profileFragment);

                break;
            default:
                Toast.makeText(instance, "Coming Soon", Toast.LENGTH_SHORT).show();
        }
    }


    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(PatientDashboard.this, "Address not found, ", Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");
            String lat = resultData.getString("lat");
            String lng = resultData.getString("lng");
            showResults(currentAdd, lat, lng);

        }
    }

    public void showResults(String currentAdd, String lat, String lng) {

        Log.d(TAG, "showResults: " + currentAdd);
        final String[] address = currentAdd.split(",");


        try {
            dashboard2Binding.tvLocation.setText(address[1]);
            dashboard2Binding.tvCity.setText(address[0]);

            setLat(lat);
            setLng(lng);

            setAreaName(address[1]);
            setCityName(address[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}