package com.inforun.safecitypolice.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WtService extends Service{

	@Override
	public void onCreate() {
		System.out.println("wt onCreate方法数据");
		Intent in = new Intent("CheckLocationStreamReceiver");
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, in, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				30 * 1000, pi);
//		startForeground(10, null);
		super.onCreate();
	}
	@Override
	public IBinder onBind(Intent arg0) {
		System.out.println("wt onBind方法数据");
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent in = new Intent("CheckLocationStreamReceiver");
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, in, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				30 * 1000, pi);
		System.out.println("wt onStartCommand方法数据");
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		//register WtBroadcast Receiver in static ,and addCategory some information ,and so on sendBroadcast 
		stopForeground(true);
		Intent intent = new Intent("com.inforun.safecitypolice.utils.WtBroadcastReceiver");
		intent.addCategory("com.inforun.safecitypolice.utils.WtBroadcastReceiver");
		sendBroadcast(intent);
		System.out.println("wt onDestroy方法数据");
		super.onDestroy();
	}
}
