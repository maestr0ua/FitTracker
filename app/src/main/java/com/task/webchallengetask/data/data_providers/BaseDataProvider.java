package com.task.webchallengetask.data.data_providers;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseDataProvider {

    protected <T> Observable<T> newThread(Observable<T> response) {
        return response.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
