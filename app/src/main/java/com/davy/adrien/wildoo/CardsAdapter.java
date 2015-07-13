package com.davy.adrien.wildoo;


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
            button.setImageResource(R.drawable.ic_action_play);
            button = (ImageButton) mView.findViewById(R.id.button_edit);
            button.setImageResource(R.drawable.ic_action_edit);
            button = (ImageButton) mView.findViewById(R.id.button_done);
            button.setImageResource(R.drawable.ic_action_accept);
            button = (ImageButton) mView.findViewById(R.id.button_pone);
            button.setImageResource(R.drawable.ic_action_new);
            button = (ImageButton) mView.findViewById(R.id.button_delete);
            button.setImageResource(R.drawable.ic_delete);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasks.get(position).delete();
                    tasks.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public CardsAdapter(List<TaskEntity> tasks)
    {
        this.tasks = tasks;
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
        return tasks.size();
    }
}
