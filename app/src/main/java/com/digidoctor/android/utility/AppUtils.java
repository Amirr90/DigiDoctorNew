package com.digidoctor.android.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.digidoctor.android.R;
import com.digidoctor.android.model.DashboardModel1;
import com.digidoctor.android.model.DoctorModel;
import com.digidoctor.android.model.LanguageModel;
import com.digidoctor.android.model.NavModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class AppUtils {
    public static final String SENDER = "sender";
    public static final int VIEW_TYPE_SENDER = 0;
    public static final int VIEW_TYPE_RECEIVER = 1;
    public static final String RECEIVER = "receiver";
    private static final String TAG = "AppUtils";
    public static Toast mToast;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    static ProgressDialog progressDialog;

    public static final int PAY_MODE_PAY_ON_VISIT = 4;
    public static final int PAY_MODE_RAZOR_PAYY = 3;
    public static final int PAY_MODE_PAY_U_MONEY = 2;
    public static final int Play = 1;
    public static final int Pause = 0;
    public static final int Stop = 2;
    public static final int Playing = 3;


    public static String getCurrencyFormat(Integer num) {
        String COUNTRY = "IN";
        String LANGUAGE = "en";
        return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
    }

    public static String getCurrencyFormat(double num) {
        String COUNTRY = "IN";
        String LANGUAGE = "en";
        return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
    }

    public static String getCurrencyFormat(long num) {
        String COUNTRY = "IN";
        String LANGUAGE = "en";
        return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(num);
    }

    public static String getCurrencyFormat(String num) {
        Double number = Double.parseDouble(num);
        String COUNTRY = "IN";
        String LANGUAGE = "en";
        return NumberFormat.getCurrencyInstance(new Locale(LANGUAGE, COUNTRY)).format(number);

    }


    public static boolean checkForSpecialCharacter(String inputString) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9-\\s-]");
        Matcher matcher = pattern.matcher(inputString);
        boolean isStringContainsSpecialCharacter = matcher.find();
        if (isStringContainsSpecialCharacter) {
            System.out.println(inputString + " contains special character");
            return true;
        } else {
            System.out.println(inputString + " does NOT contain special character");
            return false;
        }
    }

    public static List<NavModel> getNavData(Activity activity) {
        List<NavModel> navModels = new ArrayList<>();
       /* navModels.add(new NavModel(activity.getString(R.string.appointment), R.drawable.appointments));
        navModels.add(new NavModel(activity.getString(R.string.prescription_history), R.drawable.prescription));
        navModels.add(new NavModel(activity.getString(R.string.investigation_history), R.drawable.investigation));
        navModels.add(new NavModel(activity.getString(R.string.vitals_hitory), R.drawable.investigation));
        navModels.add(new NavModel(activity.getString(R.string.add_family_member), R.drawable.family_members));
        navModels.add(new NavModel(activity.getString(R.string.lab_tests), R.drawable.lab_test_icon));
        navModels.add(new NavModel(activity.getString(R.string.orders), R.drawable.order));
        navModels.add(new NavModel(activity.getString(R.string.notifications), R.drawable.notification));
        navModels.add(new NavModel(activity.getString(R.string.settings), R.drawable.settings));
        navModels.add(new NavModel(activity.getString(R.string.about_us), R.drawable.aboutus));
        navModels.add(new NavModel(activity.getString(R.string.medicine_reminder), R.drawable.ic_baseline_alarm_24));
        navModels.add(new NavModel(activity.getString(R.string.change_language), R.drawable.language_icon));
        navModels.add(new NavModel(activity.getString(R.string.share_app), R.drawable.language_icon));
        navModels.add(new NavModel(activity.getString(R.string.symptom_tracker), R.drawable.symptom_tracker_image));
        navModels.add(new NavModel(activity.getString(R.string.lab_order), R.drawable.symptom_tracker_image));
        navModels.add(new NavModel(activity.getString(R.string.home_isolation_request), R.drawable.symptom_tracker_image));

        navModels.add(new NavModel(activity.getString(R.string.logout), R.drawable.logout));*/
        return navModels;
    }

    public static List<DashboardModel1> getDashboardList(Activity activity) {
        List<DashboardModel1> dashboardModel1s = new ArrayList<>();
        dashboardModel1s.add(new DashboardModel1(activity.getString(R.string.speciality), activity.getString(R.string.find_doctors_by)));
        dashboardModel1s.add(new DashboardModel1(activity.getString(R.string.symptoms), activity.getString(R.string.find_doctors_by)));
        dashboardModel1s.add(new DashboardModel1(activity.getString(R.string.tests), activity.getString(R.string.lab)));
        dashboardModel1s.add(new DashboardModel1(activity.getString(R.string.pharmacy), activity.getString(R.string.digi)));
        return dashboardModel1s;
    }

    public static void shareApp(String uri, String description, Activity activity) {
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(uri))
                .setDomainUriPrefix("https://digidoctor.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.digidoctor.android")
                                .setMinimumVersion(30)
                                .build())
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder("com.criteriontech.digidoc")
                                .setAppStoreId("1517201659")
                                .setMinimumVersion("1.0")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Digi Doctor")
                                .setDescription(description)
                                .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Uri shortLink = task.getResult().getShortLink();
                        Uri flowchartLink = task.getResult().getPreviewLink();
                        Log.d(TAG, "shortLink: " + shortLink);
                        Log.d(TAG, "flowchartLink: " + flowchartLink);
                        assert shortLink != null;
                        openShareAppDialog(shortLink.toString(), activity);

                    } else {
                        Log.d(TAG, "onComplete: Error " + Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    }
                });

    }

    public static void openShareAppDialog(String link, Activity activity) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, link);
        activity.startActivity(Intent.createChooser(intent2, "Share via"));
    }

    public static void showRequestDialog(Activity activity) {

        //Log.d("Token-Number", AppSettings.getString(AppSettings.token));

        try {
            if (!((Activity) activity).isFinishing()) {
                progressDialog = ProgressDialog.show(activity, null, null, false, true);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showRequestDialog(Context activity) {

        //Log.d("Token-Number", AppSettings.getString(AppSettings.token));

        try {
            if (!((Activity) activity).isFinishing()) {
                progressDialog = ProgressDialog.show(activity, null, null, false, true);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
                progressDialog.setContentView(R.layout.progress_bar);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void hideDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static String parseDateToFormatDMY(String oldDate) {
        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "yyyy-MM-dd";
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

    public static String parseUserDate(String oldDate) {
        String inputPattern = "dd/MM/yy";
        String outputPattern = "yyyy-MM-dd";
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


    public static String parseDate(String inDate, String outPattern, String inputPattern) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(inDate);
            str = outputFormat.format(date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
            return inDate;
        }


    }

    public static String parseDate(String inDate, String outPattern) {

        String inputPattern = "dd/MM/yy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(inDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }


    public static String getDayOfWeekDayFromDate(String date) {
        String dayName = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date myDate = inFormat.parse(date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d");
            if (myDate != null) {
                dayName = simpleDateFormat.format(myDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayName;

    }

    public static String getCurrentDateInWeekMonthDayFormat() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current Date => " + c);
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d");
        return df.format(c);
    }

    public static void showToastSort(Context context, String text) {
        if (mToast != null && mToast.getView().isShown()) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static boolean isEmailValid(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public static void showDeleteDialog(Activity activity, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(activity).setMessage(activity.getString(R.string.delete_member_dialog_string))
                .setPositiveButton(activity.getString(R.string.delete_member), onClickListener)
                .setNegativeButton(activity.getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss()).show();
    }


    public static void shareDocProfile(DoctorModel doctorModel, Activity activity) {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, getShareDocText(doctorModel));
        activity.startActivity(Intent.createChooser(intent2, "Share via"));
    }

    private static String getShareDocText(DoctorModel doctorModel) {

        return "hi there !!\nI found A great Doctor '" +
                doctorModel.getDrName() +
                "' having" +
                " Years of experience in " +
                doctorModel.getSpeciality() +
                "." +
                "\nVisit Doctor's profile " +
                "https://www.com.digidoctor/doctor/" +
                doctorModel.getId();
    }


    public static void setAppLocale(String localeCode, Activity activity) {
        Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

    public static List<LanguageModel> getLanguageList() {
        List<LanguageModel> models = new ArrayList<>();
        models.add(new LanguageModel("हिन्दी", "hi", false));
        models.add(new LanguageModel("English", "en", false));
        models.add(new LanguageModel("Marathi", "en", false));
        models.add(new LanguageModel("Tamil", "en", false));
        models.add(new LanguageModel("Urdu", "en", false));
        models.add(new LanguageModel("Arabic", "en", false));
        models.add(new LanguageModel("Sanskrit", "en", false));
        return models;
    }

    public static LanguageModel getLanguageModel(int position) {
        return getLanguageList().get(position);
    }

    public static String capitalizeFirstLetter(String whenToUse) {

        try {
            if (!whenToUse.contains("  ")) {
                String[] words = whenToUse.toLowerCase().split(" ");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];

                    if (i > 0 && word.length() > 0) {
                        builder.append(" ");
                    }

                    String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
                    builder.append(cap);
                }
                return builder.toString();
            } else {
                return whenToUse;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return whenToUse;
        }
    }

    public static List<HashMap<String, String>> jsonObjectToList(JSONObject jsonObject) {

        List<HashMap<String, String>> list = new ArrayList<>();

        try {
            Iterator<String> iterator = jsonObject.keys();

            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = jsonObject.getString(key);

                HashMap<String, String> map = new HashMap<>();
                map.put(key, value.trim());

                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;

    }

    public static Boolean getFreeVisitData(Integer reVisitTime, String appointDate, String selectedDate) {
        String dateSelected = parseDate(selectedDate, "dd MMMM yyyy", "yyyy/MM/dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Calendar c = Calendar.getInstance();
        try {
            Date date1 = sdf.parse(appointDate);
            c.setTime(date1); // Now use today date.
            c.add(Calendar.DATE, reVisitTime); // Adding 5 days
            String output = sdf.format(c.getTime());
            System.out.println(output);
            Log.d(TAG, "selectedDate: " + dateSelected);
            Log.d(TAG, "getFreeVisitData: " + output);
            return dateSelected.compareTo(output) < 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getLastFreeVisitDate(Integer reVisitTime, String appointDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        Calendar c = Calendar.getInstance();
        try {
            Date date1 = sdf.parse(appointDate);
            c.setTime(date1); // Now use today date.
            c.add(Calendar.DATE, reVisitTime); // Adding 5 days
            String output = sdf.format(c.getTime());
            System.out.println(output);
            Log.d(TAG, "getFreeVisitData: " + output);
            return output;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String prettyCount(Integer number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    public static void showToast(String s) {
        try {
            Toast.makeText(App.context, s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showToolbar(Activity activity) {
        Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).show();
    }

    public static void hideToolbar(Activity activity) {
        Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).hide();
    }

    public void downloadPdf(FragmentActivity fragmentActivity) {
        Toast.makeText(fragmentActivity, "Downloading pdf...", Toast.LENGTH_SHORT).show();
        String downLoadPdfUrl = "https://digidoctor.in/PostCovidCare.pdf";
        Uri uri = Uri.parse(downLoadPdfUrl);
        downloadFile(App.context, uri);

    }

    private void downloadFile(Context context, Uri uri) {
        DownloadManager downloadManager = (DownloadManager)
                context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS, "post_coivd_booklet");
        downloadManager.enqueue(request);
        Notification.createLocalNotification("Downloading Started", "Downloading Post Covid Booklet pdf");
    }

    public static String getTimeFormat(long currentTimeMillis, String outFormat) {
        try {

            Timestamp ts = new Timestamp(System.currentTimeMillis());
            Date date = ts;
            System.out.println(date);

            @SuppressLint("SimpleDateFormat") String value = new java.text.SimpleDateFormat(outFormat).
                    format(date);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    public static String StringToDate(String date, String dateInPattern) {

        Calendar today = Calendar.getInstance();


        SimpleDateFormat sdf = new SimpleDateFormat(dateInPattern);
        Calendar dob = Calendar.getInstance();
        try {
            dob.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int curYear = today.get(Calendar.YEAR);
        int dobYear = dob.get(Calendar.YEAR);

        int age = curYear - dobYear;


        // if dob is month or day is behind today's month or day
        // reduce age by 1
        int curMonth = today.get(Calendar.MONTH);
        int dobMonth = dob.get(Calendar.MONTH);
        int months = curMonth - dobMonth;


        int currDay = today.get(Calendar.DAY_OF_MONTH);
        int dobdDay = dob.get(Calendar.DAY_OF_MONTH);
        int Day = currDay - dobdDay;

        Log.d(TAG, "StringToDate: curMonth " + curMonth);
        Log.d(TAG, "StringToDate: dobMonth " + dobMonth);


        if (dobMonth > curMonth) { // this year can't be counted!
            age--;
        } else if (dobMonth == curMonth) { // same month? check for day
            int curDay = today.get(Calendar.DAY_OF_MONTH);
            int dobDay = dob.get(Calendar.DAY_OF_MONTH);
            if (dobDay > curDay) { // this year can't be counted!
                age--;
            }
        }

        if (age == 0 && months == 0)
            return Day + " day(s) old";
        else if (age == 0)
            return months + " months " + Day + " day(s) old";
        else if (months == 0) {
            return age + "year(s)";
        } else
            return age + "year(s) " + months + " months old";
    }

}
