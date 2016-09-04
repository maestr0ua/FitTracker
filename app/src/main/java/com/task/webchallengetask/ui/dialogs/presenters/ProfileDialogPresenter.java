package com.task.webchallengetask.ui.dialogs.presenters;

import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.data_managers.SharedPrefManager;
import com.task.webchallengetask.ui.base.BaseDialogView;

import java.util.Arrays;
import java.util.List;

public class ProfileDialogPresenter extends BaseDialogPresenter<ProfileDialogPresenter.ProfileDialogView> {

    private List<String> genderTypes;

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        genderTypes = Arrays.asList(App.getAppContext().getResources().getStringArray(R.array.gender_list));
        getView().setGender(genderTypes);
    }

    public void onSaveClicked() {
        getView().hideKeyboard();
        if (validateForm()) {
            SharedPrefManager.getInstance().storeHeight(getView().getHeight());
            SharedPrefManager.getInstance().storeWeight(getView().getWeight());
            SharedPrefManager.getInstance().storeGender(genderTypes.get(getView().getGender()));
            SharedPrefManager.getInstance().storeAge(getView().getAge());
            getView().dismissDialog();
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
        if (getView().getAge() == 0){
            getView().showAgeError();
            valid = false;
        }
        return valid;
    }

    public interface ProfileDialogView extends BaseDialogView<ProfileDialogPresenter> {
        void setGender(List<String> _data);
        int getGender();
        int getHeight();
        int getWeight();
        int getAge();
        void showHeightError();
        void showAgeError();
        void showWeightError();
        void hideKeyboard();
    }



}
