package com.davy.adrien.wildoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import com.melnykov.fab.FloatingActionButton;


public class MainActivity extends Activity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;

    private void spawn_new_task_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View inflated = getLayoutInflater().inflate(R.layout.add_dialog_layout, null);

        builder.setTitle("New task")
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    EditText et = (EditText) inflated.findViewById(R.id.new_task_name);
                    TaskEntity new_task =
                            new TaskEntity(et.getText().toString(),
                                    System.currentTimeMillis(), 43, 342, 8324, "minutes");
                    new_task.save();

                    mAdapter = new CardsAdapter(TaskEntity.listAll(TaskEntity.class));
                    mRecyclerView.swapAdapter(mAdapter, false);

                    dialog.dismiss();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        AlertDialog new_task = builder.create();

        Spinner how_many = (Spinner) inflated.findViewById(R.id.new_how_many);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.how_many,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        how_many.setAdapter(adapter);

        Spinner what = (Spinner) inflated.findViewById(R.id.new_what);
        ArrayAdapter<CharSequence> adapter_what = ArrayAdapter.createFromResource(this, R.array.what,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        what.setAdapter(adapter_what);

        Spinner each = (Spinner) inflated.findViewById(R.id.new_each);
        ArrayAdapter<CharSequence> adapter_each = ArrayAdapter.createFromResource(this, R.array.each,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        each.setAdapter(adapter_each);


        new_task.setView(inflated);
        new_task.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager mLayoutManager;

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // add the fab to the recyclerView
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spawn_new_task_dialog();
            }
        });

        List<TaskEntity> tasks = TaskEntity.listAll(TaskEntity.class);
        mAdapter = new CardsAdapter(tasks);
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
