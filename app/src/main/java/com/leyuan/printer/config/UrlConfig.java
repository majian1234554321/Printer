package com.leyuan.printer.config;


/**
 * Created by user on 2015/5/20
 */
public class UrlConfig {
    public static final boolean debug = false;

    private static String urlHost;

    static {
        if (debug) {
            urlHost = "http://m1.aidong.me/app/";
        } else {
//            urlHost = "http://m.aidong.me/aidong11/";
            urlHost = "http://m.aidong.me/campaign/";
        }
    }

    public static final String BASE_URL = urlHost;
}

