package com.zf.lottery;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;

import cn.jpush.android.api.JPushInterface;

public class CpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Activity topActivity = ActivityUtils.getTopActivity();
            if (topActivity == null) {
                return;
            }
            String name = topActivity.getLocalClassName();
            if (name.equals("MainActivity")) {
                startMainActivity(context, intent);
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            startMainActivity(context, intent);
        }
    }

    private void startMainActivity(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }
}