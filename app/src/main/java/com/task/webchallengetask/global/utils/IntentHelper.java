package com.task.webchallengetask.global.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.task.webchallengetask.App;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.services.ActivityTrackerService;

import java.util.Calendar;

public final class IntentHelper {

    public static Intent getActivityTrackerServiceIntent(String action, String activityName) {
        Intent intent = new Intent(App.getAppContext(), ActivityTrackerService.class);
        intent.setAction(action);
        intent.putExtra(Constants.ACTIVITY_NAME_KEY, activityName);
        return intent;
    }

    public static Intent sendTimerUpdateIntent(Calendar time) {
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_TIMER_UPDATE_ACTION);
        intent.putExtra(Constants.SEND_TIMER_UPDATE_KEY, time == null ? -1 : time.getTimeInMillis());
        return intent;
    }

    public static Intent sendCaloriesIntent(float count) {
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_CALORIES_ACTION);
        intent.putExtra(Constants.SEND_CALORIES_KEY, count);
        return intent;
    }

    public static Intent sendDistanceIntent(float count) {
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_DISTANCE_ACTION);
        intent.putExtra(Constants.SEND_DISTANCE_KEY, count);
        return intent;
    }

    public static Intent sendStepIntent(int count) {
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_STEP_ACTION);
        intent.putExtra(Constants.SEND_STEP_KEY, count);
        return intent;
    }

    public static Intent sendSpeedIntent(float count) {
        Intent intent = new Intent();
        intent.setAction(Constants.SEND_SPEED_ACTION);
        intent.putExtra(Constants.SEND_SPEED_KEY, count);
        return intent;
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
