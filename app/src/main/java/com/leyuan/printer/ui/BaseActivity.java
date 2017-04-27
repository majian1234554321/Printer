package com.leyuan.printer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.leyuan.printer.utils.Logger;
import com.umeng.analytics.MobclickAgent;

import static com.leyuan.printer.ui.App.mActivities;

/**
 * Created by user on 2017/4/11.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities) {
            mActivities.add(this);
            Logger.w("mActivities.add(this) : ", getClass().getSimpleName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
            Logger.w(" mActivities.remove(this) : ", getClass().getSimpleName());
        }
    }



}
