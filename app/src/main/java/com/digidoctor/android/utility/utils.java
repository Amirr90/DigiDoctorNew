package com.digidoctor.android.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.model.PrescriptionDtTableModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.view.activity.SignUpJourneyActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.digidoctor.android.utility.AppUtils.hideDialog;

public class utils {


    public static final String PREFS_MAIN_FILE = "PREFS_DIGI_DOCTOR_FILE";

    private static final String MY_PREFS_NAME = "myPref";
    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String fcmToken = "fcmToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER = "user";
    public static final String TOKEN = "token";
    public static final String MOBILE_NUMBER = "number";
    public static final int REQ_PERMISSION_CODE = 1;
    public static final String MEMBER_ID = "memberId";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_PATIENT_NAME = "patientName";
    public static final String KEY_DOC_ID = "docId";
    public static final String APPOINTMENT_DATE = "appointmentDate";
    public static final String APPOINTMENT_TIME = "appointmentTime";
    public static final String KEY_IS_ERA_USER = "isEraUser";
    public static final String KEY_APPOINTMENT_ID = "appointmentId";
    public static final String KEY_PRESCRIPTION_ID = "prescriptionId";

    public static final String KEY_SYMPTOM_ID = "symptomId";
    public static final String KEY_DOC_NAME = "docName";
    public static final String KEY_DOB = "dob";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_AGE = "age";
    public static final String BOOKING_USER = "bookingUser";
    public static final String RE_SCHEDULE = "reschedule";
    public static final String KEY_CANCEL = "cancel";
    public static final String VITAL_ID = "vitalId";
    public static final String VITAL_NAME = "vitalName";
    public static final String VITAL_IMAGE = "vitalImage";
    public static final String PULSE_RATE_ID = "3";
    public static final String BLOOD_SUGAR_ID = "10";
    public static final String SPO2_ID = "56";
    public static final String RESPIRATORY_ID = "7";
    public static final String TEMPERATURE_ID = "5";
    public static final String BP_ID = "-1";


    public static final Integer DEVICE_TYPE = 1;
    public static final String BLE_DEVICE_TYPE = "deviceType";
    public static final String APP_TYPE = "DD";
    public static final String SERVICE_PROVIDER_ID_TYPE = "6";

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

    public static String getDateInDMYFormatFromTimestamp(long currentTimeMillis) {
        try {
            String value = new java.text.SimpleDateFormat("yyyy-MM-dd").
                    format(new java.util.Date(currentTimeMillis));
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getDateInDMYFormatFromTimestampInDayMonthFormat(long currentTimeMillis) {
        try {
            String value = new java.text.SimpleDateFormat("dd MMMM yyyy").
                    format(new java.util.Date(currentTimeMillis));
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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

    public static String parseDateToYMDToMMDD(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd MMMM yyyy";
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
    }  public static String getDate(String oldDate) {

        String outputPattern = "yyyy-MM-dd";
        String inputPattern = "dd MMMM yyyy";

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

    public static void setUserForBooking(String key, Activity activity, User myObject) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static void savePrimaryUserData(String key, Activity activity, User myObject) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    public static User getUserForBooking(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(BOOKING_USER, "");
        User obj = gson.fromJson(json, User.class);
        return obj;
    }

    public static User getPrimaryUser(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(USER, "");
        User obj = gson.fromJson(json, User.class);
        return obj;
    }


    public static boolean logout(final Activity activity) {
        try {

            new AlertDialog.Builder(activity)
                    .setTitle(R.string.logout)
                    .setMessage(R.string.logout_text)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            utils.setBoolean(utils.IS_LOGIN, false, activity);

                            activity.startActivity(new Intent(activity, SignUpJourneyActivity.class));

                            activity.finish();

                            Toast.makeText(activity, R.string.logged_out_sucvcessfully, Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            hideDialog();
            return false;
        }
    }

    public static void logout(final Activity activity, boolean status) {
        try {

            utils.setBoolean(utils.IS_LOGIN, false, activity);

            activity.startActivity(new Intent(activity, SignUpJourneyActivity.class));

            activity.finish();
            Toast.makeText(activity, R.string.logged_in_from_another_device, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            hideDialog();

        }
    }

    public static String getVitalMaxValue(String vitalId) {
        if (vitalId.equalsIgnoreCase(BP_ID))
            return "140/90mmg Higher";
        else return "";
    }

    public static String getVitalMinValue(String vitalId) {
        if (vitalId.equalsIgnoreCase(BP_ID))
            return "90/60mmg Lower";
        else return "";
    }

    public static String getDtTableData(List<PrescriptionDtTableModel> medicineDetailModels) {

        JSONArray dtDataTable = new JSONArray();
        for (PrescriptionDtTableModel model : medicineDetailModels) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("dosageFormId", model.getDosageFormId());
                jsonObject.put("medicineName", model.getMedicineName());
                jsonObject.put("medicineId", model.getMedicineId());
                jsonObject.put("strength", model.getStrength());
                jsonObject.put("frequencyId", model.getFrequencyId());
                jsonObject.put("doseUnitId", model.getDoseUnitId());
                jsonObject.put("durationInDays", model.getDays());
                dtDataTable.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "getDtTableDataError: " + e.getLocalizedMessage());
            }
        }


        return dtDataTable.toString();
    }


    public static String getDayFromDate(String incomingDate) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "EEE";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(incomingDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getYearFromDate(String incomingDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(incomingDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getMonthFromDate(String incomingDate) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(incomingDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getDateFromDate(String incomingDate) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(incomingDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}

