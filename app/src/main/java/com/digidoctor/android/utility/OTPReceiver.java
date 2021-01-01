package com.digidoctor.android.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.digidoctor.android.view.fragments.InputOtpFragment;

public class OTPReceiver extends BroadcastReceiver {
    private static final String TAG = "OTPReceiver";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage smsMessage : smsMessages) {
            String message_body = smsMessage.getMessageBody();
            StringBuilder getOTP = new StringBuilder();
            char[] chars = new char[4];
            for (int a = 0; a < 4; a++) {
                getOTP.append(message_body.charAt(a));
                chars[a] = message_body.charAt(a);
            }

            Log.d(TAG, "onReceive: OTP " + getOTP.toString());
            InputOtpFragment.getInstance().setOtp(chars);

        }
    }
}
