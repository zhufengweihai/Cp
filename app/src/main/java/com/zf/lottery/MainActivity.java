package com.zf.lottery;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
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
    private TableView tableView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableView = (TableView) findViewById(R.id.tableView);
        String[] titles = {"类型", "号码", "当前", "最值"};
        SimpleTableHeaderAdapter headAdapter = new SimpleTableHeaderAdapter(this, titles);
        headAdapter.setTypeface(Typeface.NORMAL);
        headAdapter.setTextSize(15);
        headAdapter.setTextColor(Color.BLACK);
        tableView.setHeaderAdapter(headAdapter);
        tableView.setHeaderBackgroundColor(Color.GRAY);

        handleData(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleData(intent);
    }

    private void handleData(Intent intent) {
        cancelNotification();
        try {
            List<MaxStat> maxStats = toMaxStats(intent.getExtras());
            tableView.setDataAdapter(new ResultAdapter(this, maxStats));
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
            maxStat.setType(Integer.parseInt(data[0]));
            maxStat.setNumber(Integer.parseInt(data[1]));
            maxStat.setAbsence(Integer.parseInt(data[2]));
            maxStat.setMaxAbsence(Integer.parseInt(data[3]));
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