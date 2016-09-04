package com.task.webchallengetask.ui.dialogs.presenters;

import android.content.Intent;

import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.base.BaseDialogView;
import com.task.webchallengetask.ui.base.BasePresenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseDialogPresenter<V extends BaseDialogView> implements BasePresenter<V> {
    private V mView;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void bindView(V _view) {
        mView = _view;
    }

    @Override
    public void unbindView() {

    }

    @Override
    public V getView() {
        return mView;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroyView() {
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
