package com.zf.lottery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhufeng7 on 2017-9-8.
 */

public class StatHandler {
    private static final String KEY_STAT = "STAT";

    private String content = null;
    private List<Object> stats = new ArrayList<>();
    //private List<GroupStat> groupStats = new ArrayList<>();

    public StatHandler(String content) {
        this.content = content;
    }

    public List<Object> getStats() {
        return stats;
    }

    public void hanlder() throws JSONException {
        if (content == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject(content);
        String[] statStrings = jsonObject.getString(KEY_STAT).split(":");
        String maxStatString = statStrings[0];
        String groupStatString = statStrings[1];
        handlegroupStat(maxStatString);
        handleGroupStat(groupStatString);
    }

    private void handlegroupStat(String groupStatString) {
        String[] statStrings = groupStatString.split(";");
        for (String statString : statStrings) {
            String[] data = statString.split(",");
            MaxStat maxStat = new MaxStat();
            maxStat.setType(StarType.values()[Integer.parseInt(data[0])]);
            maxStat.setNumber(Integer.parseInt(data[1]));
            maxStat.setAbsence(Integer.parseInt(data[2]));
            maxStat.setMaxAbsence(Integer.parseInt(data[3]));
            switch (maxStat.getType()) {
                case CombTwo:
                    int num = maxStat.getNumber();
                    maxStat.setProbability((float) Math.pow(0.98, maxStat.getAbsence()));
                    break;
                case FirstTwo:
                case LastTwo:
                    maxStat.setProbability((float) Math.pow(0.99, maxStat.getAbsence()));
                    break;
            }
            stats.add(maxStat);
        }
    }

    private void handleGroupStat(String groupStatString) {
        String[] statStrings = groupStatString.split(";");
        for (String statString : statStrings) {
            String[] data = statString.split(",");
            GroupStat groupStat = new GroupStat();
            groupStat.setType(StarType.values()[Integer.parseInt(data[0])]);
            int[] nums = stringArrayToIntArray(data[1]);
            groupStat.setNumber(nums);
            groupStat.setAbsences(stringArrayToIntArray(data[2]));
            stats.add(groupStat);
        }
    }

    private int[] stringArrayToIntArray(String numContent) {
        String[] numStrings = numContent.split("-");
        int[] nums = new int[numStrings.length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = Integer.parseInt(numStrings[i]);
        }
        return nums;
    }
}
