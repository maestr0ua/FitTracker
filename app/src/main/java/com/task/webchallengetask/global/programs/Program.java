package com.task.webchallengetask.global.programs;

import com.task.webchallengetask.global.Constants;
import com.task.webchallengetask.global.programs.difficults.Difficult;

import java.util.List;


public class Program {

    protected String name;
    protected String description;
    protected List<Difficult> difficult;
    protected Constants.PROGRAM_TYPES type;

    public Program(String name, String description, List<Difficult> difficult, Constants.PROGRAM_TYPES type) {
        this.name = name;
        this.description = description;
        this.difficult = difficult;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Difficult> getDifficult() {
        return difficult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Constants.PROGRAM_TYPES getType() {
        return type;
    }
}
