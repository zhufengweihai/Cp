package com.zf.lottery;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver messageReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableView tableView = (TableView) findViewById(R.id.tableView);
        String[] titles = {"类型", "号码", "当前", "最值"};
        SimpleTableHeaderAdapter headAdapter = new SimpleTableHeaderAdapter(this, titles);
        headAdapter.setTypeface(Typeface.NORMAL);
        headAdapter.setTextSize(15);
        headAdapter.setTextColor(Color.BLACK);
        tableView.setHeaderAdapter(headAdapter);
        tableView.setHeaderBackgroundColor(Color.GRAY);

        handleData(getIntent());
        createMessageReceiver();
    }

    private void createMessageReceiver() {
        messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("com.zf.lottery.MESSAGE".equals(intent.getAction())) {
                    handleData(intent);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMessageReceiver();
    }

    private void registerMessageReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("com.zf.lottery.MESSAGE");
        registerReceiver(messageReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(messageReceiver);
    }

    private void handleData(Intent intent) {
        cancelNotification();
        try {
            List<MaxStat> stats = toMaxStats(intent.getExtras());
            TableView tableView = (TableView) findViewById(R.id.tableView);
            tableView.setDataAdapter(new ResultAdapter(this, stats));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private List<MaxStat> toMaxStats(Bundle extras) throws JSONException {
        if (extras == null) {
            return new ArrayList<>();
        }
        String content = extras.getString(JPushInterface.EXTRA_EXTRA);
        if (content == null) {
            return new ArrayList<>();
        }
        JSONObject jsonObject = new JSONObject(content);
        String[] statStrings = jsonObject.getString(MaxStat.KEY_MAX_STAT).split(";");
        if (statStrings == null) {
            return new ArrayList<>();
        }
        List<MaxStat> stats = new ArrayList<>(statStrings.length);
        for (String statString : statStrings) {
            String[] data = statString.split(",");
            MaxStat maxStat = new MaxStat();
            maxStat.setType(StarType.values()[Integer.parseInt(data[0])]);
            maxStat.setNumber(Integer.parseInt(data[1]));
            maxStat.setAbsence(Integer.parseInt(data[2]));
            maxStat.setMaxAbsence(Integer.parseInt(data[3]));
            switch (maxStat.getType()) {
                case FirstThree:
                case LastThree:
                    maxStat.setProbability((float) Math.pow(0.999, maxStat.getAbsence()));
                    break;
                case CombTwo:
                    int num = maxStat.getNumber();
                    if (num % 10 != num / 10) {
                        maxStat.setProbability((float) Math.pow(1 - 1f / 45, maxStat.getAbsence()));
                        break;
                    }
                case FirstTwo:
                case LastTwo:
                    maxStat.setProbability((float) Math.pow(0.99, maxStat.getAbsence()));
                    break;
                case CombThree:

                    break;
            }
            stats.add(maxStat);
        }
        return stats;
    }

    private void cancelNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancelAll();
        }
    }
}