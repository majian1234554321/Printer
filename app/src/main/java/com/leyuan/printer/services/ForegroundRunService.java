package com.leyuan.printer.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.leyuan.printer.config.Constant;
import com.leyuan.printer.ui.App;
import com.leyuan.printer.ui.SplashActivity;
import com.leyuan.printer.utils.ScanUtils;
import com.leyuan.printer.utils.ToastGlobal;

import java.io.IOException;
import java.io.InputStream;

import android_serialport_api.SerialPort;

/**
 * Created by user on 2017/4/17.
 */
public class ForegroundRunService extends Service {
    private static final int CHECK_RUNNING_STATE = 1;
    private static final long INTERVAL = 30 * 1000;
    private static final int BROADCAST = 2;
    private static final int APPEND = 3;
    private static final int END_TAG = 4;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CHECK_RUNNING_STATE) {
                handler.removeMessages(CHECK_RUNNING_STATE);
                handler.sendEmptyMessageDelayed(CHECK_RUNNING_STATE, INTERVAL);
                if (!App.getInstance().isForeground) {
//                    if (App.getInstance().mActivities != null) {
                    App.getInstance().exitApp();
//                    }

                    Intent intent = new Intent(ForegroundRunService.this, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }

            } else if (msg.what == BROADCAST) {
                ToastGlobal.showShort("sendBroadcast");
            } else if (msg.what == END_TAG) {
                ToastGlobal.showShort("sendBroadcast");
            } else if (msg.what == APPEND) {
                ToastGlobal.showShortConsecutive("APPEND");
            }
        }
    };


    private StringBuffer scanBuffer = new StringBuffer();


    @Override
    public void onCreate() {
        super.onCreate();
        handler.removeMessages(CHECK_RUNNING_STATE);
        handler.sendEmptyMessageDelayed(CHECK_RUNNING_STATE, INTERVAL);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    SerialPort scanPort = App.getInstance().getScanPort();
                    InputStream mInputStream = scanPort.getInputStream();
                    while (true) {
                        int size;
                        try {
                            byte[] buffer = new byte[64];
                            if (mInputStream == null) return;
                            size = mInputStream.read(buffer);
                            if (size > 0) {
                                byte b = buffer[0];
                                if (b > 32 && b <= 126) {

//                                    handler.sendEmptyMessage(APPEND);
                                    scanBuffer.append(new String(buffer, 0, size));

                                } else if (b == ScanUtils.END_TAG) {
//                                    handler.sendEmptyMessage(END_TAG);
                                    Intent intent = new Intent(Constant.RECEIVER_SCAN);
                                    intent.putExtra("scanBuffer", scanBuffer.toString());
                                    sendBroadcast(intent);
                                    scanBuffer.delete(0, scanBuffer.length());

                                }

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);


    }


//    public static SerialPort getSCanPort() throws SecurityException, IOException, InvalidParameterException {
//        return App.getInstance().getScanPort();
//    }
}
