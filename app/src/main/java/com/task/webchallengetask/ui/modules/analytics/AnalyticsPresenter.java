package com.task.webchallengetask.ui.modules.analytics;

import android.graphics.Color;
import android.support.v4.util.Pair;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.data_providers.ActivityDataProvider;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.utils.MathUtils;
import com.task.webchallengetask.global.utils.TimeUtil;
import com.task.webchallengetask.ui.custom.CalendarView;
import com.task.webchallengetask.ui.base.BaseFragmentPresenter;
import com.task.webchallengetask.ui.base.BaseFragmentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalyticsPresenter extends BaseFragmentPresenter<AnalyticsPresenter.AnalyticsView> {

    private ActivityDataProvider mActivitiesProvider = ActivityDataProvider.getInstance();
    public static final Pair<Constants.DATA_TYPES, String> activityDataType = new Pair<>(Constants.DATA_TYPES.ACTIVITY_TIME, "Activity time");
    public static final Pair<Constants.DATA_TYPES, String> stepDataType = new Pair<>(Constants.DATA_TYPES.STEP, "Steps");
    public static final Pair<Constants.DATA_TYPES, String> distanceDataType = new Pair<>(Constants.DATA_TYPES.DISTANCE, "Distance");
    public static final Pair<Constants.DATA_TYPES, String> caloriesDataType = new Pair<>(Constants.DATA_TYPES.CALORIES, "Calories");

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        List<Pair<Constants.DATA_TYPES, String>> dataTypesList = new ArrayList<>();
        dataTypesList.add(activityDataType);
        dataTypesList.add(stepDataType);
        dataTypesList.add(distanceDataType);
        dataTypesList.add(caloriesDataType);
        getView().setDataTypes(dataTypesList);

        Date currentDay = new Date(TimeUtil.getCurrentDay());
        Date weekAgo = TimeUtil.minusDayFromDate(currentDay, 7);

        getView().setEndDate(TimeUtil.dateToString(new Date(TimeUtil.getCurrentDay())));
        getView().setStartDate(TimeUtil.dateToString(weekAgo));
   }

    public void getDiagram(Pair<Constants.DATA_TYPES, String> _dataType){
        Date start = TimeUtil.stringToDate(getView().getStartDate());
        Date end = TimeUtil.stringToDate(getView().getEndDate());
        switch (_dataType.first){
            case ACTIVITY_TIME:
                mActivitiesProvider.getActualTime(start,end).subscribe((floats) -> {
                    setDiagramData(floats, App.getAppContext().getString(R.string.c_time), _dataType.first);
                });
                break;
            case STEP:
                mActivitiesProvider.getSteps(start,end).subscribe((floats) -> {
                    setDiagramData(floats, App.getAppContext().getString(R.string.c_step), _dataType.first);
                });
                break;
            case DISTANCE:
                mActivitiesProvider.getDistance(start,end).subscribe((floats) -> {
                    setDiagramData(floats, App.getAppContext().getString(R.string.c_meters), _dataType.first);
                });
                break;
            case CALORIES:
                mActivitiesProvider.getCalories(start,end).subscribe((floats) -> {
                    setDiagramData(floats, App.getAppContext().getString(R.string.c_calories), _dataType.first);
                });
                break;
        }
    }

    public void setDiagramData(List<android.util.Pair<Long, Float>> floats, String _units, Constants.DATA_TYPES first){
        ArrayList<BarEntry> _entry = new ArrayList<>();
        BarData mDiagram = new BarData();
        String[] dates = new String[floats.size()];
        for (int i = 0; i < floats.size(); i++){
          dates[i] = TimeUtil.timeToStringDDMM(floats.get(i).first);
            if (first == Constants.DATA_TYPES.ACTIVITY_TIME) {
                _entry.add(new BarEntry(MathUtils.round(floats.get(i).second / 60, 1), i));
            } else {
                _entry.add(new BarEntry(MathUtils.round(floats.get(i).second, 1), i));
            }
        }
        CombinedData data = new CombinedData(dates);
        BarDataSet set = new BarDataSet(_entry, _units);
        set.setColor(Color.rgb(60, 220, 78));
        set.setValueTextColor(Color.rgb(60, 220, 78));
        set.setValueTextSize(10f);
        mDiagram.addDataSet(set);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        getView().setDiagram(mDiagram, data);
    }

    public void onStartDateClicked() {
        getView().openStartDateCalendar(_date -> {
            getView().setStartDate(TimeUtil.dateToString(_date));
            getDiagram(getView().getDataTypes());
        });
    }

    public void onEndDateClicked() {
        getView().openEndDateCalendar(_date -> {
            getView().setEndDate(TimeUtil.dateToString(_date));
            getDiagram(getView().getDataTypes());
        });
    }


    public void onDataTypeChosen(Pair<Constants.DATA_TYPES, String> _dataType) {
        getDiagram(_dataType);
    }

    public interface AnalyticsView extends BaseFragmentView<AnalyticsPresenter> {

        void setDiagram(BarData _data, CombinedData data);
        void openStartDateCalendar(CalendarView.Callback _callBack);
        void openEndDateCalendar(CalendarView.Callback _callBack);
        void setStartDate(String _text);
        void setEndDate(String _text);
        void setDataTypes(List<Pair<Constants.DATA_TYPES, String>> _data);
        String getStartDate();
        String getEndDate();
        Pair<Constants.DATA_TYPES, String> getDataTypes();
    }

}

