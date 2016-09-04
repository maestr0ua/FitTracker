package com.task.webchallengetask.ui.base;

import android.content.Intent;
import android.support.annotation.CallSuper;

import com.task.webchallengetask.global.utils.RxUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseFragmentPresenter<V extends BaseFragmentView> implements BasePresenter<V> {

    protected V mView;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void bindView(V _view) {
        mView = _view;
    }

    @Override
    public void unbindView() {
        mView = null;
    }

    @Override
    public V getView() {
        return mView;
    }

    @Override
    @CallSuper
    public void onViewCreated() {
    }

    @Override
    @CallSuper
    public void onResume() {
    }

    @Override
    @CallSuper
    public void onPause() {
    }

    @Override
    @CallSuper
    public void onStart() {

    }

    @Override
    @CallSuper
    public void onStop() {

    }

    @Override
    @CallSuper
    public void onDestroyView() {
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    protected void addSubscription(Subscription _subscription) {
        mSubscriptions.remove(_subscription);
        mSubscriptions.add(_subscription);
    }

    @Override
    public void onBackPressed() {
        if (!mView.isBackStackEmpty()) mView.popBackStack();
        else mView.finishActivity();
    }

}
