package com.task.webchallengetask.ui.modules.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.task.webchallengetask.R;
import com.task.webchallengetask.ui.base.BaseFragment;

import java.util.List;

public class SettingsFragment extends BaseFragment<SettingsPresenter> implements SettingsPresenter.SettingsView {

    private EditText etHeight;
    private EditText etWeight;
    private EditText etAge;
    private EditText etTimeSynchronization;
    private Spinner spGender;
    private CheckBox cbNotification;


    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getTitle() {
        return R.string.settings;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_settings;
    }

    @Override
    public SettingsPresenter initPresenter() {
        return new SettingsPresenter();
    }

    @Override
    public void findUI(View rootView) {
        etAge = (EditText) rootView.findViewById(R.id.etAge_FS);
        etHeight = (EditText) rootView.findViewById(R.id.etHeight_FS);
        etWeight = (EditText) rootView.findViewById(R.id.etWeight_FS);
        etTimeSynchronization = (EditText) rootView.findViewById(R.id.etTimeSynchronization_FS);
        spGender = (Spinner) rootView.findViewById(R.id.spGender_FS);
        cbNotification = (CheckBox) rootView.findViewById(R.id.cbNotification_FS);
    }

    @Override
    public void setupUI() {
        RxTextView.textChangeEvents(etWeight)
                .subscribe(t -> etWeight.setError(null));
        RxTextView.textChangeEvents(etHeight)
                .subscribe(t -> etHeight.setError(null));
        RxTextView.textChangeEvents(etAge)
                .subscribe(t -> etAge.setError(null));
        RxTextView.textChangeEvents(etTimeSynchronization)
                .subscribe(t -> etTimeSynchronization.setError(null));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_program_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save:
                getPresenter().onSaveClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setGender(List<String> _data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.spinner_item, _data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);
    }

    @Override
    public int getGender() {
       return spGender.getSelectedItemPosition();
    }

    @Override
    public int getHeight() {
        int value = 0;
        try {
            value = Integer.parseInt(etHeight.getText().toString());
        } catch (NumberFormatException e) {

        }

        return value;
    }

    @Override
    public int getWeight() {
        int value = 0;
        try {
            value = Integer.parseInt(etWeight.getText().toString());
        } catch (NumberFormatException e) {

        }
        return value;
    }

    @Override
    public int getTimeSynchronization() {
        int value = 0;
        try {
            value = Integer.parseInt(etTimeSynchronization.getText().toString());
        } catch (NumberFormatException e) {

        }
        return value;
    }

    @Override
    public int getAge() {
        int value = 0;
        try {
            value = Integer.parseInt(etAge.getText().toString());
        } catch (NumberFormatException e) {

        }

        return value;
    }

    @Override
    public void setGender(int _position) {
        spGender.setSelection(_position);
    }

    @Override
    public void setHeight(String _s) {
        etHeight.setText(_s);
    }

    @Override
    public void setWeight(String _s) {
        etWeight.setText(_s);
    }

    @Override
    public void setTimeSynchronization(String _s) {
        etTimeSynchronization.setText(_s);
    }

    @Override
    public void setAge(String _s) {
        etAge.setText(_s);
    }

    @Override
    public void showHeightError() {
        etHeight.setError("Please, enter your height");
    }

    @Override
    public void showAgeError() {
        etAge.setError("Please, enter your age");
    }

    @Override
    public void showWeightError() {
        etWeight.setError("Please, enter your weight");
    }

    @Override
    public void showTimeSynchronizationError() {
        etTimeSynchronization.setError("Please, enter time synchronization");
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void setNotificationState(boolean b) {
        cbNotification.setChecked(b);
    }

    @Override
    public boolean getNotificationState() {
        return cbNotification.isChecked();
    }

}
