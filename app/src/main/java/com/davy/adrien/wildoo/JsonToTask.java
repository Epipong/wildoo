package com.davy.adrien.wildoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class JsonToTask {

    private JSONObject mTsk;

    JsonToTask(JSONObject tsk)
    {
        mTsk = tsk;
    }

    // TODO: now time unit is dynamic, removes this function
    public long unitToSec(final String unit)
    {
        switch (unit)
        {
            case "seconds":
                return 1;
            case "minute":
                return 60;
            case "hour":
                return 60 * 60;
            case "day":
                return 60 * 60 * 24;
        }
        return 1;
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

    public String getUnit() throws JSONException
    {
        return mTsk.getString("unit");
    }
}
