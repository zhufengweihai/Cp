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
                //startMainActivity(context, intent);
                Intent i = new Intent();
                i.setAction("com.zf.lottery.MESSAGE");
                i.putExtras(intent.getExtras());
                context.sendBroadcast(i);
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            startMainActivity(context, intent);
        }
    }

    private void startMainActivity(Context context, Intent intent) {
        Intent i = new Intent(context,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(intent.getExtras());
        context.startActivity(i);
    }
}