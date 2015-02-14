package com.davy.adrien.wildoo;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private String[] data;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public CardView mCardView;
        public ViewHolder(CardView v)
        {
            super(v);
            mCardView = v;
        }
    }

    public CardsAdapter(String[] data)
    {
        this.data = data;
    }

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                                              .inflate(R.layout.task_card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ((TextView) holder.mCardView.findViewById(R.id.task_name))
                .setText(data[position]);
    }

    @Override
    public int getItemCount()
    {
        return data.length;
    }
}
