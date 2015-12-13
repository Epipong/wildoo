package com.davy.adrien.wildoo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class TimerService extends IntentService {

    private Notification.Builder mBuilder;
    private boolean isPlaying = false;
    private String mTime;
    private String mTaskName;

    public TimerService() {
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

        notifUp(workIntent);
    }

    @Override
    public void onCreate() {

        Log.d("adrien", "Service has been created ma gueule");

    }

    private int getIconFromStatus() {
        return isPlaying ? R.drawable.ic_pause
                : R.drawable.ic_play;
    }

    private void notifUp(Intent workIntent) {


        Bundle extras = workIntent == null ? null : workIntent.getExtras();

        if (extras != null) {
            mTime = extras.getString("ETA");
            mTaskName = extras.getString("task_name");
        }

        mBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_action_play)
                .setContentTitle(mTaskName + " task")
                .setContentText(mTime)
                .setOngoing(true);

        PendingIntent playPausePIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        if (isPlaying) {
            Notification.Action.Builder ab =
                    new Notification.Action.Builder(R.drawable.ic_pause, "Pause", playPausePIntent);
            mBuilder.addAction(ab.build());
        } else {
            Notification.Action.Builder ab =
                    new Notification.Action.Builder(R.drawable.ic_play, "Play", playPausePIntent);
            mBuilder.addAction(ab.build());
        }

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //mBuilder.setStyle(new Notification.MediaStyle().setShowActionsInCompactView(1));
        notifyMgr.notify(0, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        notifUp(intent);
        Log.d("adrien", "service has been started");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("adrien", "service has been binded");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("adrien", "the service has been destroyed ma gueule");
    }

}
