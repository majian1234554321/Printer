package com.leyuan.printer.ui;

import android.app.Application;
import android.content.Context;

import com.leyuan.printer.config.Constant;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

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
//    private SerialPort mSerialPort = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();
//        Stetho.initializeWithDefaults(this);
    }

    public static App getInstance() {
        return mInstance;
    }

    public SerialPort getPrintPort() throws SecurityException, IOException, InvalidParameterException{
        if(mPrintPort == null){
            mPrintPort = new SerialPort(new File(Constant.PRINT_PORT), 115200, 0);
        }
        return mPrintPort;
    }

    public void closePrintPort(){
        if(mPrintPort != null){
            mPrintPort.close();
            mPrintPort = null;
        }
    }



}
