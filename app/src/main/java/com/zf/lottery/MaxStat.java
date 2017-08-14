package com.zf.lottery;

import java.io.Serializable;

public class MaxStat implements Serializable{
    public static final String KEY_MAX_STAT = "MaxStat";

    private int type = -1;
    private int number = -1;
    private int absence = -1;
    private int maxAbsence = -1;

    public MaxStat() {
    }

    public MaxStat(int type, int number, int absence, int maxAbsence) {
        super();
        this.type = type;
        this.number = number;
        this.absence = absence;
        this.maxAbsence = maxAbsence;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

}
