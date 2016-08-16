package com.example.servicebestpractice;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class LongRunningService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		new Thread(new Runnable(){
			@Override
			public void run(){
				Log.d("LongRunningService", "executed at " + new Date().toString());
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		//int anHour = 60 * 60 * 1000;书上的时间是一小时，这里为了方便用10秒代替
		int anHour = 10 * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AlarmReceiver.class);//指定时间到达时，处理任务的广播接收器为AlarmReceiver
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//调用set方法完成设定
				
		return super.onStartCommand(intent, flags, startId);
	}

}
