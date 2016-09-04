package com.task.webchallengetask.ui.modules.program.presenters;

import android.graphics.Color;
import android.util.Pair;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.data_providers.PredictionDataProvider;
import com.task.webchallengetask.data.data_providers.ProgramDataProvider;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.Program;
import com.task.webchallengetask.global.programs.ProgramManager;
import com.task.webchallengetask.global.programs.difficults.Difficult;
import com.task.webchallengetask.global.programs.difficults.DifficultCustom;
import com.task.webchallengetask.global.utils.Logger;
import com.task.webchallengetask.global.utils.MathUtils;
import com.task.webchallengetask.global.utils.TimeUtil;
import com.task.webchallengetask.ui.base.BaseFragmentPresenter;
import com.task.webchallengetask.ui.base.BaseFragmentView;
import com.task.webchallengetask.ui.custom.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProgramDetailPresenter extends BaseFragmentPresenter<ProgramDetailPresenter.ProgramDetailView> {

    private ProgramDataProvider mProgramDataProvider = ProgramDataProvider.getInstance();
    private PredictionDataProvider mPredictionDataProvider = PredictionDataProvider.getInstance();
    private ProgramTable mProgramTable;
    private Constants.PROGRAM_TYPES mProgramType;
    private List<Pair<Long, Float>> mDataList = new ArrayList<>();

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        Date currentDay = new Date(TimeUtil.getCurrentDay());
        Date weekAgo = TimeUtil.minusDayFromDate(currentDay, 7);

        getView().setDifficultEnabled(false);
        getView().setEndDate(TimeUtil.dateToString(new Date(TimeUtil.getCurrentDay())));
        getView().setStartDate(TimeUtil.dateToString(new Date(weekAgo.getTime())));

        mProgramDataProvider.getProgram(getView().getFragmentArguments().getInt(Constants.PROGRAM_ID_KEY))
                .doOnNext(programTable -> {
                    mProgramTable = programTable;
                    mProgramType = ProgramManager.defineProgramType(mProgramTable);
                }).flatMap(programTable1 -> mProgramDataProvider.loadData(mProgramType, weekAgo, currentDay))
                .subscribe(pairs -> {
                    mDataList.clear();
                    mDataList.addAll(pairs);
                    fillProgram(mProgramTable);
                    fillDiagram(pairs);
                }, Logger::e);

    }

    public void onSaveClicked() {
        if (mProgramType == Constants.PROGRAM_TYPES.ACTIVE_LIFE) {
            int target = Integer.parseInt(getView().getTarget());
            mProgramTable.target = target * 60;
        } else {
            mProgramTable.target = Integer.parseInt(getView().getTarget());
        }

        mProgramTable.difficult = getView().getDifficult().getName();
        mProgramTable.update();
        getView().setSaveVisible(false);
        getView().setDifficultEnabled(false);
        getView().setTargetEnabled(false);
    }


    public void onStartDateClicked() {
        getView().openStartDateCalendar(_date -> {
            getView().setStartDate(TimeUtil.dateToString(_date));
            Date endDate = TimeUtil.stringToDate(getView().getEndDate());
            mProgramDataProvider.loadData(mProgramType, _date, endDate)
                    .subscribe(this::fillDiagram, Logger::e);

        });
    }

    public void onEndDateClicked() {
        getView().openEndDateCalendar(_date -> {
            getView().setEndDate(TimeUtil.dateToString(_date));
            Date startDate = TimeUtil.stringToDate(getView().getStartDate());
            mProgramDataProvider.loadData(mProgramType, startDate, _date)
                    .subscribe(this::fillDiagram, Logger::e);
        });
    }

    public void onTargetChanged() {
        compareResultWithTarget();
    }

    private void fillProgram(ProgramTable _programTable) {
        getView().setTitle(_programTable.getName());
        getView().setDifficultList(getDifficultList(_programTable.getName()));
        getView().setDifficult(getDifficultPosition(_programTable.getName(), _programTable.getDifficult()));

        if (mProgramType == Constants.PROGRAM_TYPES.ACTIVE_LIFE) {
            float targetInMinute = _programTable.getTarget() / 60;
            getView().setTarget(targetInMinute);
            setResults(mDataList.get(mDataList.size() - 1).second / 60);

        } else {
            getView().setTarget(_programTable.getTarget());
            setResults(mDataList.get(mDataList.size() - 1).second);
        }

        compareResultWithTarget();
        getView().setUnit(_programTable.getUnit());
        if (getDifficult(_programTable.getName(), _programTable.getDifficult()) instanceof DifficultCustom) {
            getView().setTargetEnabled(true);
        } else {
            getView().setTargetEnabled(false);
        }

    }

    private void fillDiagram(List<Pair<Long, Float>> _data) {
        switch (mProgramType) {
            case ACTIVE_LIFE:
                setDiagramData(_data, App.getAppContext().getString(R.string.c_time));
                break;
            case LONG_DISTANCE:
                setDiagramData(_data, App.getAppContext().getString(R.string.c_meters));
                break;
        }
    }

    private void setResults(float todayResult) {

        getView().setActualResults(todayResult);
    }

    private void compareResultWithTarget() {
        float target = 0;
        float actualResults = 0;
        if (!getView().getTarget().isEmpty())
            target = Float.valueOf(getView().getTarget());
        if (!getView().getActualResults().isEmpty())
            actualResults = Float.valueOf(getView().getActualResults());
        getView().setActualResultCompleted(actualResults >= target);
    }

    public void setDiagramData(List<android.util.Pair<Long, Float>> floats, String units) {
        ArrayList<BarEntry> entry = new ArrayList<>();
        ArrayList<Entry> entry2 = new ArrayList<>();
        LineData targetData = new LineData();
        BarData mDiagram = new BarData();
        String[] dates = new String[floats.size()];
        float target = MathUtils.round(Float.valueOf(getView().getTarget()), 1);
        for (int i = 0; i < floats.size(); i++) {
            entry2.add(new Entry(target, i));
            dates[i] = TimeUtil.timeToStringDDMM(floats.get(i).first);
            if (mProgramType == Constants.PROGRAM_TYPES.ACTIVE_LIFE) {
                entry.add(new BarEntry(MathUtils.round(floats.get(i).second / 60, 1), i));
            } else {
                entry.add(new BarEntry(MathUtils.round(floats.get(i).second, 1), i));
            }
        }
        CombinedData data = new CombinedData(dates);

        BarDataSet set = new BarDataSet(entry, "Result, " + units);
        set.setColor(Color.rgb(60, 220, 78));
        set.setValueTextColor(Color.rgb(60, 220, 78));
        set.setValueTextSize(10f);
        mDiagram.addDataSet(set);

        LineDataSet setLine = new LineDataSet(entry2, "Target, " + units);
        setLine.setColor(Color.rgb(240, 238, 70));
        setLine.setLineWidth(2.5f);
        setLine.setCircleColor(Color.rgb(240, 238, 70));
        setLine.setCircleRadius(5f);
        setLine.setFillColor(Color.rgb(240, 238, 70));
        setLine.setDrawCubic(true);
        setLine.setDrawValues(true);
        setLine.setValueTextSize(10f);
        setLine.setValueTextColor(Color.rgb(240, 238, 70));
        setLine.setAxisDependency(YAxis.AxisDependency.LEFT);
        targetData.addDataSet(setLine);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        getView().setDiagram(mDiagram, data, targetData);
    }

    private List<Difficult> getDifficultList(String _name) {
        List<Program> programs = ProgramManager.getPrograms();
        for (Program program : programs) {
            if (program.getName().equals(_name)) {
                return program.getDifficult();
            }
        }
        return new ArrayList<>();
    }

    private Difficult getDifficult(String _programName, String _difficultName) {
        for (Difficult diff : getDifficultList(_programName)) {
            if (diff.getName().equals(_difficultName)) {
                return diff;
            }
        }
        return null;
    }

    private int getDifficultPosition(String _programName, String _difficultName) {
        List<Difficult> list = getDifficultList(_programName);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(_difficultName)) {
                return i;
            }
        }
        return 0;
    }


    public void onAnalyze() {
        int completed = 0;
        int uncompleted = 0;

        for (Pair<Long, Float> value : mDataList) {
            float v = value.second;
            if (v == 0) continue;
            if (v >= Float.valueOf(getView().getTarget())) {
                completed++;
            } else
                uncompleted++;

        }

        getView().showLoadingDialog();
        addSubscription(mPredictionDataProvider.analyzeWeeklyTrendResults(completed, uncompleted)
                .timeout(40, TimeUnit.SECONDS)
                .subscribe(s -> {
                            String message = "";
                            if (s.equals("Increase")) {
                                message = "You could increase your difficult";
                            }
                            if (s.equals("Stay")) {
                                message = "You should stay with current difficult";
                            }
                            if (s.equals("Reduce")) {
                                message = "You should make a current difficult easier";
                            }
                            getView().setDifficultEnabled(true);
                            getView().showInfoDialog("Recommendation", message, null);
                            getView().setSaveVisible(true);
                            getView().hideLoadingDialog();
                        }, throwable -> {
                            Logger.e(throwable);
                            getView().showErrorDialog("Error", throwable.getMessage(), null);
                            getView().hideLoadingDialog();
                        }

                ));
    }

    public void onDifficultChanged(int _position) {
        List<Difficult> diffList = getDifficultList(mProgramTable.getName());
        if (diffList.get(_position) instanceof DifficultCustom) {
            getView().setTargetEnabled(true);
        } else {
            getView().setTarget(diffList.get(_position).getTarget());
        }
    }

    public interface ProgramDetailView extends BaseFragmentView<ProgramDetailPresenter> {
        void setSaveVisible(boolean _isVisible);

        void setTitle(String _text);

        void setDiagram(BarData _data, CombinedData data, LineData _targetData);

        void setDifficultList(List<Difficult> _data);

        void setDifficult(int _position);

        void setTarget(float _text);

        void setUnit(String _text);

        void setActualResults(float _text);

        String getActualResults();

        void setActualResultCompleted(boolean _isCompleted);

        void setTargetEnabled(boolean _isEnabled);

        void setDifficultEnabled(boolean _isEnabled);

        void openStartDateCalendar(CalendarView.Callback _callBack);

        void openEndDateCalendar(CalendarView.Callback _callBack);

        void setStartDate(String _text);

        void setEndDate(String _text);

        String getStartDate();

        String getEndDate();

        String getTarget();

        Difficult getDifficult();
    }
}
