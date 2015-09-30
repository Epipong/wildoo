package com.davy.adrien.wildoo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private List<TaskEntity> tasks;
    private Activity mActivity;

    public abstract class ViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public ViewHolder(View v)
        {
            super(v);
            mView = v;
        }

        abstract void setUpView(int position) throws JSONException;
    }

    public class HolderSmiley extends ViewHolder
    {
        public HolderSmiley(View v)
        {
            super(v);
        }

        @Override
        public void setUpView(int position)
        {
            return;
        }
    }

    public class CardHolder extends ViewHolder
    {
        public CardHolder(View v)
        {
            super(v);
        }

        @Override
        public void setUpView(final int position)
        {
            if (position >= tasks.size())
                return;

            TaskEntity task = tasks.get(position);

            TextView tsk_name = (TextView) mView.findViewById(R.id.task_name);
            TextView tsk_status = (TextView) mView.findViewById(R.id.task_status);
            TextView tsk_desc = (TextView) mView.findViewById(R.id.task_description);

            tsk_name.setText(task.name);

            long task_status = task.computeStatus();

            final TaskEntity.Unit unit = task.makeReadableUnit(task.unit, task_status);
            tsk_status.setText(unit.toString(task_status));

            if (task_status < 0)
                tsk_status.setTextColor(0xFFB23432);
            else
                tsk_status.setTextColor(0xFF004C1E);

            tsk_desc.setText(task.getDescription());

            // set the buttons icons
            ImageButton button;

            button = (ImageButton) mView.findViewById(R.id.button_play);
            button.setImageResource(R.drawable.ic_play);
            button = (ImageButton) mView.findViewById(R.id.button_edit);
            button.setImageResource(R.drawable.ic_pencil);
            button = (ImageButton) mView.findViewById(R.id.button_done);
            button.setImageResource(R.drawable.ic_checkmark);
            button = (ImageButton) mView.findViewById(R.id.button_pone);
            button.setImageResource(R.drawable.ic_plus);
            button = (ImageButton) mView.findViewById(R.id.button_delete);
            button.setImageResource(R.drawable.ic_delete);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setTitle("Delete task '" + tasks.get(position).name + "'")
                           .setMessage("Are you sure ?")
                           .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   tasks.get(position).delete();
                                   tasks.remove(position);
                                   notifyItemRemoved(position);
                                   notifyItemRangeChanged(position, tasks.size());
                                   dialog.dismiss();
                               }
                           })
                           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           });
                    builder.show();
                }
            });
        }
    }

    public CardsAdapter(List<TaskEntity> tasks, Activity activity)
    {
        this.tasks = tasks;
        this.mActivity = activity;
    }

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view;
        switch (viewType) {
            case 0:
                view = new SmileyView(parent.getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                // draw the smiley face !


                return new HolderSmiley(view);
            default:
            view = LayoutInflater.from(parent.getContext())
                                 .inflate(R.layout.task_card_view, parent, false);
            return new CardHolder(view);
        }
    }

    // the first item should be the smiley (TODO)
    @Override
    public int getItemViewType(int position)
    {
        return 1;
        // return position == 0 ? 0 : 1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        try {
            holder.setUpView(position);
        } catch (JSONException e) {
            Log.e("CardAdapter", "A JSON error occured" + e.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        // sets the visibility of the 'empty task' text message when there is no task
        int visibility;

        if (tasks.size() == 0)
            visibility = View.VISIBLE;
        else
            visibility = View.GONE;

        mActivity.findViewById(R.id.text_empty).setVisibility(visibility);

        return tasks.size();
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }
}
