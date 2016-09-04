package com.task.webchallengetask.global;

public final class Constants {

    public static final String APPLICATION_NAME = "HelloPrediction";
    public static final String STORAGE_DATA_LOCATION = "webchallengebuckettrain/distance.txt";
    public static final String MODEL_ID = "distanceidentifier";
    public static final String PROJECT_ID = "WebChallengeProject";
    public static final String SERVICE_ACCT_EMAIL = "1061853864883-compute@developer.gserviceaccount.com";
    public static final String SERVICE_ACCT_KEYFILE = "key.p12";

    public static final int TIME_SYNC = 5;

    public static final int RC_SIGN_IN_GOOGLE_PLUS = 0;
    public static final int RC_SIGN_IN_FACEBOOK = 64206;

    public static final int RC_ACTIVITY_START_ACTIVITY = 3789;

    public static final String SOCIAL_GOOGLE_PLUS = "google plus";
    public static final String SOCIAL_FACEBOOK = "facebook";

    public static final int PROFILE_PIC_SIZE = 100; // dp

    public static final String MAIN_ACTION = "action.main";

    public static final String START_TIMER_ACTION = "START_TIMER_ACTION";
    public static final String PAUSE_TIMER_ACTION = "PAUSE_TIMER_ACTION";
    public static final String STOP_TIMER_ACTION = "STOP_TIMER_ACTION";

    public static final String SEND_TIMER_UPDATE_ACTION = "SEND_TIMER_UPDATE_ACTION";
    public static final String SEND_TIMER_UPDATE_KEY = "SEND_TIMER_UPDATE_KEY";


    public static final String SEND_DISTANCE_ACTION = "SEND_DISTANCE_ACTION";
    public static final String SEND_SPEED_ACTION = "SEND_SPEED_ACTION";
    public static final String SEND_STEP_ACTION = "SEND_STEP_ACTION";
    public static final String SEND_CALORIES_ACTION = "SEND_CALORIES_ACTION";

    public static final String SEND_DISTANCE_KEY = "SEND_DISTANCE_KEY";
    public static final String SEND_SPEED_KEY = "SEND_SPEED_KEY";
    public static final String SEND_STEP_KEY = "SEND_STEP_KEY";
    public static final String SEND_CALORIES_KEY = "SEND_CALORIES_KEY";


    public static final String ACTIVITY_NAME_KEY = "ACTIVITY_NAME_KEY";
    public static final String ACTIVITY_ID_KEY = "ACTIVITY_ID_KEY";

    public static final int FOREGROUND_NOTIFICATION_SERVICE_ID = 101;

    public static final String PROGRAM_ID_KEY = "PROGRAM_ID_KEY";

    public enum PROGRAM_TYPES {
        LONG_DISTANCE,
        ACTIVE_LIFE
    }

    public enum DATA_TYPES {
        ACTIVITY_TIME,
        STEP,
        DISTANCE,
        CALORIES,
//        All
    }

    public static final String KIND_ACTIVITY_RUNNING = "Running";
    public static final String KIND_ACTIVITY_WALKING = "Walking";
    public static final String KIND_ACTIVITY_ICE_SKATING = "Ice Skating";
    public static final String KIND_ACTIVITY_TENNIS = "Tennis";
    public static final String KIND_ACTIVITY_SNOWBOARDING = "Snowboarding";
    public static final String KIND_ACTIVITY_ROWING = "Rowing";
    public static final String KIND_ACTIVITY_BICYCLING = "Bicycling";

}
