package com.leyuan.printer.ui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.leyuan.printer.serialport.SerialPort;
import com.leyuan.printer.serialport.SerialPortFinder;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

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
    private SerialPort mSerialPort = null;

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


    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
			/* Read serial port parameters */
            SharedPreferences sp = getSharedPreferences("android_serialport_api.sample_preferences", MODE_PRIVATE);
            String path = sp.getString("DEVICE", "");
            int baudrate = Integer.decode(sp.getString("BAUDRATE", "-1"));

			/* Check parameters */
            if ( (path.length() == 0) || (baudrate == -1)) {
                throw new InvalidParameterException();
            }

			/* Open the serial port */
            mSerialPort = new SerialPort(new File(path), baudrate, 0);
        }
        return mSerialPort;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    public SerialPort getPrintPort() throws SecurityException, IOException, InvalidParameterException{
        if(mPrintPort == null){
            mSerialPort = new SerialPort(new File("/dev/ttys1"), 115200, 0);
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
