package com.task.webchallengetask.ui.base;

import android.content.Intent;
import android.support.annotation.CallSuper;

import com.google.android.gms.common.api.GoogleApiClient;
import com.task.webchallengetask.data.data_managers.GoogleApiUtils;
import com.task.webchallengetask.global.utils.RxUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivityPresenter<V extends BaseActivityView>
        implements BasePresenter<V> {

    private V mView;
    private CompositeSubscription mSubscriptions;

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
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        RxUtils.unsubscribeIfNotNull(mSubscriptions);
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
    public void onBackPressed() {
        if (!mView.isBackStackEmpty()) mView.popBackStack();
        else mView.finishActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    protected final void addSubscription(Subscription _subscription) {
        mSubscriptions.remove(_subscription);
        mSubscriptions.add(_subscription);
    }

    protected GoogleApiClient setupGoogleApiClient(GoogleApiClient.ConnectionCallbacks _connectionCallbacks,
                                                   GoogleApiClient.OnConnectionFailedListener _onConnectionFailedListener,
                                                   boolean isGooglePlus) {
        GoogleApiClient googleApiClient = isGooglePlus ? GoogleApiUtils.getInstance().buildGoogleApiClientWithGooglePlus()
                : GoogleApiUtils.getInstance().buildGoogleApiClient();
        googleApiClient.registerConnectionFailedListener(_onConnectionFailedListener);
        googleApiClient.registerConnectionCallbacks(_connectionCallbacks);
        googleApiClient.connect();

        return googleApiClient;
    }
}
