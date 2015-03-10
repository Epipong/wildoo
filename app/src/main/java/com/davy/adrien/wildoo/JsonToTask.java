package com.davy.adrien.wildoo;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.lang.Math;

public class JsonToTask {

    public class Unit {

        // the divider we have to apply to seconds (ex: 1 minutes = 60 secs / 60)
        public final String name;
        public Unit(String name) { this.name = name; }

        public String toString(long n)
        {
            final String hour = mContext.getString(R.string.hour);
            final String minute = mContext.getString(R.string.minute);
            final String seconds = mContext.getString(R.string.second);

            if (name == hour)
                return (n / 3600) + " " + hour + " " + Math.abs(((n % 3600) / 60)) + " " + minute;
            else if (name == minute)
                return (n / 60) + " " + minute + " " + (n % 60) + " " + seconds;
            return Long.toString(n) + " " + name;
        }
    }

    private JSONObject mTsk;
    private final Context mContext;

    JsonToTask(Context context, JSONObject tsk)
    {
        mTsk = tsk;
        mContext = context;
    }

    public String getName() throws JSONException
    {
        return mTsk.getString("name");
    }

    public long getCreation() throws  JSONException
    {
        return Long.parseLong(mTsk.getString("timestamp_create"));
    }

    public int getDone() throws JSONException
    {
        return mTsk.getInt("done");
    }

    public int getStep() throws JSONException
    {
        return mTsk.getInt("step");
    }

    public int getObjectiveNumber() throws JSONException
    {
        return mTsk.getInt("objective_number");
    }

    public Unit makeReadableUnit(final String unitName, long amount)
    {
        if (unitName.equals("seconds")) {
            if (Math.abs(amount) >= 3600)
                return new Unit(mContext.getString(R.string.hour));
            else if (Math.abs(amount) >= 60)
                return new Unit(mContext.getString(R.string.minute));
        }

        return new Unit(unitName);
    }

    // returns, the amount that the user have to do or has in advance
    public long computeStatus() throws JSONException
    {
        Date date = new Date();

        long creationTime = getCreation();
        long currentTime = date.getTime() / 1000; // timestamp is in milliseconds
        long done = getDone();
        long shouldBeDone = ((currentTime - creationTime) / getStep()) * getObjectiveNumber();

        return done - shouldBeDone;
    }

    public String getDescription() throws JSONException
    {
        long objective = getObjectiveNumber();
        String unit = getUnit();
        // long step = getStep();

        if (unit == "seconds")
            if (objective > 60 * 60)
            {
                objective /= 60 * 60;
                unit = "heures";
            }
            else if (objective > 60)
            {
                objective /= 60;
                unit = "minutes";
            }

        // TODO: change step unit, and it could be day or week
        return objective + " " + unit + " each day";

    }

    public String getUnit()
            throws JSONException
    {
        return mTsk.getString("unit");
    }
}
