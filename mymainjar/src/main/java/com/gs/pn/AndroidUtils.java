package com.gs.pn;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AndroidUtils {
    public static void OnRestart(Activity context,String message) {
        Log.e("onrestart message",message);
        Intent mStartActivity = new Intent(context, context.getClass());
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 10, mPendingIntent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
