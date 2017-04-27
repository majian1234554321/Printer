package com.leyuan.printer.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.leyuan.printer.ui.App;
import com.leyuan.printer.ui.SplashActivity;

/**
 * Created by user on 2017/4/17.
 */
public class ForegroundRunService extends Service {
    private static final int CHECK_RUNNING_STATE = 1;
    private static final long INTERVAL = 30 * 1000;
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

            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        handler.removeMessages(CHECK_RUNNING_STATE);
        handler.sendEmptyMessageDelayed(CHECK_RUNNING_STATE, INTERVAL);

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
}
