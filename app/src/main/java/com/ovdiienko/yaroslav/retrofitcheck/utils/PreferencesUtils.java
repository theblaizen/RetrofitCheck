package com.ovdiienko.yaroslav.retrofitcheck.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class PreferencesUtils {
    private static final String SOURCE = "com.ovdiienko.yaroslav.retrofitcheck";

    public static void put(Context context, PreferencesKeys key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key.get(), (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key.get(), (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key.get(), (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key.get(), (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key.get(), (Long) object);
        } else {
            editor.putString(key.get(), object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    @Nullable
    public static Object get(Context context, PreferencesKeys key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key.get(), (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key.get(), (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key.get(), (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key.get(), (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key.get(), (Long) defaultObject);
        }

        return null;
    }

    public static void remove(Context context, PreferencesKeys key) {
        SharedPreferences sp = context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key.get());

        SharedPreferencesCompat.apply(editor);
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();

        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(Context context, PreferencesKeys key) {
        SharedPreferences sp = context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
        return sp.contains(key.get());
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SOURCE, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();


        }
    }
}
