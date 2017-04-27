package com.leyuan.printer.receivers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.leyuan.printer.services.PrintJobService;

/**
 * Created by user on 2017/4/18.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int jobId = 1;
        JobInfo.Builder jobInfo =
                new JobInfo.Builder(jobId, new ComponentName(context, PrintJobService.class));
        jobInfo.setPeriodic(1000 * 10);
        jobInfo.setPersisted(true);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo.build());
    }

}
