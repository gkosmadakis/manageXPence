package uk.co.irokottaki.moneycontrol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import uk.co.irokottaki.moneycontrol.utils.Utils;

public class AlarmReceiver extends BroadcastReceiver {
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, SplashScreen.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SplashScreen.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent
                .FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Utils utils = new Utils(context);
        Notification notification = builder
                .setContentTitle("ManageXPence")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Spending too much? Add " +
                        "your expenses on ManageXPence"))
                .setContentText("Spending too much? Add your expenses on ManageXPence")
                .setTicker("ManageXPence")
                .setSmallIcon(utils.getNotificationIcon())
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService
                (Context.NOTIFICATION_SERVICE);
        // Hide the notification after its selected
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
        Toast.makeText(context, "Notification is triggered", Toast.LENGTH_LONG).show();
    }

}