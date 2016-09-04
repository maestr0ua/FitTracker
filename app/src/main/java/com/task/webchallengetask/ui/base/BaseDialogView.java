package com.task.webchallengetask.ui.base;

import com.task.webchallengetask.ui.dialogs.presenters.BaseDialogPresenter;


public interface BaseDialogView<P extends BaseDialogPresenter> extends BaseView<P> {

    void dismissDialog();
    void hideKeyboard();
}
