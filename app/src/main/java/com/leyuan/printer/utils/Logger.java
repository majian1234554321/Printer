package com.leyuan.printer.utils;

import android.util.Log;

import com.leyuan.printer.config.UrlConfig;


public final class Logger {


    public static void i(String msg) {
        if (UrlConfig.debug) {
            Log.i("aidong", msg);
        }
    }

    public static void v(String tag, String msg) {
        if (UrlConfig.debug) {
                Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (UrlConfig.debug) {
                Log.v(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (UrlConfig.debug) {
                Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (UrlConfig.debug) {
                Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (UrlConfig.debug) {
                Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (UrlConfig.debug) {
                Log.i(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (UrlConfig.debug) {
                Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (UrlConfig.debug) {
                Log.w(tag, msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (UrlConfig.debug) {
                Log.w(tag, tr.getMessage(), tr);
        }
    }

    public static void e(String tag, String msg) {
        if (UrlConfig.debug) {
                Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (UrlConfig.debug) {
                Log.e(tag, msg, tr);
        }
    }

    public static void e(String tag, Throwable tr) {
        if (UrlConfig.debug) {
                Log.e(tag, tr.getMessage(), tr);
        }
    }

    public static void wtf(String tag, String msg) {
        if (UrlConfig.debug) {
                Log.wtf(tag, msg);
        }
    }

    public static void wtf(String tag, Throwable tr) {
        if (UrlConfig.debug) {
                Log.wtf(tag, tr);
        }
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (UrlConfig.debug) {
                Logger.wtf(tag, msg, tr);
        }
    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 3500) {
            Logger.i(tag, content.substring(0, 3500));
            largeLog(tag, content.substring(3500));
        } else {
            Logger.i(tag, content);
        }
    }
}
