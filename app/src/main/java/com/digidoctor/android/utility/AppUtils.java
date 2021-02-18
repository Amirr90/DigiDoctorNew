package com.digidoctor.android.utility;

import android.app.Activity;
import android.app.AlertDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    private static final String TAG = "AppUtils";
    public static Toast mToast;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    static ProgressDialog progressDialog;

    public static final int PAY_MODE_PAY_ON_VISIT = 4;
    public static final int PAY_MODE_RAZOR_PAYY = 3;
    public static final int PAY_MODE_PAY_U_MONEY = 2;


    public static List<NavModel> getNavData(Activity activity) {
        List<NavModel> navModels = new ArrayList<>();
        navModels.add(new NavModel(activity.getString(R.string.appointment), R.drawable.appointments));
        navModels.add(new NavModel(activity.getString(R.string.prescription_history), R.drawable.prescription));
        navModels.add(new NavModel(activity.getString(R.string.investigation_history), R.drawable.investigation));
        navModels.add(new NavModel(activity.getString(R.string.vitals_hitory), R.drawable.investigation));
        navModels.add(new NavModel(activity.getString(R.string.add_family_member), R.drawable.family_members));
        navModels.add(new NavModel(activity.getString(R.string.lab_tests), R.drawable.lab_test_icon));
        navModels.add(new NavModel(activity.getString(R.string.orders), R.drawable.order));
        navModels.add(new NavModel(activity.getString(R.string.notifications), R.drawable.notification));
        navModels.add(new NavModel(activity.getString(R.string.settings), R.drawable.settings));
        navModels.add(new NavModel(activity.getString(R.string.about_us), R.drawable.aboutus));
        navModels.add(new NavModel(activity.getString(R.string.logout), R.drawable.logout));
        navModels.add(new NavModel(activity.getString(R.string.change_language), R.drawable.language_icon));
        navModels.add(new NavModel(activity.getString(R.string.share_app), R.drawable.language_icon));
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
                if (progressDialog == null) {

                    progressDialog = ProgressDialog.show(activity, null, null, false, true);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
                    progressDialog.setContentView(R.layout.progress_bar);
                    progressDialog.show();
                } else {

                    progressDialog = ProgressDialog.show(activity, null, null, false, true);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
                    progressDialog.setContentView(R.layout.progress_bar);
                    progressDialog.show();
                }
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

    public static String parseUserDate(String oldDate) {
        String inputPattern = "dd/MM/yy";
        String outputPattern = "yyyy-MM-dd";
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


    public static String parseDate(String inDate, String outPattern) {

        String inputPattern = "dd/MM/yy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outPattern);

        Date date = null;
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
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
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

        StringBuilder builder = new StringBuilder();
        builder.append("hi there !!\nI found A great Doctor '")
                .append(doctorModel.getDrName())
                .append("' having")
                .append(" Years of experience in ")
                .append(doctorModel.getSpeciality())
                .append(".")
                .append("\nVisit Doctor's profile ")
                .append("https://www.com.digidoctor/doctor/")
                .append(doctorModel.getId());
        return builder.toString();
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

}
