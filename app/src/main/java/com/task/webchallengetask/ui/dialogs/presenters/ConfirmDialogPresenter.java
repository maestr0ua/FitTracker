package com.task.webchallengetask.ui.dialogs.presenters;

import com.task.webchallengetask.ui.base.BaseDialogView;


public class ConfirmDialogPresenter extends BaseDialogPresenter<ConfirmDialogPresenter.ConfirmDialogView>{

    public void onPositiveClicked() {
        getView().dismissDialog();
    }

    public void onNegativeClicked() {
        getView().dismissDialog();
    }

    public interface ConfirmDialogView extends BaseDialogView<ConfirmDialogPresenter> {

    }
}
