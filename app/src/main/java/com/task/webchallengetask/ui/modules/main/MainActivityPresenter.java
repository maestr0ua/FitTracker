package com.task.webchallengetask.ui.modules.main;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.task.webchallengetask.App;
import com.task.webchallengetask.data.data_managers.GoogleApiUtils;
import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.data.data_providers.PredictionDataProvider;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.utils.IntentHelper;
import com.task.webchallengetask.global.utils.Logger;
import com.task.webchallengetask.global.utils.NetworkUtils;
import com.task.webchallengetask.services.ActivityTrackerService;
import com.task.webchallengetask.ui.base.BaseActivityPresenter;
import com.task.webchallengetask.ui.base.BaseActivityView;
import com.task.webchallengetask.ui.modules.activity.views.ActivityListFragment;
import com.task.webchallengetask.ui.modules.activity.views.ActivityStartActivity;
import com.task.webchallengetask.ui.modules.analytics.AnalyticsFragment;
import com.task.webchallengetask.ui.modules.login.LoginActivity;
import com.task.webchallengetask.ui.modules.program.ProgramsListFragment;
import com.task.webchallengetask.ui.modules.settings.SettingsFragment;

public class MainActivityPresenter extends BaseActivityPresenter<MainActivityPresenter.MainView> implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private PendingIntent mSignInIntent;
    private GoogleApiClient googleApiClient;

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        if (IntentHelper.isServiceRunning(App.getAppContext(), ActivityTrackerService.class)) {
            getView().startActivity(ActivityStartActivity.class);
        }

        if (NetworkUtils.isConnectedNetwork(App.getAppContext()) && !GoogleApiUtils.getInstance().isNotEmptyClient()) {
            getView().showLoadingDialog();
            boolean isGooglePlus = TextUtils.equals(SharedPrefManager.getInstance().retrieveActiveSocial(),
                    Constants.SOCIAL_GOOGLE_PLUS);
            googleApiClient = setupGoogleApiClient(this, this, isGooglePlus);
        }

        getView().switchFragment(ActivityListFragment.newInstance(), false);
        getView().setHeaderTitle(SharedPrefManager.getInstance().retrieveUsername());
        getView().setAvatar(SharedPrefManager.getInstance().retrieveUrlPhoto());

        PredictionDataProvider.getInstance().connectAndTrain()
                .subscribe(aBoolean -> {
                }, Logger::e);
    }

    private void resolveSignInError() {
        if (mSignInIntent != null) {
            try {
                getView().hideLoadingDialog();
                getView().startSenderIntent(mSignInIntent.getIntentSender(),
                        Constants.RC_SIGN_IN_GOOGLE_PLUS);
            } catch (IntentSender.SendIntentException e) {
                googleApiClient.connect();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (getView().isDrawerOpen()) {
            getView().closeDrawer(null);
        } else
            super.onBackPressed();
    }

    public void onActivityListClicked() {
        getView().closeDrawer(() ->
                getView().switchFragment(ActivityListFragment.newInstance(), false));
    }

    public void onAnalyticsClicked() {
        getView().closeDrawer(() ->
                getView().switchFragment(AnalyticsFragment.newInstance(), false));
    }

    public void onProgramsClicked() {
        getView().closeDrawer(() ->
                getView().switchFragment(ProgramsListFragment.newInstance(), false));
    }

    public void onSettingsClicked() {
        getView().closeDrawer(() ->
                getView().switchFragment(SettingsFragment.newInstance(), false));
    }

    public void onLogoutClicked() {
        getView().showConfirmDialog("Confirm",
                "Do you really want to logout?",
                v -> {
                    revokeAccess();
                    getView().startActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    getView().finishActivity();
                });
    }

    private void revokeAccess() {
        switch (SharedPrefManager.getInstance().retrieveActiveSocial()) {
            case Constants.SOCIAL_FACEBOOK:
                LoginManager.getInstance().logOut();
                if (GoogleApiUtils.getInstance().isNotEmptyClient())
                    GoogleApiUtils.getInstance().disableFit();
                break;
            case Constants.SOCIAL_GOOGLE_PLUS:
                if (GoogleApiUtils.getInstance().isNotEmptyClient() &&
                        GoogleApiUtils.getInstance().buildGoogleApiClientWithGooglePlus().isConnected())
                    GoogleApiUtils.getInstance().logoutGooglePlus();
        }
        SharedPrefManager.getInstance().storeUsername("");
        SharedPrefManager.getInstance().storeActiveSocial("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RC_SIGN_IN_GOOGLE_PLUS:
                if (!googleApiClient.isConnecting()) {
                    googleApiClient.connect();
                }
                break;
            case Constants.RC_ACTIVITY_START_ACTIVITY:
                getView().switchFragment(ActivityListFragment.newInstance(), false);
                break;
        }
    }

    @Override
    public void onConnected(Bundle _bundle) {
        if (getView() != null)
            getView().hideLoadingDialog();
        Logger.d("Google API client onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult _connectionResult) {
        mSignInIntent = _connectionResult.getResolution();
        resolveSignInError();
    }

    public interface MainView extends BaseActivityView<MainActivityPresenter> {
        boolean isDrawerOpen();

        void closeDrawer(MainActivity.DrawerCallBack _callback);

        void setHeaderTitle(String _title);

        void startSenderIntent(IntentSender _intentSender, int _const) throws IntentSender.SendIntentException;

        void setAvatar(String _path);

    }

}
