package com.task.webchallengetask.ui.modules.activity.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.base.BaseActivity;
import com.task.webchallengetask.ui.modules.activity.presenters.StartActivityPresenter;

import java.util.List;


public class ActivityStartActivity extends BaseActivity<StartActivityPresenter>
        implements StartActivityPresenter.StartActivityView {

    private Toolbar mToolbar;
    private TextView tvTimer;
    private Spinner spChooseActivity;
    private TextView tvDistance;
    private ViewGroup vgDistanceContainer;
    private TextView tvSpeed;
    private ViewGroup vgSpeedContainer;
    private TextView tvSteps;
    private ViewGroup vgStepsContainer;
    private TextView tvCalories;
    private ViewGroup vgCaloriesContainer;
    private ImageView btnStartPause;
    private ImageView btnStop;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_start_activity;
    }

    @Override
    public StartActivityPresenter initPresenter() {
        return new StartActivityPresenter();
    }

    @Override
    public void findUI(View _rootView) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTimer = (TextView) findViewById(R.id.tvTimer_ASA);
        spChooseActivity = (Spinner) findViewById(R.id.spChooseActivity_ASA);
        tvDistance = (TextView) findViewById(R.id.tvDistance_ASA);
        vgDistanceContainer = (ViewGroup) findViewById(R.id.distanceContainer_ASA);
        tvSpeed = (TextView) findViewById(R.id.tvSpeed_ASA);
        vgSpeedContainer = (ViewGroup) findViewById(R.id.speedContainer_ASA);
        tvSteps = (TextView) findViewById(R.id.tvSteps_ASA);
        vgStepsContainer = (ViewGroup) findViewById(R.id.stepsContainer_ASA);
        tvCalories = (TextView) findViewById(R.id.tvCalories_ASA);
        vgCaloriesContainer = (ViewGroup) findViewById(R.id.caloriesContainer_ASA);
        btnStartPause = (ImageView) findViewById(R.id.btnStartPause_ASA);
        btnStop = (ImageView) findViewById(R.id.btnStop_ASA);
    }

    @Override
    public void setupUI() {
        getDelegate().setSupportActionBar(mToolbar);
        getDelegate().getSupportActionBar().setTitle(R.string.start_activity);
        getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spChooseActivity.setPrompt("Choose activity");
        spChooseActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().onSpinnerItemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RxUtils.click(btnStartPause, o -> getPresenter().onBtnStartPauseClicked());
        RxUtils.click(btnStop, o -> getPresenter().onBtnStopClicked());
    }

    @Override
    public void setSpinnerData(List<String> _data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, _data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChooseActivity.setAdapter(adapter);
    }

    @Override
    public void setSpinnerEnabled(boolean _isEnabled) {
        spChooseActivity.setEnabled(_isEnabled);
    }

    @Override
    public void setTimer(String _text) {
        tvTimer.setText(_text);
    }

    @Override
    public void clearAllField() {
        tvDistance.setText("0");
        tvSpeed.setText("0");
        tvSteps.setText("0");
        tvCalories.setText("0");
    }

    @Override
    public void toggleStartPause(int _icon) {
        btnStartPause.setImageResource(_icon);
    }

    @Override
    public void setDistance(float _text) {
        tvDistance.setText(String.format("%.1f", _text));
    }

    @Override
    public void setSpeed(float _text) {
        tvSpeed.setText(String.format("%.1f", _text));
    }

    @Override
    public void setSteps(float _text) {
        tvSteps.setText(String.format("%.0f", _text));
    }

    @Override
    public void setCalories(float _text) {
        tvCalories.setText(String.format("%.0f", _text));
    }

    @Override
    public void setStepsVisible(boolean _isVisible) {
        vgStepsContainer.setVisibility(_isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showCompleteProgramNotification(String _programName, String _target) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Congratulation!")
                .setContentText("Your have finished today program: " + _programName)
                .setSubText("Target:" + _target);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(_programName.hashCode(), notification);
    }

}
