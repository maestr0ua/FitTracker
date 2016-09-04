package com.task.webchallengetask.data.database.tables;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.task.webchallengetask.data.database.FitDatabase;



@Table(database = FitDatabase.class)
public class ProgramTable extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String name;

    @Column
    public String difficult;

    @Column
    public float target;

    @Column
    public String unit;

    public String getName() {
        return name;
    }

    public String getDifficult() {
        return difficult;
    }

    public float getTarget() {
        return target;
    }

    public int getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }
}
