package com.task.webchallengetask.data.data_providers;

import android.util.Pair;

import com.task.webchallengetask.data.database.DatabaseController;
import com.task.webchallengetask.data.database.tables.ActionParametersTable;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.global.utils.TimeUtil;

import java.util.Date;
import java.util.List;

import rx.Observable;

public class ActivityDataProvider extends BaseDataProvider {

    private static volatile ActivityDataProvider instance;
    private DatabaseController mDbController = DatabaseController.getInstance();


    private ActivityDataProvider() {
    }

    public static ActivityDataProvider getInstance() {
        ActivityDataProvider localInstance = instance;
        if (localInstance == null) {
            synchronized (ActivityDataProvider.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ActivityDataProvider();
                }
            }
        }
        return localInstance;
    }

    public Observable<List<ActionParametersTable>> getActivities() {
        return newThread(Observable.just(mDbController.getAllActionParametersModel()));
    }

    public Observable<ActionParametersTable> getActivityById(int id) {
        return newThread(Observable.just(mDbController.getActionParametersModel(id)));
    }

    public Observable<Boolean> deleteActivities(int id) {
        return newThread(Observable.just(mDbController.deleteActionParametersModel(id)));
    }

    public Observable<List<Pair<Long, Float>>> getDistance(Date startDate, Date endDate) {
        return newThread(RxUtils.createDateList(startDate, endDate)
                .flatMap(this::getDayDistance)
                .toList());
    }

    public Observable<List<Pair<Long, Float>>> getSteps(Date startDate, Date endDate) {
        return newThread(RxUtils.createDateList(startDate, endDate)
                .flatMap(this::getDaySteps)
                .toList());
    }

    public Observable<List<Pair<Long, Float>>> getActualTime(Date startDate, Date endDate) {
        return newThread(RxUtils.createDateList(startDate, endDate)
                .flatMap(this::getDayActivityTime)
                .toList());

    }

    public Observable<List<Pair<Long, Float>>> getCalories(Date startDate, Date endDate) {
        return newThread(RxUtils.createDateList(startDate, endDate)
                .flatMap(this::getDayCalories)
                .toList());

    }

    private Observable<Pair<Long, Float>> getDaySteps(Date date) {
        Date nextDay = TimeUtil.addEndOfDay(date);
        return Observable
                .just(mDbController.getActionParametersModel(date.getTime(), nextDay.getTime()))
                .map(models -> {
                    float totalSteps = 0;
                    for (ActionParametersTable model : models) {
                        totalSteps += model.getStep();
                    }
                    return new Pair<>(date.getTime(), totalSteps);
                });


    }

    private Observable<Pair<Long, Float>> getDayActivityTime(Date date) {
        Date nextDay = TimeUtil.addEndOfDay(date);
        return Observable
                .just(mDbController.getActionParametersModel(date.getTime(), nextDay.getTime()))
                .map(models -> {
                    float totalTime = 0;
                    for (ActionParametersTable model : models) {
                        totalTime += model.getActivityActualTime();
                    }
                    return new Pair<>(date.getTime(), totalTime);
                });


    }

    private Observable<Pair<Long, Float>> getDayCalories(Date date) {
        Date nextDay = TimeUtil.addEndOfDay(date);
        return Observable
                .just(mDbController.getActionParametersModel(date.getTime(), nextDay.getTime()))
                .map(models -> {
                    float totalCalories = 0;
                    for (ActionParametersTable model : models) {
                        totalCalories += model.getCalories();
                    }
                    return new Pair<>(date.getTime(), totalCalories);
                });


    }

    private Observable<Pair<Long, Float>> getDayDistance(Date date) {
        Date nextDay = TimeUtil.addEndOfDay(date);
        return Observable
                .just(mDbController.getActionParametersModel(date.getTime(), nextDay.getTime()))
                .map(models -> {
                    float totalDistance = 0;
                    for (ActionParametersTable model : models) {
                        totalDistance += model.getDistance();
                    }
                    return new Pair<>(date.getTime(), totalDistance);
                });

    }

}
