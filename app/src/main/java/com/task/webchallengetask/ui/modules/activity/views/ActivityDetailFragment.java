package com.task.webchallengetask.ui.modules.activity.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.utils.TimeUtil;
import com.task.webchallengetask.ui.custom.CalendarView;
import com.task.webchallengetask.ui.modules.activity.presenters.ActivityDetailPresenter;
import com.task.webchallengetask.ui.base.BaseFragment;


public class ActivityDetailFragment extends BaseFragment<ActivityDetailPresenter>
        implements ActivityDetailPresenter.ActivityDetailView {

    private MenuItem menuEdit;
    private MenuItem menuSave;
    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvUnit;
    private EditText etActivityTime;
    private EditText etStep;
    private EditText etDistance;
    private EditText etCalories;
    private EditText etSpeed;


    public static ActivityDetailFragment newInstance(int _id) {

        Bundle args = new Bundle();
        args.putInt(Constants.ACTIVITY_ID_KEY, _id);
        ActivityDetailFragment fragment = new ActivityDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitle() {
        return R.string.activity_detail;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_activity_details;
    }

    @Override
    public ActivityDetailPresenter initPresenter() {
        return new ActivityDetailPresenter();
    }

    @Override
    public void findUI(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle_FAD);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate_FAD);
        etActivityTime = (EditText) rootView.findViewById(R.id.etActivityTime_FAD);
        etDistance = (EditText) rootView.findViewById(R.id.etDistance_FAD);
        etSpeed = (EditText) rootView.findViewById(R.id.etSpeed_FAD);
        etStep = (EditText) rootView.findViewById(R.id.etStep_FAD);
        etCalories = (EditText) rootView.findViewById(R.id.etCalories_FAD);
        tvUnit = (TextView) rootView.findViewById(R.id.etActivityTimeUnit_FAD);
    }

    @Override
    public void setupUI() {
        tvDate.setOnClickListener(v -> getPresenter().onTimeClicked());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_activity_detail, menu);
        menuEdit = menu.findItem(R.id.menu_edit);
        menuSave = menu.findItem(R.id.menu_save);
        menuSave.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                getPresenter().onEditClicked();
                return true;
            case R.id.menu_save:
                getPresenter().onSaveClicked();
                return true;
            case R.id.menu_delete:
                getPresenter().onDeleteClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setEditVisible(boolean _isVisible) {
        menuEdit.setVisible(_isVisible);
    }

    @Override
    public void setSaveVisible(boolean _isVisible) {
        menuSave.setVisible(_isVisible);
    }

    @Override
    public void openStartDateCalendar(CalendarView.Callback _callBack) {
        CalendarView calendarView = new CalendarView(getFragmentManager(), "");
        calendarView.setCallback(_callBack);
        calendarView.show(TimeUtil.stringToDate(tvDate.getText().toString()));
    }

    @Override
    public void setTitle(String _text) {
        tvTitle.setText(_text);
    }

    @Override
    public void setDate(String _text) {
        tvDate.setText(_text);
    }

    @Override
    public void setAllFieldsEditable(boolean _isEditable) {
        etActivityTime.setEnabled(_isEditable);
        etDistance.setEnabled(_isEditable);
        etStep.setEnabled(_isEditable);
        etCalories.setEnabled(_isEditable);
        etSpeed.setEnabled(_isEditable);
        tvDate.setFocusable(_isEditable);
        tvDate.setEnabled(_isEditable);
    }

    @Override
    public void setActivityTime(float _text) {
        etActivityTime.setText(String.format("%.1f", _text));
    }

    @Override
    public void setActivityTimeUint(String _text) {
        tvUnit.setText(_text);
    }

    @Override
    public void setDistance(float _text) {
        etDistance.setText(String.format("%.1f", _text));
    }

    @Override
    public void setStep(float _text) {
        etStep.setText(String.format("%.0f", _text));
    }

    @Override
    public void setSpeed(float _text) {
        etSpeed.setText(String.format("%.1f", _text));
    }

    @Override
    public void setCalories(float _text) {
        etCalories.setText(String.format("%.0f", _text));
    }

    @Override
    public String getActivityTime() {
        return etActivityTime.getText().toString();
    }

    @Override
    public String getDistance() {
        return etDistance.getText().toString();
    }

    @Override
    public String getStep() {
        return etStep.getText().toString();
    }

    @Override
    public String getCalories() {
        return etCalories.getText().toString();
    }

    @Override
    public String getDate() {
        return tvDate.getText().toString();
    }

    @Override
    public String getSpeed() {
        return etSpeed.getText().toString();
    }

    @Override
    public void showCompleteProgramNotification(String _programName, String _target) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Congratulation!")
                .setContentText("Your have finished today program: " + _programName)
                .setSubText("Target:" + _target);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(_programName.hashCode(), builder.build());

    }
}
