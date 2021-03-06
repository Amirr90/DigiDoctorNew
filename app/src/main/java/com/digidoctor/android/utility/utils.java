package com.digidoctor.android.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.digidoctor.android.R;
import com.digidoctor.android.interfaces.Api;
import com.digidoctor.android.model.LogoutModel;
import com.digidoctor.android.model.PrescriptionDtTableModel;
import com.digidoctor.android.model.User;
import com.digidoctor.android.view.activity.SignUpJourneyActivity;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.digidoctor.android.utility.ApiUtils.RESPONSE_FAILED;
import static com.digidoctor.android.utility.ApiUtils.RESPONSE_LOGOUT;
import static com.digidoctor.android.utility.ApiUtils.RESPONSE_SUCCESS;
import static com.digidoctor.android.utility.AppUtils.hideDialog;

public class utils {


    public static final String MAC_ADDRESS = "macAddress";
    public static final String GO_TO_CART = "Go To Cart";
    public static final String ADD_TO_CART = "Add To Cart";
    public static final String ADDED = "Added";
    public static final String CONSTANT_EMAIL_ID = "criteriontech1@gmail.com";
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static final String PREFS_MAIN_FILE = "PREFS_DIGI_DOCTOR_FILE";

    public static final String MY_PREFS_NAME = "myPref";
    public static final String TIMESTAMP = "timestamp";
    public static final String MSG = "msg";
    public static final String SENDER_ID = "sender_id";
    public static final String RECEIVER_ID = "receiver_id";
    public static final String APPOINTMENT_ID = "appointment_id";
    public static final String SENDER_NAME = "sender_name";
    public static final String IS_SEEN = "myPref";
    public static final String APPOINTMENT_CHAT = "AppointmentChats";
    public static final String CATEGORY_ID = "CategoryId";


    public static final String IS_FIRST_TIME = "isFirstTime";
    public static final String fcmToken = "fcmToken";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER = "user";
    public static final String USER_MAIN = "user_main";
    public static final String TOKEN = "token";
    public static final String MOBILE_NUMBER = "number";
    public static final int REQ_PERMISSION_CODE = 1;
    public static final String MEMBER_ID = "memberId";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_PATIENT_NAME = "patientName";
    public static final String KEY_DOC_ID = "docId";
    public static final String APPOINTMENT_DATE = "appointmentDate";
    public static final String APPOINTMENT_TIME = "appointmentTime";
    public static final String IS_REVISIT = "is_revist";
    public static final String KEY_IS_ERA_USER = "isEraUser";
    public static final String KEY_APPOINTMENT_ID = "appointmentId";
    public static final String KEY_REVISIT = "isRevisit";
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
    public static final String OMRON = "omron";
    public static final String MEDCHECK = "medcheck";
    public static final String MEDITIVE = "meditive";
    public static final String CONTROL_D = "control_d";
    public static final String VIA_OXIMETER = "via_oximeter";


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

