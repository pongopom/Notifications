package com.example.android.notifications;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class NotificationJobService extends JobService {
    JobParameters mJobParameters;
    DoOnBackgroundThread mDoOnBackgroundThread;
    @Override
    public boolean onStartJob(com.firebase.jobdispatcher.JobParameters job) {
        mJobParameters = job;
        mDoOnBackgroundThread = new DoOnBackgroundThread();
        mDoOnBackgroundThread.execute(NotificationJobService.this);
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
       mDoOnBackgroundThread.cancel(true);
        return true;
    }

}

//AsyncTask subclass
class DoOnBackgroundThread extends AsyncTask<Context, Void, Context> {

    @Override
    protected Context doInBackground(Context... contexts) {
        MainActivity.createNotification((NotificationJobService) contexts[0]);
        return contexts[0];
    }

    @Override
    protected void onPostExecute(Context context) {
        NotificationJobService notificationJobService = (NotificationJobService) context;
        notificationJobService.jobFinished(notificationJobService.mJobParameters, true);
    }

}