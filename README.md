# Notifications
This is a basic app to show some backgound tasks. It Creates a notification that fires every few minutes while the device is plugged in. The notification is run on a firebase jobService and runs even when the app is in the background. An asyncTask is used to do work on a background thread. When the notification or one of its buttons is tapped it uses a PendingIntent to show a NotificationActivity. 
