package com.example.android.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // create an int to store the notification id this can be any unique int
    private static final int NOTIFICATION_ID = 1000;
    // since Oreo we need to create a channel this is the id for that channel
    private static final String OREO_CHANNEL_ID = "oreo-channel-id";
    //Create a pending intent code
    private static final int PENDING_INTENT_CODE = 12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Start the job service
        NotificationJob.startJob(this);
    }


    // create a method to build the notification
    public static void createNotification(NotificationJobService context) {
        // create a NotificationManager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        // create a channel needed for Oreo
        //check if device is running  oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //if running oreo create a channel
            NotificationChannel mChannel = new NotificationChannel(OREO_CHANNEL_ID, "Important", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        // build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, OREO_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(android.R.drawable.ic_menu_info_details)
                .setVibrate(new long[] { 1000, 1000})
                .setContentTitle("Notification Test")
                .setContentText("Tap to show the NotificationActivity")
                .setContentIntent(content("No buttons tapped", context))
                .addAction(button1Action(context))
                .addAction(button2Action(context))
                .setAutoCancel(true);
        // add high priority to none Oreo versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        // call notify on the notification manager to show
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    // create methods for the notification buttons
    // first button
    public static Action button1Action(Context context) {
        //create an intent to show the NotificationActivity
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        Bundle extras = new Bundle();
        // pass in a sting to show in the NotificationActivity
        extras.putString("KEY", "Button 1 tapped");
        //when using the notification button the notification is not automatically dismissed so pass in the id so we have a reference to the notification
        //  so we can dismiss it when we are done
        extras.putInt("NOTIFICATION_ID", NOTIFICATION_ID);
        notificationIntent.putExtras(extras);
        //create a pending intent that contains our notificationIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, PENDING_INTENT_CODE, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //create and return the action
        Action action = new Action(android.R.drawable.ic_menu_info_details, "Button 1", pendingIntent);
        return action;
    }

    // second button
    public static Action button2Action(Context context) {
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        Bundle extras = new Bundle();
        extras.putString("KEY", "Button 2 tapped");
        extras.putInt("NOTIFICATION_ID", NOTIFICATION_ID);
        notificationIntent.putExtras(extras);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 13, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Action action = new Action(android.R.drawable.ic_menu_info_details, "Button 2", pendingIntent);
        return action;
    }

    // is called if the notification body is tapped (No buttons tapped
    // in this case the notification is dismissed automatically so no need to pass the notification id
    public static PendingIntent content(String extra, Context context) {
        Intent notificationIntent = new Intent(context, NotificationActivity.class);
        notificationIntent.putExtra("KEY", extra);
        return PendingIntent.getActivity(context, 14, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
