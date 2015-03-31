package com.davy.adrien.wildoo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.melnykov.fab.FloatingActionButton;


public class MainActivity extends Activity {

    /*
    ** name : task name
    ** timestamp_create : creation timestamp
    ** done : how many of the unity has been done
    ** step : duration in seconds between each time the user must do the objective number of units
    ** objective_number : the number that the user must do (in unit unit)
    ** unit : the unit for measuring the task (ex: minutes, apples)
    */
    private JSONObject readTaskJSON() throws IOException, JSONException
    {
        String filename = "tasks.json";
        String string = "{ 'tasks' : [ "
        + "{ 'name' : 'Guitare', 'timestamp_create' : '1424086908', 'done': 10, 'step' : 86400, 'objective_number': 600, 'unit' : 'seconds'},"
        + "{ 'name' : 'Cupcakes', 'timestamp_create' : '1424086908', 'done': 2, 'step' : 86400, 'objective_number': 400, 'unit' : 'cup'},"
        + "{ 'name' : 'Email', 'timestamp_create' : '1424086808', 'done': 3000000, 'step' : 26400, 'objective_number': 90, 'unit' : 'seconds'},"
        + "{ 'name' : 'Email', 'timestamp_create' : '1424086808', 'done': 3000000, 'step' : 26400, 'objective_number': 90, 'unit' : 'seconds'}]"
        + "}";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileInputStream inputStream = openFileInput(filename);
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer, 0, inputStream.available());

        String resultingJSON = new String(buffer, "UTF-8");

        return new JSONObject(resultingJSON);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter mAdapter;


        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // add the fab to the recyclerView
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

        JSONObject data;
        try { data = readTaskJSON(); }
        catch (JSONException | IOException e) { data = new JSONObject(); }

        mAdapter = new CardsAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
