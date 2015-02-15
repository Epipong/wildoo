package com.davy.adrien.wildoo;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private JSONObject data;

    private JSONArray getDataArray() throws JSONException
    {
        return data.getJSONArray("tasks");
    }

    private JSONObject getTask(int position) throws  JSONException
    {
        JSONArray a = getDataArray();

        return (JSONObject) a.get(position);
    }

    private String getTaskName(int position) throws JSONException
    {
        JSONObject task = getTask(position);

        return task.getString("name");
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        public ViewHolder(View v)
        {
            super(v);
            mView = v;
        }

        abstract void setUpView(int position);
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
        public void setUpView(int position)
        {
            TextView t = (TextView) mView.findViewById(R.id.task_name);

            try {
                t.setText(getTaskName(position));
            } catch (JSONException e) {
                t.setText("JSON error");
            }
        }
    }

    public CardsAdapter(JSONObject data)
    {
        this.data = data;
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
        holder.setUpView(position);
    }


    @Override
    public int getItemCount()
    {
        try {
            return getDataArray().length();
        } catch (JSONException e)
        {
            return 0;
        }
    }
}
