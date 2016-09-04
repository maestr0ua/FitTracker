package com.task.webchallengetask.ui.modules.settings;

import android.text.TextUtils;

import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.ui.base.BaseFragmentPresenter;
import com.task.webchallengetask.ui.base.BaseFragmentView;

import java.util.Arrays;
import java.util.List;

public class SettingsPresenter extends BaseFragmentPresenter<SettingsPresenter.SettingsView> {

    private List<String> genderTypes;

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        genderTypes = Arrays.asList(App.getAppContext().getResources().getStringArray(R.array.gender_list));
        getView().setGender(genderTypes);
        int position = TextUtils.equals(SharedPrefManager.getInstance().retrieveGender(), genderTypes.get(0)) ? 0 : 1;
        getView().setGender(position);

        getView().setAge(String.valueOf(SharedPrefManager.getInstance().retrieveAge()));
        getView().setHeight(String.valueOf(SharedPrefManager.getInstance().retrieveHeight()));
        getView().setWeight(String.valueOf(SharedPrefManager.getInstance().retrieveWeight()));
        getView().setTimeSynchronization(String.valueOf(SharedPrefManager.getInstance().retrieveTimeSynchronization()));
        getView().setNotificationState(SharedPrefManager.getInstance().retrieveNotificationState());
    }

    public void onSaveClicked() {
        getView().hideKeyboard();
        if (validateForm()) {
            SharedPrefManager.getInstance().storeHeight(getView().getHeight());
            SharedPrefManager.getInstance().storeWeight(getView().getWeight());
            SharedPrefManager.getInstance().storeGender(genderTypes.get(getView().getGender()));
            SharedPrefManager.getInstance().storeAge(getView().getAge());
            SharedPrefManager.getInstance().storeTimeSynchronization(getView().getTimeSynchronization());
            SharedPrefManager.getInstance().storeNotificationState(getView().getNotificationState());
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        if (getView().getHeight() == 0) {
            getView().showHeightError();
            valid = false;
        }
        if (getView().getWeight() == 0) {
            getView().showWeightError();
            valid = false;
        }
        if (getView().getAge() == 0) {
            getView().showAgeError();
            valid = false;
        }
        if (getView().getTimeSynchronization() == 0) {
            getView().showTimeSynchronizationError();
            valid = false;
        }
        return valid;
    }

    public interface SettingsView extends BaseFragmentView<SettingsPresenter> {
        void setGender(List<String> _data);

        int getGender();

        int getHeight();

        int getWeight();

        int getTimeSynchronization();

        int getAge();

        void setGender(int _position);

        void setHeight(String _s);

        void setWeight(String _s);

        void setTimeSynchronization(String _s);

        void setAge(String _s);

        void showHeightError();

        void showAgeError();

        void showWeightError();

        void showTimeSynchronizationError();

        void hideKeyboard();

        void setNotificationState(boolean b);

        boolean getNotificationState();
    }

}
