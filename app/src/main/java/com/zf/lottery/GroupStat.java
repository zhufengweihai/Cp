package com.zf.lottery;

import java.io.Serializable;

public class GroupStat implements Serializable {
	public static final String KEY_GROUP_STAT = "GroupStat";

	private StarType type = null;
	private int[] number = null;
	private int[] absences = null;

	public StarType getType() {
		return type;
	}

	public void setType(StarType type) {
		this.type = type;
	}

	public int[] getNumber() {
		return number;
	}

	public void setNumber(int... number) {
		this.number = number;
	}

	public int[] getAbsences() {
		return absences;
	}

	public void setAbsences(int... absences) {
		this.absences = absences;
	}

}
