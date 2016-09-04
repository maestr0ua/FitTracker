package com.task.webchallengetask.ui.dialogs;


import com.task.webchallengetask.R;

public class ErrorDialog extends InfoDialog {

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_error_layout;
    }

    @Override
    public void setupUI() {
        super.setupUI();
        if (tvTitle.getText().toString().isEmpty()) {
            tvTitle.setText(getString(R.string.error));
        }
    }
}
