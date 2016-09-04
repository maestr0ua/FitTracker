package com.task.webchallengetask.ui.modules.activity.presenters;

import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.data.data_providers.ActivityDataProvider;
import com.task.webchallengetask.data.data_providers.ProgramDataProvider;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.ProgramManager;
import com.task.webchallengetask.global.utils.Logger;
import com.task.webchallengetask.global.utils.TimeUtil;
import com.task.webchallengetask.ui.base.BaseFragmentPresenter;
import com.task.webchallengetask.ui.base.BaseFragmentView;
import com.task.webchallengetask.ui.custom.CalendarView;

import java.util.Date;
import java.util.List;


public class ActivityDetailPresenter extends BaseFragmentPresenter<ActivityDetailPresenter.ActivityDetailView> {

    private int id;
    private ProgramDataProvider mProgramDataProvider = ProgramDataProvider.getInstance();
    private ActivityDataProvider mActivityDataProvider = ActivityDataProvider.getInstance();
    private List<ProgramTable> mPrograms;

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        id = getView().getFragmentArguments().getInt(Constants.ACTIVITY_ID_KEY);

        if (mPrograms == null) {
            mProgramDataProvider.getPrograms()
                    .subscribe(programTables -> {
                        if (!programTables.isEmpty()) {
                            mPrograms = programTables;
                        }
                    });
        }

        mActivityDataProvider.getActivityById(id)
                .subscribe(_model -> {
                    getView().setAllFieldsEditable(false);
                    getView().setDate(TimeUtil.dateToString(new Date(_model.getDate())));
                    float actualTime = _model.getActivityActualTime();
                    getView().setTitle(_model.getName());
                    getView().setActivityTime(actualTime / 60); // from seconds
                    getView().setActivityTimeUint("min");
                    getView().setDistance(_model.getDistance());
                    getView().setStep(_model.getStep());
                    getView().setCalories(_model.getCalories());
                    getView().setSpeed(_model.getSpeed());
                });
    }

    public void onEditClicked() {
        getView().setSaveVisible(true);
        getView().setEditVisible(false);
        getView().setAllFieldsEditable(true);
    }

    public void onSaveClicked() {
        getView().setSaveVisible(false);
        getView().setEditVisible(true);
        getView().setAllFieldsEditable(false);
        mActivityDataProvider.getActivityById(id)
                .subscribe(model -> {
                    model.date = TimeUtil.stringToDate(getView().getDate()).getTime();
                    model.distance = Float.parseFloat(getView().getDistance());
                    float activityTime = Float.parseFloat(getView().getActivityTime());
                    model.activityActualTime = activityTime * 60; // to seconds
                    model.step = Integer.parseInt(getView().getStep());
                    model.calories = Float.parseFloat(getView().getCalories());
                    model.speed = Float.parseFloat(getView().getSpeed());
                    model.save();
                    checkNewData();
                }, Logger::e);
    }

    public void onDeleteClicked() {
        ActivityDataProvider.getInstance()
                .deleteActivities(id)
                .subscribe(t -> getView().onBackPressed());
    }

    private void checkNewData() {
        if (SharedPrefManager.getInstance().retrieveNotificationState()) {
            if (mPrograms != null) {
                for (ProgramTable programTable : mPrograms) {
                    String previousResult = "";
                    Date previousResultDate = null;
                    if (SharedPrefManager.getInstance().contains(programTable.getName())) {
                        previousResult = SharedPrefManager.getInstance().retrieveString(programTable.getName());
                        previousResultDate = TimeUtil.stringToDate(previousResult);
                    }
                    Date today = new Date(TimeUtil.getCurrentDay());
                    Date nextDay = TimeUtil.addEndOfDay(today);

                    if (previousResultDate == null || TimeUtil.compareDay(previousResultDate.getTime(), today.getTime()) != 0) {
                        Constants.PROGRAM_TYPES type = ProgramManager.defineProgramType(programTable);

                        mProgramDataProvider.loadData(type, today, nextDay)
                                .subscribe(pairs -> {
                                    if (pairs.get(0).second >= programTable.getTarget()) {
                                        float target = 0;
                                        if (type == Constants.PROGRAM_TYPES.ACTIVE_LIFE) {
                                            target = programTable.getTarget() / 60;
                                        } else {
                                            target = programTable.getTarget();
                                        }

                                        String targetString = target + " " + programTable.getUnit();
                                        getView().showCompleteProgramNotification(programTable.getName(), targetString);
                                        String date = TimeUtil.dateToString(new Date(TimeUtil.getCurrentDay()));
                                        SharedPrefManager.getInstance().saveString(programTable.getName(), date);
                                    }
                                }, Logger::e);
                    }
                }
            }
        }
    }


    public void onTimeClicked() {
        getView().openStartDateCalendar(_date -> getView().setDate(TimeUtil.dateToString(_date)));
    }

    public interface ActivityDetailView extends BaseFragmentView<ActivityDetailPresenter> {
        void setEditVisible(boolean _isVisible);

        void setSaveVisible(boolean _isVisible);

        void openStartDateCalendar(CalendarView.Callback _callBack);

        void setTitle(String _text);

        void setDate(String _text);

        void setAllFieldsEditable(boolean _isEditable);

        void setActivityTime(float _text);

        void setActivityTimeUint(String _text);

        void setDistance(float _text);

        void setStep(float _text);

        void setSpeed(float _text);

        void setCalories(float _text);

        String getActivityTime();

        String getDistance();

        String getStep();

        String getCalories();

        String getDate();

        String getSpeed();

        void showCompleteProgramNotification(String _programName, String _difficult);
    }
}
