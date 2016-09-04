package com.task.webchallengetask.ui.modules.program.presenters;

import com.task.webchallengetask.data.data_providers.ProgramDataProvider;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.global.utils.Logger;
import com.task.webchallengetask.ui.base.BaseFragmentPresenter;
import com.task.webchallengetask.ui.base.BaseFragmentView;
import com.task.webchallengetask.ui.dialogs.DialogListener;
import com.task.webchallengetask.ui.modules.program.ProgramDetailFragment;

import java.util.ArrayList;
import java.util.List;


public class ProgramsListPresenter extends BaseFragmentPresenter<ProgramsListPresenter.ProgramListView> {

    private List<ProgramTable> programs = new ArrayList<>();

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        getPrograms();
    }

    public void getPrograms() {
        ProgramDataProvider.getInstance().getPrograms()
                .doOnNext(this::showPrograms)
                .exists(t -> t != null && t.size() == 0)
                .doOnNext(_boolean -> {
                    if (_boolean) getView().showHolder();
                    else getView().hideHolder();
                })
                .subscribe();
    }

    public void showPrograms(List<ProgramTable> _data) {
        programs.clear();
        programs.addAll(_data);
        getView().showPrograms(_data);
    }

    public void onAddProgramClicked() {
        getView().showAddProgramDialog(this::getPrograms);
    }

    public void onProgramClicked(int position) {
        getView().switchFragment(ProgramDetailFragment.newInstance(programs.get(position).getId()), true);
    }

    public interface ProgramListView extends BaseFragmentView<ProgramsListPresenter> {
        void showAddProgramDialog(DialogListener _listener);
        void showPrograms(List<ProgramTable> _data);
        void showHolder();
        void hideHolder();
    }
}