        Objects.requireNonNull(activity).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null) {
                //noinspection deprecation
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }

        return false;
    }

    public static String getDateInDMYFormatFromTimestamp(long currentTimeMillis) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").
                    format(new Date(currentTimeMillis));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getDateInDMYFormatFromTimestampInDayMonthFormat(long currentTimeMillis) {
        try {
            return new SimpleDateFormat("dd MMMM yyyy").
                    format(new Date(currentTimeMillis));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String parseDateToYMDToMMDD(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getDate(String oldDate) {

        String outputPattern = "yyyy-MM-dd";
        String inputPattern = "dd MMMM yyyy";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(oldDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
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

    } public static String getString(String key, Context activity) {
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

    public static void setUserForBooking(String key, Context activity, User myObject) {
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

    public static void saveMainUserData(String key, Activity activity, User myObject) {
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
        return gson.fromJson(json, User.class);
    }

    public static User getUserForBooking(Context activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(BOOKING_USER, "");
        return gson.fromJson(json, User.class);
    }

    public static User getPrimaryUser(Activity activity) {
        SharedPreferences pref = App.context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(USER, "");
        return gson.fromJson(json, User.class);
    }

    public static User getPrimaryUser() {
        SharedPreferences pref = App.context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(USER, "");
        return gson.fromJson(json, User.class);
    }

    public static User getMainUser(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pref.getString(USER_MAIN, "");
        return gson.fromJson(json, User.class);

    }


    public static boolean logout(final Activity activity) {
        try {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.logout)
                    .setMessage(R.string.logout_text)
                    .setPositiveButton(R.string.yes, (dialog, id) -> {
                        dialog.cancel();
                        logoutFromServer(activity);
                    }).setNegativeButton(R.string.no, (dialogInterface, i) -> {

            }).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            hideDialog();
            return false;
        }
    }

    private static void logoutFromServer(Activity activity) {
        LogoutModel model = new LogoutModel();
        Api iRestInterfaces = URLUtils.getAPIServiceForPatient();
        Call<LogoutModel> call = iRestInterfaces.logout(model);
        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(@NotNull Call<LogoutModel> call, @NotNull Response<LogoutModel> response) {

                if (response.code() == 200) {
                    LogoutModel checkLoginRes = response.body();
                    if (null != checkLoginRes) {
                        int responseCode = (int) checkLoginRes.getResponseCode();
                        List<String> list = new ArrayList<>();
                        if (!list.isEmpty())
                            list.add(checkLoginRes.getResponseMessage());

                        switch (responseCode) {
                            case RESPONSE_SUCCESS: {
                                utils.setBoolean(utils.IS_LOGIN, false, activity);
                                logoutFromDatabase();
                                activity.startActivity(new Intent(activity, SignUpJourneyActivity.class));
                                activity.finish();
                                Toast.makeText(activity, R.string.logged_out_sucvcessfully, Toast.LENGTH_SHORT).show();
                            }
                            break;
                            case RESPONSE_FAILED:
                            case RESPONSE_LOGOUT:
                                Toast.makeText(activity, activity.getString(R.string.retry), Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                } else {
                    Toast.makeText(activity, activity.getString(R.string.retry), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LogoutModel> call, @NotNull Throwable t) {
                AppUtils.hideDialog();
                Toast.makeText(activity, activity.getString(R.string.retry), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void logoutFromDatabase() {
        /*AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });*/
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

    public static CharSequence createDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        Log.d("TAG", "createDate: " + d);
        return new SimpleDateFormat("HH:mm:ss").format(d);

    }

    public static CharSequence getDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public static int getDay(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        Date date = cal.getTime();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
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

        Date date;
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

        Date date;
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

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(incomingDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static StringBuilder getDocTiming(String jsonString) throws JSONException {
        List<String> days = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        StringBuilder builder = new StringBuilder();
        Log.d("TAG", "getDocTiming: " + jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String day = jsonObject.getString("dayName");
            String timeFrom = jsonObject.getString("timeFrom");
            String timeTo = jsonObject.getString("timeTo");

            if (!days.contains(day)) {
                builder.append(day);
                days.add(day);
                for (int a = i + 1; a < jsonArray.length(); a++) {
                    JSONObject jsonObject2 = (JSONObject) jsonArray.get(a);
                    String dayName = jsonObject2.getString("dayName");
                    String timeFrom2 = jsonObject2.getString("timeFrom");
                    String timeTo2 = jsonObject2.getString("timeTo");
                    if (day.equalsIgnoreCase(dayName)) {
                        Log.d("TAG", "getDocTiming: day for a " + day + " day for b " + dayName);
                        builder.append("\n").append(timeFrom2).append(" - ").append(timeTo2);
                    }
                }

                builder.append("\n").append(timeFrom).append(" - ").append(timeTo);
                builder.append("\n\n");
            }

        }
        return builder;
    }

    public static Animation slideUp(Activity requireActivity) {
        return AnimationUtils.loadAnimation(requireActivity, R.anim.slide_up);
    }
}

