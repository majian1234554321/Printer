package com.leyuan.printer.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import com.leyuan.printer.ui.App;

/**
 * Created by user on 2017/4/17.
 */
public class PrintJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        App.context.startService(new Intent(App.context, ForegroundRunService.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
