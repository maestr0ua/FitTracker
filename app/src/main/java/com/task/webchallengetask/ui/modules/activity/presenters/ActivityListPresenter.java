package com.task.webchallengetask.ui.modules.activity.presenters;

import android.content.Intent;

import com.task.webchallengetask.data.data_providers.ActivityDataProvider;
import com.task.webchallengetask.data.database.tables.ActionParametersTable;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.utils.Logger;
import com.task.webchallengetask.global.utils.TimeUtil;
import com.task.webchallengetask.ui.base.BaseFragmentPresenter;
import com.task.webchallengetask.ui.base.BaseFragmentView;
import com.task.webchallengetask.ui.modules.activity.views.ActivityDetailFragment;
import com.task.webchallengetask.ui.modules.activity.views.ActivityStartActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityListPresenter extends BaseFragmentPresenter<ActivityListPresenter.ActivityListView> {

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        ActivityDataProvider.getInstance()
                .getActivities()
                .doOnNext(this::fillList)
                .exists(t -> t != null && t.size() == 0)
                .doOnNext(_boolean -> {
                    if (_boolean) getView().showHolder();
                    else getView().hideHolder();
                })
                .subscribe(t1 -> {}, Logger::e);

    }

    private void fillList(List<ActionParametersTable> _modelList) {
        if (!_modelList.isEmpty()) {

            long previousDay = _modelList.get(0).getDate();
            ActionParametersTable previousModel = _modelList.get(0);
            List<ActionParametersTable> currentList = new ArrayList<>();
            currentList.add(previousModel);

            for (int i = 1; i < _modelList.size(); i++) {
                long currentDay = _modelList.get(i).getDate();
                if (TimeUtil.compareDay(previousDay, currentDay) != 0) {
                    getView().addActivities(currentList);
                    currentList.clear();
                }
                currentList.add(_modelList.get(i));

                previousDay = currentDay;
            }

            getView().addActivities(currentList);
        }
    }

    public void onFABClicked() {
        getView().startActivityForResult(ActivityStartActivity.class, Constants.RC_ACTIVITY_START_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onActivityClicked(ActionParametersTable _model) {
        getView().switchFragment(ActivityDetailFragment.newInstance(_model.id), true);
    }

    public interface ActivityListView extends BaseFragmentView<ActivityListPresenter> {
        void addActivities(List<ActionParametersTable> _data);

        void showHolder();

        void hideHolder();
    }

}
