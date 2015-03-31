package com.davy.adrien.wildoo;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("this is the content of the textview");
            }
        });


        String string = "{ 'tasks' : [ "
                + "{ 'name' : 'Guitare', 'timestamp_create' : '1424086908', 'done': 10, 'step' : 86400, 'objective_number': 600, 'unit' : 'seconds'},"
                + "{ 'name' : 'Cupcakes', 'timestamp_create' : '1424086908', 'done': 2, 'step' : 86400, 'objective_number': 400, 'unit' : 'cup'},"
                + "{ 'name' : 'Email', 'timestamp_create' : '1424086808', 'done': 3000000, 'step' : 26400, 'objective_number': 90, 'unit' : 'seconds'},"
                + "{ 'name' : 'Email', 'timestamp_create' : '1424086808', 'done': 3000000, 'step' : 26400, 'objective_number': 90, 'unit' : 'seconds'}]"
                + "}";

        try {
            JSONArray jsonTasks = (new JSONObject(string)).getJSONArray("tasks");

            for (int i = 3; i >= 0; i--)
                new TaskNotification((JSONObject) jsonTasks.get(i), this);
            finish();
        } catch (Exception e) {
            // TODO
        }

    }

}
