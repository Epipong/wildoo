package com.davy.adrien.wildoo;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.adrien.common.JsonToTask;

public class TaskNotification {

    static int notificationId = 001;
    public TaskNotification(JSONObject data, Context context) throws JSONException
    {
        notificationId++;

        /*
        JsonToTask tsk = new JsonToTask(data);
        long task_status = tsk.computeStatus();

        final JsonToTask.Unit unit = tsk.makeReadableUnit(tsk.getUnit(), task_status);

        Intent playIntent = new Intent(context, MainActivity.class);
        //playIntent.putExtra(EXTRA_EVENT_ID, eventId);
        PendingIntent playPendingIntent =
                PendingIntent.getActivity(context, 0, playIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(tsk.getName())
                        .setContentText(unit.toString(task_status))
                        .addAction(R.drawable.ic_action_accept, "task is done", playPendingIntent)
                        .extend(new NotificationCompat.WearableExtender()
                                //.setContentAction(0)
                                .setContentIcon(R.drawable.ic_full_cancel));

        //.setContentIntent(viewPendingIntent);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
        */

    }
}
