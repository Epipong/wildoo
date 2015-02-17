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

    public long unitToSec(final String unit)
    {
        switch (unit)
        {
            case "second":
                return 1;
            case "minute":
                return 60;
            case "hour":
                return 60 * 60;
            case "day":
                return 60 * 60 * 24;
        }
        return -1;
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
        long currentTime = date.getTime();
        long done = getDone() * unitToSec(getUnit());
        long shouldBeDone = ((currentTime - creationTime) / getStep()) * getObjectiveNumber();

        return (done - shouldBeDone) / getObjectiveNumber();
    }

    public String getUnit() throws JSONException
    {
        return mTsk.getString("unit");
    }
}
