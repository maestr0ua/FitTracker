package com.task.webchallengetask.ui.modules.login;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.task.webchallengetask.App;
import com.task.webchallengetask.BuildConfig;
import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.exceptions.PicassoException;
import com.task.webchallengetask.ui.base.BaseActivityPresenter;
import com.task.webchallengetask.ui.base.BaseActivityView;
import com.task.webchallengetask.ui.modules.main.MainActivity;

import rx.Observable;

public class LoginPresenter extends BaseActivityPresenter<LoginPresenter.LoginView> implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        FacebookCallback<LoginResult> {

    private CallbackManager mCallbackManager;
    private GoogleApiClient googleApiClient;
    private boolean isIntentInProgress;
    private boolean isSignInClicked;
    private PendingIntent mSignInIntent;

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        if (!SharedPrefManager.getInstance().isNotificationStateExist()) {
            SharedPrefManager.getInstance().storeNotificationState(true);
        }
        if (SharedPrefManager.getInstance().retrieveTimeSynchronization() == 0){
            SharedPrefManager.getInstance().storeTimeSynchronization(Constants.TIME_SYNC);
        }

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        getView().setLoginPermission("publish_actions");
        mCallbackManager = CallbackManager.Factory.create();
        getView().setCallbackManager(mCallbackManager);
    }

    public void onGoogleSignInClicked() {
        if (googleApiClient == null)
            googleApiClient = setupGoogleApiClient(this, this, true);
        isSignInClicked = true;
        resolveSignInError();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.RC_SIGN_IN_GOOGLE_PLUS:
                if (resultCode != Activity.RESULT_OK) {
                    isSignInClicked = false;
                }

                isIntentInProgress = false;

                if (!googleApiClient.isConnecting()) {
                    googleApiClient.connect();
                }
                break;
            case Constants.RC_SIGN_IN_FACEBOOK:
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        AppEventsLogger.activateApp(App.getAppContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppEventsLogger.deactivateApp(App.getAppContext());
    }

    @Override
    public void onConnected(Bundle _bundle) {
        isSignInClicked = false;
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleApiClient);

        if (currentPerson != null) {
            storeData(currentPerson);
        }
    }

    private void resolveSignInError() {
        if (mSignInIntent != null) {
            try {
                getView().startSenderIntent(mSignInIntent.getIntentSender(),
                        Constants.RC_SIGN_IN_GOOGLE_PLUS);
            } catch (IntentSender.SendIntentException e) {
                isIntentInProgress = false;
                googleApiClient.connect();
            }
        }
    }

    private void storeData(Person _person) {
        SharedPrefManager.getInstance().storeUsername(_person.getDisplayName());
        String urlPhoto = _person.getImage().getUrl();
        urlPhoto = urlPhoto.substring(0, urlPhoto.length() - 2)
                + Constants.PROFILE_PIC_SIZE;

        SharedPrefManager.getInstance().storeUrlPhoto(urlPhoto);
        SharedPrefManager.getInstance().storeActiveSocial(Constants.SOCIAL_GOOGLE_PLUS);

        addSubscription(loadPhoto(Uri.parse(urlPhoto))
                .subscribe(t -> startMainActivity(),
                        _throwable -> getView().showErrorDialog("Failure", "Something went wrong", null)));

    }

    private void startMainActivity() {
        getView().startActivity(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getView().finishActivity();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult _connectionResult) {
        if (!isIntentInProgress) {
            mSignInIntent = _connectionResult.getResolution();
            if (isSignInClicked) {
                resolveSignInError();
            }
        }
    }

    @Override
    public void onSuccess(LoginResult result) {
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                this.stopTracking();
                getView().showLoadingDialog();
                storeData(currentProfile);

                addSubscription(loadPhoto(currentProfile.getProfilePictureUri(Constants.PROFILE_PIC_SIZE, Constants.PROFILE_PIC_SIZE))
                        .subscribe(t -> startMainActivity(),
                                _throwable -> getView().showErrorDialog("Failure", "Something went wrong", null)));
            }
        };
        profileTracker.startTracking();
    }

    private void storeData(Profile currentProfile) {
        SharedPrefManager.getInstance().storeUsername(currentProfile.getName());
        SharedPrefManager.getInstance().storeUrlPhoto(currentProfile
                .getProfilePictureUri(Constants.PROFILE_PIC_SIZE, Constants.PROFILE_PIC_SIZE).toString());
        SharedPrefManager.getInstance().storeActiveSocial(Constants.SOCIAL_FACEBOOK);
    }

    private Observable<Boolean> loadPhoto(Uri _url) {
        return Observable.create(_subscriber ->
                Picasso.with(App.getAppContext())
                        .load(_url)
                        .fetch(new Callback() {
                            @Override
                            public void onSuccess() {
                                _subscriber.onNext(true);
                            }

                            @Override
                            public void onError() {
                                _subscriber.onError(new PicassoException());
                            }
                        }));
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException error) {
        getView().showErrorDialog("Failure", "Something went wrong", null);
    }

    public interface LoginView extends BaseActivityView<LoginPresenter> {
        void startSenderIntent(IntentSender _intentSender, int _const) throws IntentSender.SendIntentException;

        void setLoginPermission(String _loginPermission);

        void setCallbackManager(CallbackManager _callbackManager);
    }

}

