package com.task.webchallengetask.global.programs.difficults;


public class Difficult {

    String name;
    float target;
    String unit;

    public Difficult(String name, float target, String unit) {
        this.name = name;
        this.target = target;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public String getUnit() {
        return unit;
    }

}
