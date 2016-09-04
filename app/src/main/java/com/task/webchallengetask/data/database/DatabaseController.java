package com.task.webchallengetask.data.database;


import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.task.webchallengetask.data.database.tables.ActionParametersTable;
import com.task.webchallengetask.data.database.tables.ActionParametersTable_Table;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.data.database.tables.ProgramTable_Table;

import java.util.List;

import rx.Observable;


public class DatabaseController {

    private static volatile DatabaseController instance;

    public static DatabaseController getInstance() {
        DatabaseController localInstance = instance;
        if (localInstance == null) {
            synchronized (DatabaseController.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DatabaseController();
                }
            }
        }
        return localInstance;
    }

    public List<ActionParametersTable> getActionParametersModel(long startDate, long endDate){
        ConditionGroup conditionGroup = ConditionGroup.clause();
        conditionGroup.and(ActionParametersTable_Table.date.greaterThanOrEq(startDate));
        conditionGroup.and(ActionParametersTable_Table.date.lessThan(endDate));

        return new Select().from(ActionParametersTable.class)
                .where(conditionGroup)
                .orderBy(ActionParametersTable_Table.startTime, true)
                .queryList();
    }

    public List<ActionParametersTable> getAllActionParametersModel(){

        return new Select().from(ActionParametersTable.class)
                .orderBy(ActionParametersTable_Table.date, true)
                .queryList();
    }

    public ActionParametersTable getActionParametersModel(int id){
        return new Select().from(ActionParametersTable.class)
                .where(ActionParametersTable_Table.id.eq(id))
                .querySingle();
    }

    public boolean deleteActionParametersModel(int _id){
        new Delete().from(ActionParametersTable.class)
                .where(ActionParametersTable_Table.id.eq(_id))
                .query();

        return true;
    }

    public Observable<List<ProgramTable>> getPrograms() {
        return Observable.from(new Select().from(ProgramTable.class).queryList()).toList();
    }

    public Observable<ProgramTable> getProgram(int id) {
        return Observable.just(new Select().from(ProgramTable.class)
                .where(ProgramTable_Table.id.eq(id))
                .querySingle());
    }

}
