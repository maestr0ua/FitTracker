package com.task.webchallengetask.data.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.task.webchallengetask.data.database.FitDatabase;



@Table(database = FitDatabase.class)
public class ActionParametersTable extends BaseModel {

    @PrimaryKey(autoincrement = true)
    @Column
    public int id;

    @Column
    public String name;

    @Column
    public long startTime;

    @Column
    public long endTime;

    @Column
    public long date;

    public long getDate() {
        return date;
    }

    @Column
    public float activityActualTime;

    @Column
    public int step;

    @Column
    public float distance;

    @Column
    public float speed;

    @Column
    public float calories;

    @Column
    public boolean synchronize;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public float getActivityActualTime() {
        return activityActualTime;
    }

    public int getStep() {
        return step;
    }

    public float getDistance() {
        return distance;
    }

    public float getSpeed() {
        return speed;
    }

    public float getCalories() {
        return calories;
    }

}
