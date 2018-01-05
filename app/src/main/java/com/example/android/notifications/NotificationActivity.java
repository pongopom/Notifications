package com.example.android.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    int mNotificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        TextView textView = findViewById(R.id.textView2);
        textView.setText(getIntent().getStringExtra("KEY"));
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras.containsKey("KEY")) {
            String buttonTapped = extras.getString("KEY");
            // show the string we passed in in a textView
            textView.setText(buttonTapped);
        }
        // check if we passed in a Notification id and if so use it to get and dismiss our notification
        if (extras.containsKey("NOTIFICATION_ID")) {
            mNotificationId = extras.getInt("NOTIFICATION_ID", 0);
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            //dismiss the notification
            manager.cancel(mNotificationId);
        }
    }

    //dismiss this activity
    public void done(View view) {
        finish();
    }

}
