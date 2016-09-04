package com.task.webchallengetask.ui.dialogs.presenters;

import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.Program;
import com.task.webchallengetask.global.programs.ProgramManager;
import com.task.webchallengetask.global.programs.difficults.Difficult;
import com.task.webchallengetask.global.programs.difficults.DifficultCustom;
import com.task.webchallengetask.ui.base.BaseDialogView;

import java.util.List;


public class AddProgramDialogPresenter extends BaseDialogPresenter<AddProgramDialogPresenter.AddProgramView> {

    private List<Program> programs;

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        programs = ProgramManager.getPrograms();
        getView().setPrograms(programs);
    }

    public void onProgramChose(int _position) {
        String description = "";
        switch (_position) {
            case 0:
                description = App.getAppContext().getString(R.string.distance_description);
                break;
            case 1:
                description = App.getAppContext().getString(R.string.activity_description);
                break;
        }
        getView().setDescription(description);
        getView().setDifficultEnabled(true);
        getView().setTarget(programs.get(_position).getDifficult().get(0).getTarget());
        getView().setUnit(programs.get(_position).getDifficult().get(0).getUnit());
        getView().setDifficult(programs.get(_position).getDifficult());
    }

    public void onDifficultChoosed(int _position) {
        if (programs.get(getView().getProgram()).getDifficult().get(_position) instanceof DifficultCustom) {
            getView().setTargetEditable(true);
        } else {
            getView().setTarget(programs.get(getView().getProgram()).getDifficult().get(_position).getTarget());
            getView().setTargetEditable(false);
        }

    }

    public void onSaveClicked() {
        getView().hideKeyboard();
        if (validate()) {
            Program program = programs.get(getView().getProgram());
            Difficult difficult = program.getDifficult().get(getView().getDifficult());

            ProgramTable programTable = new ProgramTable();
            programTable.name = program.getName();
            programTable.difficult = difficult.getName();
            if (program.getType() == Constants.PROGRAM_TYPES.ACTIVE_LIFE) {
                float targetInMinute = Float.valueOf(getView().getTarget());
                programTable.target = targetInMinute * 60;
            } else {
                programTable.target = Float.valueOf(getView().getTarget());
            }
            programTable.unit = getView().getUnit();
            programTable.save();
            getView().dismissDialog();

        }
    }

    private boolean validate() {
        Difficult difficult = programs.get(getView().getProgram()).getDifficult().
                get(getView().getDifficult());

        if (difficult instanceof DifficultCustom) {
            try {
                boolean isValid = Integer.valueOf(getView().getTarget()) > 0;
                if (!isValid) getView().setTargetError("Enter some value");
                return isValid;
            } catch (NumberFormatException e) {
                getView().setTargetError("Enter some value");
                return false;
            }
        } else return true;
    }

    public interface AddProgramView extends BaseDialogView<AddProgramDialogPresenter> {
        void setPrograms(List<Program> data);
        int getProgram();
        void setDifficult(List<Difficult> data);
        int getDifficult();
        void setDifficultEnabled(boolean _isEnabled);
        void setUnit(String _text);
        String getUnit();
        void setDescription(String _text);
        void setTarget(float _text);
        void setTargetError(String _text);
        String getTarget();
        void setTargetEditable(boolean isEditable);
    }
}
