package sorindonosa.whereismyrabbit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class NotificationReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notifManag = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent myIntent = new Intent(context, Feed.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pend = PendingIntent.getActivity(context, 100, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent buyIntent = new Intent(context, Buy.class);
        buyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SharedPreferences.Editor editor = Menu.sharedPref.edit();

        long currentDate = System.currentTimeMillis();
        Uri sound = Uri.parse("android.resource://sorindonosa.whereismyrabbit/" + R.raw.notification);

        long alarmTime = Menu.sharedPref.getLong("alarmTime", 0L);
        long superRabbits = Menu.sharedPref.getLong("superRabbits", 0L);
        long vegDateMillis = Menu.sharedPref.getLong("vegDate", currentDate);

        long superRab = getLevel(superRabbits);
        int grup = (int) superRab * Feed.NR_SUPER_GRUP;

        long diffZile = vegDateMillis - currentDate;
        long diffVeg = (int) (diffZile / ((grup + 1) * (1000 * 60 * 60 * 24)));

        long diffs = alarmTime - currentDate;
        int diff = (int) (diffs / (1000 * 60 * 60 * 24));

        if (diff < 0) {
            if (diffVeg <= 0) {
                if (alarmTime + 86400000 < currentDate) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentIntent(pend)
                            .setSmallIcon(R.drawable.rabbit_32)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.rabbit_32))
                            .setContentTitle("FEED your rabbits!").setContentText("Your rabbits are starving!")
                            .setTicker("Feed your Rabbits!").setVibrate(new long[]{500, 500, 500, 500})
                            .setSound(sound).setLights(Color.GREEN, 500, 1000)
                            .setAutoCancel(true);

                    assert notifManag != null;
                    notifManag.notify(100, builder.build());

                    long timeNow = Calendar.getInstance().getTimeInMillis();
                    editor.putLong("alarmTime", timeNow);
                    if (superRabbits > 50) {
                        editor.putLong("superRabbits", superRabbits - 50);
                    } else {
                        editor.putLong("superRabbits", 0);
                    }
                    editor.apply();
                }
            }

            if (diffVeg == 1) {

                if (alarmTime + 86400000 < currentDate) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentIntent(pend)
                            .setSmallIcon(R.drawable.rabbit_32)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.rabbit_32))
                            .setContentTitle("FEED your rabbits!").setContentText("You have low food!")
                            .setTicker("Feed your Rabbits!").setVibrate(new long[]{500, 500, 500, 500})
                            .setSound(sound).setLights(Color.GREEN, 500, 1000).setAutoCancel(true);

                    assert notifManag != null;
                    notifManag.notify(100, builder.build());

                    long timeNow = Calendar.getInstance().getTimeInMillis();
                    editor.putLong("alarmTime", timeNow);
                    editor.commit();

                }
            }

        }

    }

    public long getLevel(long rabbits) {
        return rabbits / 5000;
    }

}
