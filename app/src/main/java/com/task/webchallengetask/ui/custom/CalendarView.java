package com.task.webchallengetask.ui.custom;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.roomorama.caldroid.CalendarHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hirondelle.date4j.DateTime;


public final class CalendarView extends CaldroidListener {

    private Callback mCallback;
    private CaldroidFragment mCaldroidFragment = new CaldroidFragment();
    private FragmentManager mFragmentManager;
    private String tag;

    public CalendarView(FragmentManager _fragmentManager, String _tag) {
        mFragmentManager = _fragmentManager;
        tag = _tag;
    }

    public void show(Date _date) {
        if (_date != null) {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            cal.setTime(_date);

            DateTime dateTime = new DateTime(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0);
            ArrayList<DateTime> selectedDate = new ArrayList<>();
            selectedDate.add(dateTime);

            args.putStringArrayList(CaldroidFragment.SELECTED_DATES, CalendarHelper.convertToStringList(selectedDate));
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            mCaldroidFragment.setArguments(args);
        }
        mCaldroidFragment.setCaldroidListener(this);
        mCaldroidFragment.show(mFragmentManager, tag);
    }

    public void setCallback(Callback _callback) {
        mCallback = _callback;
    }

    @Override
    public void onSelectDate(Date _date, View _view) {
        mCaldroidFragment.clearSelectedDates();
        mCaldroidFragment.setSelectedDate(_date);
        mCaldroidFragment.refreshView();
        if (mCallback != null) {
            mCallback.getDate(_date);
            mCaldroidFragment.dismiss();
        }

    }

    @Override
    public void onLongClickDate(Date _date, View _view) {
    }

    @Override
    public void onChangeMonth(int _month, int _year) {
    }

    @Override
    public void onCaldroidViewCreated() {
    }

    public interface Callback {
        void getDate(Date _date);
    }

}
