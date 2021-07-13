package com.digidoctor.android.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.digidoctor.android.R;
import com.digidoctor.android.adapters.NavAdapter;
import com.digidoctor.android.broadcast.InternetBroadcastReceiver;
import com.digidoctor.android.databinding.ActivityDashBoardBinding;
import com.digidoctor.android.interfaces.NavigationInterface;
import com.digidoctor.android.model.NavModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.newVideoCall.LocalNotification;
import com.digidoctor.android.utility.GetAddressIntentService;
import com.digidoctor.android.utility.WakefulBroadcasterReceiver;
import com.digidoctor.android.utility.utils;
import com.digidoctor.android.view.fragments.digiDoctorFragments.SearchBluetoothDeviceFragment;
import com.digidoctor.android.viewHolder.PatientViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.grisoftware.updatechecker.GoogleChecker;
import com.payu.base.models.ErrorResponse;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.digidoctor.android.utility.AppUtils.hideDialog;
import static com.digidoctor.android.utility.AppUtils.shareApp;
import static com.digidoctor.android.utility.AppUtils.showRequestDialog;
import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.view.fragments.digiDoctorFragments.BookAppointmentFragment.bookAppointment;
import static com.digidoctor.android.view.fragments.digiDoctorFragments.PatientDashboardFragment.dashboard2Binding;
import static com.digidoctor.android.view.fragments.digiDoctorFragments.SearchBluetoothDeviceFragment.REQUEST_ENABLE_BT;
import static com.digidoctor.android.view.fragments.lab.FragmentReviewOrderLab.payment;

public class PatientDashboard extends AppCompatActivity implements PaymentResultWithDataListener, NavigationInterface, PayUCheckoutProListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    ActivityDashBoardBinding mainBinding;

    NavController navController;

    User user;
    String lat, lng;


    private static final String TAG = "PatientDashboard";


    InternetBroadcastReceiver broadcastReceiver = new InternetBroadcastReceiver();

    public static PatientDashboard instance;


    public static PatientDashboard getInstance() {
        return instance;
    }

    public String cityName;
    public String areaName;

    NavAdapter navAdapter;
    List<NavModel> navModels;

    MenuItem cartItem = null;
    MenuItem favouriteItem = null;
    MenuItem searchItem = null;

    int cartCounter = 0;
    int favouriteCounter = 0;
    TextView cartTv, favouriteTv;

    PatientViewModel viewModel;

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

        //Register Broadcast Receiver
        registerBroadcast();

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

        viewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        setNavRec();



