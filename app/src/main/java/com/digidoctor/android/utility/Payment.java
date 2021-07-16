package com.digidoctor.android.utility;

import android.app.Activity;
import android.widget.Toast;

import com.digidoctor.android.interfaces.LabOrderInterface;
import com.digidoctor.android.model.labmodel.LabOrderModel;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class Payment extends PaymentModel {
    private static final String TAG = "Payment";
    public static final String PAY_MODE_RAZOR_PAY = "razor_pay";

    Activity activity;
    String payMode;
    LabOrderModel labModel;
    LabOrderInterface anInterface;


    public Payment(Activity activity) {
        this.activity = activity;
    }

    public void startPayment(String payMode) {
        this.payMode = payMode;
        initPayment();

    }


    public void setLabModel(LabOrderModel labModel) {
        this.labModel = labModel;
    }

    public void setLabInterface(LabOrderInterface anInterface) {
        this.anInterface = anInterface;

    }

    private void initPayment() {
        if (payMode.equals(PAY_MODE_RAZOR_PAY)) {
            initRazorPay();
        }
    }

    private void initRazorPay() {
        Checkout.preload(activity);
        Checkout checkout = new Checkout();

        //checkout.setKeyID("rzp_live_BwhTaXRxeklaAI");
        checkout.setKeyID("rzp_test_dUtqKgNQUPcSDE");

        // bookAppointment.setTrxId(getTxId());
        String image = "https://digidoctor.in/assets/images/logonew.png";

        float amount = Float.parseFloat(getAmount()) * 100;
        try {
            JSONObject options = new JSONObject();
            options.put("name", getName());
            options.put("description", "" + System.currentTimeMillis());
            options.put("image", image);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "" + amount);//pass amount in currency subunits
            options.put("prefill.email", getEmail());
            options.put("prefill.contact", getMobileNumber());
            checkout.open(activity, options);
        } catch (Exception e) {

        }
    }

    public void paymentSuccess(PaymentData paymentData) throws JSONException {
        Timber.d("paymentSuccess: %s", paymentData.getData().toString());
        String dtTableData = "[{\"paymentStatus\":\"success\",\"bankRefNo\":\"" + paymentData.getData().getString("razorpay_payment_id") + "\",\"paymentAmount\":\"" + amount + "\",\"transactionNo\":\"" + paymentData.getPaymentId() + "\",\"isErauser\":" + paymentData.getUserContact() + "}]";

        labModel.setDtPaymentTable(String.valueOf(paymentData.getData()));
        LabOrder labOrder = new LabOrder(activity, labModel, anInterface);
        labOrder.placeOrder();
    }

    public void paymentFailed(String msg) {
        AppUtils.hideDialog();
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        anInterface.onFailed(msg);
    }

}
