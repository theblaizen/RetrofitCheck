package com.ovdiienko.yaroslav.retrofitcheck.utils;

import android.support.annotation.Nullable;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

/**
 * Use to <b>store keys</b> for <b>SharedPreferences</b> in app.
 */
public enum PreferencesKeys {
    IS_LOGGED_IN("IS_USER_LOGGED_IN"),
    OWN_USER_LOGIN("OWN_USER_LOGIN"),
    OWN_USER_TOKEN("OWN_USER_TOKEN");

    private String key;

    PreferencesKeys(String key) {
        this.key = key;
    }

    public String get() {
        return key;
    }

    @Nullable
    public PreferencesKeys findByKey(String key) {
        for (PreferencesKeys item : PreferencesKeys.values()) {
            if (item.key.equals(key))
                return item;
        }
        return null;
    }

}
