package android.reserver.stv2.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.reserver.stv2.R;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * This class is used to send, receive and handle broadcasts. It extends the BroadcastReceiver
 * class.
 */
public class MyReceiver extends BroadcastReceiver {

    String channel_id ="test"; // creates a String variable to hold the channel_id "test"
    static int notificationID; // creates a static integer to hold the notificationID;

    /**
     * This method is called for the registered event
     * @param context the context which is running
     * @param intent the intent that is received
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        // creates an instance of the method using the class variable
        createNotificationChannel(context, channel_id);
        // builds a new notification and assigns it to the notification variable
        Notification notification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // sets icon
                .setContentText(intent.getStringExtra("title")) // sets message
                .setContentTitle("Notification").build(); // sets title
        // creates a system service and assigns it to a notificationManager variable
        NotificationManager notificationManager = (NotificationManager)context.getSystemService
                (NOTIFICATION_SERVICE);
        // creates an instance of the manager with the notificationID
        notificationManager.notify(notificationID++, notification);

        }

    /**
     * This method creates a notification channel for the application
     * @param context running context
     * @param CHANNEL_ID the id of the notification channel
     */
    private  void createNotificationChannel(Context context, String CHANNEL_ID) {

        // branch statement for APIs with supported libraries
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = context.getResources().getString(R.string.channel_name); // creates a variable to hold the channelName
            // creates a variable to hold the channel description
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // creates new instance of NotificationChannel and assigns to variable
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description); // sets the channel descrition
            // creates instance of the notification manager
            NotificationManager notificationManager = context.getSystemService
                    (NotificationManager.class);
            // creates the notification channel
            notificationManager.createNotificationChannel(channel);
        }
    }
}