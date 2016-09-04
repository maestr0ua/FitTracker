package com.task.webchallengetask.ui.base;

import android.os.Bundle;
import android.view.View;


public interface BaseFragmentView<P extends BaseFragmentPresenter> extends BaseView<P> {

    void showInfoDialog(String _title, String _message, View.OnClickListener _listener);
    void showErrorDialog(String _title, String _message, View.OnClickListener _listener);
    void showConfirmDialog(String _title, String _message, View.OnClickListener _listener);

    void showLoadingDialog();
    void hideLoadingDialog();

    void switchFragment(final BaseFragment _fragment, boolean _addToBackStack);

    void onBackPressed();
    boolean isBackStackEmpty();
    void popBackStack();


    void startActivity(Class _activityClass, Bundle _bundle);
    void startActivity(Class _activityClass, int... _flag);
    void startActivityForResult(Class _activityClass, int _requestCode);

    void finishActivity();
    Bundle getFragmentArguments();


}
