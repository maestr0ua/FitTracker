package com.task.webchallengetask.global.utils;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

public final class RxUtils {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static void click(View _view, Action1 action) {
        RxView.clicks(_view)
                .throttleFirst(800, TimeUnit.MILLISECONDS)
                .subscribe(action, Logger::e);
    }


    public static Observable<Void> click(View view) {
        return RxView.clicks(view)
                .throttleFirst(800, TimeUnit.MILLISECONDS);
    }

    public static Observable<Date> createDateList(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<>();
        Date currentDate = startDate;
        do {
            dateList.add(currentDate);
            currentDate = TimeUtil.addDayToDate(currentDate, 1);
        } while (currentDate.before(endDate) || currentDate.equals(endDate));
        return Observable.from(dateList);
    }


}
