package com.leyuan.printer.ui;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.facebook.stetho.Stetho;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.leyuan.printer.config.Constant;
import com.leyuan.printer.utils.ForegroundCallbacks;
import com.leyuan.printer.utils.Logger;
import com.leyuan.printer.utils.SharePrefUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

/**
 * Created by user on 2017/4/10.
 */

public class App extends Application {

    private static App mInstance;
    public static Context context;
    private String token;

    public static double lat;
    public static double lon;

    public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mPrintPort = null;
    private SerialPort mScanPort = null;
    public boolean isForeground;
    private static String channel;

    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();


        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        // 如果不需要自动登录，在初始化 SDK 初始化的时候 false 关闭
//        options.setAutoLogin(false);
//初始化
        EMClient.getInstance().init(context, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        ForegroundCallbacks foregroundCallbacks = ForegroundCallbacks.init(this);
        foregroundCallbacks.addListener(foregroundListener);
        Stetho.initializeWithDefaults(this);


    }

    public String getChannel() {
        if (channel == null) {
            try {
                ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(),
                        PackageManager.GET_META_DATA);
                channel = appInfo.metaData.getString("UMENG_CHANNEL");
                // Tag﹕ app key : AIzaSyBhBFOgVQclaa8p1JJeqaZHiCo2nfiyBBo
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Logger.i("UMENG_CHANNEL = " + channel);
        }
        return channel;
    }

    public static App getInstance() {
        return mInstance;
    }

    public SerialPort getPrintPort() throws SecurityException, IOException, InvalidParameterException {
        if (mPrintPort == null) {
            mPrintPort = new SerialPort(new File(Constant.PRINT_PORT), 115200, 0);
        }
        return mPrintPort;
    }

    public void closePrintPort() {
        if (mPrintPort != null) {
            mPrintPort.close();
            mPrintPort = null;
        }
    }

    public SerialPort getScanPort() throws SecurityException, IOException, InvalidParameterException {
        if (mScanPort == null) {
            mScanPort = new SerialPort(new File(Constant.SCAN_PORT), Constant.SCAN_RATE, 0);
        }
        return mScanPort;
    }

    public void closeScanPort() {
        if (mScanPort != null) {
            mScanPort.close();
            mScanPort = null;
        }
    }

    private ForegroundCallbacks.Listener foregroundListener = new ForegroundCallbacks.Listener() {
        @Override
        public void onBecameForeground() {
            isForeground = true;
        }

        @Override
        public void onBecameBackground() {
            isForeground = false;
        }
    };

    public void exitApp() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }


    private String deviceId;

    public String getDevicesId() {
        if (deviceId == null) {
            deviceId = SharePrefUtils.getDeviesId(this);
        }
        return deviceId;
    }
}
