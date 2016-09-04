package com.task.webchallengetask.ui.base;

import android.content.Intent;

public interface BasePresenter<V extends BaseView> {

    void bindView(V _view);

    void unbindView();

    V getView();

    void onViewCreated();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onPause();

    void onResume();

    void onStart();

    void onStop();

    void onDestroyView();

    void onBackPressed();
}
