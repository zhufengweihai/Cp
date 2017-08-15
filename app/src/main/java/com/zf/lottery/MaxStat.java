package com.zf.lottery;

import java.io.Serializable;

public class MaxStat implements Serializable {
    public static final String KEY_MAX_STAT = "MaxStat";

    private StarType type = null;
    private int number = -1;
    private int absence = -1;
    private int maxAbsence = -1;
    private float probability = -1f;

    public MaxStat() {
    }

    public StarType getType() {
        return type;
    }

    public void setType(StarType type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public int getMaxAbsence() {
        return maxAbsence;
    }

    public void setMaxAbsence(int maxAbsence) {
        this.maxAbsence = maxAbsence;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }
}
