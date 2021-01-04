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
import android.view.Menu;
import android.view.MenuItem;
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
import com.digidoctor.android.utility.GetAddressIntentService;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.fragments.SearchBluetoothDeviceFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.view.fragments.BookAppointmentFragment.bookAppointment;
import static com.digidoctor.android.view.fragments.PatientDashboardFragment.dashboard2Binding;
import static com.digidoctor.android.view.fragments.SearchBluetoothDeviceFragment.REQUEST_ENABLE_BT;

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


    Menu menu;

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


        Objects.requireNonNull(getSupportActionBar()).hide();

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

    public void updateUser() {
        user = getPrimaryUser(this);
        mainBinding.setUser(user);
    }

    private void setNavRec() {
        mainBinding.imageView6.setVisibility(user.getIsExists() == 0 ? View.GONE : View.VISIBLE);


        mainBinding.setUser(getPrimaryUser(this));
        navModels = new ArrayList<>();
        navAdapter = new NavAdapter(navModels, PatientDashboard.this);
        mainBinding.navRec.setAdapter(navAdapter);
        loadNavData();
    }

    private void loadNavData() {
        navModels.add(new NavModel(getString(R.string.appointment), R.drawable.appointments));
        navModels.add(new NavModel(getString(R.string.prescription_history), R.drawable.prescription));
        navModels.add(new NavModel(getString(R.string.investigation_history), R.drawable.investigation));
        navModels.add(new NavModel(getString(R.string.vitals_hitory), R.drawable.investigation));
        navModels.add(new NavModel(getString(R.string.add_family_member), R.drawable.family_members));
        navModels.add(new NavModel(getString(R.string.lab_tests), R.drawable.lab_test_icon));
        navModels.add(new NavModel(getString(R.string.orders), R.drawable.order));
        navModels.add(new NavModel(getString(R.string.notifications), R.drawable.notification));
        navModels.add(new NavModel(getString(R.string.settings), R.drawable.settings));
        navModels.add(new NavModel(getString(R.string.about_us), R.drawable.aboutus));
        navModels.add(new NavModel(getString(R.string.logout), R.drawable.logout));
        navAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainBinding.textView14.setOnClickListener(view -> {
                    navController.navigate(R.id.profileFragment);
                    closeDrawer();
                }
        );

        mainBinding.changeMemberLay.setOnClickListener(view -> {

            closeDrawer();
            if (user.getIsExists() == 1) {
                Bundle mBundle = new Bundle();
                mBundle.putString("FROM", "DashboardFragment");
                navController.navigate(R.id.showMemberListFragment, mBundle);
            } else {
                navController.navigate(R.id.profileFragment);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        try {
            utils.hideSoftKeyboard(PatientDashboard.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Toast.makeText(this, this.getString(R.string.transaction_successful), Toast.LENGTH_LONG).show();

        Log.d(TAG, "onPaymentSuccess: " + status);

        bookAppointment.startBookingAppointment(paymentData.getPaymentId());

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
            Toast.makeText(PatientDashboard.this, "Can't find current address, ", Toast.LENGTH_SHORT).show();
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
                if (user.getIsExists() == 1)
                    navController.navigate(R.id.appointmentsFragment);
                else navController.navigate(R.id.profileFragment);

                break;
            case 1:
                if (user.getIsExists() == 1)
                    navController.navigate(R.id.prescriptionHistoryFragment);
                else navController.navigate(R.id.profileFragment);
                break;


            case 2:
                if (user.getIsExists() == 1)
                    navController.navigate(R.id.investigationFragment);
                else navController.navigate(R.id.profileFragment);
                break;


            case 3:
                if (user.getIsExists() == 1)
                    navController.navigate(R.id.chooseVitalHistoryTypeFragment);
                else navController.navigate(R.id.profileFragment);
                break;

            case 4:
                if (user.getIsExists() == 1)
                    navController.navigate(R.id.addMemberFragment);
                else navController.navigate(R.id.profileFragment);
                break;


            case 10:
                showRequestDialog(this);
                if (utils.logout(this))
                    hideDialog();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            SearchBluetoothDeviceFragment.getInstance().CheckBluetoothState();
        }
    }


    public void closeDrawer() {
        mainBinding.drawerLayout.close();
    }

    public void openDrawer() {
        mainBinding.drawerLayout.open();
    }

}