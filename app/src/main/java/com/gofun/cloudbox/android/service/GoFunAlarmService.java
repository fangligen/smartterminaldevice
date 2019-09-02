package com.gofun.cloudbox.android.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import com.gofun.cloudbox.android.receiver.AlarmReceiver;

/**
 * @author gaoqian
 * @date 2017/7/26
 */

public class GoFunAlarmService extends Service {

  private static final int INTERVAL = 1000 * 6 * 0;

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
    long triggerAtTime = SystemClock.elapsedRealtime() + INTERVAL;
    Intent i = new Intent(this, AlarmReceiver.class);
    PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
    manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
    return super.onStartCommand(intent, flags, startId);
  }

  @Nullable @Override public IBinder onBind(Intent intent) {
    return null;
  }
}
