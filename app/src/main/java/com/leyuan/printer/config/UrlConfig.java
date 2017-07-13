package com.leyuan.printer.config;


/**
 * Created by user on 2015/5/20
 */
public class UrlConfig {
    public static final boolean debug = true;

    private static String urlHost;

    static {
        if (debug) {
            urlHost = "http://app.aidong.me/checkin-1/";
        } else {
//            urlHost = "http://m.aidong.me/aidong11/";
//            urlHost = "http://m.aidong.me/campaign/";
            urlHost = "http://app.aidong.me/checkin-1/";
        }
    }

    public static final String BASE_URL = urlHost;
}

