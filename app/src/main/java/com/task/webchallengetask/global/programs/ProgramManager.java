package com.task.webchallengetask.global.programs;

import com.task.webchallengetask.App;
import com.task.webchallengetask.R;
import com.task.webchallengetask.data.database.tables.ProgramTable;
import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.difficults.Difficult;
import com.task.webchallengetask.global.programs.difficults.DifficultActiveBeginner;
import com.task.webchallengetask.global.programs.difficults.DifficultActiveCustom;
import com.task.webchallengetask.global.programs.difficults.DifficultActiveMedium;
import com.task.webchallengetask.global.programs.difficults.DifficultActiveProfessional;
import com.task.webchallengetask.global.programs.difficults.DifficultRunnerBeginner;
import com.task.webchallengetask.global.programs.difficults.DifficultRunnerCustom;
import com.task.webchallengetask.global.programs.difficults.DifficultRunnerMedium;
import com.task.webchallengetask.global.programs.difficults.DifficultRunnerProfessional;

import java.util.ArrayList;
import java.util.List;


public class ProgramManager {

    private static Program createLongDistance() {
        String description = App.getAppContext().getString(R.string.distance_description);

        List<Difficult> programList = new ArrayList<>();
        programList.add(new DifficultRunnerBeginner());
        programList.add(new DifficultRunnerMedium());
        programList.add(new DifficultRunnerProfessional());
        programList.add(new DifficultRunnerCustom());

        return new Program("Long distance runner", description, programList, Constants.PROGRAM_TYPES.LONG_DISTANCE);
    }

    private static Program createActiveLifeDistance() {
        String description = App.getAppContext().getString(R.string.activity_description);

        List<Difficult> programList = new ArrayList<>();
        programList.add(new DifficultActiveBeginner());
        programList.add(new DifficultActiveMedium());
        programList.add(new DifficultActiveProfessional());
        programList.add(new DifficultActiveCustom());

        return new Program("Active lifestyle", description, programList, Constants.PROGRAM_TYPES.ACTIVE_LIFE);
    }

    public static List<Program> getPrograms() {
        List<Program> programs = new ArrayList<>();
        programs.add(ProgramManager.createActiveLifeDistance());
        programs.add(ProgramManager.createLongDistance());
        return programs;
    }

    public static Constants.PROGRAM_TYPES defineProgramType(ProgramTable table) {
        for (Program program : ProgramManager.getPrograms()) {
            if (program.getName().equals(table.getName())) {
                return program.getType();
            }
        }
        return null;
    }


}
