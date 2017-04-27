package com.leyuan.printer.ui;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.leyuan.printer.R;
import com.leyuan.printer.mvp.presenter.PrinterPresenter;
import com.leyuan.printer.services.PrintJobService;
import com.leyuan.printer.utils.WindowDisplayUtils;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by user on 2017/4/10.
 */
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            if (gifImageView != null) {
//                GifDrawable g = (GifDrawable) gifImageView.getDrawable();
//                if (g != null) {
//                    g.stop();
//                }
//            }
            finish();

        }
    };
    private GifImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        startJobScheduler();
        handler.sendEmptyMessageDelayed(1, 6000);
        PrinterPresenter presenter = new PrinterPresenter(this);
        presenter.getBanners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WindowDisplayUtils.hideNavigationBar(this);
    }

    /**
     * 5.x以上系统启用 JobScheduler API 进行实现守护进程的唤醒操作
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startJobScheduler() {
        int jobId = 1;
        JobInfo.Builder jobInfo =
                new JobInfo.Builder(jobId, new ComponentName(this, PrintJobService.class));
        jobInfo.setPeriodic(1000 * 10);
        jobInfo.setPersisted(true);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo.build());
    }

}
