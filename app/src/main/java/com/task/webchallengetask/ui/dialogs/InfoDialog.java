package com.task.webchallengetask.ui.dialogs;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.task.webchallengetask.R;
import com.task.webchallengetask.global.utils.RxUtils;
import com.task.webchallengetask.ui.base.BaseDialog;
import com.task.webchallengetask.ui.dialogs.presenters.InfoDialogPresenter;

public class InfoDialog extends BaseDialog<InfoDialogPresenter>
        implements InfoDialogPresenter.InfoDialogView {

    protected TextView tvTitle;
    protected TextView tvMessage;
    protected TextView btnClose;

    private String mMessage;
    private String mTitle;
    private String mButtonTitle;
    private View.OnClickListener mListener;

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_info_layout;
    }

    @Override
    public InfoDialogPresenter initPresenter() {
        return new InfoDialogPresenter();
    }

    @Override
    public void findUI(View rootView) {
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle_DIL);
        tvMessage = (TextView) rootView.findViewById(R.id.tvMessage_DIL);
        btnClose = (TextView) rootView.findViewById(R.id.btnNegative_DIL);
    }

    @Override
    public void setupUI() {
        RxUtils.click(btnClose).subscribe(o -> onClick());

        if (!TextUtils.isEmpty(mMessage)) tvMessage.setText(mMessage);
        if (!TextUtils.isEmpty(mTitle)) tvTitle.setText(mTitle);
        if (!TextUtils.isEmpty(mButtonTitle)) btnClose.setText(mButtonTitle);
    }

    private void onClick() {
        dismiss();
        if (mListener != null) mListener.onClick(null);
    }

    @Override
    public void setTitle(String _title) {
        mTitle = _title;
    }

    @Override
    public void setMessage(String _text) {
        mMessage = _text;
    }

    @Override
    public void setOnClickListener(View.OnClickListener _listener) {
        mListener = _listener;
    }
}
