package com.davy.adrien.wildoo;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.Date;

public class TaskEntity extends SugarRecord<TaskEntity> {

    String name;
    long timestamp_create;
    long done;
    long step;
    long objective_number;
    String unit;

    public boolean playing = false;
    private final Thread mPlayThread = new Thread(new Runnable() {
        @Override
        public void run() {

            long carry = 0;

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (playing) {
                    done++;
                    // TODO: flush to the filesystem every ten seconds
                    if (carry++ % 20 == 0)
                        save();
                }
            }
        }
    });

    public TaskEntity() {
    }

    public String getName() {
        return name;
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

    static public class Unit {

        // the divider we have to apply to seconds (ex: 1 minutes = 60 secs / 60)
        public final String name;
        public Unit(String name) { this.name = name; }

        // FIXME: are these units really used ?
        public String toString(long n)
        {
            final String hour = "heures";
            final String minute = "minutes";
            final String seconds = "secondes";

            if (name.equals(hour))
                return (n / 3600) + " " + hour + " " + Math.abs(((n % 3600) / 60)) + " " + minute;
            else if (name.equals(minute))
                return (n / 60) + " " + minute + " " + (n % 60) + " " + seconds;
            return Long.toString(n) + " " + name;
        }

        static public boolean isTimeUnit(String unit) {
            return unit.equals("min") || unit.equals("hour");
        }

        static public int toSeconds(int how_many, String what) {
            if (what.equals("min"))
                return how_many * 60;
            else if (what.equals("hour"))
                return how_many * 3600;
            return how_many;
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

    public void play() {
        playing = true;
        if (!mPlayThread.isAlive())
            mPlayThread.start();
    }

    public void pause() {
        playing = false;
    }

    public void done() {
        done = getShouldBeDone();
        save();
    }

    private long getShouldBeDone() {
        long creationTime = timestamp_create;
        long currentTime = System.currentTimeMillis() / 1000;
        float azefj = (currentTime - creationTime) / (float)step;

        return (long)(azefj * objective_number);
    }

    // returns, the amount that the user have to do or has in advance
    public long computeStatus()
    {
        long shouldBeDone = getShouldBeDone();

        return done - shouldBeDone;
    }

    public String getDescription()
    {
        long objective = objective_number;
        String onscreen_unit = unit;

        double day_multiplier = 3600f * 24f / step;
        objective *= day_multiplier;

        if (unit.equals("seconds"))
            if (objective > 60 * 60)
            {
                objective /= 60 * 60;
                onscreen_unit = "heures";
            }
            else if (objective > 60)
            {
                objective /= 60;
                onscreen_unit = "minutes";
            }

        // TODO: change step unit, and it could be day or week. Also, add "about 10 minutes each day"
        return objective + " " + onscreen_unit + " each day";

    }


    public String getReadableStatus() {

        long task_status = computeStatus();

        final TaskEntity.Unit unitT = makeReadableUnit(unit, task_status);
        return unitT.toString(task_status);
    }

}
