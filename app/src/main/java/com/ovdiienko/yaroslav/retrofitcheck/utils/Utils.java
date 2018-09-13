package com.ovdiienko.yaroslav.retrofitcheck.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ovdiienko.yaroslav.retrofitcheck.BasicApp;

import java.util.regex.Pattern;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class Utils {

    public static boolean isEmailValid(CharSequence email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})$");
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        String pattern = "^[a-zA-Z0-9]*$";
        return password.matches(pattern) && !password.isEmpty();
    }

    public static void signOut(Context context) {
        PreferencesUtils.put(context, PreferencesKeys.IS_LOGGED_IN, false);
        PreferencesUtils.put(context, PreferencesKeys.OWN_USER_LOGIN, "");
        PreferencesUtils.put(context, PreferencesKeys.OWN_USER_TOKEN, "");

        // clearAllTables() is used because only one entity apply
        ((BasicApp) context.getApplicationContext()).getDataRepository().clearAllTables();
    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
