package com.task.webchallengetask.ui.base;

import android.view.View;

public interface BaseView<P extends BasePresenter> {

    int getLayoutResource();

    P initPresenter();

    P getPresenter();

    void findUI(View rootView);

    void setupUI();

}
