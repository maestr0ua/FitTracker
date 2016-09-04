package com.task.webchallengetask.ui.dialogs;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.task.webchallengetask.R;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.base.BaseDialog;
import com.task.webchallengetask.ui.dialogs.presenters.ProfileDialogPresenter;

import java.util.List;

import rx.Observable;

public class ProfileDialog extends BaseDialog<ProfileDialogPresenter>
        implements ProfileDialogPresenter.ProfileDialogView {

    private TextView btnSave;
    private EditText etHeight;
    private EditText etWeight;
    private EditText etAge;
    private Spinner spGender;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_profile_layout;
    }

    @Override
    public ProfileDialogPresenter initPresenter() {
        return new ProfileDialogPresenter();
    }

    @Override
    public void setupUI() {
        setCancelable(false);
        RxUtils.click(btnSave, o -> getPresenter().onSaveClicked());

        RxTextView.textChangeEvents(etWeight)
                .subscribe(t -> etWeight.setError(null));
        RxTextView.textChangeEvents(etHeight)
                .subscribe(t -> etHeight.setError(null));
        RxTextView.textChangeEvents(etAge)
                .subscribe(t -> etAge.setError(null));
    }

    @Override
    public void findUI(View rootView) {
        btnSave = (TextView) rootView.findViewById(R.id.btnSave_DPL);
        etAge = (EditText) rootView.findViewById(R.id.etAge_DPL);
        etHeight = (EditText) rootView.findViewById(R.id.etHeight_DPL);
        etWeight = (EditText) rootView.findViewById(R.id.etWeight_DPL);
        spGender = (Spinner) rootView.findViewById(R.id.spGender_DPL);
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
        } catch (NumberFormatException ignored) {

        }

        return value;
    }

    @Override
    public int getWeight() {
        int value = 0;
        try {
            value = Integer.parseInt(etWeight.getText().toString());
        } catch (NumberFormatException ignored) {

        }
        return value;
    }

    @Override
    public int getAge() {
        int value = 0;
        try {
            value = Integer.parseInt(etAge.getText().toString());
        } catch (NumberFormatException ignored) {

        }

        return value;
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
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        hideKeyboard();
    }
}