/*        ApiUtils.getcartcountutils(this, new ApiCallbackInterface() {
            @Override
            public void onSuccess(List<?> o) {
                List<CartCount.CartcountList> CartcountList = (List<CartCount.CartcountList>) o;
                if (null == CartcountList)
                    return;
                if (CartcountList.isEmpty())
                    return;

                Log.d(TAG, "onSuccess: " + CartcountList.get(0).getCartCount());
                cartCounter = CartcountList.get(0).getCartCount();
                updateCartCount(cartCounter);
            }

            @Override
            public void onError(String s) {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });*/

        changeMenuView();

       /* if (null != getIntent().getStringExtra("twillioAccessToken")) {
            String type = getIntent().getStringExtra("type");
            if (null != type && type.equals("1"))
                showNotification();
            else Log.d(TAG, "notificationType is not 1: ");
        } else Log.d(TAG, "notification data is null: ");*/
    }

    private void changeMenuView() {
           /*     navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (null != searchItem)
                searchItem.setVisible(destination.getId() == R.id.onlinePharmacyFragment);
            if (null != cartItem)
                cartItem.setVisible(destination.getId() != R.id.cart_Details_Fragment);

            if (null != searchItem && null != cartItem && null != favouriteItem) {
                switch (destination.getId()) {
                    case R.id.updateSymptomsFragment:
                    case R.id.symptomTrackerFragment:
                    case R.id.appointmentsFragment:
                    case R.id.appointmentDetailFragment:
                    case R.id.symptomsFragment2:
                    case R.id.recommendedDoctorsFragment:
                    case R.id.doctorShortProfileFragment:
                    case R.id.prescriptionHistoryFragment:
                    case R.id.addPrescriptionManuallyFragment:
                    case R.id.chooseTimeFragment2:
                    case R.id.bookAppointmentFragment:
                    case R.id.profileFragment:
                    case R.id.visitFragment:
                    case R.id.fragmentCartListLab:
                    case R.id.testDetailsFRagment:
                    case R.id.homeIsolationFragment:
                    case R.id.homeIsolationRequestListFragment:
                    case R.id.homeIsolationRequestDetailFragment:
                    case R.id.medicineReminderListFragment:
                    case R.id.stethAndLaryngoDataFragment:
                    case R.id.chooseVitalHistoryTypeFragment:
                    case R.id.chatForAppointmentFragment:
                    case R.id.videoFragmentFragment:
                    case R.id.patientInvestigationReportFragment:
                    case R.id.investigationFragment:
                    case R.id.emergencySpecialityFragment:
                    case R.id.addMemberFragment:
                        hideAllItem();
                        break;

                    case R.id.timeSlotForLabFragment:
                        hideFavouriteIcon();


                }
            }
        });*/
    }


    private void hideFavouriteIcon() {
        searchItem.setVisible(false);
        cartItem.setVisible(true);
        favouriteItem.setVisible(false);
    }

    private void hideAllItem() {
        searchItem.setVisible(false);
        cartItem.setVisible(false);
        favouriteItem.setVisible(false);
    }

    private void showNotification() {
       /* String type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("4")) {

        } else {

        }*/
        Intent myIntent = new Intent(this, WakefulBroadcasterReceiver.class);
        myIntent.setAction("myReceiver");
        myIntent.putExtra("roomName", getIntent().getStringExtra("roomName"));
        myIntent.putExtra("accessToken", getIntent().getStringExtra("twillioAccessToken"));
        myIntent.putExtra("message", getIntent().getStringExtra("msg"));
        myIntent.putExtra("title", getIntent().getStringExtra("title"));
        myIntent.putExtra("profilePhotoPath", getIntent().getStringExtra("profilePhotoPath"));
        myIntent.putExtra("doctorName", getIntent().getStringExtra("doctorName"));
        sendBroadcast(myIntent);
    }

    private void registerBroadcast() {
        //Create Intent Filter here
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
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


        viewModel.getMenuData().observe(this, navModels -> {
            this.navModels.addAll(navModels);
            Log.d(TAG, "setNavRec: " + navModels.toString());
            navAdapter.notifyDataSetChanged();

        });
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
        mainBinding.imageView45.setOnClickListener(view -> {
            closeDrawer();
            if (user.getIsExists() == 1) {
                Bundle mBundle = new Bundle();
                mBundle.putString("FROM", "DashboardFragment");
                navController.navigate(R.id.showMemberListFragment, mBundle);
            } else {
                navController.navigate(R.id.profileFragment);
            }
        });

        checkForUpdate();

        if (getIntent().hasExtra("action")) {
            if (getIntent().getStringExtra("action").equalsIgnoreCase("callHistory")) {
                navController.navigate(R.id.videoCallHistoryFragment);
                LocalNotification.hideMissedCallNotification(this);
            }
        }
    }


    private void checkForUpdate() {
        new GoogleChecker("com.digidoctor.android", PatientDashboard.this, true, "en");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        cartItem = menu.findItem(R.id.cart_Details_Fragment);
        favouriteItem = menu.findItem(R.id.fragmentAllWishLIstProduct);
        searchItem = menu.findItem(R.id.allProductsFragment);

        View actionView = cartItem.getActionView();
        View actionView2 = favouriteItem.getActionView();

        cartTv = actionView.findViewById(R.id.tvCartCount);
        favouriteTv = actionView2.findViewById(R.id.tvFavouriteCount);

        cartTv.setText(String.valueOf(cartCounter));
        favouriteTv.setText(String.valueOf(favouriteCounter));

        actionView.setOnClickListener(v -> onOptionsItemSelected(cartItem));
        actionView2.setOnClickListener(v -> onOptionsItemSelected(favouriteItem));

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

        if (null != bookAppointment)
            bookAppointment.startBookingAppointment(paymentData.getPaymentId());

        if (null != payment)
            try {
                payment.paymentSuccess();
            } catch (Exception e) {
                payment.paymentFailed(e.getLocalizedMessage());
            }

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
        if (lat == null) return "0";
        else
            return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        if (lng == null) return "0";
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission not granted, " + "restart the app if you want the feature", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onNavigationItemClicked(Object obj) {
        mainBinding.drawerLayout.close();
        NavModel navModel = (NavModel) obj;
        if (navModel.getId() == 1) {
            if (isProfileFilled())
                navController.navigate(R.id.appointmentsFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 2) {
            if (isProfileFilled())
                navController.navigate(R.id.prescriptionHistoryFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 3) {
            if (isProfileFilled())
                navController.navigate(R.id.investigationFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 4) {
            if (isProfileFilled())
                navController.navigate(R.id.chooseVitalHistoryTypeFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 5) {
            if (isProfileFilled())
                navController.navigate(R.id.addMemberFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 6) {
            //init for video Cal Screen
            if (isProfileFilled())
                navController.navigate(R.id.videoCallHistoryFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 7) {
            //init for wallet Screen
            if (isProfileFilled())
                navController.navigate(R.id.walletFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 11) {
            if (isProfileFilled())
                navController.navigate(R.id.medicineReminderListFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 13) {
            shareApp("https://digidoctor.in/invitation?invitationCode=" + getPrimaryUser(PatientDashboard.getInstance()).getMemberId(), "This is demo description", this);
        } else if (navModel.getId() == 14) {
            if (isProfileFilled())
                navController.navigate(R.id.symptomTrackerFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 16) {
            if (isProfileFilled())
                navController.navigate(R.id.homeIsolationRequestListFragment);
            else navController.navigate(R.id.profileFragment);
        } else if (navModel.getId() == 17) {
            showRequestDialog(this);
            if (utils.logout(this))
                hideDialog();
        } else Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();

    }

    private boolean isProfileFilled() {
        return user.getIsExists() == 1;
    }

    private void openBrowser() {
       /* String url = "http://www.example.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);*/
        Log.d(TAG, "openBrowser: open browser called !!");
    }


    @Override
    public void onPaymentSuccess(@NotNull Object o) {
        HashMap<String, Object> result = (HashMap<String, Object>) o;
        String merchantResponse = (String) result.get(PayUCheckoutProConstants.CP_MERCHANT_RESPONSE);
        bookAppointment.startBookingAppointment(merchantResponse);
    }

    @Override
    public void onPaymentFailure(@NotNull Object response) {
        Toast.makeText(instance, R.string.failed_to_book, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentCancel(boolean b) {
        Log.d(TAG, "onPaymentCancel: " + b);
        Toast.makeText(instance, R.string.failed_to_book, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(@NotNull ErrorResponse errorResponse) {
        String errorMessage = errorResponse.getErrorMessage();
        Log.d(TAG, "onError: " + errorMessage);
        Toast.makeText(instance, R.string.failed_to_book, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void generateHash(@NotNull HashMap<String, String> hashMap, @NotNull com.payu.ui.model.listeners.PayUHashGenerationListener payUHashGenerationListener) {
        Log.d(TAG, "generateHash: ");
    }

    @Override
    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {

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

    public void updateCartCount(int count) {
        if (null != cartTv) {
            cartTv.setText(String.valueOf(count));
        }
    }
}