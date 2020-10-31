package com.digidoctor.android.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.digidoctor.android.R;
import com.digidoctor.android.databinding.ActivityDashBoardBinding;
import com.digidoctor.android.model.User;
import com.digidoctor.android.utility.OnlineAppointmentInterface;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import static com.digidoctor.android.utility.utils.getPrimaryUser;
import static com.digidoctor.android.view.fragments.BookAppointmentFragment.bookAppointment;

public class PatientDashboard extends AppCompatActivity implements PaymentResultWithDataListener {

    ActivityDashBoardBinding mainBinding;

    NavController navController;

    User user;

    OnlineAppointmentInterface onlineAppointmentInterface;

    private static final String TAG = "PatientDashboard";


    public static PatientDashboard instance;

    public static PatientDashboard getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board);

        instance = this;

        user = getPrimaryUser(getInstance());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);


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
        user = getPrimaryUser(getInstance());
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.d(TAG, "onPaymentSuccess: " + s);
        Toast.makeText(this, this.getString(R.string.transaction_successful), Toast.LENGTH_LONG).show();
        bookAppointment.setDtDataTable(paymentData.getData().toString());
        bookAppointment.startBookingAppointment();
        Log.d(TAG, "onPaymentSuccessData: " + paymentData.getData());
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.d(TAG, "onPaymentError: " + s);
        Toast.makeText(instance, R.string.failed_to_book, Toast.LENGTH_SHORT).show();
    }
}