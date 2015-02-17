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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;


public class MainActivity extends Activity {

    private JSONObject readTaskJSON() throws IOException, JSONException
    {
        String filename = "tasks.json";
        String string = "{ \"tasks\" : [ { \"name\" : \"Piano\", \"timestamp_create\" : \"1424086908000\", \"done\": 32, \"step\" : 86400, \"objective_number\": 20, \"unit\" : \"minute\"}] }";
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

    // [nom de la tâche] [combien d'unité] [n fois] [par [jour | semaine | mois]] en [minutes | heures | jours | pages | unité custom]

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;
        RecyclerView.Adapter mAdapter;


        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        JSONObject data;
        try { data = readTaskJSON(); }
        catch (JSONException | IOException e) { data = new JSONObject(); }

        mAdapter = new CardsAdapter(data);
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
