package com.task.webchallengetask.data.data_managers;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.plus.Plus;
import com.task.webchallengetask.App;

public final class GoogleApiUtils {

    private static GoogleApiUtils instance;
    private GoogleApiClient googleApiClient;

    public static GoogleApiUtils getInstance() {
        GoogleApiUtils localInstance = instance;
        if (localInstance == null) {
            synchronized (GoogleApiUtils.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new GoogleApiUtils();
                }
            }
        }
        return localInstance;
    }

    public GoogleApiClient buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(App.getAppContext())
                    .addApi(Fitness.SENSORS_API)
                    .addApi(Fitness.RECORDING_API)
                    .addApi(Fitness.HISTORY_API)
                    .addApi(Fitness.SESSIONS_API)
                    .addApi(Fitness.CONFIG_API)
                    .addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                    .addScope(Fitness.SCOPE_LOCATION_READ_WRITE)
                    .build();
        }

        return googleApiClient;
    }

    public GoogleApiClient buildGoogleApiClientWithGooglePlus() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(App.getAppContext())
                    .addApi(Fitness.SENSORS_API)
                    .addApi(Fitness.RECORDING_API)
                    .addApi(Plus.API, Plus.PlusOptions.builder().build())
                    .addApi(Fitness.HISTORY_API)
                    .addApi(Fitness.SESSIONS_API)
                    .addApi(Fitness.CONFIG_API)
                    .addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE)
                    .addScope(Fitness.SCOPE_LOCATION_READ_WRITE)
                    .addScope(new Scope(Scopes.PROFILE))
                    .addScope(new Scope(Scopes.PLUS_ME))
                    .addScope(new Scope(Scopes.PLUS_LOGIN))
                    .build();
        }

        return googleApiClient;
    }

    public boolean isNotEmptyClient() {
        return googleApiClient != null;
    }

    public void logoutGooglePlus() {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient);
        disableFit();
    }

    public void disableFit() {
        Fitness.ConfigApi.disableFit(googleApiClient);
        googleApiClient = null;
    }

}
