package com.task.webchallengetask.data.data_managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.CheckResult;

import com.task.webchallengetask.App;
import com.task.webchallengetask.global.SharedPrefConst;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public final class SharedPrefManager {

    private static SharedPrefManager instance;
    private SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPrefManager getInstance() {
        if (instance == null) {
            instance = new SharedPrefManager(App.getAppContext());
        }
        return instance;
    }

    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void saveInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String retrieveString(String s) {
        return sharedPreferences.getString(s, "");
    }

    private boolean retrieveBoolean(String s) {
        return sharedPreferences.getBoolean(s, false);
    }

    private int retrieveInt(String s) {
        return sharedPreferences.getInt(s, 0);
    }

    public void storeUsername(String _username) {
        saveString(SharedPrefConst.SHARED_PREF_USERNAME, _username);
    }

    public void storeWeight(int weight) {
        saveInt(SharedPrefConst.SHARED_PREF_WEIGHT, weight);
    }

    public void storeHeight(int height) {
        saveInt(SharedPrefConst.SHARED_PREF_HEIGHT, height);
    }

    public void storeGender(String gender) {
        saveString(SharedPrefConst.SHARED_PREF_GENDER, gender);
    }

    public int retrieveWeight() {
        return retrieveInt(SharedPrefConst.SHARED_PREF_WEIGHT);
    }

    public int retrieveHeight() {
        return retrieveInt(SharedPrefConst.SHARED_PREF_HEIGHT);
    }

    public String retrieveGender() {
        return retrieveString(SharedPrefConst.SHARED_PREF_GENDER);
    }

    public String retrieveUsername() {
        return retrieveString(SharedPrefConst.SHARED_PREF_USERNAME);
    }

    public void storeUrlPhoto(String url) {
        saveString(SharedPrefConst.SHARED_PREF_URL_PHOTO, url);
    }

    public String retrieveUrlPhoto() {
        return retrieveString(SharedPrefConst.SHARED_PREF_URL_PHOTO);
    }

    public void storeActiveSocial(String s) {
        saveString(SharedPrefConst.SHARED_PREF_ACTIVE_SOCIAL, s);
    }

    public int retrieveAge() {
        return retrieveInt(SharedPrefConst.SHARED_PREF_AGE);
    }

    public void storeAge(int age) {
        saveInt(SharedPrefConst.SHARED_PREF_AGE, age);
    }


    public int retrieveTimeSynchronization() {
        return retrieveInt(SharedPrefConst.SHARED_PREF_TIME_SYNCHRONIZATION);
    }

    public void storeTimeSynchronization(int time) {
        saveInt(SharedPrefConst.SHARED_PREF_TIME_SYNCHRONIZATION, time);
    }

    public String retrieveActiveSocial() {
        return retrieveString(SharedPrefConst.SHARED_PREF_ACTIVE_SOCIAL);
    }

    public void storeNotificationState(boolean state) {
        saveBoolean(SharedPrefConst.SHARED_PREF_NOTIFICATION_STATE, state);
    }

    public boolean isNotificationStateExist() {
        return sharedPreferences.contains(SharedPrefConst.SHARED_PREF_NOTIFICATION_STATE);
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean retrieveNotificationState() {
        return retrieveBoolean(SharedPrefConst.SHARED_PREF_NOTIFICATION_STATE);
    }

}
