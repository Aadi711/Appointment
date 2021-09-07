package com.example.appointment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    private static SharedPreferences mSharedPref;
    public static String IS_SHORTCUT_CREATED = "isShortcutCreated";

    private PreferencesManager() {

    }

    public static void init(Context context) {
        if (mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }

//    public static String read(Context context, String key, String defValue) {
//        if (mSharedPref!=null) {
//            return mSharedPref.getString(key, defValue);
//        }else {
//            init(context);
//        }
//        return "";
//    }

    public static String read(Context context, String key, String defValue) {
        if (mSharedPref != null) {
            return mSharedPref.getString(key, defValue);
        } else {
            init(context);
            return mSharedPref.getString(key, defValue);
        }
    }

    public static void write(String key, String value) {
        if (mSharedPref != null) {
            SharedPreferences.Editor prefsEditor = mSharedPref.edit();
            prefsEditor.putString(key, value);
            prefsEditor.commit();
        }
    }

    public static boolean read(String key, boolean defValue) {
        if (mSharedPref != null)
            return mSharedPref.getBoolean(key, defValue);
        return false;
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static long read(Context context, String key, long defValue) {

        if (mSharedPref != null) {
            return mSharedPref.getLong(key, defValue);
        } else {
            init(context);
            return mSharedPref.getLong(key, defValue);
        }
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).commit();
    }

    public static void write(String key, long value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putLong(key, value).commit();
    }

    public static void clear() {
        try {
            mSharedPref.edit().clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearSpecificKey(String key) {
        try {
            mSharedPref.edit().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

