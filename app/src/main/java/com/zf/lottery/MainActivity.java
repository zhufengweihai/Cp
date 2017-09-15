package com.zf.lottery;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver messageReceiver;
    private SoundPool soundPool;
    private int soundId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        soundId = soundPool.load(this, R.raw.sound, 1);

        TableView maxStatView = (TableView) findViewById(R.id.maxStatView);
        String[] titles = {"类型", "号码", "当前", "最值", "概率"};
        SimpleTableHeaderAdapter headAdapter = new SimpleTableHeaderAdapter(this, titles);
        headAdapter.setTypeface(Typeface.NORMAL);
        headAdapter.setTextSize(15);
        headAdapter.setTextColor(Color.BLACK);
        maxStatView.setHeaderAdapter(headAdapter);
        maxStatView.setHeaderBackgroundColor(Color.GRAY);

        handleData(getIntent());
        createMessageReceiver();
        registerMessageReceiver();
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

    private void registerMessageReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction("com.zf.lottery.MESSAGE");
        registerReceiver(messageReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }

    private void handleData(Intent intent) {
        cancelNotification();
        try {
            List<Object> stats = toStats(intent.getExtras());
            TableView tableView = (TableView) findViewById(R.id.maxStatView);
            tableView.setDataAdapter(new ResultAdapter(this, stats));
            if (stats.size() > 0) {
                soundPool.play(soundId, 1, 1, 0, 3, 1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private List<Object> toStats(Bundle extras) throws JSONException {
        if (extras == null) {
            return new ArrayList<>();
        }
        String content = extras.getString(JPushInterface.EXTRA_EXTRA);
        StatHandler handler = new StatHandler(content);
        handler.handle();
        return handler.getStats();
    }

    private void cancelNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancelAll();
        }
    }
}