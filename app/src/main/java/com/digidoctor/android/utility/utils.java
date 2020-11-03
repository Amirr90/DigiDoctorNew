package com.digidoctor.android.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.digidoctor.android.R;
import com.digidoctor.android.model.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class utils {


    private static final String MY_PREFS_NAME = "myPref";
    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String fcmToken = "fcmToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER = "user";
    public static final String TOKEN = "token";
    public static final String MOBILE_NUMBER = "number";
    public static final int REQ_PERMISSION_CODE = 1;

    public static String getCurrentDateInMonthDateFormat(long timeStamp) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp * 1000L);
        String date = DateFormat.format("EEE, MMM d, ''yy", cal).toString();

        return date;
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            try {
                @SuppressLint("WrongConstant") InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService("input_method");
                View view = activity.getCurrentFocus();
                if (view != null) {
                    IBinder binder = view.getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String parseDateToDMYFormat(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static ArrayList<HashMap<String, String>> getNextWeekDays() {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy");

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        HashMap<String, String> hashMap = new HashMap<>();

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat sdfDay = new SimpleDateFormat("EEE", Locale.getDefault());
        SimpleDateFormat sdfDateSend = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

        for (int i = 0; i < 7; i++) {
            hashMap = new HashMap<>();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String date = sdfDate.format(calendar.getTime());
            String day = sdfDay.format(calendar.getTime());
            String dateSend = sdfDateSend.format(calendar.getTime());

            hashMap.put("date", date);
            hashMap.put("day", day);
            hashMap.put("dateSend", dateSend);

            list.add(hashMap);

        }

        return list;
    }


    public static String getJSONFromModel(Object o) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(o);
        try {
            JSONObject request = new JSONObject(jsonString);
            return request.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Animation fadeIn(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.fade_in);
    }

    public static Animation fadeOut(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.fade_out);
    }


    public static void setString(String key, String value, Activity activity) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setBoolean(String key, boolean value, Activity activity) {
        SharedPreferences sharedpreferences = activity.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(String key, Activity activity) {
        if (activity != null) {
            SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            return pref.getString(key, "");
        } else return null;

    }

    public static boolean getBoolean(String key, Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return pref.getBoolean(key, true);
    }

    public static boolean getLoginStatus(String key, Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static void savePrimaryUserData(String key, Activity activity, User myObject) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static User getPrimaryUser(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(USER, "");
        User obj = gson.fromJson(json, User.class);
        return obj;
    }


}

