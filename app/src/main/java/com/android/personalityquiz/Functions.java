package com.android.personalityquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    //2017-01-12 17:31:03

    public static final String ServerDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String HHmmss = "HH:mm:ss";
    public static final String hhmmAMPM = "hh:mm a";
    public static final String ddMMMYYYY = "dd MMM, yyyy";
    //17 Jan, 2017 03:11pm
    public static final String CommonDateTimeFormat = ddMMMYYYY + "" + hhmmAMPM;

    public static final String ServerDateFormat = "yyyy-MM-dd";

    public static DialogOptionsSelectedListener dialogOptionsSelectedListener = null;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void fireIntent(Context context, Class cls, boolean isNew) {
        Intent i = new Intent(context, cls);
        context.startActivity(i);
        if (isNew) {
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public static void fireIntent(Context context, Intent intent, boolean isNew) {
        context.startActivity(intent);
        if (isNew) {
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static void hideKeyPad(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static Typeface getRegularFont(Context _context) {
        Typeface tf = Typeface.createFromAsset(_context.getAssets(), "ge_ss_bold.otf");
        return tf;
    }

    public static Typeface getBoldFont(Context _context) {
        Typeface tf = Typeface.createFromAsset(_context.getAssets(), "fonts/bold.ttf");
        return tf;
    }

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String toStr(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static int toLength(EditText editText) {
        return editText.getText().toString().trim().length();
    }

  /*  public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }*/

    public static void openInMap(Context context, double latitude, double longitude, String labelName) {
        String newUri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + labelName + ")";

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newUri));
        context.startActivity(intent);
    }

    public static void makePhoneCall(Context context, String callNo) {
        Intent dialIntent = new Intent();
        dialIntent.setAction(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + callNo));
        context.startActivity(dialIntent);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }

    public static String parseDate2(String inputDate, SimpleDateFormat outputFormat, SimpleDateFormat inputFormat) {

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(inputDate);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    public static String checkString(String input) {
        if (input != null && input.trim().toString().length() > 0 && !input.toString().toLowerCase().equals("null")) {
            return input;
        } else {
            return "";
        }
    }

    public interface DialogOptionsSelectedListener {
        void onSelect(boolean isYes);
    }

    public static void setDialogOptionsSelectedListener(DialogOptionsSelectedListener _dialogOptionsSelectedListener) {
        dialogOptionsSelectedListener = _dialogOptionsSelectedListener;
    }
}
