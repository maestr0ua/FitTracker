package com.task.webchallengetask.ui.dialogs;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.base.BaseDialog;
import com.task.webchallengetask.ui.dialogs.presenters.ConfirmDialogPresenter;

public class ConfirmDialog extends BaseDialog<ConfirmDialogPresenter>
        implements ConfirmDialogPresenter.ConfirmDialogView {

    protected TextView tvTitle;
    protected TextView tvMessage;
    protected TextView btnNegative;
    protected TextView btnPositive;

    private String mMessage;
    private String mTitle;
    private String mButtonPositiveTitle;
    private String mButtonNegativeTitle;
    private View.OnClickListener mPositiveListener;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_confirm_layout;
    }

    @Override
    public ConfirmDialogPresenter initPresenter() {
        return new ConfirmDialogPresenter();
    }

    @Override
    public void findUI(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle_DCL);
        tvMessage = (TextView) rootView.findViewById(R.id.tvMessage_DCL);
        btnNegative = (TextView) rootView.findViewById(R.id.btnNegative_DCL);
        btnPositive = (TextView) rootView.findViewById(R.id.btnPositive_DCL);
    }

    @Override
    public void setupUI() {
        setCancelable(false);

        RxUtils.click(btnNegative).subscribe(o -> dismiss());
        RxUtils.click(btnPositive).subscribe(o -> {
            getPresenter().onPositiveClicked();
            if (mPositiveListener != null) mPositiveListener.onClick(null);
        });

        RxUtils.click(btnNegative).subscribe(o -> {
            getPresenter().onNegativeClicked();
        });

        if (!TextUtils.isEmpty(mMessage)) tvMessage.setText(mMessage);
        if (!TextUtils.isEmpty(mTitle)) tvTitle.setText(mTitle);
        if (!TextUtils.isEmpty(mButtonPositiveTitle)) btnPositive.setText(mTitle);
        if (!TextUtils.isEmpty(mButtonNegativeTitle)) btnNegative.setText(mTitle);
    }

    @Override
    public void setTitle(String _title) {
        mTitle = _title;
    }

    @Override
    public void setMessage(String _message) {
        mMessage = _message;
    }

    @Override
    public void setOnClickListener(View.OnClickListener _listener) {
        mPositiveListener = _listener;

    }
}
