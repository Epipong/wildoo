package com.davy.adrien.wildoo;

import com.orm.SugarRecord;

import java.util.Date;

public class TaskEntity extends SugarRecord<TaskEntity> {

    String name;
    long timestamp_create;
    long done;
    long step;
    long objective_number;
    String unit;
    int objective;

    public TaskEntity() {
    }

    public TaskEntity(String name,
                      long timestamp_create,
                      long done,
                      long step,
                      long objective_number,
                      String unit) {
        this.name = name;
        this.timestamp_create = timestamp_create;
        this.done = done;
        this.step = step;
        this.objective_number = objective_number;
        this.unit = unit;
    }

    public class Unit {

        // the divider we have to apply to seconds (ex: 1 minutes = 60 secs / 60)
        public final String name;
        public Unit(String name) { this.name = name; }

        public String toString(long n)
        {
            final String hour = "heures";
            final String minute = "minutes";
            final String seconds = "secondes";

            if (name == hour)
                return (n / 3600) + " " + hour + " " + Math.abs(((n % 3600) / 60)) + " " + minute;
            else if (name == minute)
                return (n / 60) + " " + minute + " " + (n % 60) + " " + seconds;
            return Long.toString(n) + " " + name;
        }
    }

    public Unit makeReadableUnit(final String unitName, long amount)
    {
        // TODO: use R.string.etc
        if (unitName.equals("seconds")) {
            if (Math.abs(amount) >= 3600)
                return new Unit("heures");
            else if (Math.abs(amount) >= 60)
                return new Unit("minutes");
        }

        return new Unit(unitName);
    }

    // returns, the amount that the user have to do or has in advance
    public long computeStatus()
    {
        long creationTime = timestamp_create;
        long currentTime = System.currentTimeMillis();
        long shouldBeDone = ((currentTime - creationTime) / step) * objective_number;

        return done - shouldBeDone;
    }

    public String getDescription()
    {
        long objective = objective_number;

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
}
