package com.task.webchallengetask.ui.modules.check;

import android.content.Intent;
import android.text.TextUtils;

import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.ui.base.BaseActivityPresenter;
import com.task.webchallengetask.ui.base.BaseActivityView;
import com.task.webchallengetask.ui.modules.login.LoginActivity;
import com.task.webchallengetask.ui.modules.main.MainActivity;


public class CheckPresenter extends BaseActivityPresenter<CheckPresenter.CheckView> {

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        if (!TextUtils.isEmpty(SharedPrefManager.getInstance().retrieveUsername())
                && !TextUtils.isEmpty(SharedPrefManager.getInstance().retrieveActiveSocial())) {
            getView().startActivity(MainActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            getView().startActivity(LoginActivity.class, Intent.FLAG_ACTIVITY_NO_ANIMATION, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        getView().finishActivity();
    }

    public interface CheckView extends BaseActivityView<CheckPresenter> {
    }
}
