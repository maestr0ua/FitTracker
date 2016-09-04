package com.task.webchallengetask.ui.dialogs;

import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.global.programs.difficults.Difficult;
import com.task.webchallengetask.global.programs.Program;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.modules.program.adapters.DifficultAdapter;
import com.task.webchallengetask.ui.modules.program.adapters.ProgramAdapter;
import com.task.webchallengetask.ui.base.BaseDialog;
import com.task.webchallengetask.ui.dialogs.presenters.AddProgramDialogPresenter;

import java.util.List;


public class AddProgramDialog extends BaseDialog<AddProgramDialogPresenter>
        implements AddProgramDialogPresenter.AddProgramView {

    private Spinner spProgram;
    private Spinner spDifficult;
    private TextView tvDescription;
    private TextView tvUnit;
    private EditText etTarget;
    private TextView btnSave;

    private DialogListener mListener;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_add_program;
    }

    @Override
    public AddProgramDialogPresenter initPresenter() {
        return new AddProgramDialogPresenter();
    }

    @Override
    public void findUI(View rootView) {
        spProgram = (Spinner) rootView.findViewById(R.id.spProgram_DAP);
        spDifficult = (Spinner) rootView.findViewById(R.id.spDifficult_DAP);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription_DAP);
        etTarget = (EditText) rootView.findViewById(R.id.tvTarget_DAP);
        tvUnit = (TextView) rootView.findViewById(R.id.tvUnit_DAP);
        btnSave = (TextView) rootView.findViewById(R.id.btnSave_DAP);
    }

    @Override
    public void setupUI() {
        RxUtils.click(btnSave, o -> getPresenter().onSaveClicked());
        spProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().onProgramChose(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDifficult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPresenter().onDifficultChoosed(position);
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

            }
        });
    }

    @Override
    public void setPrograms(List<Program> _data) {
        ProgramAdapter adapter = new ProgramAdapter(getContext(), android.R.layout.simple_spinner_item, _data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProgram.setAdapter(adapter);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mListener != null) mListener.onDismiss();
    }

    @Override
    public int getProgram() {
        return spProgram.getSelectedItemPosition();
    }

    @Override
    public void setDifficult(List<Difficult> _data) {
        DifficultAdapter adapter = new DifficultAdapter(this.getContext(), android.R.layout.simple_spinner_item, _data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDifficult.setAdapter(adapter);
    }

    @Override
    public int getDifficult() {
        return spDifficult.getSelectedItemPosition();

    }

    @Override
    public void setDifficultEnabled(boolean _isEnabled) {
        spDifficult.setEnabled(_isEnabled);
    }

    @Override
    public void setUnit(String _text) {
        tvUnit.setText(_text);
    }

    @Override
    public String getUnit() {
        return tvUnit.getText().toString();
    }

    @Override
    public void setDescription(String _text) {
        tvDescription.setText(_text);
    }

    @Override
    public void setTarget(float _text) {
        etTarget.setText(String.format("%.0f", _text));
    }

    @Override
    public void setTargetError(String _text) {
        etTarget.setError(_text);
    }

    @Override
    public String getTarget() {
        return etTarget.getText().toString();
    }

    @Override
    public void setTargetEditable(boolean isEditable) {
        etTarget.setEnabled(isEditable);
    }

    public void setOnDismissListener(DialogListener _listener) {
        mListener = _listener;
    }

}
