package com.task.webchallengetask.global.utils;

import android.util.Log;

public final class Logger {

    private static final String TAG = "Logger";

    public static void e (Throwable _throwable) {
        Log.e(TAG, _throwable.getClass().getName() + ", cause - " + _throwable.getCause() + ", message - " + _throwable.getMessage());
    }

    public static void d (String _message) {
        Log.d(TAG, _message);
    }

}
