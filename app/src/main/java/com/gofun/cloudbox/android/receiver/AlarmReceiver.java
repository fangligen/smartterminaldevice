package com.gofun.cloudbox.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.gofun.cloudbox.android.service.GoFunAlarmService;

/**
 * @author gaoqian
 * @date 2017/7/26
 */

public class AlarmReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    Intent i = new Intent(context, GoFunAlarmService.class);
    context.startService(i);
    AlarmObservable.getInstance().notifyObservers();
  }
}
