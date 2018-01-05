package com.example.android.notifications;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by peterpomlett on 29/12/2017.
 */

public class NotificationJob {

    private static boolean mJobStarted;

    synchronized public static void startJob(Context context) {
        if (mJobStarted) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job job = dispatcher.newJobBuilder()
                .setService(NotificationJobService.class)
                .setTag("notification_job")
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(60, 120))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(job);
        mJobStarted = true;
    }

}
