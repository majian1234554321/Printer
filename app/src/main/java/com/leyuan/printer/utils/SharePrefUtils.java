package com.leyuan.printer.utils;


import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefUtils {
    private static final String SHARE_PREFS_NAME = "dataconfig";
    private static final String TAG = "SharePrefUtils";
    private static SharedPreferences mSharedPreferences;

    public static void putInt(Context ctx, String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt(key, defaultValue);
    }


    public static void putBoolean(Context ctx, String key, boolean value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getString(key, defaultValue);
    }


    public static String getToken(Context context) {
        return getString(context, "token", null);
    }

    public static void setToken(Context context, String token) {
        putString(context, "token", token);
    }
}
