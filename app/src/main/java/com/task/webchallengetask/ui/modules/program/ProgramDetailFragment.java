package com.task.webchallengetask.ui.modules.program;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.task.webchallengetask.R;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.difficults.Difficult;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.global.utils.TimeUtil;
import com.task.webchallengetask.ui.base.BaseFragment;
import com.task.webchallengetask.ui.custom.CalendarView;
import com.task.webchallengetask.ui.modules.program.adapters.DifficultAdapter;
import com.task.webchallengetask.ui.modules.program.presenters.ProgramDetailPresenter;

import java.util.List;


public class ProgramDetailFragment extends BaseFragment<ProgramDetailPresenter> implements
        ProgramDetailPresenter.ProgramDetailView {

    private TextView tvTitle;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private LinearLayout llStartDate;
    private LinearLayout llEndDate;
    private EditText etTarget;
    private TextView tvUnitTarget;
    private TextView tvUnitActual;
    private Spinner spDifficult;
    private TextView tvActualResult;
    private Button btnAnalyze;
    private CombinedChart mChart;
    private MenuItem menuSave;


    public static ProgramDetailFragment newInstance(int _programId) {

        Bundle args = new Bundle();
        args.putInt(Constants.PROGRAM_ID_KEY, _programId);
        ProgramDetailFragment fragment = new ProgramDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitle() {
        return R.string.program_detail;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_program_detail;
    }

    @Override
    public ProgramDetailPresenter initPresenter() {
        return new ProgramDetailPresenter();
    }

    @Override
    public void findUI(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle_FP);
        tvStartDate  = (TextView) rootView.findViewById(R.id.tvStartDate_FP);
        tvEndDate  = (TextView) rootView.findViewById(R.id.tvEndDate_FP);
        llStartDate  = (LinearLayout) rootView.findViewById(R.id.llStartDate_FP);
        llEndDate  = (LinearLayout) rootView.findViewById(R.id.llEndDate_FP);
        etTarget  = (EditText) rootView.findViewById(R.id.etTarget_FP);
        spDifficult = (Spinner) rootView.findViewById(R.id.spDifficult_FP);
        tvActualResult = (TextView) rootView.findViewById(R.id.tvActualResult_FP);
        btnAnalyze = (Button) rootView.findViewById(R.id.btnAnalyze_FP);
        tvUnitTarget = (TextView) rootView.findViewById(R.id.tvUnitTarget_FP);
        tvUnitActual = (TextView) rootView.findViewById(R.id.tvUnitActual_FP);
        mChart = (CombinedChart) rootView.findViewById(R.id.chart_FP);
    }



    @Override
    public void setupUI() {
        spDifficult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().onDifficultChanged(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        etTarget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etTarget.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getPresenter().onTargetChanged();
            }
        });

        RxUtils.click(btnAnalyze, o -> getPresenter().onAnalyze());
        RxUtils.click(llStartDate, o -> getPresenter().onStartDateClicked());
        RxUtils.click(llEndDate, o -> getPresenter().onEndDateClicked());
    }

    @Override
    public void setSaveVisible(boolean _isVisible) {
        menuSave.setVisible(_isVisible);
    }

    @Override
    public void setTitle(String _text) {
        tvTitle.setText(_text);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_program_detail, menu);
        menuSave = menu.findItem(R.id.menu_save);
        menuSave.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            getPresenter().onSaveClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setDiagram(BarData _value, CombinedData _date, LineData _targetData) {
        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[] {
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.BUBBLE,
                CombinedChart.DrawOrder.CANDLE,
                CombinedChart.DrawOrder.LINE,
                CombinedChart.DrawOrder.SCATTER
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        _date.setData(_value);
        _date.setData(_targetData);
        mChart.setData(_date);
        mChart.invalidate();
    }

    @Override
    public void setDifficultList(List<Difficult> _data) {

        DifficultAdapter adapter = new DifficultAdapter(
                this.getContext(),
                android.R.layout.simple_spinner_item,
                _data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDifficult.setAdapter(adapter);

    }

    @Override
    public void setDifficult(int _position) {
        spDifficult.setSelection(_position);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setTarget(float _text) {
        etTarget.setText(String.format("%.0f", _text));
    }

    @Override
    public void setUnit(String _text) {
        tvUnitTarget.setText(_text);
        tvUnitActual.setText(_text);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void setActualResults(float _text) {
        tvActualResult.setText(String.format("%.0f", _text));
    }

    @Override
    public String getActualResults() {
        return tvActualResult.getText().toString();
    }

    @Override
    public void setActualResultCompleted(boolean _isCompleted) {
        int resColor = _isCompleted ? R.color.green_700 : R.color.red_700;
        tvActualResult.setTextColor(ContextCompat.getColor(getContext(), resColor));
        tvUnitActual.setTextColor(ContextCompat.getColor(getContext(), resColor));
    }

    @Override
    public void setTargetEnabled(boolean _isEnabled) {
        etTarget.setEnabled(_isEnabled);
    }

    @Override
    public void setDifficultEnabled(boolean _isEnabled) {
        spDifficult.setEnabled(_isEnabled);
    }

    @Override
    public void openStartDateCalendar(CalendarView.Callback _callBack) {
        CalendarView calendarView = new CalendarView(getFragmentManager(), "");
        calendarView.setCallback(_callBack);
        calendarView.show(TimeUtil.stringToDate(tvStartDate.getText().toString()));
    }

    @Override
    public void openEndDateCalendar(CalendarView.Callback _callBack) {
        CalendarView calendarView = new CalendarView(getFragmentManager(), "");
        calendarView.setCallback(_callBack);
        calendarView.show(TimeUtil.stringToDate(tvEndDate.getText().toString()));
    }

    @Override
    public void setStartDate(String _text) {
        tvStartDate.setText(_text);
    }

    @Override
    public void setEndDate(String _text) {
        tvEndDate.setText(_text);
    }

    @Override
    public String getStartDate() {
        return tvStartDate.getText().toString();
    }

    @Override
    public String getEndDate() {
        return tvEndDate.getText().toString();
    }

    @Override
    public String getTarget() {
        return etTarget.getText().toString();
    }

    @Override
    public Difficult getDifficult() {
        return ((DifficultAdapter) spDifficult.getAdapter())
                .getDifficult(spDifficult.getSelectedItemPosition());
    }

}
